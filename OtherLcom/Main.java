import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class parser{

	public parser(String path) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(path));
		
		//Initializing required variables
		ArrayList<String> lines = new ArrayList<>();
		String line;
		int i = 0;
		
		//Trimming lines and storing them
		while((line = reader.readLine()) != null) {
			line = line.trim();
			if(line.length() == 0)
				continue;
			
			lines.add(line.toString());
			//System.out.println(i + " : " + line);
			i++;
		}
		
		
		
		new LCOM(lines);
	}
	
}