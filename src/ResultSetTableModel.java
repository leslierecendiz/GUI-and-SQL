
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.table.AbstractTableModel;




public class ResultSetTableModel extends AbstractTableModel 
{
   private Connection connection;
   private Statement statement;
   private ResultSet results;
   private ResultSetMetaData metaData;
   private int numberOfRows;

   //will check connection status
   private boolean databaseStatus = false;
   
  
   public ResultSetTableModel(Connection c, String query ) 
      throws SQLException, ClassNotFoundException
   {         
      
      connection = c;

      // create Statement to query through database
      statement = connection.createStatement( 
         ResultSet.TYPE_SCROLL_INSENSITIVE,
         ResultSet.CONCUR_READ_ONLY );

      //change status
      databaseStatus = true;
      
      StringBuilder firstLetter = new StringBuilder();
      firstLetter.append(query.charAt(0));
      if(firstLetter.toString().equalsIgnoreCase("s"))
      {
    	  
    	  setQuery( query );
      }
      else
    	  setUpdate(query);
   } 

   public Class getColumnClass( int column ) throws IllegalStateException {
//set columns 
      if ( !databaseStatus ) 
         throw new IllegalStateException( "Not Connected to Database" );

      try 
      {
         String className = metaData.getColumnClassName( column + 1 );
         
         return Class.forName( className );
      } 
      catch ( Exception exception ) 
      {
         exception.printStackTrace();
      } 
      
      return Object.class; 
   } 

   //  number of columns in the results
   public int getColumnCount() throws IllegalStateException
   {   
      
      if ( !databaseStatus ) 
         throw new IllegalStateException( "Not Connected to Database" );

      try  {
         return metaData.getColumnCount(); 
      }
      catch ( SQLException sqlException ) 
      {
         sqlException.printStackTrace();
      } 
      
      return 0; 
   } 

   public String getColumnName( int column ) throws IllegalStateException {    
      if ( !databaseStatus ) 
         throw new IllegalStateException( "Not Connected to Database" );

      try 
      {
         return metaData.getColumnName( column + 1 );  
      }
      catch ( SQLException sqlException ) 
      {
         sqlException.printStackTrace();
      }
      
      return ""; 
   } 

   
   public int getRowCount() throws IllegalStateException {   
      if ( !databaseStatus ) 
         throw new IllegalStateException( "Not Connected to Database" );
 
      return numberOfRows;
   } 

  
   public Object getValueAt( int row, int column ) 
      throws IllegalStateException  {
      // ensure database connection is available
      if ( !databaseStatus ) 
         throw new IllegalStateException( "Not Connected to Database" );

      // obtain a value at specified ResultSet row and column
      try  {
		   results.next();  
         results.absolute( row + 1 );
         return results.getObject( column + 1 );
      } catch ( SQLException sqlException ) {
         sqlException.printStackTrace();
      } 
      
      return ""; 
   } 
   

   public void setQuery( String query )  throws SQLException, IllegalStateException   {
      // check if database connection is available
      if ( !databaseStatus ) 
         throw new IllegalStateException( "Not Connected to Database" );


      results = statement.executeQuery( query );


      metaData = results.getMetaData();


      results.last();                   
      numberOfRows = results.getRow();     
      
      fireTableStructureChanged();
   } 



   public void setUpdate( String query )  throws SQLException, IllegalStateException  {
	    int specificQuery;

      if ( !databaseStatus ) 
         throw new IllegalStateException( "Not Connected to Database" );

      specificQuery = statement.executeUpdate( query );  

      fireTableStructureChanged();
   } 


         //close connection
   public void disconnectFromDatabase()  {              
      if ( !databaseStatus )                  
         return;
         
      try  {                                            
         statement.close();                        
         connection.close();                       
      } catch ( SQLException sqlException )  {                                            
         sqlException.printStackTrace();           
      } finally   {                                            
         databaseStatus = false;              
      }                             
   }     
}  



