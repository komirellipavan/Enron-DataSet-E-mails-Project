package com.usq;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.util.function.Consumer;

import com.mysql.jdbc.PreparedStatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ImportData {

	static int count = 0;
	static int batchSize = 200;
	static double large_file = 0;
	static String large_filename = "";

	static Connection connection = null;

	public void walk(String path, PreparedStatement pstmt) throws IOException, SQLException {

		File root = new File(path);
		File[] list = root.listFiles();

		String file_path;

		File file;
		FileInputStream inputStream;

		if (list == null)
			return;

		for (File f : list) {
			if (f.isDirectory()) {
				walk(f.getAbsolutePath(), pstmt);
//                System.out.println( "Dir----------------:" + f.getAbsoluteFile() );
			} else {
//                System.out.println( "File:" + f.getAbsoluteFile() ); 

				file_path = f.getAbsolutePath();

				file = new File(file_path);
				inputStream = new FileInputStream(file);

//            	large_file = ((double) file.length() / 1024)>large_file?((double) file.length() / 1024):large_file;

				byte[] data = new byte[(int) file.length()];
				inputStream.read(data);
				String str = new String(data, "UTF-8");

				try {
					pstmt.setString(1, file_path);
					pstmt.setString(2, str);
					pstmt.addBatch();

					count++;

					if (count % batchSize == 0) {
						System.out.println("Commit the next records of batch = " + count);
						int[] result = pstmt.executeBatch();
						System.out.println("Number of rows inserted: " + result.length);
						pstmt.clearBatch();
						connection.commit();
					}

				} catch (Exception e) {

					System.out.println("last file added = : " + file_path);
					System.out.println(large_file);

					e.printStackTrace();
					connection.rollback();
				} finally {

					inputStream.close();
				}

			}
		}
	}

	public static void main(String[] args) throws IOException, SQLException {
		ImportData fw = new ImportData();

		connection = ConnectionManager.getConnection();
		System.out.println(connection);
		connection.setAutoCommit(false);

		String sqlQuery = "insert into raw_data (file,data) values (?,?)";
		PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sqlQuery);
		
		long startTime = System.nanoTime();
		fw.walk("D:\\Aus\\USQ\\sem4\\ICT\\enron_mail_20150507\\maildir", pstmt);
//    	fw.walk("D:\\sem4\\nlp\\enron_mail_20150507\\test\\",pstmt );

		if (count % batchSize != 0) {
			System.out.println("Commit the last batch" + count);
			int[] result = pstmt.executeBatch();
			System.out.println("Number of rows inserted: " + result.length);
			connection.commit();
		}

		if (pstmt != null)
			pstmt.close();
		if (connection != null)
			connection.close();

		long endTime = System.nanoTime();

		long timeElapsed = endTime - startTime;

		System.out.println("Execution time in milliseconds : " + timeElapsed / 1000000);

		System.out.println("Total data inserted:" + count);
		System.out.println("***********finish************");

	}

}
