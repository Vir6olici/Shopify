package dataTypes;

import jdbc.ActiveRecord;
import jdbc.ActiveRecordEntity;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@ActiveRecordEntity(tablename = "products" , keyColumnName = "cod")
public class Product extends ActiveRecord {
    public String cod;
    public String nume;
    public String descriere;
    public int stock;
    public int price;

    @Override
    public String toString() {
        return "Product{" +
                "cod='" + cod + '\'' +
                ", nume='" + nume + '\'' +
                ", descriere='" + descriere + '\'' +
                ", stock=" + stock +
                ", price=" + price +
                '}';
    }
}
