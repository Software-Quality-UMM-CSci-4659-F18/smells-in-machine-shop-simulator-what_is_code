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
}
