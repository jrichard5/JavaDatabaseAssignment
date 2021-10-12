/**
 * @author Deitel, P.J & Deitel, H. (2017). Java How to Program, Early Objects. [Bookshelf Ambassadored]. Retrieved from https://ambassadored.vitalsource.com/#/books/9780134748559/ 
 * 
 * Reorder and changes made by Jeremiah Richard October 24, 2020
 */
import javax.swing.table.AbstractTableModel;
import java.sql.*;    //1st step

/*
Order of methods
1.getRowCount  (abstract from AbstractTable)  need getRowCount from setQuery
2.setQuery  (needed to add boolean if connected.  Needed to make constructor to get statement
3.Constructor (Needed connection, statment) (Go back to setQuery)
4.setQuery ---- no loose ends, to next abstract method from AbstractTable "getColumnCount"
5.getColumnCount(abstract from AbstractTable)
6.getValueAt(abstract from AbstractTable)
7.fireTableStructureChanged() --- from book
8.Close methods ---- from book


9.  add a get resultData method

10.  added getColumnName  (while not necessary, whille get A, B, C as column headers)
*/



public class QueryAppTable extends AbstractTableModel{

    
    private boolean connectedToDatabase = false;
    
    private final Connection connection;
    private final Statement formatStatement;
    
    private ResultSet usefulData;
    private ResultSetMetaData usefulDataMetaData;
    private int numberOfRows;
    
    
    //constructor
    public QueryAppTable(String url, String userName, String password, String query) throws SQLException{
        
        //connects to database using parameters  //3rd step connect to it
        connection = DriverManager.getConnection(url, userName, password);  
        
        //fourth step.  Doesn't update result set automatically.  Cannot update it
        formatStatement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); 
        //now that we are connected, update connected to true
        connectedToDatabase = true;
        
        //call setquery method with query parameter
        setQuery(query);
        
        
    }
    
    
        //implemented from AbstractTableModel
    @Override
    public int getRowCount() {
        return numberOfRows;        //need to make a query method to get number of rows
    }
    
    public void setQuery(String query) throws SQLException, IllegalStateException{
        if(!connectedToDatabase){
            throw new IllegalStateException("Connection: False");  //need new
        }
        usefulData = formatStatement.executeQuery(query);
        
        usefulDataMetaData = usefulData.getMetaData();
        
        usefulData.last();
        numberOfRows = usefulData.getRow();
        
        fireTableStructureChanged(); //notify JTable
    }
    
    
    //Implemented from AbstractTableModel
    @Override
    public int getColumnCount() throws IllegalStateException{
        if (!connectedToDatabase){
            throw new IllegalStateException("Connection: False");
        }
        try{
            return usefulDataMetaData.getColumnCount();
        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        //incase try fails to return a statement
        return 0;

    }

    //Implemented from AbstractTableModel
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) throws IllegalStateException {
        if(!connectedToDatabase){
            throw new IllegalStateException("Connection: False");
        }
        //Result sets (usefulData) starts at 1
        try{
            usefulData.absolute(rowIndex + 1);
            return usefulData.getObject(columnIndex +1);
        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        //in case try does not return an object
        return "";
        
    }
    
    public void disconnectFromDataBase(){
        if(connectedToDatabase){
            try{
                usefulData.close();
                formatStatement.close();
                connection.close();
            }
            catch(SQLException sqlException){
                sqlException.printStackTrace();
            }
            finally {
                connectedToDatabase = false;
            }
        }
    }
    
    public ResultSet getUsefulData(){
        return usefulData;
    }
    
    @Override
    public String getColumnName(int column) throws IllegalStateException{
        if(!connectedToDatabase){
            throw new IllegalStateException("Connection: False");
        }
        try{
            return usefulDataMetaData.getColumnName(column + 1);
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return "";
        
    }
            
    

    
}
