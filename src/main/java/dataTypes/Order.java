package dataTypes;


import jdbc.ActiveRecord;
import jdbc.ActiveRecordEntity;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Data
@Getter
@Setter
@NoArgsConstructor
@ActiveRecordEntity(tablename = "orders" , keyColumnName = "id")
public class Order extends ActiveRecord {
    public int id;
    public Date order_date;
    public Date shipped_date;
    public String stat;
    public String comments;
    public int customer_id;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", order_date=" + order_date +
                ", shipped_date=" + shipped_date +
                ", stat='" + stat + '\'' +
                ", comments='" + comments + '\'' +
                ", customer_id=" + customer_id +
                '}';
    }
}
