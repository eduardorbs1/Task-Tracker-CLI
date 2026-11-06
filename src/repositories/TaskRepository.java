package repositories;

import entities.Task;
import serializers.TaskSerializer;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class TaskRepository {

    private static final Path PATH = Path.of("tasks.json");

    public TaskRepository() {
    }

    public void saveAll(List<Task> tasks) {
        try {

            String serializedTasks = TaskSerializer.serializeAll(tasks);
            Files.writeString(PATH, serializedTasks);

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public List<Task> findAll() {
        return null;
    }


}
