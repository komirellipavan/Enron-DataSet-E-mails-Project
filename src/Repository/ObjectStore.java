package Repository;

import java.util.Vector;

public interface ObjectStore {

    /**
     * Retrieves the records of all objects in this store.
     *
     * @return selected of the records
     */
    Vector getObject(String st) throws StorageFailure;

    /**
     * Update an object to persistent storage.
     *
     * @param query the query for that object
     *
     * @throws StorageFailure if the save operation fialed
     */
    void saveObject(String query) throws StorageFailure;


    /**
     * Closes this object store.
     * All store resources should be released by implementations of 
     * this method.
     */
    void close() throws StorageFailure;

}
