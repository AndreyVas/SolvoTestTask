package solvo.testTask;

public class QueueHandler implements Runnable
{
	private Thread currentThread;
	private boolean stopFlag;
	private TasksQueue tasksQueue;
	private final int DELAY = 10;
	
	public QueueHandler(TasksQueue tasksQueue)
	{
		this.tasksQueue = tasksQueue;
		this.stopFlag = false;
		
		this.currentThread = new Thread(this);
		this.currentThread.start();
	}
	
	public void run() 
	{
		while(true)
		{
			Task t = tasksQueue.getTask();
			
			if(t != null)
			{
				System.out.println("Processing start for task : " + t + " in queue " + t.getQueueNumber());
				
				
				try 
				{
					Thread.sleep(DELAY);
				} 
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
			}
			
			
			synchronized(this)
			{
				if(this.stopFlag)
					break;
			}
		}
		

		System.out.println("***** END THREAD " + this.tasksQueue.getNumber());

	}
	
	public void stop()
	{
		this.stopFlag = true;
	}
	
	public Thread getThread()
	{
		return this.currentThread;
	}

}
