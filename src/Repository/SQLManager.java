package Repository;

import java.util.Vector;
import java.sql.*;
import java.io.*;

/**
 * An  JDBC Manager that uses Microsoft Access database to manage ontology instances.
 * 
 * This implements ObjectStore interface
 */
public class SQLManager implements ObjectStore {
           
    /** instances for JDBC connection*/
    private String dbName;
    private Connection jConnection;
    private Statement jStatement;
    private ResultSet jResultSet;	  

    /**
     * Gets a reference to a serialized object store at the default location.
     */
    public SQLManager() throws StorageFailure {
        this(FILENAMES.DEFAULT_STORE_NAME);
    }

    /**
     * Gets a reference to a JDBC object store at the default location.
     *
     * @param name the name of the directory containing the object store
     */
    public SQLManager(FILENAMES fname) throws StorageFailure{

        this.dbName = fname.toString();
        
        try{
        	Class.forName( "com.mysql.jdbc.Driver" );
        	jConnection = DriverManager.getConnection ("jdbc:mysql://localhost/" 
        					+ this.dbName, "root", "password");
        	jStatement = jConnection.createStatement();
		}catch(Exception e ){
			//throw new StorageFailure("SQL driver loading problem");
			System.out.println(e.toString());
		}
	
    }    

     /**
     * Gets all references to JDBC object store.
     *
     */
    public Vector getObject(String query)throws StorageFailure {
    	Vector v = new Vector();
    	try{
			ResultSet rs = jStatement.executeQuery(query);	
			ResultSetMetaData metadata = rs.getMetaData();
			int columns = metadata.getColumnCount();
			while ( rs.next() ) {
				Vector subv = new Vector();
				for(int i = 1; i<=columns; i++)				
					subv.add(rs.getObject(i));
				v.add(subv);
			}
		}catch(SQLException sqle) {	
			sqle.printStackTrace();
		}
		    	
    	return v;
    }

    public  void saveObject(String query) throws StorageFailure{	
    	try{
			int result = jStatement.executeUpdate(query);  
      	}catch ( SQLException sqle ) { 
        	sqle.printStackTrace();
	    }
    }

    /**
     * Close JDBC database connection
     */
    public void close() throws StorageFailure {
     	closeConnection();
    }

/*********************Private methods*******************/

    /**
     *method for JDBC connection
     */
   private Statement makeConnection(String odbcName) { 
      	try {
         	String jdbcURL = "jdbc:odbc:" + odbcName;
         	jConnection = DriverManager.getConnection(jdbcURL );
         	return jConnection.createStatement();			
        }
      	catch (SQLException sqle ) { 
      		sqle.printStackTrace();
         	return null ;
      	}
    } 

    /**
     *method for JDBC close
     */
    private void closeConnection() { 
      	try {
          	jStatement.close() ;
          	jConnection.close() ;
      	}
     	catch ( SQLException sqle ) {
     		sqle.printStackTrace();
     	}        
    } 

    /**
     * Throws an error message
     */
    private void storageFailure(String message, Throwable error) throws StorageFailure {

        message = (error == null ?
                   message :  message + ": " + error.getMessage());
        
        throw new StorageFailure(message);
    }
}
