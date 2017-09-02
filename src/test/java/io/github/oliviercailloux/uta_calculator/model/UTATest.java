package io.github.oliviercailloux.uta_calculator.model;

import static org.junit.Assert.assertEquals;

import java.util.List;

import io.github.oliviercailloux.uta_calculator.model.Alternative;
import io.github.oliviercailloux.uta_calculator.view.MainBuyingNewCar;

import org.junit.Test;

public class UTATest {

	@Test
	public void utaLearningFromExamplesShouldReproduceThoseExample(){

		MainBuyingNewCar buyingNewCarExercice = new MainBuyingNewCar();
		List<Alternative> correctAlternatives = buyingNewCarExercice.getMainAlternatives();
		List<Alternative> utaAlternatives = buyingNewCarExercice.getAlternativeSorted();

		assertEquals("Does the buying new car work fines ?", correctAlternatives, utaAlternatives);
	}

}