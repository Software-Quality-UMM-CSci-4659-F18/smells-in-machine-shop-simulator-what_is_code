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
    Machine() {
        jobQ = new LinkedQueue();
    }

    LinkedQueue getJobQ() {
        return jobQ;
    }

    int getChangeTime() {
        return changeTime;
    }

    void setChangeTime(int changeTime) {
        this.changeTime = changeTime;
    }

    int getTotalWait() {
        return totalWait;
    }

    void setTotalWait(int totalWait) {
        this.totalWait = totalWait;
    }

    int getNumTasks() {
        return numTasks;
    }

    void setNumTasks(int numTasks) {
        this.numTasks = numTasks;
    }

    Job getActiveJob() {
        return activeJob;
    }

    void setActiveJob(Job activeJob) {
        this.activeJob = activeJob;
    }
}