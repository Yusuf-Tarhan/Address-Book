
import java.sql.*;

public class DBConnection
{
	
//-Datenbank Informationen---------------------------------------------------*
   static String URL = "jdbc:mysql://localhost/Adressbuch?useSSL=false";
   static String benutzername = "root";
   static  String passwort = "Rootsecret42!";
   static Statement befehl = null;
   static Connection verbindung = null;

  
/**
 * Building connections to database.
 */
  protected static void verbinden()
 
  {
    
      try 
      {
           verbindung = DriverManager.getConnection (
                                         URL,
                                         benutzername,
                                         passwort);

           befehl = verbindung.createStatement();
           System.out.println("Verbindung hergestellt");

      }
      catch ( Exception ae) 
      {
           System.out.println("Verbindung konnte nicht hergestellt werden :-(");
      }
      
     
    		  
      try 
      {
    	 
          befehl.executeUpdate("CREATE TABLE IF NOT EXISTS daten(IDnummer INTEGER NOT NULL ,name CHAR(30),vorname CHAR(30),Strasse CHAR(50),HausNr INT, Ort CHAR(30), PLZ INT,Handy BIGINT, PRIMARY KEY(IDnummer));");
          System.out.println("Tabelle erfolgreich erzeugt :-)");
      }
     catch (Exception e) 
      {
            e.printStackTrace();
      }     
    		  
  }

/**
 * Close the Database connections.
 */
  protected static void schliessen() {
    try {
      verbindung.close();
    }
    catch (Exception ae) {
      ae.printStackTrace();
    }
  }

}