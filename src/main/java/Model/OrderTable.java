package Model;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderTable {
    private String code;
    private String des;
    private Integer qty;
    private Double price;
    private Double total;

    @Override
    public boolean equals(Object o) {
        OrderTable order = (OrderTable) o;
        if (this.code == order.getCode() ) return true;
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, des, qty, price, total);
    }
}
