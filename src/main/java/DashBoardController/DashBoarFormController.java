package DashBoardController;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashBoarFormController implements Initializable {

    public AnchorPane customerDashboard;

    public void btnCustomerOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = this.getClass().getResource("/View/CustomerForm.fxml");

        assert resource!=null;

        Parent load = FXMLLoader.load(resource);
        this.customerDashboard.getChildren().clear();
        this.customerDashboard.getChildren().add(load);
    }

    public void btnItemOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = this.getClass().getResource("/View/ItemForm.fxml");

        assert resource!=null;

        Parent load = FXMLLoader.load(resource);
        this.customerDashboard.getChildren().clear();
        this.customerDashboard.getChildren().add(load);
    }

    public void btnOrderOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = this.getClass().getResource("/View/OrderForm.fxml");

        assert resource!=null;

        Parent load = FXMLLoader.load(resource);
        this.customerDashboard.getChildren().clear();
        this.customerDashboard.getChildren().add(load);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        URL resource = this.getClass().getResource("/View/OrderForm.fxml");

        assert resource!=null;

        Parent load = null;
        try {
            load = FXMLLoader.load(resource);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.customerDashboard.getChildren().clear();
        this.customerDashboard.getChildren().add(load);
    }
}
