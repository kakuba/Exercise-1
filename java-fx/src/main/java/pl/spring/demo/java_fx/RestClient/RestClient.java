package pl.spring.demo.java_fx.RestClient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import pl.spring.demo.to.BookTo;

public class RestClient {
	
	public Collection<BookTo> find(String title) {
		try {
			
			Collection<BookTo> bookCollection = new ArrayList<>();
			
			Client client = Client.create();
			
			WebResource webResource = client
					.resource("http://localhost:9721/workshop/books-by-title");
//			http://localhost:9721/workshop/books-by-title?titlePrefix=P
			
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
}

