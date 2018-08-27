package applications;

import com.pholser.junit.quickcheck.From;
import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

@RunWith(JUnitQuickcheck.class)
public class SimulationProperties {
    @Property
    public void lastJobCompletesAtOverallFinishTime(
            @From(SimulationSpecificationGenerator.class)
                    SimulationSpecification specification)
    {
        final SimulationResults results = MachineShopSimulator.runSimulation(specification);
        final int finishTime = results.getFinishTime();
        final JobCompletionData[] jobCompletionData = results.getJobCompletionData();
        final int lastJobCompletionTime = jobCompletionData[jobCompletionData.length-1].getCompletionTime();
        assertEquals(finishTime, lastJobCompletionTime);
    }

    @Property
    public void waitTimesShouldMatch(
            @From(SimulationSpecificationGenerator.class)
                    SimulationSpecification specification)
    {
        final SimulationResults results = MachineShopSimulator.runSimulation(specification);

        int totalMachineWaitTime = 0;
        for (int waitTime : results.getTotalWaitTimePerMachine()) {
            assertThat(waitTime, greaterThanOrEqualTo(0));
            totalMachineWaitTime += waitTime;
        }

        int totalJobWaitTime = 0;
        for (JobCompletionData jobCompletionData : results.getJobCompletionData()) {
            final int jobWaitTime = jobCompletionData.getTotalWaitTime();
            assertThat(jobWaitTime, greaterThanOrEqualTo(0));
            totalJobWaitTime += jobWaitTime;
        }

        assertEquals(totalJobWaitTime, totalMachineWaitTime);
    }

    @Property
    public void jobsOutputInTimeOrder(
            @From(SimulationSpecificationGenerator.class)
                SimulationSpecification specification)
    {
        final SimulationResults results = MachineShopSimulator.runSimulation(specification);

        JobCompletionData[] jobCompletionData = results.getJobCompletionData();
        for (int i=1; i<jobCompletionData.length-1; ++i) {
            assertThat(jobCompletionData[i].getCompletionTime(),
                    lessThanOrEqualTo(jobCompletionData[i+1].getCompletionTime()));
        }
    }

    @Property
    public void machinesCompletedCorrectNumberOfTasks(
            @From(SimulationSpecificationGenerator.class)
                SimulationSpecification specification)
    {
        final SimulationResults results = MachineShopSimulator.runSimulation(specification);

        int numMachines = specification.getNumMachines();
        int numJobs = specification.getNumJobs();
        int[] expectedMachineTaskCounts = new int[numMachines+1];

        for (int i=1; i<=numJobs; ++i) {
            JobSpecification jobSpecification = specification.getJobSpecifications(i);
            int numTasks = jobSpecification.getNumTasks();
            int[] specsForTasks = jobSpecification.getSpecificationsForTasks();
            for (int j=1; j<=numTasks; ++j) {
                int theMachine = specsForTasks[2*(j-1)+1];
                ++expectedMachineTaskCounts[theMachine];
            }
        }

        int[] actualMachineTasksCounts = results.getNumTasksPerMachine();
        for (int i=1; i<=numMachines; ++i) {
            assertEquals(expectedMachineTaskCounts[i], actualMachineTasksCounts[i]);
        }
    }
}
