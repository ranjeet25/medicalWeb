package dataLogic;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//import org.apache.logging.log4j.*;

public class DBconnection {
	
	//public static Logger log = LogManager.getLogger(DBconnection.class.getName());
	
	// Instance variables for connection
    public static Connection connection;

    public DBconnection() {
        final String dburl = "jdbc:mysql://localhost:3306/medicalstockmngmt";
        final String dbusername = "root";
        final String dbpassword = "ranjeet@sql";

        try {
            // Create DB connection
            connection = DriverManager.getConnection(dburl, dbusername, dbpassword);
   
        } catch (SQLException e) {
        	//log.error("Error creating DB connection");
            System.out.println("Failed to connect to DB " + e.getMessage());
        }
    }

}
