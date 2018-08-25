/** machine shop simulation */

package applications;

import utilities.MyInputStream;
import exceptions.MyInputException;

public class MachineShopSimulator {
    
    public static final String NUMBER_OF_MACHINES_MUST_BE_AT_LEAST_1 = "number of machines must be >= 1";
    public static final String NUMBER_OF_MACHINES_AND_JOBS_MUST_BE_AT_LEAST_1 = "number of machines and jobs must be >= 1";
    public static final String CHANGE_OVER_TIME_MUST_BE_AT_LEAST_0 = "change-over time must be >= 0";
    public static final String EACH_JOB_MUST_HAVE_AT_LEAST_1_TASK = "each job must have >= 1 task";
    public static final String BAD_MACHINE_NUMBER_OR_TASK_TIME = "bad machine number or task time";

    // data members of MachineShopSimulator
    private static int timeNow; // current time
    private static int numMachines; // number of machines
    private static int numJobs; // number of jobs
    private static EventList eList; // pointer to event list
    private static Machine[] machine; // array of machines
    private static int largeTime; // all machines finish before this

    // methods
    /**
     * move theJob to machine for its next task
     * 
     * @return false iff no next task
     */
    static boolean moveToNextMachine(Job theJob) {
        if (theJob.getTaskQ().isEmpty()) {// no next task
            System.out.println("Job " + theJob.getId() + " has completed at "
                    + timeNow + " Total wait was " + (timeNow - theJob.getLength()));
            return false;
        } else {// theJob has a next task
                // get machine for next task
            int p = ((Task) theJob.getTaskQ().getFrontElement()).getMachine();
            // put on machine p's wait queue
            machine[p].getJobQ().put(theJob);
            theJob.setArrivalTime(timeNow);
            // if p idle, schedule immediately
            if (eList.nextEventTime(p) == largeTime) {// machine is idle
                changeState(p);
            }
            return true;
        }
    }

    /**
     * change the state of theMachine
     * 
     * @return last job run on this machine
     */
    static Job changeState(int theMachine) {// Task on theMachine has finished,
                                            // schedule next one.
        Job lastJob;
        if (machine[theMachine].getActiveJob() == null) {// in idle or change-over
                                                    // state
            lastJob = null;
            // wait over, ready for new job
            if (machine[theMachine].getJobQ().isEmpty()) // no waiting job
                eList.setFinishTime(theMachine, largeTime);
            else {// take job off the queue and work on it
                machine[theMachine].setActiveJob((Job) machine[theMachine].getJobQ()
                        .remove());
                machine[theMachine].setTotalWait(machine[theMachine].getTotalWait() + timeNow
                        - machine[theMachine].getActiveJob().getArrivalTime());
                machine[theMachine].setNumTasks(machine[theMachine].getNumTasks() + 1);
                int t = machine[theMachine].getActiveJob().removeNextTask();
                eList.setFinishTime(theMachine, timeNow + t);
            }
        } else {// task has just finished on machine[theMachine]
                // schedule change-over time
            lastJob = machine[theMachine].getActiveJob();
            machine[theMachine].setActiveJob(null);
            eList.setFinishTime(theMachine, timeNow
                    + machine[theMachine].getChangeTime());
        }

        return lastJob;
    }

    /** input machine shop data */
    static SimulationSpecification inputData() {
        SimulationSpecification specification = new SimulationSpecification();

        // define the input stream to be the standard input stream
        MyInputStream keyboard = new MyInputStream();

        readNumberMachinesAndJobs(specification, keyboard);

        // Move this to startShop when ready
        MachineShopSimulator.numMachines = specification.getNumMachines();
        MachineShopSimulator.numJobs = specification.getNumJobs();
        createEventAndMachineQueues(specification);

        readChangeOverTimes(specification, keyboard);

        // Move this to startShop when ready
        setMachineChangeOverTimes(specification);

        readJobSpecifications(specification, keyboard);

        // Move this to startShop when ready
        setUpJobs(specification);

        return null;
    }

    private static void setMachineChangeOverTimes(SimulationSpecification specification) {
        for (int i = 1; i<=specification.getNumMachines(); ++i) {
            machine[i].setChangeTime(specification.getChangeOverTimes(i));
        }
    }

    private static void readNumberMachinesAndJobs(SimulationSpecification specification, MyInputStream keyboard) {
        System.out.println("Enter number of machines and jobs");
        int numMachines = keyboard.readInteger();
        int numJobs = keyboard.readInteger();
        if (numMachines < 1 || numJobs < 1) {
            throw new MyInputException(NUMBER_OF_MACHINES_AND_JOBS_MUST_BE_AT_LEAST_1);
        } else {
            specification.setNumMachines(numMachines);
            specification.setNumJobs(numJobs);
        }
    }

