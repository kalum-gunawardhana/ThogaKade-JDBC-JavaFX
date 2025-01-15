package OrderController;

import Model.*;
import com.mysql.cj.x.protobuf.MysqlxCrud;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
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
    public Label lblOrderId;
    public TableView tblOrders;
    public TableColumn colCode;
    public TableColumn colTotal;
    public Label lblTotal;

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
        setOrderIdLabel();
    }

    /*public ArrayList<OrderDetail> orderDetails = new ArrayList<>();
    public void btnPlaceOnAction(ActionEvent actionEvent) {
        for(Object obList : observableList){
            orderDetails.add((OrderDetail) obList);
        }


        orderDetails.add(new OrderDetail(
                lblOrderId.getText(),
                cbItem.getSelectionModel().getSelectedItem().toString(),
                Integer.parseInt(txtQty.getText()),
                Double.parseDouble(txtUniPri.getText())
        ));

        Order order = new Order(
                lblOrderId.getText(),
                lblDate.getText(),
                cbCustomer.getSelectionModel().getSelectedItem().toString(),
                orderDetails
        );

        if (OrderController.placeOrder(order)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Order Placed Successfully");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to Place Order");
            alert.show();
        }
        observableList.clear();
    }*/

    public ObservableList<OrderTable> observableList = FXCollections.observableArrayList();
    public void btnCardOnAction(ActionEvent actionEvent) {
        boolean stock = OrderController.checkAvailableStock(cbItem.getSelectionModel().getSelectedItem().toString(), Integer.valueOf(txtQty.getText()));
        if(!stock){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid qty");
            alert.show();
        }else {

            colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
            colDes.setCellValueFactory(new PropertyValueFactory<>("des"));
            colPrice.setCellValueFactory(new PropertyValueFactory<>("qty"));
            colQty.setCellValueFactory(new PropertyValueFactory<>("price"));
            colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));


            int index = observableList.indexOf(new OrderTable(
                    cbItem.getSelectionModel().getSelectedItem().toString(),
                    null,
                    0,
                    0.0,
                    0.0));

            if (index != -1) {
                OrderTable alreadyExitOrder = (OrderTable) observableList.get(index);
                observableList.set(index, new OrderTable(
                        cbItem.getSelectionModel().getSelectedItem().toString(),
                        txtDes.getText(),
                        Integer.parseInt(txtQty.getText()) + alreadyExitOrder.getQty(),
                        Double.parseDouble(txtUniPri.getText()),
                        Integer.parseInt(txtQty.getText()) * Double.parseDouble(txtUniPri.getText()) + alreadyExitOrder.getTotal()));
            } else {
                observableList.add(new OrderTable(
                        cbItem.getSelectionModel().getSelectedItem().toString(),
                        txtDes.getText(),
                        Integer.parseInt(txtQty.getText()),
                        Double.parseDouble(txtUniPri.getText()),
                        Integer.parseInt(txtQty.getText()) * Double.parseDouble(txtUniPri.getText()))
                );
                tblOrders.setItems(observableList);
            }
            setTotal();
            txtQty.clear();
            //System.out.println(observableList);
        }
    }


    public void btnPlaceOnAction(ActionEvent actionEvent) {
        ArrayList<OrderDetail> orderDetails = new ArrayList<>();
        for(OrderTable obList : observableList){
            OrderDetail orderDetail = new OrderDetail(lblOrderId.getText(),obList.getCode(),obList.getQty(),obList.getPrice());
            orderDetails.add(orderDetail);
        }


        Order order = new Order(
                lblOrderId.getText(),
                lblDate.getText(),
                cbCustomer.getSelectionModel().getSelectedItem().toString(),
                orderDetails
        );

        if (OrderController.placeOrder(order)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Order Placed Successfully");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to Place Order");
            alert.show();
        }
        observableList.clear();
        System.out.println(orderDetails);
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

    public void setOrderIdLabel(){
        String lastOrderId = OrderController.getLastOrderId();
        Integer substring = Integer.valueOf(lastOrderId.substring(2,4))+1;
        String orderId = "D0"+substring;
        //System.out.println(substring);
        lblOrderId.setText(orderId);
    }

    public void setTotal() {
        double total = 0.00;
        for (OrderTable orderTable : observableList) {
            total += orderTable.getTotal();
        }
        lblTotal.setText(String.format("%.2f", total));
    }
}
