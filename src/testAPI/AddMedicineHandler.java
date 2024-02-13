package testAPI;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import dataLogic.DBconnection;
import dataLogic.Medicine;

public class AddMedicineHandler implements HttpHandler {
	
	Medicine medicineDetail = new Medicine(DBconnection.connection);

	

	public void handle(HttpExchange exchange) throws IOException {
        // Get the output stream to write the response
        OutputStream os = exchange.getResponseBody();
        
        Map<String, String> queryParams = getQueryParams(exchange.getRequestURI().getQuery());
        
        System.out.println(queryParams);
        
        // Extract parameters from the query
        String medIDstr = queryParams.get("medID");
        String medicineType = queryParams.get("medicineType");
        String medicineName = queryParams.get("medicineName");
        String medicineCompany = queryParams.get("medicineCompany");
        String quantitystr = queryParams.get("quantity") ;
        String purchaseRatestr =  queryParams.get("purchaseRate");
        String purchaseDate = queryParams.get("purchaseDate");
        String expiryDate = queryParams.get("expiryDate");
        String supplierName = queryParams.get("supplierName");
        
        int medID = 0;
        int quantity = 0;
        int purchaseRate = 0;
        
        try {
        	medID = Integer.parseInt(medIDstr);
            quantity = Integer.parseInt(quantitystr);
            purchaseRate = Integer.parseInt(purchaseRatestr);
            
        } catch (NumberFormatException e) {
            // Handle the exception, e.g., log an error message 
            e.printStackTrace();
        }
        
        try {
        	
        	medicineDetail.inputMedicineDetails(medID, medicineType,medicineName, medicineCompany, quantity, purchaseDate, purchaseRate, expiryDate, supplierName);
        	medicineDetail.insertMedicineDetails();
            String jsonResponse = "{\"message\": \"Data Added and saved in DB successfully!\"}";
            //System.out.println("Data Updated in DB successfully");
            sendResponse(exchange, jsonResponse);
        } catch (Exception e) {
            e.printStackTrace();
            String errorResponse = "{\"error\": \"Failed to Add data in DB.\"}";
            sendResponse(exchange, errorResponse);
        }

        
    	
        
        
        String jsonResponse = "{\"message\": \"Data received and saved in DB successfully!\"}";
        System.out.println("Data saved in DB successfully");        
        sendResponse(exchange, jsonResponse);
 
    }
	
	 	// Helper method to parse query parameters
    	private Map<String, String> getQueryParams(String query) throws UnsupportedEncodingException {
    		Map<String, String> params = new HashMap();
    			if (query != null) {
    				String[] pairs = query.split("&");
    				for (String pair : pairs) {
    					int idx = pair.indexOf("=");
    					String key = URLDecoder.decode(pair.substring(0, idx), "UTF-8");
    					String value = URLDecoder.decode(pair.substring(idx + 1), "UTF-8");
    					params.put(key, value);
    				}
    			}
        return params;
    	}
   
    	// Helper method to send the HTTP response
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
