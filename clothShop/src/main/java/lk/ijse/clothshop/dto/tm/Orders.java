package lk.ijse.clothshop.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Orders {
    private String orderid;
    private String orderdate;
    private String price;
    private String Customerid;;
    private String item;
    private String itemprice;
    private String bonus;
    private String amount;
}
