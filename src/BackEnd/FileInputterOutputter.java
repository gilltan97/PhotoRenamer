package BackEnd;

public interface FileInputterOutputter{
	/**
	 * This is a part of the strategy pattern.
	 * It is an interface for a strategy of inputting and outputting.
	 */
	
	/**
	 * Reads data from the file at filePath into data structure 
	 * of type T and returns that data structure.
	 * 
	 * @param filePath
	 * 			 path from file from where data is to be read
	 * @return
	 * 			data of type T
	 */
	public  <T> T readFromFile(String filePath);
	
	
	/**
	 * Writes data, contentToWrite, of type T to the file at filePath.
	 * 
	 * @param filePath
	 * 			path of the file where data is to written 
	 * @param contentToWrite
	 * 			data to to written in the specified path
	 */
	public <T> void writeToFile(String filePath, T contentToWrite);
}
