package pl.spring.demo.java_fx.controller;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import pl.spring.demo.java_fx.RestClient.RestClient;
import pl.spring.demo.java_fx.model.BookSearch;
import pl.spring.demo.to.BookTo;
import pl.spring.demo.web.rest.BookRestService;

public class BookSearchController {
	private static final Logger LOG = Logger.getLogger(BookSearchController.class);
	
	@FXML
	private ResourceBundle resources;
	
	@FXML
	private URL location;
	
	@FXML
	private TextField titleField;
	
	@FXML
	private Button searchButton;
	
	@FXML
	private TableView<BookTo> resultTable;
	
	@FXML
	private TableColumn<BookTo, String> titleColumn;

	@FXML
	private TableColumn<BookTo, String> authorsColumn;
	
	private final BookSearch model = new BookSearch();
	
	private final RestClient restClient = new RestClient();
	
	public BookSearchController() {
		LOG.debug("Constructor: nameField = " + titleField);
	}

	@FXML
	private void initialize() {
		LOG.debug("initialize(): nameField = " + titleField);


		initializeResultTable();

		titleField.textProperty().bindBidirectional(model.titleProperty());
		resultTable.itemsProperty().bind(model.resultProperty());


		searchButton.disableProperty().bind(titleField.textProperty().isEmpty());
	}



	private void initializeResultTable() {
		titleColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getTitle()));
		// nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		authorsColumn
				.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getAuthors()));

		resultTable.setPlaceholder(new Label(resources.getString("table.emptyText")));

	}


	@FXML
	private void searchButtonAction(ActionEvent event) {
		LOG.debug("'Search' button clicked");

		searchButtonAction();
	}

	private void searchButtonAction() {
		Task<Collection<BookTo>> backgroundTask = new Task<Collection<BookTo>>() {

			@Override
			protected Collection<BookTo> call() throws Exception {
				LOG.debug("call() called");
//				Collection<BookTo> result = new ArrayList<>();
				Collection<BookTo> result = restClient.find(model.getTitle());

				
				
//				Collection<BookTo> result = dataProvider.findPersons( //
//						model.getName(), //
//						model.getSex().toSexVO());

				return result;
			}

			@Override
			protected void succeeded() {
				LOG.debug("succeeded() called");

				Collection<BookTo> result = getValue();

				model.setResult(new ArrayList<BookTo>(result));

				resultTable.getSortOrder().clear();
			}
		};

		new Thread(backgroundTask).start();
	}


}
