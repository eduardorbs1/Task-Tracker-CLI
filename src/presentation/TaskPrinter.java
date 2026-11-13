package presentation;

import entities.Task;

import java.util.List;

public class TaskPrinter {

    public static void  printTasks(List<Task> tasks){
        if(!tasks.isEmpty()){
            for(Task task: tasks){
                IO.println(task.toString());
            }
        }else {
            IO.println("No tasks found");
        }


    }
}
