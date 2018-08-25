package applications;

public class JobSpecification {
    private int numTasks;
    private int[] specificationsForTasks;

    public void setNumTasks(int numTasks) {
        this.numTasks = numTasks;
    }

    public int getNumTasks() {
        return numTasks;
    }

    public void setSpecificationsForTasks(int[] specificationsForTasks) {
        this.specificationsForTasks = specificationsForTasks;
    }

    public int[] getSpecificationsForTasks() {
        return specificationsForTasks;
    }
}
