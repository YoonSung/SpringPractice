import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Caculator {

	public Integer calcSum(String filePath) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		Integer sum = 0;
		
		String line = null;
		
		while((line = br.readLine()) != null) {
			sum += Integer.valueOf(line);
		}
		
		br.close();
		return sum;
	}
}
