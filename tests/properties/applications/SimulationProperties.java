package applications;

import com.pholser.junit.quickcheck.From;
import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertEquals;

@RunWith(JUnitQuickcheck.class)
public class SimulationProperties {
    @Property
    public void lastJobCompletesAtOverallFinishTime(
            @From(SimulationSpecificationGenerator.class)
                    SimulationSpecification specification) {
        final SimulationResults results = MachineShopSimulator.runSimulation(specification);
        final int finishTime = results.getFinishTime();
        final JobCompletionData[] jobCompletionData = results.getJobCompletionData();
        final int lastJobCompletionTime = jobCompletionData[jobCompletionData.length-1].getCompletionTime();
        assertEquals(finishTime, lastJobCompletionTime);
    }
}
