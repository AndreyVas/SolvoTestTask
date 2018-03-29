package solvo.testTask.utill;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

import solvo.testTask.Task;

public class TasksHash 
{
	private Map<Integer, Task> tasksHash;
	private Semaphore sem;
	
	public TasksHash()
	{
		this.tasksHash = new HashMap<Integer, Task>();
		sem = new Semaphore(1);
	}
	
	synchronized public void isPesent(Task t)
	{
		Task isPresent = this.tasksHash.get(t.getParam());
		
		if(isPresent == null)
		{
			this.tasksHash.put(t.getParam(), t);
		}
		else
		{
			isPresent.setNext(t.block());
		}
	}
	
	public Semaphore getSemaphore()
	{
		return this.sem;
	}
	
	synchronized public void remove(Task t)
	{
		//System.out.println("in remove");
		if(this.tasksHash.get(t.getParam()).equals(t)) 
		{
			if(t.isNext())
			{
				Task next = t.getNext();

				this.tasksHash.put(next.getParam(), next);
				next.unblock();
			}
			else
			{
				this.tasksHash.remove(t.getParam());
			}
		}
		//System.out.println("in remove end");
	}
	
	public void showHash()
	{
		System.out.println("-------------------------------------------------");
		System.out.println("Tasks hash table");
		
		for (Map.Entry<Integer, Task> entry : this.tasksHash.entrySet()) 
		{
		    System.out.print("Param: " + entry.getKey() + "| Task: " + entry.getValue() + "| Next : ");
		    
		    Task t = entry.getValue().getNext();
		    
		    while(t != null)
		    {
		    	System.out.print(t + " -> ");
		    	
		    	t = t.getNext();
		    }
		    
		    System.out.println("no");
		}
		
		System.out.println("-------------------------------------------------");
	}
}
