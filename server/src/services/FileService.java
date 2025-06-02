package services;

import app.MessageType;
import connection.DatabaseConnection;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import models.FileModel;
import models.PackageSenderModel;
import models.ReceiveImageModel;
import models.ReceiverFileModel;
import models.SendMessageModel;

public class FileService {
    
    public FileService(){
        this.con = DatabaseConnection.getInstance().getConnection();
        this.fileReceivers = new HashMap<>();
    }
    
    public FileModel addFileReceiver(String fileExtension) throws SQLException{
        FileModel data;
        PreparedStatement p = con.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
        p.setString(1, fileExtension);
        p.execute();
        ResultSet r = p.getGeneratedKeys();
        r.first();
        int fileID = r.getInt(1);
        data = new FileModel(fileID, fileExtension);
        r.close();
        p.close();
        return data;
    }
    
    private void updateBlurHashDone(int fileID, String blurHash) throws SQLException{
        
    }
    
    public void updateDone(int fileID) throws SQLException{
        
    }
    
    public void initFile(FileModel file, SendMessageModel message) throws SQLException, FileNotFoundException{
        fileReceivers.put(file.getFileID(), new ReceiverFileModel(message, toFileObject(file)));
    }
    
    public void receiveFile(PackageSenderModel dataPackage) throws IOException{
        if(!dataPackage.isFinish()){
            fileReceivers.get(dataPackage.getFileID()).writeFile(dataPackage.getData());
        } else{
            fileReceivers.get(dataPackage.getFileID()).close();
        }
    }
    
    public SendMessageModel closeFile(ReceiveImageModel dataImage)throws IOException, SQLException{
        ReceiverFileModel file = fileReceivers.get(dataImage.getFileID());
        if(file.getMessage().getMessageType() == MessageType.IMAGE.getValue()){
            
            // Image file
            // So create blurhash image string
        } else{
            updateDone(dataImage.getFileID());
        }
        return file.getMessage();
    }
    
    private File toFileObject(FileModel file){
        return new File(PATH_FILE + file.getFileID() + file.getFileExtension());
    }
    
    // SQL
    private final String PATH_FILE = "server_data/";
    private final String INSERT = "insert into files (FileExtension) values (?)";
    
    // Instance
    private final Connection con;
    private final Map<Integer, ReceiverFileModel> fileReceivers;
}
