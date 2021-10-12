/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.util.ResourceBundle;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;


import javafx.embed.swing.SwingNode;
import javax.swing.JComponent;


import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 * FXML Controller class
 *
 * @author J
 */
public class QueryAppfxmlController implements Initializable {
    //database, username, and password
    private final String DATABASE_URL = "jdbc:derby:books";
    private final String USERNAME = "deitel";
    private final String PASSWORD = "deitel";
    
    //what we need to make JTable
    private QueryAppTable usefulTable;
    // deafult query        
    private final String DEFAULT_QUERY = "SELECT * FROM authors";
    
    //used for sorter
    private TableRowSorter<TableModel> sorter;
    
    
    

    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private BorderPane queryPnae;
    @FXML
    private Label predefinedLabel;
    @FXML
    private ComboBox<PredefinedQuery> predefinedBox;
    //add observable list?
    private ObservableList<PredefinedQuery> predefinedQuerys = FXCollections.observableArrayList();
    @FXML
    private Label customLabel;
    @FXML
    private Button submitButton;
    @FXML
    private TextArea customQueryText;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        customQueryText.setText(DEFAULT_QUERY);
        //Initialize PredefinedQueries
        predefinedQuerys.add(new PredefinedQuery());
        
        //Gets Author's for ComboBox
        try{
            usefulTable = new QueryAppTable(DATABASE_URL, USERNAME, PASSWORD, "SELECT LastName, FirstName FROM authors");
        }
        catch(SQLException sqlException){
            System.out.println("Error 1.");
            usefulTable.disconnectFromDataBase();
            System.exit(1);
        }
        try{
            usefulTable.getUsefulData().first();
            while(usefulTable.getUsefulData().next()){
                try{
                    System.out.println("loops");
                    predefinedQuerys.add(new PredefinedQuery(usefulTable.getUsefulData().getString("LastName"), usefulTable.getUsefulData().getString("FirstName")));
                }
                catch (SQLException sqlException){
                    System.out.println("Error 2");
                }           
            }
        }
        catch(SQLException sqlException){
            System.out.println("Error 3.");
        }
        //End getting author from ComboBox
        
        
        //Disconnect so can open new query
        usefulTable.disconnectFromDataBase();
        
        
        //Getting Titles for ComboBox
        try{
            usefulTable = new QueryAppTable(DATABASE_URL, USERNAME, PASSWORD, "SELECT Title FROM titles");
        }
        catch(SQLException sqlException){
            System.out.println("Error 4.");
            usefulTable.disconnectFromDataBase();
            System.exit(1);
        }
        try{
            usefulTable.getUsefulData().first();
            while(usefulTable.getUsefulData().next()){
                System.out.println("loops 2");
                predefinedQuerys.add(new PredefinedQuery(usefulTable.getUsefulData().getString("Title")));
            }        
        }
        catch(SQLException sqlException){
            System.out.println("Error 5.");
        }
        //End getting Titles for comboBox
        
        
        //disconenct
        usefulTable.disconnectFromDataBase();
        
        //sets all items in predefinedQuerys to the combobox
        predefinedBox.setItems(predefinedQuerys);
        
        //Start making the JTable
        try {
            usefulTable = new QueryAppTable(DATABASE_URL, USERNAME, PASSWORD, "SELECT * FROM authors");
            System.out.println("Finished makeing table");
            JTable jreTable = new JTable(usefulTable);
            System.out.println("Finished makeing Jtable");
            
            //how important is the sort
            //sorter = new TableRowSorter<TableModel>(usefulTable);
            //jreTable.setRowSorter(sorter);
            
            System.out.println("sorted");
            
            final SwingNode swingNode = new SwingNode();
            System.out.println("Finished makeing node");
            swingNode.setContent(new JScrollPane(jreTable));
            System.out.println("Finished makeing scrolltable");
            mainBorderPane.setCenter(swingNode);
            System.out.println("set to center");
        } catch (SQLException ex) {
            System.out.println("Error 6.");
        }
        

        
    }    

    @FXML
    private void comboBoxAction(ActionEvent event) {
        try{
            usefulTable.setQuery(predefinedBox.getSelectionModel().getSelectedItem().getQuery());
        }
        catch(SQLException sqlException){
            System.out.println("Error with list");
        }
    }

    @FXML
    private void submitClicked(ActionEvent event) {
        try{
            usefulTable.setQuery(customQueryText.getText());
        }
        catch(SQLException sqlException){
            System.out.println("Error with Button.");
            usefulTable.disconnectFromDataBase();
            System.exit(1);
            
        }
    }
    
}
