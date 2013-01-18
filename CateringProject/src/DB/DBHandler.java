package DB;

import java.sql.*;

/**
 * The general database handler class.
 * 
 * @author Mark Lessard 
 */
public class DBHandler {

	private final String DB_PORT = "3306";
	private final String DB_NAME = "catering";
	private final String DB_URL = "jdbc:mysql://crashdowne.dyndns.org:"
			+ DB_PORT + "/" + DB_NAME;
	private final String DB_USER = "sengacc";
	private final String DB_PASSWORD = "sengproject";

	private Connection con = null;
	
	/**
	 * Connect to database.
	 * 
	 * @return	true if connection established successfully; false otherwise
	 * @throws	SQLException	Error connecting to database
	 */
	private boolean connect() {
		// Close the connection if it is open for some reason...
		try {
			if (con != null) {
				if (!con.isClosed()) {
					con.close();
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Open a new connection to the db
		try {
			con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		// Return false if the connection couldn't be established
		try {
			if (con.isClosed()) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return true;
	}

	/*
	 * private void disconnect() { try { if (!con.isClosed()) { con.close(); } }
	 * catch (SQLException e) { e.printStackTrace(); }
	 * 
	 * }
	 */

	/**
	 * Queries the database.
	 * 
	 * @param	query		the query command/syntax
	 * @return	ResultSet of query result
	 * @throws	SQLException	Error querying database
	 */
	public ResultSet query(String query) throws SQLException{
		Statement st = null;
		ResultSet result = null;

		if (!connect()) {
			System.out.println("DB CONNECTION ERROR :(");
			return null;
		}
		
		st = con.createStatement();
		result = st.executeQuery(query);

		return result;
	}
	
	/**
	 * Updates the database.
	 * 
	 * @param	query		the query command/syntax
	 * @return	either (1) the row count for SQL Data Manipulation Language (DML) statements or
	 * 			(2) 0 for SQL statements that return nothing
	 * @throws	SQLException	Error querying database
	 */
	public int updateQuery(String query) throws SQLException{
		Statement st = null;
		int result = 9999;

		if (!connect()) {
			System.out.println("DB CONNECTION ERROR :(");
			return 0;
		}
		
		st = con.createStatement();
		result = st.executeUpdate(query);

		return result;
	}
}
