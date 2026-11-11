package services;

import entities.Task;
import repositories.TaskRepository;
import utils.Status;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TaskService {

    private final List<Task> tasks = new ArrayList<>();
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        this.tasks.addAll(this.taskRepository.findAll());
    }

    public void addTask(String description){
        Task task = new Task(this.resolveTaskId(), description);
        this.tasks.add(task);
        this.taskRepository.saveAll(tasks);
    }

    private Optional<Task> findTask(int id){
        return this.tasks.stream().filter(task -> task.getId() == id).findFirst();
    }

    private int resolveTaskId(){

        int maxId = 0;

        for(Task task : this.tasks){

            if (task.getId() > maxId){
                maxId = task.getId();
            }
        }

        return maxId + 1;
    }


    public void updateTask(int id, String description){

        Task task = findTask(id).orElseThrow(() -> new IllegalArgumentException("Task not found"));
        task.update(description);
        this.taskRepository.saveAll(tasks);
    }

    public void deleteTask(int id){
        Task task = findTask(id).orElseThrow(() -> new IllegalArgumentException("Task not found"));
        this.tasks.remove(task);
        this.taskRepository.saveAll(tasks);
    }

    public void markInProgress(int id){
        Task task = findTask(id).orElseThrow(() -> new IllegalArgumentException("Task not found"));
        task.markInProgress();
        this.taskRepository.saveAll(tasks);
    }

    public void markDone(int id){
        Task task = findTask(id).orElseThrow(() -> new IllegalArgumentException("Task not found"));
        task.markDone();
        this.taskRepository.saveAll(tasks);
    }

    public List<Task> list(){
        return Collections.unmodifiableList(tasks);
    }

    public List<Task> listByStatus(Status status){

        return this.tasks.stream().filter(task -> task.getStatus() == status).toList();

    }
}
