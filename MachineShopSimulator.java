/** machine shop simulation */

package applications;

import utilities.*;
import exceptions.*;
import dataStructures.*;

public class MachineShopSimulator
{
   // top-level nested classes
   private static class Task
   {
      // data members
      private int machine;
      private int time;

      // constructor
      private Task(int theMachine, int theTime)
      {
         machine = theMachine;
         time = theTime;
      }
   }

   private static class Job
   {
      // data members
      private LinkedQueue taskQ;  // this job's tasks
      private int length;         // sum of scheduled task times
      private int arrivalTime;    // arrival time at current queue
      private int id;             // job identifier

      // constructor
      private Job(int theId)
      {
         id = theId;
         taskQ = new LinkedQueue();
         // length and arrivalTime have default value 0
      }

      // other methods
      private void addTask(int theMachine, int theTime)
      {taskQ.put(new Task(theMachine, theTime));}

      /** remove next task of job and return its time
        * also update length */
      private int removeNextTask()
      {
         int theTime = ((Task) taskQ.remove()).time;
         length += theTime;
         return theTime;
      }
   }


   private static class Machine
   {
      // data members
      LinkedQueue jobQ; // queue of waiting jobs for this machine
      int changeTime;   // machine change-over time
      int totalWait;    // total delay at this machine
      int numTasks;     // number of tasks processed on this machine
      Job activeJob;    // job currently active on this machine

      // constructor
      private Machine()
         {jobQ = new LinkedQueue();}
   }

   private static class EventList
   {
      // data members
      int [] finishTime; // finish time array

      // constructor
      private EventList(int theNumMachines, int theLargeTime)
      {// initialize finish times for m machines
         if (theNumMachines < 1)
            throw new IllegalArgumentException
                  ("number of machines must be >= 1");
         finishTime = new int [theNumMachines + 1];

         // all machines are idle, initialize with
         // large finish time
         for (int i = 1; i <= theNumMachines; i++)
            finishTime[i] = theLargeTime;
      }
      
      /** @return machine for next event */
      private int nextEventMachine()
      {
         // find first machine to finish, this is the
         // machine with smallest finish time
         int p = 1;
         int t = finishTime[1];
         for (int i = 2; i < finishTime.length; i++)
            if (finishTime[i] < t)
            {// i finishes earlier
               p = i;
               t = finishTime[i];
            }
         return p;
      }

      private int nextEventTime(int theMachine)
      {return finishTime[theMachine];}

      private void setFinishTime(int theMachine, int theTime)
      {finishTime[theMachine] = theTime;}
   }


   // data members of MachineShopSimulator
   private static int timeNow;         // current time
   private static int numMachines;     // number of machines
   private static int numJobs;         // number of jobs
   private static EventList eList;     // pointer to event list
   private static Machine [] machine;  // array of machines
   private static int largeTime;       // all machines finish before this
   
   // methods
   /** move theJob to machine for its next task
     * @return false iff no next task */
   static boolean moveToNextMachine(Job theJob)
   {
      if (theJob.taskQ.isEmpty())
      {// no next task
         System.out.println("Job " + theJob.id + " has completed at "
           + timeNow + " Total wait was " + (timeNow - theJob.length));
         return false;
      }
      else
      {// theJob has a next task
       // get machine for next task
         int p = ((Task) theJob.taskQ.getFrontElement()).machine;
         // put on machine p's wait queue
         machine[p].jobQ.put(theJob);
         theJob.arrivalTime = timeNow;
         // if p idle, schedule immediately
         if (eList.nextEventTime(p) == largeTime)
         {// machine is idle
            changeState(p);
         }
         return true;
      }
   }
   
