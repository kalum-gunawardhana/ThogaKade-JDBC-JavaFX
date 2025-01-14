package CustomerController;

import Model.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> getAll();

    boolean addCustomer(Customer customer);

    int updateCustomer(Customer customer);

    int deleteCustomer(String id);

    Customer searchCustomer(String id);
}
