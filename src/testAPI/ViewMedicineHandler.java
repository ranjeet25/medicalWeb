package testAPI;

import dataLogic.DBconnection;
import dataLogic.MedicineManager;
import org.json.JSONArray;
import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewMedicineHandler implements HttpHandler {

    private final DBconnection createConnection = new DBconnection();
    private final MedicineManager test = new MedicineManager(DBconnection.connection);
    JSONObject viewMedJSON = new JSONObject();

    public void handle(HttpExchange exchange) throws IOException {
        // Get the output stream to write the response
        OutputStream os = exchange.getResponseBody();

        try {
            // Create a new ResultSet for each request
            ResultSet resultSet = test.viewAvailableMedicine();

            // Write the JSON response
            viewMedJSON = new JSONObject();
            viewMedJSON.put("medicinesDetails", getMedResult(resultSet));

            String jsonResponse = viewMedJSON.toString();
            sendResponse(exchange, jsonResponse);
            
        } catch (SQLException e) {
            // Handle SQLException appropriately
            e.printStackTrace();

            // Set the response headers for error
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(500, 0);

            // Write the error response
            String errorResponse = "{\"error\": \"Failed to fetch data from DB.\"}";
            os.write(errorResponse.getBytes());
        } finally {
            // Close the output stream
            os.close();
        }
    }

    private JSONArray getMedResult(ResultSet resultSet) throws SQLException {
        JSONArray medResult = new JSONArray();
            while (resultSet.next()) {
                JSONObject med = new JSONObject();
                med.put("medID", resultSet.getString("medID"));
                med.put("medicineType", resultSet.getString("medicineType"));
                med.put("medicineName", resultSet.getString("medicineName"));
                med.put("medicineCompany", resultSet.getString("medicineCompany"));
                med.put("Quantity", resultSet.getString("Quantity"));
                med.put("purchaseDate", resultSet.getString("purchaseDate"));
                med.put("purchaseRate", resultSet.getString("purchaseRate"));
                med.put("expiryDate", resultSet.getString("expiryDate"));
                med.put("suppliername", resultSet.getString("suppliername"));

                medResult.put(med);
            }
        return medResult;
    }
    
		private void sendResponse(HttpExchange exchange, String response) throws IOException {
        // Get the output stream to write the response
    		OutputStream os = exchange.getResponseBody();

        // Set the response headers
    		exchange.getResponseHeaders().set("Content-Type", "application/json");
    		exchange.sendResponseHeaders(200, response.length());

        // Write the JSON response
    		os.write(response.getBytes());

        // Close the output stream
    		os.close();
    	}
}
