package repositories;

import entities.Task;
import serializers.TaskSerializer;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TaskRepository {

    private static final Path DIRECTORY = Path.of(System.getProperty("user.home"), ".task-tracker-cli");
    private static final Path PATH = DIRECTORY.resolve("tasks.json");

    public TaskRepository() {
        try {
            Files.createDirectories(DIRECTORY);
        } catch (IOException e) {
            throw new RuntimeException("Could not create data directory", e);
        }
    }

    public void saveAll(List<Task> tasks) {

        String serializedTasks = TaskSerializer.serializeAll(tasks);

        try {
            Files.writeString(PATH, serializedTasks);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public List<Task> findAll() {

        if(!Files.exists(PATH)){
            return new ArrayList<>();
        }

        try {
            String serializedTasks = Files.readString(PATH);
            return TaskSerializer.deserializeAll(serializedTasks);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

}
