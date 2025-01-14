package ItemController;

import Model.Item;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class ItemFormController {

    public TextField txtCode;
    public TextField txtDes;
    public TextField txtUniPri;
    public TextField txtQOH;

    public void btnAddItemOnAction(ActionEvent actionEvent) {
        int addItem = ItemController.getInstance().addItem(new Item(txtCode.getText(), txtDes.getText(), Double.parseDouble(txtUniPri.getText()), Integer.parseInt(txtQOH.getText())));

        if(addItem>0){
            new Alert(Alert.AlertType.INFORMATION,"Success").show();
        }else{
            new Alert(Alert.AlertType.INFORMATION,"Fail").show();
        }

        txtCode.clear();
        txtDes.clear();
        txtUniPri.clear();
        txtQOH.clear();
    }

    public void btnItemTableOnAction(ActionEvent actionEvent) {
    }

    public void btnSearchItemOnAction(ActionEvent actionEvent) {
    }

    public void btnDeleteDeleteOnAction(ActionEvent actionEvent) {
    }

    public void btnUpdateItemOnAction(ActionEvent actionEvent) {
    }
}
