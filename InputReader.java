import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.*;

public class InputReader
{
    public BufferedReader read_file(String filename) throws IOException
    {
        try
        {
            File file = new File(filename);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            
            return bufferedReader;
        }
        catch(IOException ex)
        {
            ex.getStackTrace();
            System.exit(1); 
        }
        return null;
    }
}