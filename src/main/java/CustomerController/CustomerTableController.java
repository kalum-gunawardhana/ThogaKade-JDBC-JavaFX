package CustomerController;

import DBConnection.DBConnection;
import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerTableController {

    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colSalary;
    public TableView tblCusTable;

    public void btnLoadTableOnAction(ActionEvent actionEvent) {
        loadTable();
    }

    public void btnBackOnAction(ActionEvent actionEvent) {
        
    }

    List<Customer> customerList=new ArrayList<>();

    public void loadTable()  {
        colId.setCellValueFactory(new PropertyValueFactory<>("cusId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("cusName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("cusAddress"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("cusSalary"));

        Statement statement = null;
        try {
            statement = DBConnection.getInstance().getConnection().createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery("select * from customer");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        while(true){
            try {
                if (!resultSet.next()) break;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                customerList.add(new Customer(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),Double.parseDouble(resultSet.getString(4))));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


        ObservableList<Customer> customerObservableList = FXCollections.observableArrayList();

        customerList.forEach(customer -> {
            customerObservableList.add(customer);
        });


        tblCusTable.setItems(customerObservableList);
    }
}
