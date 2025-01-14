package LoginColtroller;

import DBConnection.DBConnection;
import Model.Customer;
import org.jasypt.util.text.BasicTextEncryptor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {
    public static LoginController instance;

    private LoginController() {

    }

    public static LoginController getInstance(){
        if(instance == null){
            instance=new LoginController();
        }
        return instance;
    }

    public boolean addUser(String userName, String password){

        try {
            ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery("select * from users");
            String key="12345";
            BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
            basicTextEncryptor.setPassword(key);

            while (resultSet.next()){
                if(resultSet.getString(3).equals(userName) && basicTextEncryptor.decrypt(resultSet.getString(4)).equals(password)){
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
