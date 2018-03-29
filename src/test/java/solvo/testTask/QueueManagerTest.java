package solvo.testTask;

import java.util.InputMismatchException;
import java.util.Scanner;

import org.junit.Test;

import solvo.testTask.utill.Types;

public class QueueManagerTest 
{
	int tasksCount = 30;
	int aPercentage = 10;
	int parMax = 10;
	
	final int SET_COUNT = 1;
	final int SET_PERSENTAGE = 2;
	final int SHOW_VALUES = 3;
	final int START = 4;
	
	public void menu()
	{
		System.out.println("");
		System.out.println("1 - Set count of tasks");
		System.out.println("2 - Set the percentage of task with type A");
		System.out.println("3 - Show default values");
		System.out.println("4 - Start test");
		
		System.out.println("");
		System.out.print(">>");
	}
	
	public int random(int start, int end)
	{ 
	      return start + (int) (Math.random() * end);
	}
	
	public boolean selectAction()
	{
		int inputVal = 0;
		Scanner in = new Scanner(System.in);
		
		while(true)
		{
			try
			{
				menu();
				inputVal = in.nextInt();

				switch(inputVal)
				{
					case SET_COUNT:
						System.out.println("Type number of the generated tasks and press Enter :");
						System.out.print(">>");
						tasksCount = in.nextInt();
						break;
						
					case SET_PERSENTAGE:
						System.out.println("Type the percentage of tasks of the type A and press Enter :");
						System.out.print(">>");
						aPercentage = in.nextInt();
						break;
					case SHOW_VALUES:
						System.out.println("Tasks count = " + tasksCount);
						System.out.println("Count of parameter X : " + parMax);
						System.out.println("Persentage of tasks with A type : " + aPercentage);
						System.out.println("Persentage of tasks with B type : " + (100 - aPercentage));
						break;
						
					case START:
						return true;
						
				}
				
				
			}
			catch(InputMismatchException e)
			{
				System.out.println("Incorrect symbol entered, pleas try again");
				in = new Scanner(System.in);
			}
			catch(Exception e)
			{
				System.out.println("Unknown error please try again");
				in = new Scanner(System.in);
			}
			
			inputVal = 0;
		}
	}
	
	@Test
	public void testAddTask() 
	{
		selectAction();
		
		QueueManager ip = new QueueManager(10);
		
		for(int i = 0; i < tasksCount; i++)
		{
			ip.addTask(new Task(random(0, 100) < aPercentage ? Types.A : Types.B, random(0, parMax)));
		}
			
		ip.stop();
		
		/*for(int j = 0; j < 1000; j++)
		{
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! " + j);
			QueueManager ip = new QueueManager(10);
			
			for(int i = 0; i < tasksCount; i++)
			{
				ip.addTask(new Task(random(0, 100) < aPercentage ? Types.A : Types.B, random(0, parMax)));
			}
				
			ip.stop();
		}*/

		System.out.println("The application has completed its work");
	}

}
