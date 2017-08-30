package simulation;

import static org.junit.Assert.assertEquals;

import java.util.List;

import io.github.oliviercailloux.uta_calculator.model.Alternative;
import io.github.oliviercailloux.uta_calculator.view.MainBuyingNewCar;

import org.junit.Test;

public class UTATest {

	//Il faut en particulier qqs tests qui montrent que la fonction UTA, 
	//quand elle apprend à partir d’exemples, reproduit bien ces examples. 

	@Test
	public void utaLearningFromExamplesShouldReproduceThoseExample(){

		MainBuyingNewCar buyingNewCarExercice = new MainBuyingNewCar();
		List<Alternative> correctAlternatives = buyingNewCarExercice.getMainAlternatives();
		List<Alternative> utaAlternatives = buyingNewCarExercice.getAlternativeSorted();

		assertEquals("Does the buying new car work fines ?", true, comparing(correctAlternatives, utaAlternatives));
	}

	public boolean comparing(List<Alternative> correctAlternatives, List<Alternative> utaAlternatives) {
		for(int i = 0; i < correctAlternatives.size(); i++){
			if(correctAlternatives.get(i).getId() != utaAlternatives.get(i).getId()){
				return false;
			}
		}
		return true;
	}

	@Test
	public void multiplicationOfZeroIntegersShouldReturnZero(){
		assertEquals("10 x 0 must be 0", 0, multiply(10, 0));
	}

	public int multiply(int input1, int input2){
		return input1 * input2;
	}

}