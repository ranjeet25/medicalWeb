package testAPI;
import dataLogic.MedicineManager;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import dataLogic.DBconnection;


public class DeleteMedicineHandler implements HttpHandler {
	
	MedicineManager med = new MedicineManager(DBconnection.connection);

	

	public void handle(HttpExchange exchange) throws IOException {
        // Get the output stream to write the response
        
		Map<String, String> queryParams = getQueryParams(exchange.getRequestURI().getQuery());
		
        //System.out.println(medIDstr);
		 String medIDstr = queryParams.get("medID");
   
        int medID = 0;

        try {
            medID = Integer.parseInt(medIDstr);
        } catch (NumberFormatException e) {
            // Handle the exception, e.g., log an error message
            e.printStackTrace();
        }

        // Add proper exception handling here
        try {
        	med.deleteMedicine(medID);
            String jsonResponse = "{\"message\": \"Data Deleted in DB successfully!\"}";
            //System.out.println("Data Deleted in DB successfully");
            sendResponse(exchange, jsonResponse);
        } catch (Exception e) {
            e.printStackTrace();
            String errorResponse = "{\"error\": \"Failed to Delete data in DB.\"}";
            sendResponse(exchange, errorResponse);
        }
 
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
