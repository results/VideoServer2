package task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class TaskHandler implements Runnable {
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private static final int INITAL_DELAY = 600;
	private static final int RATE = 600;

    private static ArrayList<Task> tasks = new ArrayList<Task>();
	private static ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();//sets timer for threads
	private static ExecutorService pool = Executors.newCachedThreadPool();// the threadpool
        
    public TaskHandler() {
    	//Logger.log("TaskScheduler started on cached thread pool.");
    	scheduler.scheduleAtFixedRate(this, INITAL_DELAY, RATE, TimeUnit.MILLISECONDS);
    }
    
    public static void addTask(Task t) {
    	if(t != null) {
    		if(TaskHandler.getTasks() != null) {
		    	if(!TaskHandler.getTasks().contains(t)) {
		    		TaskHandler.getTasks().add(t);
		    	}
    		}
    	}
    }
    
    public static void removeTask(Task t) {
    	if(t != null) {
    		if(TaskHandler.getTasks() != null) {
		    	if(TaskHandler.getTasks().contains(t)) {
		    		TaskHandler.getTasks().remove(t);
		    	}
    		}
    	}
    }

	public static ArrayList<Task> getTasks() {
		return tasks;
	}

	@Override
	public void run() {
        for(Task task : TaskHandler.getTasks()) {
        	if(task != null) {
        		task.setCaller(this);
        		TaskHandler.pool.submit(task);
        		if(task.runOnce) {
        			TaskHandler.removeTask(task);
        			task = null;
        		}
        	}
        }
	}

}
