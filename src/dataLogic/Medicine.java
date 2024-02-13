package dataLogic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
//import org.apache.logging.log4j.*;

public class Medicine  {
	
		//public static Logger log = LogManager.getLogger(Medicine.class.getName());
		
		private Connection connection;
		// Constructor for connection instance
		public Medicine(Connection connection) {
			this.connection = connection;
		}
	
	  	private int medicineID;
	  	private String medicineType;
	    private String medicineName;
	    private String medicineCompany;
	    private int medicineQuantity;
	    private String purchaseDate;
	    private int purchaseRate;
	    private String expiryDate;
		private String supplierName;
		
		
	
	    // Method to Take input of medicine details from the user
	    public void inputMedicineDetails( int medID,String medicineType, String medicineName, String medicineCompany,  int quantity,  
	    		String purchaseDate,  int purchaseRate2,  String expiryDate,  String supplierName) {
//	        Scanner scanner = new Scanner(System.in);
//
//	        System.out.println("Enter Medicine ID: ");
	        this.medicineID = medID;
	        
//	        System.out.println("Enter Medicine Type (Tablet/Syrup/Spray): ");
	        this.medicineType = medicineType;

//	        System.out.println("Enter Medicine Name: ");
	        this.medicineName = medicineName;

//	        System.out.println("Enter Medicine Company: ");
	        this.medicineCompany = medicineCompany;

//	        System.out.println("Enter Medicine Quantity: ");
	        this.medicineQuantity = quantity;

//	        scanner.nextLine(); // Consume the newline character left by nextInt()

//	        System.out.println("Enter Medicine Purchase Date (YYYY-MM-DD): ");
	        this.purchaseDate = purchaseDate;

//	        System.out.println("Enter Medicine Purchase Rate: ");
	        this.purchaseRate = purchaseRate2;

//	        scanner.nextLine(); // Consume the newline character left by nextDouble()

//	        System.out.println("Enter Medicine Expiry Date (YYYY-MM-DD): ");
	        this.expiryDate = expiryDate;

//	        System.out.println("Enter Supplier Name: ");
	        this.supplierName = supplierName;
	        

	    }
	
	    
	// Method to insert a new medicine Details
	 public void insertMedicineDetails() {
	        try {
	        	 Statement statement = connection.createStatement();
	        	 
	        	 	// SQL query to insert medicine details
	                String insertQuery = "INSERT INTO medicine" +
	                        "(medID, medicineType, medicineName, medicineCompany, Quantity, purchaseDate, purchaseRate, expiryDate, suppliername) " +
	                        "VALUES ('" + this.medicineID + "', '" + this.medicineType + "', '" + this.medicineName + "', '" +
	                        this.medicineCompany + "', " + this.medicineQuantity + ", '" + this.purchaseDate +
	                        "', " + this.purchaseRate + ", '" + this.expiryDate + "', '" + this.supplierName + "')";
	                
	                // Validation Check
	                if (medicineQuantity > 100 && isDuplicateCombination(medicineName, supplierName)) {
	                    System.out.println("Error: Duplicate Supplier and Medicine Name combination for quantity > 100.");
	                   // log.fatal("Error: Duplicate Supplier and Medicine Name combination for quantity > 100");
	                    return; // Do not proceed with the insertion
	                }
	                
	                // Execute the query
	                int rowEffected = statement.executeUpdate(insertQuery);
	                
	                if(rowEffected>0) {
	                	System.out.println("Medicine details inserted into the database sucessFully");
	                }else {
	                	System.out.println("Data Not Inserted :( ");
	                }
	                
	        } catch (SQLException e) {
	            System.out.println("Error executing query: " + e.getMessage());
	        }
	    }
	 
	 
	 
	 
	 
	 // Duplicate Combination Validation Helper Function
	 public boolean isDuplicateCombination(String medicineName, String supplierName) {
		    try {
		        Statement statement = connection.createStatement();

		        String checkQuery = "SELECT COUNT(*) FROM medicine WHERE medicineName = '" + medicineName +
		                            "' AND suppliername = '" + supplierName + "'";
		        ResultSet resultSet = statement.executeQuery(checkQuery);

		        if (resultSet.next()) {
		            int count = resultSet.getInt(1);
		            return count > 0; // If count > 0, the combination already exists
		        }

		    } catch (SQLException e) {
		        System.out.println("Error executing query: " + e.getMessage());
		    }
		    return false; // Assume no duplicate combination (error case)
		}

}
