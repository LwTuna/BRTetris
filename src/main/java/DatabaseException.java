
/**
 * A custom exception thrown when a Error in database request occurs
 * Can be caught in try catch but no necessary
 * @author jonas
 *
 */
public class DatabaseException extends RuntimeException{

	//Default generate serialisation ID
	private static final long serialVersionUID = 7596778443537478312L;
	
	/**
	 * Contructor for the Database Exception
	 * @param message
	 */
	public DatabaseException(String message) {
		super(message);
	}

}
