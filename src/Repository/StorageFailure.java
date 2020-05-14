package Repository;

public class StorageFailure extends Exception {

    /**
     * Creates a storage failure with the specified message.
     *
     * @param message the message explaining the cause of the failure
     */
    public StorageFailure(String message) {
        super(message);

    }
    
    FileManager fileManager = new FileManager();
}
