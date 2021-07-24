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
@ActiveRecordEntity(tablename = "customers" ,keyColumnName = "id")
public class Customer extends ActiveRecord {
    public int id;
    public String username;
    public String last_name;
    public String first_name;
    public String phone;
    public String address;
    public String city;
    public String postalcode;
    public String country;

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", last_name='" + last_name + '\'' +
                ", first_name='" + first_name + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", postalcode='" + postalcode + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
