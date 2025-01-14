package SignUpController;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.jasypt.util.text.BasicTextEncryptor;

public class SignUpFormController {

    public TextField txtUserName;
    public TextField txtEmail;
    public TextField txtPassword;

    public void btnRegisterOnAction(ActionEvent actionEvent) {
        String key="12345";
        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
        basicTextEncryptor.setPassword(key);

        String encrypt = basicTextEncryptor.encrypt(txtPassword.getText());

        int signUp = SignUpController.getInstance().signUp(txtUserName.getText(), txtEmail.getText(), encrypt);

        if(signUp>0){
            new Alert(Alert.AlertType.INFORMATION,"Success").show();
        }else{
            new Alert(Alert.AlertType.INFORMATION,"Fail").show();
        }
    }
}
