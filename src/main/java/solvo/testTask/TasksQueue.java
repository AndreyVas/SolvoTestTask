package solvo.testTask;

import java.util.LinkedList;
import java.util.Queue;

import solvo.testTask.utill.TasksHash;

public class TasksQueue 
{
	private String queueType;
	private Queue<Task> tasks;
	private QueueHandler queueHandler;
	private TasksHash tasksHash;
	
	private int number;
	private boolean stop;
	
	TasksQueue(TasksHash tasksHash, int number)
	{
		this.number = number;
		
		this.tasks = new LinkedList<Task>();
		this.tasksHash = tasksHash;
		this.queueType = null;
		this.stop = false;
		
		this.queueHandler = new QueueHandler(this);
	}
	
	synchronized public void add(Task t)
	{
		this.tasksHash.isPesent(t);
		
		if(tasks.size() == 0)
		{
			queueType = t.getType();
			this.tasks.add(t.setQueue(this));
		}
		else
			this.tasks.add(t.setQueue(this));
		
		notify();
	}
	
	synchronized public Task getTask()
	{
		// remove completed task from queue
		if(this.tasks.peek() != null && this.tasks.peek().isProceed())
		{
			Task tmp = this.tasks.poll();
			this.tasksHash.remove(tmp);

			System.out.println("Processing end for task : " + tmp + " in queue " + tmp.getQueueNumber());
			
			if(tasks.size() == 0)
				queueType = null;
		}

		while((this.tasks.size() == 0 && !this.stop) || 
				(this.tasks.size() != 0 && this.tasks.peek().isBlocked()))
		{
			try 
			{
				wait(5);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
	
		// stop thread
		if(this.stop && this.tasks.size() == 0)
		{
			this.queueHandler.stop();
			return null;
		}

		Task t = this.tasks.peek();
		t.toProceed();

		return t; 
	}
	
	synchronized public void awakeningThread()
	{
		notify();
	}
	
	public Task showTopTask()
	{
		return this.tasks.peek();
	}
	
	public boolean isEmpty()
	{
		if(tasks.size() == 0)
			return true;
		else
			return false;
	}
	
	public int getSize()
	{
		return tasks.size();
	}
	
	public String getType()
	{
		return this.queueType;
	}
	
	public int getNumber()
	{
		return this.number;
	}
	
	synchronized public void stopThread()
	{
		this.stop = true;
		notify();
	}
	
	public Thread getThread()
	{
		return this.queueHandler.getThread();
	}
	
	synchronized public String toString()
	{
		StringBuffer ret = new StringBuffer();
		
		ret.append("num : " + this.number);
		ret.append("|Type:" + (this.queueType == null ? "no" : (" " + this.queueType)));
		ret.append("|Size:" + this.tasks.size());
		ret.append("|tasks -- ");
		
		for(Task t: this.tasks)
		{
			ret.append(t.toString() + " ");
		}
		
		return ret.toString();
	}
}
