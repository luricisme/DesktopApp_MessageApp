package services;

import app.MessageType;
import connection.DatabaseConnection;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import models.FileModel;
import models.PackageSenderModel;
import models.ReceiveImageModel;
import models.FileReceiverModel;
import models.FileSenderModel;
import models.SendMessageModel;
import views.swing.blurHash.BlurHash;

public class FileService {

    public FileService() {
        this.con = DatabaseConnection.getInstance().getConnection();
        this.fileReceivers = new HashMap<>();
        this.fileSenders = new HashMap<>();
    }

    public FileModel addFileReceiver(String fileExtension) throws SQLException {
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

    private void updateBlurHashDone(int fileID, String blurHash) throws SQLException {
        PreparedStatement p = con.prepareStatement(UPDATE_BLUR_HASH_DONE);
        p.setString(1, blurHash);
        p.setInt(2, fileID);
        p.execute();
        p.close();
    }

    public void updateDone(int fileID) throws SQLException {
        PreparedStatement p = con.prepareStatement(UPDATE_DONE);
        p.setInt(1, fileID);
        p.execute();
        p.close();
    }

    public void initFile(FileModel file, SendMessageModel message) throws SQLException, FileNotFoundException {
        fileReceivers.put(file.getFileID(), new FileReceiverModel(message, toFileObject(file)));
    }

    public FileModel getFile(int fileID) throws SQLException {
        PreparedStatement p = con.prepareStatement(GET_FILE_EXTENSION);
        p.setInt(1, fileID);
        ResultSet r = p.executeQuery();
//        r.first();
//        String fileExtension = r.getString(1);
//        FileModel data = new FileModel(fileID, fileExtension);
        FileModel data = null;
        if (r.next()) { // dùng next() thay vì first()
            String fileExtension = r.getString(1);
            data = new FileModel(fileID, fileExtension);
        }
        r.close();
        p.close();
        return data;
    }

    public synchronized FileModel initFile(int fileID) throws IOException, SQLException {
        FileModel file;
        if (!fileSenders.containsKey(fileID)) {
            file = getFile(fileID);
            fileSenders.put(fileID, new FileSenderModel(file, new File(PATH_FILE + fileID + file.getFileExtension())));
        } else {
            file = fileSenders.get(fileID).getData();
        }
        return file;
    }

    public byte[] getFileData(long currentLength, int fileID) throws IOException, SQLException {
        initFile(fileID);
        return fileSenders.get(fileID).read(currentLength);
    }

    public long getFileSize(int fileID) {
        return fileSenders.get(fileID).getFileSize();
    }

    public void receiveFile(PackageSenderModel dataPackage) throws IOException {
        if (!dataPackage.isFinish()) {
            fileReceivers.get(dataPackage.getFileID()).writeFile(dataPackage.getData());
        } else {
            fileReceivers.get(dataPackage.getFileID()).close();
        }
    }

    public SendMessageModel closeFile(ReceiveImageModel dataImage) throws IOException, SQLException {
        FileReceiverModel file = fileReceivers.get(dataImage.getFileID());
        if (file.getMessage().getMessageType() == MessageType.IMAGE.getValue()) {
            // Image file
            // So create blurhash image string
            file.getMessage().getText();
            String blurHash = convertFileToBlur(file.getFile(), dataImage);
            updateBlurHashDone(dataImage.getFileID(), blurHash);
        } else {
            updateDone(dataImage.getFileID());
        }
        fileReceivers.remove(dataImage.getFileID());
        return file.getMessage();
    }

    private String convertFileToBlur(File file, ReceiveImageModel dataImage) throws IOException {
        BufferedImage img = ImageIO.read(file);
        Dimension size = getAutoSize(new Dimension(img.getWidth(), img.getHeight()), new Dimension(200, 200));
        //  Convert image to small size
        BufferedImage newImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = newImage.createGraphics();
        g2.drawImage(img, 0, 0, size.width, size.height, null);
        String blurhash = BlurHash.encode(newImage);
        dataImage.setWidth(size.width);
        dataImage.setHeight(size.height);
        dataImage.setImage(blurhash);
        return blurhash;
    }

    private Dimension getAutoSize(Dimension fromSize, Dimension toSize) {
        int w = toSize.width;
        int h = toSize.height;
        int iw = fromSize.width;
        int ih = fromSize.height;
        double xScale = (double) w / iw;
        double yScale = (double) h / ih;
        double scale = Math.min(xScale, yScale);
        int width = (int) (scale * iw);
        int height = (int) (scale * ih);
        return new Dimension(width, height);
    }

    private File toFileObject(FileModel file) {
        return new File(PATH_FILE + file.getFileID() + file.getFileExtension());
    }

    // SQL
    private final String PATH_FILE = "server_data/";
    private final String INSERT = "insert into files (FileExtension) values (?)";
    private final String UPDATE_BLUR_HASH_DONE = "update files set `BlurHash`=?, `Status`='1' where `FileID`=? limit 1";
    private final String UPDATE_DONE = "update files set `Status`='1' where `FileID`=? limit 1";
    private final String GET_FILE_EXTENSION = "select `FileExtension` from files where `FileID`=? limit 1";

    // Instance
    private final Connection con;
    private final Map<Integer, FileReceiverModel> fileReceivers;
    private final Map<Integer, FileSenderModel> fileSenders;
}
