package BackEnd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class TextFileInputterOutputter implements FileInputterOutputter {
	/**
	 * This is a part of the strategy pattern.
	 * It is a concrete class implementing FileInputterOutputter. 
	 * This concrete class reads from a text file and writes by 
	 * appending content to the data in the file.
	 */
	
	/**
	 * Reads data from the text file at filePath into data structure 
	 * of type T and returns that data structure.
	 * 
	 * @param filePath
	 * 					the path of the file to be read from
	 * @return
	 * 			data of type T
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T readFromFile(String filePath) {
		// variable where all the data is to be stored
		T readDataStorage = null;
		try{
			// Create a new file if it doesn't exist
			File newFile = new File(filePath);
			newFile.createNewFile();
			
			// Declare the file at file path and string
			// buffer where all the data is read
			FileReader file = new FileReader(filePath);
			BufferedReader bufferedReader = new BufferedReader(file);
			String line = "";
			
			// In each iteration add the line read from file in string buffer
			StringBuffer allLogs = new StringBuffer();
			do{
				allLogs.append(line + "\n");
			}while((line = bufferedReader.readLine()) != null);
			readDataStorage = (T) allLogs;
			bufferedReader.close();
		}
		
		// If file is not available catch FileNotFoundException
		// and show a user friendly message
		catch(FileNotFoundException e1){
			System.out.println("Unable to reach file at " + filePath);
		}
		
		// Catch exception and print a user friendly 
		// message about the exception
		catch(IOException e2){
			System.out.println("Unable to write data in file at " + filePath);
		}
		return readDataStorage;
	}
	

	/**
	 * 	
	 * This method writes data <code>contentToWrite</code> to the text 
	 * file at the path specified by filePath.
	 * 
	 * @param filePath 
	 * 				the path of the text file to write data to
	 * @param contentToWrite
	 * 				data of type T to write to file
	 */
	@Override
	public <T> void writeToFile(String filePath, T contentToWrite) {
		try{
			// Create a new file if it doesn't exist
			File newFile = new File(filePath);
			newFile.createNewFile();
			
			FileWriter file = new FileWriter(filePath, true);
			PrintWriter printWriter = new PrintWriter(file);
			printWriter.println(contentToWrite);
			printWriter.close();
		}
		
		// Catch exception and print a user friendly 
		// message about the exception
		catch(IOException e1){
			System.out.println("Unable to write data in file at " + filePath);
		}

	}

}
