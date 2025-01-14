package CustomerController;

import DBConnection.DBConnection;
import Model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CustomerController implements CustomerService{
    public static CustomerController instance;

    private CustomerController() {

    }

    public static CustomerController getInstance(){
        if(instance == null){
            instance=new CustomerController();
        }
        return instance;
    }

    @Override
    public List<Customer> getAll() {
        return List.of();
    }

    @Override
    public boolean addCustomer(Customer customer) {
        try {
            PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO Customer VALUES (?,?,?,?)");
            preparedStatement.setString(1,customer.getCusId());
            preparedStatement.setString(2,customer.getCusName());
            preparedStatement.setString(3,customer.getCusAddress());
            preparedStatement.setDouble(4,customer.getCusSalary());
            return preparedStatement.executeUpdate() >0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int updateCustomer(Customer customer) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("UPDATE customer SET name = ?, address = ?, salary = ? WHERE id = '"+customer.getCusId()+"'");
            preparedStatement.setString(1,customer.getCusName());
            preparedStatement.setString(2,customer.getCusAddress());
            preparedStatement.setDouble(3,customer.getCusSalary());
            int update = preparedStatement.executeUpdate();
            return update;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteCustomer(String id) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("DELETE FROM customer WHERE id = '"+id+"'");
            int executed = preparedStatement.executeUpdate();
            return executed;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Customer searchCustomer(String cusid) {
        try {
            ResultSet rst = DBConnection.getInstance().getConnection().createStatement().executeQuery("select * from customer where id='" + cusid + "'");

            if(rst.next()){
                String id = rst.getString("id");
                String name = rst.getString("name");
                String address = rst.getString("address");
                String salary = rst.getString("salary");

                return new Customer(id,name,address,Double.parseDouble(salary));
            }

            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
