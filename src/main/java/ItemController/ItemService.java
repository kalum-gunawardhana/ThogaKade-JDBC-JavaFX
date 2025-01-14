package ItemController;

import Model.Item;

import java.util.List;

public interface ItemService {
    List<Item> getItem();

    int addItem(Item item);

    int updateItem(Item item);

    int deleteItem(String code);

    Item searchItem(String code);
}
