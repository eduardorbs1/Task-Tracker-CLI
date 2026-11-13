package application;

import presentation.ErrorPrinter;
import repositories.TaskRepository;
import services.TaskService;

public class Main {
    public static void main(String[] args) {
        TaskRepository taskRepository = new TaskRepository();
        TaskService taskService = new TaskService(taskRepository);
        CommandDispatcher commandDispatcher = new CommandDispatcher(taskService);

        try{
            commandDispatcher.dispatchCommand(args);
        } catch (IllegalArgumentException e) {
            ErrorPrinter.printError(e);
        }

    }
}
