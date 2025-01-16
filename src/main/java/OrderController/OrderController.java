package OrderController;

import DBConnection.DBConnection;
import Model.Customer;
import Model.Item;
import Model.Order;
import Model.OrderDetail;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
                Customer customer = new Customer(null, resultSet.getString("name"), null, null);
                return customer;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getLastOrderId(){
        try {
            PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("select id from orders order by id desc limit 1");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String orderId = resultSet.getString("id");
                return orderId;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean placeOrder(Order order) {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            PreparedStatement stm = connection.prepareStatement("INSERT INTO Orders VALUES(?,?,?)");
            stm.setObject(1, order.getId());
            stm.setObject(2, order.getDate());
            stm.setObject(3, order.getCustromerId());
            boolean isOrderAdded = stm.executeUpdate() > 0;

            if (isOrderAdded) {
                boolean isOrderDetailsAdded = addOrderDetail(order.getOrderDetails());
                if (isOrderDetailsAdded) {
                    boolean isItemTableUpdated = updateSellItem(order.getOrderDetails());
                    if (isItemTableUpdated) {
                        connection.commit();
                        return true;
                    }
                }
            }
            connection.rollback();
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException e) {
                    throw new RuntimeException("Failed to reset auto-commit", e);
                }
            }
        }
    }

    public static boolean addOrderDetail(ArrayList<OrderDetail> orderDetails) {
        System.out.println(orderDetails);
        for (OrderDetail orderDetail : orderDetails) {
            boolean isAdded = addToTable(orderDetail);
            if (!isAdded) {
                return false;
            }
        }
        return !orderDetails.isEmpty();
    }

    public static boolean addToTable(OrderDetail orderDetail) {
        System.out.println(orderDetail);
        PreparedStatement stm = null;
        try {
            stm = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO orderdetail VALUES(?,?,?,?)");
            stm.setObject(1, orderDetail.getOrderId());
            stm.setObject(2, orderDetail.getItemCode());
            stm.setObject(3, orderDetail.getQty());
            stm.setObject(4, orderDetail.getUnitPrice());
            return stm.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean updateSellItem(ArrayList<OrderDetail> orderDetails) {
        try {
            PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement("UPDATE item SET qtyOnHand = qtyOnHand - ? WHERE code = ?");

            for (OrderDetail orderDetail : orderDetails) {
                statement.setInt(1, orderDetail.getQty());
                statement.setString(2, orderDetail.getItemCode());

                int result = statement.executeUpdate();
                if (result <= 0) {
                    return false;
                }
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean checkAvailableStock(String itemId, Integer qty){
        try {
            ResultSet rst = DBConnection.getInstance().getConnection().createStatement().executeQuery("select qtyOnHand from item where code='"+itemId+"'");
            if (rst.next()){
                if (rst.getInt(1)>qty){
                    return true;
                }
            }
            return false;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
