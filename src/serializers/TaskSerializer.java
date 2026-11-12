package serializers;

import entities.Task;
import utils.Status;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class TaskSerializer {

    private static String escape(String string) {
        char c;

        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i < string.length(); i++) {

            c = string.charAt(i);

            String escaped = switch(c) {
                case '"' -> "\\\"";

                case '\\' -> "\\\\";

                case '\n' -> "\\n";

                case '\t' -> "\\t";

                default -> String.valueOf(c);
            };

            stringBuilder.append(escaped);
        }

        return stringBuilder.toString();
    }

    private static String unescape(String string) {
        char c;

        StringBuilder unescaped = new StringBuilder();

        for(int i = 0; i < string.length(); i++) {
            c = string.charAt(i);

            if(c != '\\') {
                unescaped.append(c);
                continue;
            }

            if (++i >= string.length()) {
                throw new IllegalArgumentException("Invalid escape sequence");
            }

            char escaped = string.charAt(i);

            switch(escaped) {
                case '"' -> unescaped.append('"');
                case '\\' -> unescaped.append('\\');
                case 'n' -> unescaped.append('\n');
                case 't' -> unescaped.append('\t');
                default -> throw new IllegalArgumentException("Invalid escape sequence: \\" + escaped);
            }
        }
        return unescaped.toString();
    }


    public static String serialize(Task task) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\t{\n");
        stringBuilder.append("\t\t\"id\": ").append(task.getId()).append(",\n");
        stringBuilder.append("\t\t\"description\": ").append("\"" + escape(task.getDescription()) + "\"").append(",\n");
        stringBuilder.append("\t\t\"status\": ").append("\"" + task.getStatus() + "\"").append(",\n");
        stringBuilder.append("\t\t\"createdAt\": ").append("\"" + task.getCreatedAt() + "\"").append(",\n");
        stringBuilder.append("\t\t\"updatedAt\": ").append("\"" + task.getUpdatedAt() + "\"").append("\n");
        stringBuilder.append("\t}");

        return stringBuilder.toString();
    }

    public static String serializeAll(List<Task> tasks) {
        if(tasks == null || tasks.isEmpty()) {
            return "[]";
        }

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("[\n");

        for(Task task : tasks) {
            stringBuilder.append(TaskSerializer.serialize(task));
            if (tasks.getLast().getId() != task.getId()) {
                stringBuilder.append(",\n");
            }
        }

        stringBuilder.append("\n]");
        return stringBuilder.toString();
    }


    public static Task deserialize(String serializedTask){

        int backSlash = 0;
        boolean insideString = false, insideValue = false;

        StringBuilder stringBuilder = new StringBuilder();

        List<String> properties = new ArrayList<>();

        for(int i = 0; i < serializedTask.length(); i++) {
            char c = serializedTask.charAt(i);

            switch (c){
                case '\\' -> backSlash++;

                case '"' -> {
                    if(backSlash % 2 == 0){
                        insideString = !insideString;
                    }
                    backSlash = 0;
                }

                case ':' -> {
                    if(!insideString){
                        insideValue = true;
                        continue;
                    }
                    backSlash = 0;
                }

                case ',' -> {
                    if(!insideString){
                        insideValue = false;
                        properties.add(stringBuilder.toString());
                        stringBuilder.setLength(0);
                        continue;
                    }

                    backSlash = 0;
                }

                case '}' ->{
                    if(!insideString){
                        properties.add(stringBuilder.toString());

                    }
                }

                default -> backSlash = 0;
            }

            if(insideValue){
                stringBuilder.append(c);
            }
        }

        int id = Integer.parseInt(properties.getFirst().strip());

        String description = properties.get(1).strip();
        description = unescape(description.substring(1, description.length()-1));

        String statusString = properties.get(2).strip();
        Status status = Status.valueOf(statusString.substring(1, statusString.length()-1));

        String createdAtString = properties.get(3).strip();
        Instant createdAt = Instant.parse(createdAtString.substring(1, createdAtString.length()-1));

        String updatedAtString = properties.get(4).strip();
        Instant updatedAt = Instant.parse(updatedAtString.substring(1, updatedAtString.length()-1));


        return new Task(id, description, status, createdAt, updatedAt);
    }

    public static List<Task> deserializeAll(String serializedTasks) {

        List<Task> tasks = new ArrayList<>();

        boolean insideObject = false, insideString = false;

        int backSlash = 0;

        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i < serializedTasks.length(); i++) {
            char c = serializedTasks.charAt(i);

            switch(c) {
                case '\\' -> backSlash++;

                case '"' -> {
                    if(backSlash % 2 == 0){
                        insideString = !insideString;
                    }

                    backSlash = 0;
                }

                case '{' -> {
                    if(!insideString){
                        insideObject = true;
                    }

                    backSlash = 0;
                }

                case '}' -> {
                    if(!insideString){
                        stringBuilder.append(c);
                        tasks.add(TaskSerializer.deserialize(stringBuilder.toString()));
                        stringBuilder.setLength(0);
                        insideObject = false;
                        continue;
                    }

                    backSlash = 0;
                }

                default -> backSlash = 0;
            }

            if(insideObject){
                stringBuilder.append(c);
            }
        }
        return  tasks;
    }
}
