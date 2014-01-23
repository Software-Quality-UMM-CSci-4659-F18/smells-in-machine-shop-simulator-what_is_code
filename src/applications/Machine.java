package applications;

import applications.MachineShopSimulator.Job;
import dataStructures.LinkedQueue;

public class Machine {
    // data members
    private LinkedQueue jobQ; // queue of waiting jobs for this machine
    int changeTime; // machine change-over time
    int totalWait; // total delay at this machine
    int numTasks; // number of tasks processed on this machine
    Job activeJob; // job currently active on this machine

    // constructor
    public Machine() {
        this.jobQ = new LinkedQueue();
    }

    @Deprecated
    /**
     * We've deprecated this because it allows uncontrolled access to the
     * Machine's Job queue. We'd like to replace accesses to this with 
     * more controlled access.
     */
    public LinkedQueue getJobQ() {
        return jobQ;
    }

    public boolean hasNoActiveJob() {
        return getJobQ().isEmpty();
    }
}
