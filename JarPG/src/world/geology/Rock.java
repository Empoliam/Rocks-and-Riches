package world.geology;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Rock 
{

	final static List<String> allrocks = new ArrayList<String>();
	
	int id;
	String name;
	int yeild;
	int meta;
	
	public Rock(int id)
	{
		
		this.id = id;
		
		String[] line = allrocks.get(id).split(",");	
				
		name = line[1];
		yeild = Integer.parseInt(line[2]);
		meta = Integer.parseInt(line[3]);
		
	}
	
	public static void getList()
	{
		
		try
		{
			BufferedReader br = new BufferedReader(new FileReader("resources/rocks.csv"));
			
			String line = br.readLine();
			
			while(line != null)
			{
				
				allrocks.add(line);
				line = br.readLine();
				
			}
			
			br.close();
			
		}
		catch(java.io.FileNotFoundException e){}
		catch(IOException e2){};
		
	}
	
}
