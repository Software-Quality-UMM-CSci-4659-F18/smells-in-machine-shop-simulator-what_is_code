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

    public LinkedQueue getJobQ() {
        return jobQ;
    }

    public int getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(int changeTime) {
        this.changeTime = changeTime;
    }

    public int getTotalWait() {
        return totalWait;
    }

    public Job getActiveJob() {
        return activeJob;
    }

    public int getNumTasks() {
        return numTasks;
    }

    public void addTimeNow(int timeNow){
        this.totalWait = totalWait + timeNow - activeJob.getArrivalTime();
    }

    public void addToNumTasks(int nums) {
        this.numTasks += nums;
    }

    public int getTaskTime(){ return this.activeJob.removeNextTask();}

    public boolean isEmpty(){
        return jobQ.isEmpty();
    }

    public void takeJobFromQueue() {
        this.activeJob = (Job) jobQ.remove();
    }

    public void setJobtoNull() {this.activeJob = null;}

    public void addToQ(Job newJob){
        jobQ.put(newJob);
    }
}
