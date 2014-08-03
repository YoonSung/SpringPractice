import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.io.IOException;

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
	public void sumOfNumbers() throws IOException {
		assertSame(10, calculator.calcSum(this.numFilePath));
	}
	
	@Test
	public void muliplyOfNumbers() throws IOException {
		assertThat(calculator.calcMultiply(this.numFilePath), is(24));
	}
}
