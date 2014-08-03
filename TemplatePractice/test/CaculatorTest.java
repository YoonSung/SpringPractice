import static org.junit.Assert.assertSame;

import org.junit.Test;


public class CaculatorTest {

	@Test
	public void test() {
		Caculator caculator = new Caculator();
		int sum = 0;
		try {
			sum = caculator.calcSum("numbers.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		assertSame(10, sum);
	}

}
