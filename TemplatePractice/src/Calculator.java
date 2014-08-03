import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {

	public Integer calcSum(String filePath) throws Exception {
		return lineReadTemplate(filePath, new LineCallback() {
			
			@Override
			public Integer doSomethingWithLine(String line, Integer result) {
				return result + Integer.valueOf(line);
			}
		}, 0);
	}
	
	//템플릿 1
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

	//템플릿 2
	public Integer lineReadTemplate(String filePath, LineCallback callback, int initVal) throws Exception {
		BufferedReader br = null;
		Integer result = initVal;
		try {
			br = new BufferedReader(new FileReader(filePath));
			String line = null;
			
			while((line = br.readLine()) != null) {
				result = callback.doSomethingWithLine(line, result);
			}
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
		
		return result;
	}
	
	public int calcMultiply(String filePath) throws Exception {
		return lineReadTemplate(filePath, new LineCallback() {
			
			@Override
			public Integer doSomethingWithLine(String line, Integer result) {
				return result * Integer.valueOf(line);
			}
		}, 0);
	}
}