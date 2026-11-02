package services;

import entities.Task;
import repositories.TaskRepository;
import utils.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskService {

    private final List<Task> tasks = new ArrayList<>();
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        // Hydrated tasks
    }

    public void addTask(Task task){
        this.tasks.add(task);
    }

    private Optional<Task> findTask(int id){
        return this.tasks.stream().filter(task -> task.getId() == id).findFirst();
    }

    public void updateTask(int id, String description){

        Task task = findTask(id).orElseThrow(() -> new IllegalArgumentException("Task not found"));
        task.update(description);
    }

    public void deleteTask(int id){
        Task task = findTask(id).orElseThrow(() -> new IllegalArgumentException("Task not found"));
        this.tasks.remove(task);
    }

    public void markInProgress(int id){
        Task task = findTask(id).orElseThrow(() -> new IllegalArgumentException("Task not found"));
        task.markInProgress();
    }

    public void markDone(int id){
        Task task = findTask(id).orElseThrow(() -> new IllegalArgumentException("Task not found"));
        task.markDone();
    }

    public List<Task> list(Optional<Status> status){

        return this.tasks.stream().filter(task -> status.isEmpty() || task.getStatus() == status.get()).toList();

    }
}
