package dataLogic;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

//import org.apache.logging.log4j.Logger;

public class MedicineManager {

	private Connection connection;
	//public Logger log;

	// Constructor for connection instance
	public MedicineManager(Connection connection) {
		this.connection = connection;
	}

	Scanner sc = new Scanner(System.in);

	// Search Medicine information by name Method

	public ResultSet searchMedicineWithName() {
		System.out.print("Enter Medine name : ");
		String medicineName = sc.nextLine();
		
		ResultSet resultSet = null;

		try {
			Statement statement = connection.createStatement();

			String searchQuery = "SELECT * FROM medicine WHERE medicineName LIKE '%" + medicineName + "%'";
			statement.executeQuery(searchQuery);
			 resultSet = statement.executeQuery(searchQuery);
			
			System.out.printf("%-6s%-15s%-20s%-20s%-10s%-15s%-15s%-15s%-20s\n", "medID", "medicineType", "medicineName",
					"medicineCompany", "Quantity", "purchaseDate", "purchaseRate", "expiryDate", "suppliername");

			while (resultSet.next()) {
				System.out.printf("%-6s%-15s%-20s%-20s%-10s%-15s%-15s%-15s%-20s\n", resultSet.getString("medID"),
						resultSet.getString("medicineType"), resultSet.getString("medicineName"),
						resultSet.getString("medicineCompany"), resultSet.getString("Quantity"),
						resultSet.getString("purchaseDate"), resultSet.getString("purchaseRate"),
						resultSet.getString("expiryDate"), resultSet.getString("suppliername"));
			}

		} catch (SQLException e) {
			//log.error("Error executing query: {}", e.getMessage());
			System.out.println("Error executing query: " + e.getMessage());
		}
		
		return resultSet;
	}
	
	
	
	

	// Method to Update Medicine Details

	public void updateMedicineDetails(int medID ,int fieldNum, String newValue) {
//		System.out.print("Enter Medine ID to update Details : ");
		int medicineID = medID;

		// Consume the newline character left in the buffer
//		sc.nextLine();
//
//		System.out.println("select below fields to update :  ");
		String[] medicineDetail = { "medicineName", "medicineCompany", "Quantity", "purchaseDate", "purchaseRate",
				"expiryDate", "suppliername" };
//
//		int number = 0;
//		for (String s : medicineDetail) {
//			System.out.println(++number + " " + s);
//		}

//		System.out.print("Enter the number corresponding to the field to update : ");
//		int selectedFieldNumber = sc.nextInt();
		
		int selectedFieldNumber = fieldNum;

		// Consume the newline character left in the buffer
//		sc.nextLine();

		String medicineColumnToUpdate = medicineDetail[selectedFieldNumber - 1];

//		System.out.print("Enter New Value to be updated :  ");
//		String newMedicineDataToUpdate = sc.nextLine();
		
		String newMedicineDataToUpdate = newValue;

		try {
			Statement statement = connection.createStatement();
			// Construct the UPDATE query with proper syntax
			String updateQuery = "UPDATE medicine SET " + medicineColumnToUpdate + " = '" + newMedicineDataToUpdate
					+ "' WHERE medID = " + medicineID;

			int rowsAffected = statement.executeUpdate(updateQuery);

			if (rowsAffected > 0) {
				System.out.println("Data Updated Successfully");
			} else {
				System.out.println("Zero Rows Affected");
			}

		} catch (SQLException e) {
			System.out.println("Error executing query: " + e.getMessage());
		}
	}
	
	
	

	// Method to Display all available medicines

	public ResultSet viewAvailableMedicine() {
			
		ResultSet resultSet = null;
		
		try {
			Statement statement = connection.createStatement();

			String searchQuery = "SELECT * FROM medicine WHERE Quantity > 0";
			resultSet = statement.executeQuery(searchQuery);

//			System.out.printf("%-6s%-15s%-20s%-20s%-10s%-15s%-15s%-15s%-20s\n", "medID", "medicineType", "medicineName",
//					"medicineCompany", "Quantity", "purchaseDate", "purchaseRate", "expiryDate", "suppliername");
//
//			while (resultSet.next()) {
//				System.out.printf("%-6s%-15s%-20s%-20s%-10s%-15s%-15s%-15s%-20s\n", resultSet.getString("medID"),
//						resultSet.getString("medicineType"), resultSet.getString("medicineName"),
//						resultSet.getString("medicineCompany"), resultSet.getString("Quantity"),
//						resultSet.getString("purchaseDate"), resultSet.getString("purchaseRate"),
//						resultSet.getString("expiryDate"), resultSet.getString("suppliername"));
//			}

		} catch (SQLException e) {
			System.out.println("Error executing query: " + e.getMessage());
		}
		
		return resultSet;
	}
	
	public void deleteMedicine(int id) {
		
		try {
			Statement statement = connection.createStatement();

			String deleteQuery = "DELETE FROM medicine WHERE medID = " + id;
			statement.executeUpdate(deleteQuery);
			
			System.out.println("Medicine with id " + id + " deleted successfully");

		} catch (SQLException e) {
			System.out.println("Error executing query: " + e.getMessage());
		}
		
	}

}
