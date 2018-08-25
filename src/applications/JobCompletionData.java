package applications;

public class JobCompletionData {
    private final int completionTime;
    private final int totalWaitTime;
    private final int jobNumber;

    public JobCompletionData(int jobNumber, int completionTime, int totalWaitTime) {
        this.jobNumber = jobNumber;
        this.completionTime = completionTime;
        this.totalWaitTime = totalWaitTime;
    }

    public int getCompletionTime() {
        return completionTime;
    }

    public int getTotalWaitTime() {
        return totalWaitTime;
    }

    public int getJobNumber() {
        return jobNumber;
    }
}
