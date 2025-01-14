package ItemController;

import CustomerController.CustomerController;
import DBConnection.DBConnection;
import Model.Customer;
import Model.Item;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ItemController implements ItemService{
    public static ItemController instance;

    private ItemController() {
    }

    public static ItemController getInstance(){
        if(instance == null){
            instance=new ItemController();
        }
        return instance;
    }

    @Override
    public List<Item> getItem() {
        return List.of();
    }

    @Override
    public int addItem(Item item) {
        try {
            PreparedStatement pst = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO Item VALUES (?,?,?,?)");
            pst.setObject(1, item.getCode());
            pst.setObject(2, item.getDescription());
            pst.setObject(3, Double.parseDouble(String.valueOf(item.getUnitPrice())));
            pst.setObject(4,Integer.parseInt(String.valueOf(item.getQtyOnHand())));

            int update = pst.executeUpdate();
            return update;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int updateItem(Item item) {
        return 0;
    }

    @Override
    public int deleteItem(String code) {
        return 0;
    }

    @Override
    public Item searchItem(String code) {
        return null;
    }
}
