package applications;

import dataStructures.LinkedQueue;

class Machine {
    // data members
    private LinkedQueue jobQ; // queue of waiting jobs for this machine
    private int changeTime; // machine change-over time
    private int totalWait; // total delay at this machine
    private int numTasks; // number of tasks processed on this machine
    private Job activeJob; // job currently active on this machine

    // constructor
    public Machine(int changeoverTime) {
        changeTime = changeoverTime;
        jobQ = new LinkedQueue();
    }

    public int getChangeTime() {
        return changeTime;
    }

    public int getTotalWait() {
        return totalWait;
    }

    public int getNumTasks() {
        return numTasks;
    }

    public void incrementNumTasks() {
        ++numTasks;
    }

    boolean hasNoWaitingJobs() {
        return jobQ.isEmpty();
    }

    public Job nextJob() {
        return (Job) jobQ.remove();
    }

    public void addJob(Job theJob) {
        jobQ.put(theJob);
    }

    public void incrementTotalWaitTime() {
        int currentTime = MachineShopSimulator.getCurrentTime();
        int thisWaitTime = currentTime - activeJob.getArrivalTime();
        totalWait = totalWait + thisWaitTime;
    }

    public void advanceActiveJob() {
        activeJob = nextJob();
    }

    public void setToNoActiveJob() {
        activeJob = null;
    }

    public int startNextJob() {
        advanceActiveJob();
        incrementTotalWaitTime();
        incrementNumTasks();
        int t = activeJob.removeNextTask();
        return t;
    }

    public Job endActiveJob() {
        Job lastJob = activeJob;
        activeJob = null;
        return lastJob;
    }

    public boolean noActiveMethod() {
        return activeJob == null;
    }
}