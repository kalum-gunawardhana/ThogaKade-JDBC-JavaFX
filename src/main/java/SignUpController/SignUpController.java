package SignUpController;

import CustomerController.CustomerController;
import DBConnection.DBConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignUpController {
    public static SignUpController instance;

    private SignUpController() {

    }

    public static SignUpController getInstance(){
        if(instance == null){
            instance=new SignUpController();
        }
        return instance;
    }

    public int signUp(String userName, String email, String encrypt ){
        try {
            PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO users (username,email,password)VALUES (?,?,?)");
            preparedStatement.setString(1,userName);
            preparedStatement.setString(2,email);
            preparedStatement.setString(3,encrypt);
            int update = preparedStatement.executeUpdate();
            return update;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
