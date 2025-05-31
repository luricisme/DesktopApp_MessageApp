package services;

import connection.DatabaseConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import models.LoginModel;
import models.MessageModel;
import models.RegisterModel;
import models.UserAccountModel;

public class UserService {

    public UserService() {
        this.con = DatabaseConnection.getInstance().getConnection();
    }

    public MessageModel register(RegisterModel data) throws SQLException {
        // Check user exit
        MessageModel message = new MessageModel();
        try {
            PreparedStatement p = con.prepareStatement(CHECK_USER);
            p.setString(1, data.getUserName());
            ResultSet r = p.executeQuery();
            if (r.next()) {
                System.out.println("User already exists");
                message.setAction(false);
                message.setMessage("User already exit");
            } else {
                System.out.println("User does not exist, inserting...");
                message.setAction(true);
            }
            r.close();
            p.close();

            if (message.isAction()) {
                // Insert user register
                con.setAutoCommit(false);
                System.out.println("INSERT USER");
                p = con.prepareStatement(INSERT_USER, PreparedStatement.RETURN_GENERATED_KEYS);
                p.setString(1, data.getUserName());
                p.setString(2, data.getPassword());
                p.execute();
                r = p.getGeneratedKeys();
                r.first();
                int userID = r.getInt(1);
                r.close();
                p.close();
                // Create user account
                p = con.prepareStatement(INSERT_USER_ACCOUNT);
                p.setInt(1, userID);
                p.setString(2, data.getUserName());
                p.execute();
                p.close();
                con.commit();
                con.setAutoCommit(true);

                message.setAction(true);
                message.setMessage("Register successfully");
                message.setData(new UserAccountModel(userID, data.getUserName(), "", "", true));
            }
        } catch (Exception e) {
            message.setAction(false);
            message.setMessage("Server Error");
            try {
                if (con.getAutoCommit() == false) {
                    con.rollback();
                    con.setAutoCommit(true);
                }
            } catch (SQLException e1) {

            }
        }

        return message;
    }
    
    public List<UserAccountModel> getUser(int exitUser) throws SQLException{
        List<UserAccountModel> list = new ArrayList<>();
        PreparedStatement p = con.prepareStatement(SELECT_USER_ACCOUNT);
        p.setInt(1, exitUser);
        ResultSet r = p.executeQuery();
        while(r.next()){
            int userID = r.getInt(1);
            String userName = r.getString(2);
            String gender = r.getString(3);
            String image = r.getString(4);
            list.add(new UserAccountModel(userID, userName, gender, image, true));
        }
        
        r.close();
        p.close();
        return list;
    }
    
    public UserAccountModel login(LoginModel login) throws SQLException{
        UserAccountModel data = null;
        PreparedStatement p = con.prepareStatement(LOGIN);
        p.setString(1, login.getUserName());
        p.setString(2, login.getPassword());
        ResultSet r = p.executeQuery();
        if(r.next()){
            int userID = r.getInt(1);
            String userName = r.getString(2);
            String gender = r.getString(3);
            String image = r.getString(4);
            data = new UserAccountModel(userID, userName, gender, image, true);
        }
        r.close();
        p.close();
        
        return data;
    }

    // SQL
    private final String INSERT_USER = "insert into user (`UserName`, `Password`) values (?, ?)";
    private final String CHECK_USER = "select `UserID` from user where UserName = ? limit 1";

    private final String INSERT_USER_ACCOUNT = "insert into user_account (`UserID`, `UserName`) values(?, ?)";
    
    private final String SELECT_USER_ACCOUNT = "select `UserID`, `UserName`, `Gender`, `ImageString` from user_account where user_account.`Status` = '1' and `UserID`<>?";
    
    private final String LOGIN = "select UserId, `user_account`.UserName, Gender, ImageString from `chat_application`.`user` join `chat_application`.`user_account` using (UserID) where `user`.UserName=BINARY(?) and `user`.Password=BINARY(?) and `user_account`.Status='1'";

    // Instance
    private final Connection con;
}
