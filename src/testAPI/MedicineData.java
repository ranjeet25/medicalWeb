package testAPI;
import java.util.*;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;


public class MedicineData implements HttpHandler {
	
	
	 public void handle(HttpExchange exchange) throws IOException {
         // Get the output stream to write the response
         OutputStream os = exchange.getResponseBody();

         // Set the response headers
         exchange.getResponseHeaders().set("Content-Type", "application/json");
         exchange.sendResponseHeaders(200, 0);

         // Write the JSON response
         String jsonResponse = "{\"message\": \" World!\"}";
         os.write(jsonResponse.getBytes());

         // Close the output stream
         os.close();
     }

}
