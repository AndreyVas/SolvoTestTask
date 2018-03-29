package solvo.testTask.utill;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import solvo.testTask.TasksQueue;

public class QueueInfo 
{
	private Map<String, Float>  fillingDencity;			// count of the tasks in all queue with one type / queue count
	private Map<String, TasksQueue> minFillingQueue;	// queue with minimum number of tasks
	private Map<String, Integer> queuesCount;			// number of queue with contain tasks with one type
	private TasksQueue emptyQueue;
	
	public QueueInfo()
	{
		fillingDencity = new HashMap<String, Float>();			
		minFillingQueue = new HashMap<String, TasksQueue>();	
		queuesCount = new HashMap<String, Integer>();			
	}
	
	/**
	 * Class for collection info about queues before choice suitable queue   
	 * 
	 * @param queues
	 */
	public void update(List<TasksQueue> queues)
	{
		emptyQueue = null;
		
		int aQueueCount = 0;
		int bQueueCount = 0;
		
		int aQueueItemsCount = 0;
		int bQueueItemsCount = 0;
		
		TasksQueue minQueueA = null;
		TasksQueue minQueueB = null;
		
		for(TasksQueue tk: queues)
		{
			if(!tk.isEmpty())
			{
				if(tk.getType().equals(Types.A))
				{
					aQueueCount++;
					aQueueItemsCount += tk.getSize();
					
					if(minQueueA == null)
						minQueueA = tk;
					else
					{
						if(minQueueA.getSize() > tk.getSize())
							minQueueA = tk;
					}
				}
				else
				{
					bQueueCount++;
					bQueueItemsCount += tk.getSize();
					
					if(minQueueB == null)
						minQueueB = tk;
					else
					{
						if(minQueueB.getSize() > tk.getSize())
							minQueueB = tk;
					}
				}
			}
			else
			{
				if(emptyQueue == null)
					emptyQueue = tk;
			}
				
		}
		
		float aFilling = aQueueCount > 0 ? aQueueItemsCount/aQueueCount : 0;
		float bFilling = bQueueCount > 0 ? bQueueItemsCount/bQueueCount : 0;
		
		fillingDencity.put(Types.A, aFilling);
		fillingDencity.put(Types.B, bFilling);
		
		queuesCount.put(Types.A, aQueueCount);
		queuesCount.put(Types.B, bQueueCount);
		
		minFillingQueue.put(Types.A, minQueueA);
		minFillingQueue.put(Types.B, minQueueB);
	}
	
	/**
	 * 
	 * @param type - type of the queue A or B
	 * @return count of the tasks in all queue with one type / queue count
	 */
	public float getFillingDencity(String type)
	{
		return this.fillingDencity.get(type);
	}
	
	public float getReverseFillingDencity(String type)
	{
		if(type.equals(Types.A))
			return this.fillingDencity.get(Types.B);
		else
			return this.fillingDencity.get(Types.A);
	}
	
	/**
	 * 
	 * @param type - type of the queue A or B
	 * @return number of queue with contain task with type from param
	 */
	public int getQueuesCount(String type)
	{
		return this.queuesCount.get(type);
	}
	
	public int getReverseQueuesCount(String type)
	{
		if(type.equals(Types.A))
			return this.queuesCount.get(Types.B);
		else
			return this.queuesCount.get(Types.A);
	}
	
	/**
	 * 
	 * @param type - type of the queue A or B
	 * @return queue with minimum number of tasks
	 */
	public TasksQueue getMinFillingQueue(String type)
	{
		return this.minFillingQueue.get(type);
	}
	
	/**
	 * 
	 * @return count of the queue witch contains one or more tasks 
	 */
	public int getBusyQueueCount()
	{
		int ret = 0;
		
		for (int val : this.queuesCount.values())
		    ret += val;
		
		return ret;
	}
	
	public TasksQueue getEmptyQueue()
	{
		return this.emptyQueue;
	}
}
