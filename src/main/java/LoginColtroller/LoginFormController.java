package LoginColtroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormController {

    public TextField txtUserName;
    public TextField txtPassword;

    public void btnLognOnAction(ActionEvent actionEvent) throws IOException {
        boolean added = LoginController.getInstance().addUser(txtUserName.getText(), txtPassword.getText());
        if(added){

            txtUserName.clear();
            txtPassword.clear();

            Stage stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/View/DashBoard.fxml"))));
            stage.show();
        }else{
            new Alert(Alert.AlertType.INFORMATION,"Fail").show();
        }
    }

    public void hyperRegisterOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/View/SignUpForm.fxml"))));
        stage.show();
    }
}
