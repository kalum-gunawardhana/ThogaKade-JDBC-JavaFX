package Model;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Customer {
    private String cusId;
    private String cusName;
    private String cusAddress;
    private Double cusSalary;
}
