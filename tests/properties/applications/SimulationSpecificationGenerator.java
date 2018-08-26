package applications;

import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;

public class SimulationSpecificationGenerator extends Generator<SimulationSpecification> {
    // I initially thought of having larger numbers here, like 100 or 1,000, but
    // in playing with properties for `LinkedQueue` it became clear that if your
    // sizes were too big you never hit edge cases like an empty queue.
    public static final int MAX_MACHINES = 10;
    public static final int MAX_JOBS = 10;
    public static final int MAX_TASKS = 10;
    public static final int MAX_CHANGEOVER_TIME = 10;
    private static final int MAX_TASK_TIME = 10;

    public SimulationSpecificationGenerator() {
        super(SimulationSpecification.class);
    }

    @Override public SimulationSpecification generate(SourceOfRandomness r, GenerationStatus status) {
        SimulationSpecification result = new SimulationSpecification();
        // I add one to these random numbers so I don't end up with 0 machines
        // or 0 jobs.
        int numMachines = r.nextInt(MAX_MACHINES) + 1;
        int numJobs = r.nextInt(MAX_JOBS) + 1;

        result.setNumMachines(numMachines);
        result.setNumJobs(numJobs);

        // Ugh – the annoying problem of the indices starting at one again
        // so I have to make the array one too large and skip the first
        // slot.
        int[] changeOverTimes = new int[numMachines + 1];
        for (int i=1; i<=numMachines; ++i) {
            // Changeover times can be 0 so I don't need to add 1 here.
            changeOverTimes[i] = r.nextInt(MAX_CHANGEOVER_TIME);
        }
        result.setChangeOverTimes(changeOverTimes);

        JobSpecification[] jobSpecifications = new JobSpecification[numJobs + 1];
        for (int i=1; i<=numJobs; ++i) {
            jobSpecifications[i] = new JobSpecification();
        }
        result.setJobSpecification(jobSpecifications);
        for (int i=1; i<=numJobs; ++i) {
            int numTasks = r.nextInt(MAX_TASKS) + 1;
            jobSpecifications[i].setNumTasks(numTasks);

            int[] specificationsForTasks = new int[2 * numTasks + 1];

            for (int j = 1; j <= numTasks; ++j) {
                int theMachine = r.nextInt(numMachines) + 1;
                int theTaskTime = r.nextInt(MAX_TASK_TIME) + 1;
                specificationsForTasks[2 * (j - 1) + 1] = theMachine;
                specificationsForTasks[2 * (j - 1) + 2] = theTaskTime;
            }
            result.setSpecificationsForTasks(i, specificationsForTasks);
        }

        return result;
    }
}