   /** change the state of theMachine
     * @return last job run on this machine */
   static Job changeState(int theMachine)
   {// Task on theMachine has finished, schedule next one.
      Job lastJob;
      if (machine[theMachine].activeJob == null) 
      {// in idle or change-over state
         lastJob = null;
         // wait over, ready for new job
         if (machine[theMachine].jobQ.isEmpty()) // no waiting job
               eList.setFinishTime(theMachine, largeTime);
         else
         {// take job off the queue and work on it
            machine[theMachine].activeJob = 
                 (Job) machine[theMachine].jobQ.remove();
            machine[theMachine].totalWait +=
                 timeNow - machine[theMachine].activeJob.arrivalTime;
            machine[theMachine].numTasks++;
            int t = machine[theMachine].activeJob.removeNextTask();
            eList.setFinishTime(theMachine, timeNow + t);
         }
      }
      else
      {// task has just finished on machine[theMachine]
       // schedule change-over time
         lastJob = machine[theMachine].activeJob;
         machine[theMachine].activeJob = null;
         eList.setFinishTime(theMachine, timeNow +
                             machine[theMachine].changeTime);
      }

      return lastJob;
   }
   
   /** input machine shop data */
   static void inputData()
   {
      // define the input stream to be the standard input stream
      MyInputStream keyboard = new MyInputStream();

      System.out.println("Enter number of machines and jobs");
      numMachines = keyboard.readInteger();
      numJobs = keyboard.readInteger();
      if (numMachines < 1 || numJobs < 1)
            throw new MyInputException
                  ("number of machines and jobs must be >= 1");
   
      // create event and machine queues
      eList = new EventList(numMachines, largeTime);
      machine = new Machine [numMachines + 1];
      for (int i = 1; i <= numMachines; i++)
         machine[i] = new Machine();
      
      // input the change-over times
      System.out.println("Enter change-over times for machines");
      for (int j = 1; j <= numMachines; j++)
      {
         int ct = keyboard.readInteger();
         if (ct < 0)
            throw new MyInputException
                  ("change-over time must be >= 0");
         machine[j].changeTime = ct;
      }

      // input the jobs
      Job theJob;
      for (int i = 1; i <= numJobs; i++)
      {
         System.out.println("Enter number of tasks for job " + i);
         int tasks = keyboard.readInteger();  // number of tasks
         int firstMachine = 0;                // machine for first task
         if (tasks < 1)
            throw new MyInputException
                  ("each job must have > 1 task");

         // create the job
         theJob = new Job(i);
         System.out.println("Enter the tasks (machine, time)"
              + " in process order");
         for (int j = 1; j <= tasks; j++)
         {// get tasks for job i
            int theMachine = keyboard.readInteger();
            int theTaskTime = keyboard.readInteger();
            if (theMachine < 1 || theMachine > numMachines
                || theTaskTime < 1)
            throw new MyInputException
                  ("bad machine number or task time");
            if (j == 1)
               firstMachine = theMachine;    // job's first machine
            theJob.addTask(theMachine, theTaskTime);  // add to
         }                                            // task queue
         machine[firstMachine].jobQ.put(theJob);
      }                                           
   }
   
   /** load first jobs onto each machine */
   static void startShop()
   {
      for (int p = 1; p <= numMachines; p++)
         changeState(p);
   }
   
   /** process all jobs to completion */
   static void simulate()
   {
      while (numJobs > 0)
      {// at least one job left
         int nextToFinish = eList.nextEventMachine();
         timeNow = eList.nextEventTime(nextToFinish);
         // change job on machine nextToFinish
         Job theJob = changeState(nextToFinish);
         // move theJob to its next machine
         // decrement numJobs if theJob has finished
         if (theJob != null && !moveToNextMachine(theJob)) numJobs--;
      }
   }
   
   /** output wait times at machines */
   static void outputStatistics()
   {
      System.out.println("Finish time = " + timeNow);
      for (int p = 1; p <= numMachines; p++)
      {
         System.out.println("Machine " + p + " completed "
              + machine[p].numTasks + " tasks");
         System.out.println("The total wait time was "
              + machine[p].totalWait);
         System.out.println();
      }
   }
   
   /** entry point for machine shop simulator */
   public static void main(String [] args)
   {
      largeTime = Integer.MAX_VALUE;
      inputData();         // get machine and job data
      startShop();         // initial machine loading
      simulate();          // run all jobs through shop
      outputStatistics();  // output machine wait times
   }
}