    private static void readJobSpecifications(SimulationSpecification specification, MyInputStream keyboard) {
        // input the jobs
        JobSpecification[] jobSpecifications = new JobSpecification[specification.getNumJobs()+1];
        for (int i=1; i <= specification.getNumJobs(); i++) {
            jobSpecifications[i] = new JobSpecification();
        }
        specification.setJobSpecification(jobSpecifications);
        for (int i = 1; i <= specification.getNumJobs(); i++) {
            System.out.println("Enter number of tasks for job " + i);
            int tasks = keyboard.readInteger(); // number of tasks
            if (tasks < 1)
                throw new MyInputException(EACH_JOB_MUST_HAVE_AT_LEAST_1_TASK);
            jobSpecifications[i].setNumTasks(tasks);

            int[] specificationsForTasks = new int[2 * tasks + 1];

            System.out.println("Enter the tasks (machine, time)"
                    + " in process order");
            for (int j = 1; j <= tasks; j++) { // get tasks for job i
                int theMachine = keyboard.readInteger();
                int theTaskTime = keyboard.readInteger();
                if (theMachine < 1 || theMachine > specification.getNumMachines()
                        || theTaskTime < 1)
                    throw new MyInputException(BAD_MACHINE_NUMBER_OR_TASK_TIME);
                specificationsForTasks[2*(j-1)+1] = theMachine;
                specificationsForTasks[2*(j-1)+2] = theTaskTime;
            }
            specification.setSpecificationsForTasks(i, specificationsForTasks);
        }
    }

    private static void setUpJobs(SimulationSpecification specification) {
        // input the jobs
        Job theJob;
        for (int i = 1; i <= specification.getNumJobs(); i++) {
            int tasks = specification.getJobSpecifications(i).getNumTasks();
            int firstMachine = 0; // machine for first task

            // create the job
            theJob = new Job(i);
            for (int j = 1; j <= tasks; j++) {
                int theMachine = specification.getJobSpecifications(i).getSpecificationsForTasks()[2*(j-1)+1];
                int theTaskTime = specification.getJobSpecifications(i).getSpecificationsForTasks()[2*(j-1)+2];
                if (j == 1)
                    firstMachine = theMachine; // job's first machine
                theJob.addTask(theMachine, theTaskTime); // add to
            } // task queue
            machine[firstMachine].getJobQ().put(theJob);
        }
    }

    private static void readChangeOverTimes(SimulationSpecification specification, MyInputStream keyboard) {
        // input the change-over times
        int changeOverTimes[] = new int[specification.getNumMachines()+1];

        System.out.println("Enter change-over times for machines");
        for (int j = 1; j <= specification.getNumMachines(); j++) {
            int ct = keyboard.readInteger();
            if (ct < 0)
                throw new MyInputException(CHANGE_OVER_TIME_MUST_BE_AT_LEAST_0);
            changeOverTimes[j] = ct;
        }

        specification.setChangeOverTimes(changeOverTimes);
    }

    private static void createEventAndMachineQueues(SimulationSpecification specification) {
        // create event and machine queues
        eList = new EventList(specification.getNumMachines(), largeTime);
        machine = new Machine[specification.getNumMachines() + 1];
        for (int i = 1; i <= specification.getNumMachines(); i++)
            machine[i] = new Machine();
    }

    /** load first jobs onto each machine
     * @param specification*/
    static void startShop(SimulationSpecification specification) {
        for (int p = 1; p <= numMachines; p++)
            changeState(p);
    }

    /** process all jobs to completion */
    static void simulate() {
        while (numJobs > 0) {// at least one job left
            int nextToFinish = eList.nextEventMachine();
            timeNow = eList.nextEventTime(nextToFinish);
            // change job on machine nextToFinish
            Job theJob = changeState(nextToFinish);
            // move theJob to its next machine
            // decrement numJobs if theJob has finished
            if (theJob != null && !moveToNextMachine(theJob))
                numJobs--;
        }
    }

    /** output wait times at machines */
    static SimulationResults outputStatistics() {
        System.out.println("Finish time = " + timeNow);
        for (int p = 1; p <= numMachines; p++) {
            System.out.println("Machine " + p + " completed "
                    + machine[p].getNumTasks() + " tasks");
            System.out.println("The total wait time was "
                    + machine[p].getTotalWait());
            System.out.println();
        }
        return null;
    }

    /** entry point for machine shop simulator */
    public static void main(String[] args) {
        largeTime = Integer.MAX_VALUE;
        /*
         * It's vital that we (re)set this to 0 because if the simulator is called
         * multiple times (as happens in the acceptance tests), because timeNow
         * is static it ends up carrying over from the last time it was run. I'm
         * not convinced this is the best place for this to happen, though.
         */
        timeNow = 0;
        SimulationSpecification inputs = inputData(); // get machine and job data
        startShop(inputs); // initial machine loading
        simulate(); // run all jobs through shop
        SimulationResults simulationResults = outputStatistics(); // output machine wait times
    }
}
