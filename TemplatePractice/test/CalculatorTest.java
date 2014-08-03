import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;


public class CalculatorTest {

	Calculator calculator;
	String numFilePath;
	
	@Before
	public void setUp() {
		calculator = new Calculator();
	}
	
	@Test
	public void sumOfNumbers() {
		Calculator caculator = new Calculator();
		int sum = 0;
		try {
			sum = caculator.calcSum("numbers.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		assertSame(10, sum);
	}

}
