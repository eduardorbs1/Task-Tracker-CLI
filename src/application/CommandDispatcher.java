package application;

import services.TaskService;
import utils.Status;

public class CommandDispatcher {

    private final TaskService taskService;

    public CommandDispatcher(TaskService taskService) {
        this.taskService = taskService;
    }

    private int parseId(String value){
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid id: "+ value, e);
        }
    }

    private void validateArgumentCount(String[] command, int expectedArgumentCount) {
        if (command.length - 1 != expectedArgumentCount) {
            throw new IllegalArgumentException("Invalid command format");
        }
    }

    public void dispatchCommand(String[] command){

        if(command == null || command.length == 0){
            throw  new IllegalArgumentException("No command provided");
        }

        switch (command[0]){
            case "add" -> {
                this.validateArgumentCount(command, 1);
                this.taskService.addTask(command[1]);//
            }

            case "update" -> {
                this.validateArgumentCount(command, 2);
                this.taskService.updateTask(this.parseId(command[1]), command[2]);
            }

            case "delete" -> {
                this.validateArgumentCount(command, 1);
                this.taskService.deleteTask(this.parseId(command[1]));
            }

            case "mark-in-progress" -> {
                this.validateArgumentCount(command, 1);
                this.taskService.markInProgress(this.parseId(command[1]));
            }

            case "mark-done" -> {
              this.validateArgumentCount(command, 1);
              this.taskService.markDone(this.parseId(command[1]));
            }

            case "list" -> {

                if(command.length == 1){
                    this.taskService.list();
                    return;
                }

                this.validateArgumentCount(command, 1);

                Status status;

                try {

                    status = Status.valueOf(command[1].toUpperCase());

                }catch (IllegalArgumentException e){

                    throw  new IllegalArgumentException("Invalid status: " + command[1], e);
                }

                taskService.listByStatus(status);

            }

            default -> throw new IllegalArgumentException("Unknown command: " + command[0]);
        }

    }
}
