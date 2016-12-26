package BackEnd;

import java.io.File;
import java.sql.Timestamp;
import java.util.Date;

public class LogManager {
	/**
	 * This class uses singleton design pattern.
	 * It only allows a single instance to exist at once,
	 * and allows classes to get the instance.
	 * 
	 * It also uses the strategy pattern. 
	 * This class stores the strategy of reading and writing
	 * in a variable ioStrategy. And its readFromFile() and
	 * writeToFile() methods use this variable.
	 */
	
	private static final LogManager logManagerInstance = new LogManager();
	
	private StringBuffer allLogs = new StringBuffer();
	private final String FILE_PATH = "src" + File.separator +
            				         "BackEnd" + File.separator +
            				         "ManagerFiles" + File.separator +
            				         "logData.txt";
	// the strategy of reading and writing that this LogManager will use
	private FileInputterOutputter ioStrategy = new TextFileInputterOutputter();
	
	
	/**
	 * Returns the single instance of LogManager that exists.
	 * 
	 * @return	LogManager object
	 */
	public static LogManager getInstance(){
		return LogManager.logManagerInstance;
	}
	
	
	/**
	 * Return a string of the logs made by the user.
	 * 
	 * @return	String containing all logs
	 **/
	public String getLogs(){
		return allLogs.toString();
	}
	
	
	/**
	 * Add new log to all the existing logs user 
	 * ever made with new name, old name and time-stamp.
	 * 
	 * @param filePath
	 * 				path of the file being changed
	 * @param oldName
	 * 				old name of the file being transformed
	 * @param newName
	 * 				new name of the file being transformed
	 */
	public void addLog(String filePath, String oldName, String newName){
		String content = "Change: " + oldName + " ----> " + newName + "\n" + 
						 "File Path: " + filePath + "\n" + 
						 "Date: " + new Timestamp(new Date().getTime()) + "\n";
		allLogs.append(content);
		writeToFile();
	}
	
	
	/**
	 * This method writes text to a file at path <code>FILE_PATH</code>.
	 * 
	 * @serialData write text in file at <code>FILE_PATH</code>
	 */
	public void writeToFile(){
		ioStrategy.writeToFile(FILE_PATH, this.allLogs);
	}
	
	
	/**
	 * This method reads text from the file at <code>FILE_PATH</code> and
	 * stores in instance variable <code>allLogs</code>.
	 * 
	 * @serialData read data as <code>List</code> and store it
	 * 			   in instance variable <code>allTags</code>
	 */
	public void readFromFile(){
		this.allLogs = ioStrategy.readFromFile(FILE_PATH);
	}
}
