package Repository;

import java.io.File;
import java.io.IOException;
import java.io.*;

public class FileManager{
	
	private static FileOutputStream fos;
    private static DataOutputStream outStream;
	
	public FileManager(){
	}
	
	public void closeFile(){
		try{
			outStream.close();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void openFile(String filename){
		openFile(filename, true);
	}
	
	//false to append data the end of the file
	//true to create a blank new file
	public void openFile(String filename, boolean b){
		try{
			fos=new FileOutputStream (filename, b);
    		outStream =new DataOutputStream (fos);
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void writeToFile(String st){
		st = st + " \n";
		
		try{			
			outStream.writeBytes(st);
			
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
  			
	}
	
	public void clearFile(String filename)throws IOException{
		openFile(filename, false);
		writeToFile("");
		closeFile();
	}
	
}