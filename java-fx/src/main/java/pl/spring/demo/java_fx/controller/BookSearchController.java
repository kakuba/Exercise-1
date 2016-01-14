package pl.spring.demo.java_fx.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import pl.spring.demo.java_fx.RestClient.RestClient;
import pl.spring.demo.java_fx.model.BookSearch;
import pl.spring.demo.to.BookTo;

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
	private Button addButton;
	
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


//		searchButton.disableProperty().bind(titleField.textProperty().isEmpty());
	}



	private void initializeResultTable() {
		titleColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getTitle()));
		authorsColumn
				.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getAuthors()));

		resultTable.setPlaceholder(new Label(resources.getString("table.emptyText")));

	}


	@FXML
	private void searchButtonAction(ActionEvent event) {
		LOG.debug("'Search' button clicked");

		searchButtonAction();
	}
	
	@FXML
	public void addButtonAction(ActionEvent event) throws Exception {               
		LOG.debug("'Add' button clicked");
        try {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pl/spring/demo/java_fx/controller/view/book-add.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root1));  
                
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initOwner(addButton.getScene().getWindow());
                
                stage.show();
                
                stage.setOnHiding(new EventHandler<WindowEvent>() {
    				public void handle(WindowEvent we) {
    					searchButtonAction();
    				}
    			});
        } catch(Exception e) {
           e.printStackTrace();
          }
        
}
	
	private void searchButtonAction() {
		Task<Collection<BookTo>> backgroundTask = new Task<Collection<BookTo>>() {

			@Override
			protected Collection<BookTo> call() throws Exception {
				LOG.debug("call() called");
				
				if(model.getTitle() == null) {
					model.setTitle(" ");
				}
				
				Collection<BookTo> result = restClient.find(model.getTitle());

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
