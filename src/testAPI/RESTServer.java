package testAPI;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class RESTServer {
	
	 public static void main(String[] args) throws IOException {
	        // Create HttpServer instance and bind it to a specific port
	        HttpServer server = HttpServer.create(new InetSocketAddress(8090), 0);
	        
	        
	        // Test Handler
	        server.createContext("/", new MedicineData());
	        
	        // View Available Medicine Handler
	        server.createContext("/medicine", new ViewMedicineHandler()); 
	        
	        // Add Medicine Details Handler
	        server.createContext("/addmedicine", new AddMedicineHandler());
	        
	        // Update Medicine Details Handler
	        server.createContext("/update", new UpdateMedHandler());
	        
	        // Delete Medicine Details Handler
	        server.createContext("/delete", new DeleteMedicineHandler());

	        // Set the executor to null (use default executor)
	        server.setExecutor(null);

	        // Start the server
	        server.start();

	        System.out.println("Server Started at port 8090");
	    }
}
    

