package CustomerController;

import Model.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CustomerFormController {
    public TextField txtCusId;
    public TextField txtCusNam;
    public TextField txtCusAdd;
    public TextField txtCusSal;

    public void btnAddCustomerOnAction(javafx.event.ActionEvent actionEvent) {
        String idText = txtCusId.getText();
        String namText = txtCusNam.getText();
        String cusAddText = txtCusAdd.getText();
        Double cusSalText= Double.valueOf(txtCusSal.getText());

        Customer customer = new Customer(idText, namText, cusAddText, cusSalText);

        boolean added = CustomerController.getInstance().addCustomer(customer);

        if(added){
            new Alert(Alert.AlertType.INFORMATION,"Customer Added Success").show();
        }else{
            new Alert(Alert.AlertType.INFORMATION,"Customer Added Fail").show();
        }

        txtCusId.clear();
        txtCusNam.clear();
        txtCusAdd.clear();
        txtCusSal.clear();
    }

    public void btnCustomerTableOnAction(ActionEvent actionEvent) {
        Stage stage1 = new Stage();
        try {
            stage1.setScene(new Scene(FXMLLoader.load(getClass().getResource("/View/CustomerTable.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage1.show();
    }

    public void btnSearchCustomerOnAction(ActionEvent actionEvent) {
        String idText = txtCusId.getText();
        Customer customer = CustomerController.getInstance().searchCustomer(idText);

        if(customer!=null){
            txtCusId.setText(customer.getCusId());
            txtCusNam.setText(customer.getCusName());
            txtCusAdd.setText(customer.getCusAddress());
            txtCusSal.setText(customer.getCusSalary().toString());
        }else{
            new Alert(Alert.AlertType.INFORMATION,"Customer Not Found").show();
        }
    }

    public void btnDeleteCustomerOnAction(ActionEvent actionEvent) {
        int deleted = CustomerController.getInstance().deleteCustomer(txtCusId.getText());

        if(deleted>0){
            new Alert(Alert.AlertType.INFORMATION,"Customer Deleted Success").show();
        }else{
            new Alert(Alert.AlertType.INFORMATION,"Customer Deleted Fail").show();
        }
    }

    public void btnUpdateCustomerOnAction(ActionEvent actionEvent) {
        String idText = txtCusId.getText();
        String namText = txtCusNam.getText();
        String cusAddText = txtCusAdd.getText();
        Double cusSalText= Double.valueOf(txtCusSal.getText());

        Customer customer = new Customer(idText, namText, cusAddText, cusSalText);

        int updated = CustomerController.getInstance().updateCustomer(customer);

        if(updated>0){
            new Alert(Alert.AlertType.INFORMATION,"Customer Updated Success").show();
        }else{
            new Alert(Alert.AlertType.INFORMATION,"Customer Updated Fail").show();
        }

        txtCusId.clear();
        txtCusNam.clear();
        txtCusAdd.clear();
        txtCusSal.clear();
    }
}
