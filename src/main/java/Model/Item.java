package Model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Item {
    private String code;
    private String description;
    private Double unitPrice;
    private Integer qtyOnHand;
}
