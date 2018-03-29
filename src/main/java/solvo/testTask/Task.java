package solvo.testTask;

public class Task 
{
	private String type;
	private int param;
	private Task next;
	private TasksQueue queue;
	
	private boolean blocked;
	private boolean proseed;
	
	Task(String type, int param)
	{
		this.type = type;
		this.param = param;
		
		this.next = null;
		this.queue = null;
		
		this.proseed = false;
		this.blocked = false;
	}
	
	public String getType()
	{
		return this.type;
	}
	
	public int getParam()
	{
		return this.param;
	}
	
	public Task setQueue(TasksQueue q)
	{
		this.queue = q;
		
		return this;
	}
	
	public boolean isBlocked()
	{
		return this.blocked;
	}
	
	public Task block()
	{
		this.blocked = true;
		return this;
	}
	
	public Task unblock()
	{
		this.blocked = false;
		return this;
	}
	
	public void setNext(Task nextTask)
	{
		Task t = this;
		
		while(t.next != null)
			t = t.next;
		
		t.next = nextTask;
	}
	
	public Task getNext()
	{
		return this.next;
	}
	
	public boolean isNext()
	{
		if(this.next == null)
			return false;
		else
			return true;
	}
	
	public int getQueueNumber()
	{
		if(this.queue != null)
			return this.queue.getNumber();
		else
			return -1;
	}
	
	public boolean isProceed()
	{
		return this.proseed;
	}
	
	public void toProceed()
	{
		this.proseed = true;
	}
	
	public String toString()
	{
		return "[" + type + "|" + param + "|status:" + (this.proseed == true ? "in process" : "wait      ")
				+ "|blocked:" + (this.blocked == true ? "T" : "F") + "]";
	}
}
