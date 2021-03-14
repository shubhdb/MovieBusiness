package movie;

	import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.SQLException;

public class DBConnections {
	Connection con=null;
		
	public Connection getConnection() {
		if(con==null)
		{	
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","db_shubh","db_shubh");
				System.out.println("Connection created");
			return con; 
			}
			catch (ClassNotFoundException e) {
				System.out.println("Class not found try again");
			}
			catch (SQLException e) {
				System.out.println("Some error occurred try again");
			}
		}
		
		return con;
	}
	
	public void stopConnection(Connection con) {
		try {
			con.close();
		} catch (SQLException e) {
			
			System.out.println("Some error occurred try again");
		}
	}
	
}


