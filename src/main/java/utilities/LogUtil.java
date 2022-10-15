package utilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class LogUtil {
	
	ZonedDateTime date = ZonedDateTime.now();
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyyHHMMSS");
	String fileNameFormat = date.format(formatter);
	
	private BufferedWriter bufferedWriter = null;
	
	public void CreateLogFile() 
	{
		try 
		{
			File dir = new File("resourse");
			if(!dir.exists())
				dir.mkdir();
			
			File logfile = new File(dir + "/"+ fileNameFormat);
			
			FileWriter fileWriter = new FileWriter(logfile.getAbsoluteFile());
			
			bufferedWriter  = new BufferedWriter(fileWriter);
		}
		
		catch(Exception e)
		{
			
		}
	}
	
	public void Write(String message) 
	{
		try 
		{	
			bufferedWriter.write(message);
			bufferedWriter.close();
		}
		
		catch(Exception e)
		{
			
		}
	}
}
