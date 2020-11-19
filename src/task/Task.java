package task;

import java.util.concurrent.Callable;


public abstract class Task implements Callable<Object> {
	
	private TaskHandler caller;
		
	@Override
	public abstract Object call();
	
	public boolean runOnce = false;
	
	public void setCaller(TaskHandler taskHandler) {
		this.caller = taskHandler;
	}
	
	public TaskHandler getCaller() {
		return caller;
	}
	

}
