package pl.spring.demo.java_fx.RestClient;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import pl.spring.demo.java_fx.controller.BookSearchController;
import pl.spring.demo.to.BookTo;

public class RestClient {
	private static final Logger LOG = Logger.getLogger(BookSearchController.class);
	public Collection<BookTo> find(String title) {
		try {
			Collection<BookTo> bookCollection = new ArrayList<>();
			
			Client client = Client.create();
			
			WebResource webResource = client
					.resource("http://localhost:9721/workshop/books-by-title");
			
			ClientResponse response = webResource.queryParam("titlePrefix", title).accept("APPLICATION/JSON")
					.get(ClientResponse.class);
			
			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}
			
			String output = response.getEntity(String.class);
			
			Gson gson = new Gson();
			
			BookTo[] bookArray = gson.fromJson(output, BookTo[].class);
			
			for (BookTo bookTo : bookArray) {
				bookCollection.add(bookTo);
			}
			
			
			return bookCollection;
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public void add(String title, String authors) {
		try {
				
				Gson gson = new Gson();
				BookTo newBook = new BookTo(title, authors);
				String input = gson.toJson(newBook);
				
				Client client = Client.create();
				
				WebResource webResource = client
						.resource("http://localhost:9721/workshop/book");
				
				ClientResponse response = webResource.type("APPLICATION/JSON")
						.post(ClientResponse.class, input);
				
				if (response.getStatus() != 201 && response.getStatus() != 200) {
					throw new RuntimeException("Failed : HTTP error code : "
							+ response.getStatus());
				}
				
				showInformation(title, authors);
				
				String output = response.getEntity(String.class);
				LOG.debug("New book(" + output + ") was added");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void showInformation(String title, String authors) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText(null);
		alert.setContentText("New book wad added :)\nWith title: " + title + "\nWith authors: " + authors);

		alert.showAndWait();
		
	}
}

