package pl.spring.demo.java_fx.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.spring.demo.java_fx.RestClient.RestClient;
import pl.spring.demo.java_fx.model.BookAdd;

public class BookAddController {
	private static final Logger LOG = Logger.getLogger(BookSearchController.class);
	
	@FXML
	private ResourceBundle resources;
	
	@FXML
	private URL location;
	
	@FXML
	private TextField bookTitleField;
	
	@FXML
	private TextField bookAuthorsField;
	
	@FXML
	private Button addBookButton;
	
	private final BookAdd model = new BookAdd();
	
	private final RestClient restClient = new RestClient();
	
	public BookAddController() {
		LOG.debug("Constructor: bookTitleField = " + bookTitleField);
		LOG.debug("Constructor: bookAuthorsField = " + bookAuthorsField);
	}

	@FXML
	private void initialize() {
		LOG.debug("initialize(): bookTitleField = " + bookTitleField);
		LOG.debug("initialize(): bookAuthorsField = " + bookAuthorsField);


		bookTitleField.textProperty().bindBidirectional(model.titleProperty());
		bookAuthorsField.textProperty().bindBidirectional(model.authorsProperty());

		BooleanBinding booleanBinding = 
			      bookTitleField.textProperty().isEmpty().or(bookAuthorsField.textProperty().isEmpty());
		
		addBookButton.disableProperty().bind(booleanBinding);
	}
	
	@FXML
	private void addButtonAction(ActionEvent event) {
		LOG.debug("'Add' button clicked");
		
		restClient.add(model.getTitle(), model.getAuthors());
		
		Stage stage = (Stage) addBookButton.getScene().getWindow();
		stage.close();
	}
}
