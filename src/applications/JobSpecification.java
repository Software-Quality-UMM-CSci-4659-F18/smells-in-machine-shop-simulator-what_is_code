package applications;

public class JobSpecification {
    private int numTasks;
    private int[] specifyTasks;

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

    public int getTaskMachine(int j) {
        return specifyTasks[2*(j-1)+1];
    }

    public int getTaskTime(int j) {
        return specifyTasks[2*(j-1)+2];
    }
}
