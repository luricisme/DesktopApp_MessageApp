package services;

import com.mysql.cj.protocol.Message;
import connection.DatabaseConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import models.ViewMessageModel;

public class MessageService {

    private final Connection con;

    private final String INSERT_MESSAGE = "INSERT INTO messages (from_user_id, to_user_id, message_type, content, file_id) VALUES (?, ?, ?, ?, ?)";
    private final String GET_MESSAGE = """
            SELECT * FROM `messages`
            WHERE (from_user_id = ? AND to_user_id = ?)
               OR (from_user_id = ? AND to_user_id = ?)
            ORDER BY created_at ASC
        """;

    public MessageService() {
        this.con = DatabaseConnection.getInstance().getConnection();
    }

    public boolean insertMessage(int fromUserId, int toUserId, int messageType, String content, Integer fileId) {
        try (PreparedStatement ps = con.prepareStatement(INSERT_MESSAGE)) {
            ps.setInt(1, fromUserId);
            ps.setInt(2, toUserId);
            ps.setInt(3, messageType);
            ps.setString(4, content);

            if (fileId != null) {
                ps.setInt(5, fileId);
            } else {
                ps.setNull(5, 0);
            }

            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<ViewMessageModel> getMessages(int fromUserId, int toUserId) {
        List<ViewMessageModel> messages = new ArrayList<>();

        try (PreparedStatement ps = con.prepareStatement(GET_MESSAGE)) {
            ps.setInt(1, fromUserId);
            ps.setInt(2, toUserId);
            ps.setInt(3, toUserId);  // Đảo ngược để lấy chiều ngược lại
            ps.setInt(4, fromUserId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ViewMessageModel message = new ViewMessageModel(
                        rs.getLong("message_id"),
                        rs.getInt("from_user_id"),
                        rs.getInt("to_user_id"),
                        rs.getInt("message_type"),
                        rs.getString("content"),
                        rs.getObject("file_id") != null ? rs.getInt("file_id") : null,
                        rs.getTimestamp("created_at"),
                        rs.getBoolean("is_read")
                );
                messages.add(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return messages;
    }
}
