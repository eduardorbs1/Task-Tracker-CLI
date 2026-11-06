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


    public static String serialize(Task task) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\t{\n");
        stringBuilder.append("\t\t\"id\": ").append(task.getId()).append(",\n");
        stringBuilder.append("\t\t\"description\": ").append(String.format("\"%s\"", escape(task.getDescription()))).append(",\n");
        stringBuilder.append("\t\t\"status\": ").append(String.format("\"%s\"", task.getStatus())).append(",\n");
        stringBuilder.append("\t\t\"createdAt\": ").append(String.format("\"%s\"", task.getCreatedAt())).append(",\n");
        stringBuilder.append("\t\t\"updatedAt\": ").append(String.format("\"%s\"", task.getUpdatedAt())).append("\n");
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

    public static Task deserialize(String serializedTask) {
        return  null;
    }

    public static List<Task> deserializeAll(String serializedTasks) {
        return  null;
    }
}
