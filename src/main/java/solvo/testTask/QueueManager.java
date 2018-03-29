package solvo.testTask;

import java.util.ArrayList;
import java.util.List;

import solvo.testTask.utill.QueueInfo;
import solvo.testTask.utill.TasksHash;

public class QueueManager
{
	private int maxQueueCount;

	private List<TasksQueue> queues;
	private TasksHash tasksHash;
	private QueueInfo queueInfo;

	//-----------------
	
	QueueManager(int queueCount)
	{
		this.maxQueueCount = queueCount;
		this.queues = new ArrayList<TasksQueue>();
		
		this.tasksHash = new TasksHash();
		this.queueInfo = new QueueInfo();
		
		for(int i = 0; i < this.maxQueueCount; i++)
		{
			this.queues.add(new TasksQueue(tasksHash, i));
		}
	}

	public void addTask(Task t)
	{
		System.out.println("Entered new task - " + t);
		
		//this.tasksHash.isPesent(t);
		
		
		queueInfo.update(queues);
		
		if(maxQueueCount == queueInfo.getBusyQueueCount())
		{
			queueInfo.getMinFillingQueue(t.getType()).add(t);;
		}
		else if(maxQueueCount / 2 > queueInfo.getBusyQueueCount() 
				|| maxQueueCount / 2 > queueInfo.getQueuesCount(t.getType()))
		{
			// add task to any free queue
			
			queueInfo.getEmptyQueue().add(t);
		}
		else
		{
			if(queueInfo.getFillingDencity(t.getType()) > queueInfo.getReverseFillingDencity(t.getType())
					&& (maxQueueCount - queueInfo.getBusyQueueCount() > 1 || queueInfo.getReverseQueuesCount(t.getType()) != 0))
			{	
				queueInfo.getEmptyQueue().add(t);
			}
			else
			{
				queueInfo.getMinFillingQueue(t.getType()).add(t);
			}
		}

		showQueues();
		//this.tasksHash.showHash();
	}

	public void stop()
	{
		for(TasksQueue tq: this.queues)
		{
			tq.stopThread();
		}
		
		for(TasksQueue tq: this.queues)
		{
			try 
			{
				tq.getThread().join();
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	private void showQueues()
	{
		StringBuffer ret = new StringBuffer();
		
		ret.append("-------------------------------------------------");
		ret.append("Queues\n");
		
		for(TasksQueue tq: queues)
		{
			ret.append(tq + "\n");
		}
		
		ret.append("-------------------------------------------------");
		
		
		System.out.println(ret);
	
	}
}
