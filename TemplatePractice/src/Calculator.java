import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {

	public Integer calcSum(String filePath) throws IOException {
		return fileReadTemplate(filePath, new BufferedReaderCallback() {
			
			@Override
			public Integer doSomethingWithReader(BufferedReader br) throws IOException {
				int sum = 0;
				String line = null;
				while((line = br.readLine()) != null) {
					sum += Integer.valueOf(line);
				}		
				return sum;
			}
		});
	}
	
	public Integer fileReadTemplate(String filePath, BufferedReaderCallback callback) throws IOException {
		
		BufferedReader br = null;
		int sum = 0;
		try {
			br = new BufferedReader(new FileReader(filePath));
			sum = callback.doSomethingWithReader(br);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;
		} finally {
			
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}
		}
		
		return sum;
	}
}