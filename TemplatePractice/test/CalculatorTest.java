import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CalculatorTest {

	Calculator calculator;
	String numFilePath;
	
	@Before
	public void setUp() {
		calculator = new Calculator();
		this.numFilePath = "numbers.txt";//getClass().getResource("numbers.txt").getPath();
	}
	
	@Test
	public void sumOfNumbers() throws Exception {
		assertSame(10, calculator.calcSum(this.numFilePath));
	}
	
	@Test
	public void muliplyOfNumbers() throws Exception {
		assertThat(calculator.calcMultiply(this.numFilePath), is(24));
	}
	
	@Test
	public void concatenateStrings() throws Exception {
		assertTrue(calculator.concatenate(this.numFilePath).equalsIgnoreCase("1234"));
	}
}