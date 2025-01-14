package OrderController;

import DBConnection.DBConnection;
import Model.Customer;
import Model.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderController {
    public static ObservableList<String> loadCustomerCombo(){
        ObservableList<String> observableCusList = FXCollections.observableArrayList();

        try {
            PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("SELECT id FROM customer ORDER BY id ASC");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                observableCusList.add(resultSet.getString("id"));
            }
            return observableCusList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ObservableList<String> loadItemCombo(){
        ObservableList<String> observableItemList = FXCollections.observableArrayList();

        try {
            PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("SELECT code FROM item ORDER BY code ASC");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                observableItemList.add(resultSet.getString("code"));
            }
            return observableItemList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Item getItemDetail(String code){
        try {
            PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("select * from item where code='" + code + "'");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                /*String des=resultSet.getString("description");
                Double pri=resultSet.getDouble("unitPrice");*/
                Item item = new Item(null, resultSet.getString("description"), resultSet.getDouble("unitPrice"), null);
                return item;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Customer getCustomerDetail(String id){
        try {
            PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("select * from customer where id='" + id + "'");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                /*String des=resultSet.getString("description");
                Double pri=resultSet.getDouble("unitPrice");*/
                Customer customer = new Customer(null, resultSet.getString("name"), null, null);
                return customer;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
