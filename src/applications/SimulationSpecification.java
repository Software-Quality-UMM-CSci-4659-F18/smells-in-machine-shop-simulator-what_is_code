package applications;

import java.util.Arrays;

public class SimulationSpecification {
    private int numMachines;
    private int numJobs;
    private int[] changeOverTimes;
    private JobSpecification[] jobSpecifications;

    //job specifications
    private int numTasks;
    private int[] specifyTasks;

    public void setNumMachines(int numMachines) {
        this.numMachines = numMachines;
    }

    public void setNumJobs(int numJobs) {
        this.numJobs = numJobs;
    }

    public int getNumMachines() {
        return numMachines;
    }

    public int getNumJobs() {
        return numJobs;
    }

    public void setChangeOverTimes(int[] changeOverTimes) {
        this.changeOverTimes = changeOverTimes;
    }

    public int getChangeOverTimes(int machineNumber) {
        return changeOverTimes[machineNumber];
    }

    public void setSpecificationsForTasks(int jobNumber, int[] specificationsForTasks) {
        jobSpecifications[jobNumber].setSpecificationsForTasks(specificationsForTasks);
    }

    public void setJobSpecification(JobSpecification[] jobSpecifications) {
        this.jobSpecifications = jobSpecifications;
    }

    public JobSpecification getJobSpecifications(int jobNumber) {
        return jobSpecifications[jobNumber];
    }

    //Imported from JobSpecification class

    public void setNumTasks(int numTasks) {
        this.numTasks = numTasks;
    }

    public int getNumTasks() {
        return numTasks;
    }

    public void setSpecificationsForTasks(int[] specificationsForTasks) {
        this.specifyTasks = specificationsForTasks;
    }

    public int[] getSpecificationsForTasks() {
        return specifyTasks;
    }

    public int getTaskMachine(int i, int j) {
        return jobSpecifications[i].getSpecificationsForTasks()[2*(j-1)+1];
    }

    public int getTaskTime(int i, int j) {
        return jobSpecifications[i].getSpecificationsForTasks()[2*(j-1)+2];
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("<").append(numMachines).append(" machines, ");
        builder.append(numJobs).append(" jobs; ");
        builder.append("change overs: ").append(Arrays.toString(changeOverTimes));
        for (int i=1; i<=numJobs; ++i) {
            builder.append("; job ").append(i).append(" tasks: ");
            builder.append(Arrays.toString(jobSpecifications[i].getSpecificationsForTasks()));
        }

        builder.append(">");
        return builder.toString();
    }
}
