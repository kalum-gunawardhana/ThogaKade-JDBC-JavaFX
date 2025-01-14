package OrderController;

import Model.Customer;
import Model.Item;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class OrderFormController implements Initializable {

    public Label lblDate;
    public Label lblTime;
    public ComboBox cbCustomer;
    public ComboBox cbItem;
    public Date date;
    
    public TextField txtDes;
    public TextField txtUniPri;
    public TextField txtCusName;
    
    public TableColumn colDes;
    public TableColumn colPrice;
    public TableColumn colQty;
    public TextField txtQty;

    private void loadDateAndTime() {
        date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));

        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime cTime = LocalTime.now();
            lblTime.setText(
                    cTime.getHour() + ":" + cTime.getMinute() + ":" + cTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();

    }

    public void loadCustomerCombo(){
        ObservableList<String> customerCombo = OrderController.loadCustomerCombo();

        /*for (String id : customerCombo) {
            cbCustomer.addItem(id);
        }*/

        cbCustomer.setItems(customerCombo);
    }

    public void loadItemCombo(){
        ObservableList<String> observableItemList = OrderController.loadItemCombo();
        cbItem.setItems(observableItemList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDateAndTime();
        loadCustomerCombo();
        loadItemCombo();
    }

    public void btnPlaceOnAction(ActionEvent actionEvent) {
    }

    public void btnCardOnAction(ActionEvent actionEvent) {
    }

    public void cbItemOnAction(ActionEvent actionEvent) {
        Item itemDetail = OrderController.getItemDetail((String) cbItem.getSelectionModel().getSelectedItem());

        txtDes.setText(itemDetail.getDescription());
        txtUniPri.setText(itemDetail.getUnitPrice().toString());
    }

    public void cbCustomerOnAction(ActionEvent actionEvent) {
        Customer customer = OrderController.getCustomerDetail((String) cbCustomer.getSelectionModel().getSelectedItem());
        txtCusName.setText(customer.getCusName());
    }
}
