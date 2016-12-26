package BackEnd;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class SerialDataFileInputterOutputter implements FileInputterOutputter {
	/**
	 * This is a part of the strategy pattern.
	 * It is a concrete class implementing FileInputterOutputter. 
	 * This concrete class uses serial data when reading and writing to file.
	 */
	
	/**
	 * This method reads all the data of generic type T from the file at 
	 * specified path and returns the data read.
	 * 
	 * @param filePath 
	 * 					the file path of the file to read data from
	 * @return the data read				
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T readFromFile(String filePath) {
		// variable where all the data is to be stored 
		T readDataStorage = null;
		
		try {			
			InputStream file = new FileInputStream(filePath);
			InputStream buffer = new BufferedInputStream(file);
			ObjectInput input = new ObjectInputStream(buffer);
			
			// Convert the type of the object so that it matches the 
			// type of readDataStorage parameter
			readDataStorage = (T) input.readObject();
            input.close();
		}
            
        // If file is not available catch FileNotFoundException
    	// and show a user friendly message
		catch (FileNotFoundException e) {
			System.out.println("Unable to reach file at " + filePath);
			
		// If ClassNotFoundException is raised while constructing the 
		// data structure catch the exception and show a message
		} catch (ClassNotFoundException e) {
			System.out.println("Unable to construct the data structure");
			
		// If IOException occurs then catch the exception 
		// and show a user friendly message in this case also
		} catch (IOException e) {
			System.out.println("Unable to read data from file at " + filePath);
		}
		return readDataStorage;
	}


	/**
	 * This method writes all the data of generic type T to the file 
	 * at specified path.
	 * 
	 * @param filePath 
	 * 				the file path of the file to write data to
	 * @param contentToWrite
	 * 				the object to write to file
	 */
	@Override
	public <T> void writeToFile(String filePath, T contentToWrite) {
		try {
			// Create a new file if it doesn't exist
			File newFile = new File(filePath);
			newFile.createNewFile();
			
			// passing false overwrites the data in the file if it exists
			OutputStream file = new FileOutputStream(filePath, false);
	        OutputStream buffer = new BufferedOutputStream(file);
	        ObjectOutput output = new ObjectOutputStream(buffer);

	        // serialize the Map
	        output.writeObject(contentToWrite);
	        output.close();
		}
		
		// If file is not available catch FileNotFoundException
		// and show a user friendly message
		catch(FileNotFoundException e){
			System.out.println("Unable to reach file at " + filePath);
		}
		
		// If an IOException occurs then catch the exception 
		// and show a user friendly message in this case also
		catch(IOException i){
			System.out.println("Unable to write data in file at " + filePath);
		}
	}

}
