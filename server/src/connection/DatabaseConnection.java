package connection;

import java.sql.SQLException;
import java.sql.Connection;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;
    
    public static DatabaseConnection getInstance(){
       if(instance == null){
           instance = new DatabaseConnection();
       } 
       return instance;
    }
    
    private DatabaseConnection(){
        
    }
    
    public void connectToDatabase()throws SQLException{
        String server = "localhost";
        String port = "3306";
        String database = "chat_application";
        String username = "root";
        String password = "Nguyenluric2009";
        connection = java.sql.DriverManager.getConnection("jdbc:mysql://" + server + ":" + port + "/" + database, username, password);
    }
    
    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
