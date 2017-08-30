package simulation;

import io.github.oliviercailloux.uta_calculator.view.MainSimulation;

import static org.junit.Assert.assertEquals;
import org.junit.Test;


public class SimulationTest {
	
	@Test
	public void multiplicationOfZeroIntegersShouldReturnZero(){
		MainSimulation simulation = new MainSimulation();
		assertEquals("10 x 0 must be 0", 0, simulation.multiply(10, 0));
		assertEquals("0 x 10 must be 0", 0, simulation.multiply(0, 10));
		assertEquals("0 x 0 must be 0", 0, simulation.multiply(0, 0)); 
		assertEquals("10 x 10 must be 100", 100, simulation.multiply(10, 10)); 
	}
	
}