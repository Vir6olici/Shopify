package jdbc;

import dataTypes.Customer;
import dataTypes.Order;
import dataTypes.Product;
import database.*;

import javax.xml.crypto.Data;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public abstract class ActiveRecord {
    public boolean getById(int id) throws SQLException, ClassNotFoundException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException {
        Class<ActiveRecord> c = (Class<ActiveRecord>) this.getClass();
        ActiveRecordEntity activeRecordEntity = (ActiveRecordEntity) c.getAnnotation(ActiveRecordEntity.class);
        DataBaseProvider dataBaseProvider = new DataBaseProvider();
        Connection connection = DataBaseProvider.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + activeRecordEntity.tablename() + " WHERE id = ?");
        preparedStatement.setInt(1,id);
        ResultSet res = preparedStatement.executeQuery();
        if(!res.isBeforeFirst()){
            return false;
        } else{
            res.next();
            ResultSetMetaData metaData = res.getMetaData();
            int colCount = metaData.getColumnCount();
            for (int i = 1; i < colCount + 1; i++) {
                Field f = c.getField(metaData.getColumnName(i).toLowerCase());
                if(f.getType() == int.class){
                    f.setInt(this, res.getInt(i));
                } else if(f.getType() == String.class){
                    f.set(this,res.getString(i));
                } else if(f.getType() == Date.class){
                    f.set(this,res.getDate(i));
                }
            }
        }
        return true;
    }
    public List<Customer> getAllCustomers() throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        List<Customer> customerList = new ArrayList<>();
        Class<ActiveRecord> c = (Class<ActiveRecord>)  this.getClass();
        DataBaseProvider dataBaseProvider = new DataBaseProvider();
        Connection connection = dataBaseProvider.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM customers");
        ResultSet res = preparedStatement.executeQuery();
        if(!res.isBeforeFirst()){
            return null;
        } else {
            while(res.next()) {
                ResultSetMetaData metaData = res.getMetaData();
                int colCount = metaData.getColumnCount();
                Customer customer = new Customer();
                for (int i = 1; i < colCount + 1; i++) {
                    Field f = c.getField(metaData.getColumnName(i).toLowerCase());
                    if (f.getType() == int.class) {
                        f.setInt(customer, res.getInt(i));
                    } else if (f.getType() == String.class) {
                        f.set(customer, res.getString(i));
                    } else if (f.getType() == Date.class) {
                        f.set(customer, res.getDate(i));
                    }
                }
                customerList.add(customer);
            }
        }
        return customerList;
    }

    public List<Order> getAllOrders() throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        List<Order> orderList = new ArrayList<>();
        Class<ActiveRecord> c = (Class<ActiveRecord>)  this.getClass();
        DataBaseProvider dataBaseProvider = new DataBaseProvider();
        Connection connection = dataBaseProvider.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM orders");
        ResultSet res = preparedStatement.executeQuery();
        if(!res.isBeforeFirst()){
            return null;
        } else {
            while(res.next()) {
                ResultSetMetaData metaData = res.getMetaData();
                int colCount = metaData.getColumnCount();
                Order order = new Order();
                for (int i = 1; i < colCount + 1; i++) {
                    Field f = c.getField(metaData.getColumnName(i).toLowerCase());
                    if (f.getType() == int.class) {
                        f.setInt(order, res.getInt(i));
                    } else if (f.getType() == String.class) {
                        f.set(order, res.getString(i));
                    } else if (f.getType() == Date.class) {
                        f.set(order, res.getDate(i));
                    }
                }
                orderList.add(order);
            }
        }
        return orderList;
    }

    public boolean insert() throws SQLException, ClassNotFoundException, IllegalAccessException {
        Class<ActiveRecord> c = (Class<ActiveRecord>) this.getClass();
        ActiveRecordEntity arAnnotation = (ActiveRecordEntity) c.getAnnotation(ActiveRecordEntity.class);
        Field[] fields = this.getClass().getFields();
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO " + arAnnotation.tablename() + " (");
        byte fieldsCount = 0;
        for (Field field : fields) {
            fieldsCount++;
            query.append(field.getName() + ",");
        }
        query.deleteCharAt(query.length() - 1);
        query.append(") VALUES (");
        for (int i = 0; i < fieldsCount; i++) {
            query.append("?,");
        }
        query.deleteCharAt(query.length() - 1);
        query.append(");");
        DataBaseProvider factory=new DataBaseProvider();
        Connection connection = factory.getConnection();
        PreparedStatement ptstmt = connection.prepareStatement(query.toString());
        byte count = 1;
        for (Field field : fields) {
            if(field.getType() == int.class){
                ptstmt.setInt(count++, field.getInt(this));
            } else if(field.getType() == String.class) {
                ptstmt.setString(count++, (String) field.get(this));
            } else if(field.getType() == Date.class){
                ptstmt.setDate(count++, (Date) field.get(this));
            }
        }
        System.out.println(ptstmt.toString());
        ptstmt.execute();
        connection.commit();
        return true;
    }
    public boolean updateAddress(String newAddress,int id) throws SQLException, ClassNotFoundException {
        Connection connection = DataBaseProvider.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE customers SET address ="+newAddress+" WHERE id = " + id);
        preparedStatement.execute();
        connection.commit();
        return true;
    }
    public boolean updateOrderComment(String comment , int id) throws SQLException, ClassNotFoundException {
        Connection connection = DataBaseProvider.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE orders SET comments="+comment+" WHERE id = " + id);
        preparedStatement.execute();
        connection.commit();
        return true;
    }

    public boolean deleteAfterId(int id) throws SQLException, ClassNotFoundException {
        Class<ActiveRecord> c = (Class<ActiveRecord>) this.getClass();
        ActiveRecordEntity arAnnotation = (ActiveRecordEntity) c.getAnnotation(ActiveRecordEntity.class);
        Connection connection = DataBaseProvider.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM " +arAnnotation.tablename() +" WHERE id = " + id);
        preparedStatement.execute();
        connection.commit();
        return true;
    }
    public List<Order> getOrderFromCustomerId(int id) throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        List<Order> orderList = new ArrayList<>();
        Class<ActiveRecord> c = (Class<ActiveRecord>) this.getClass();
        ActiveRecordEntity activeRecordEntity = (ActiveRecordEntity) c.getAnnotation(ActiveRecordEntity.class);
        Connection connection = DataBaseProvider.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM "+activeRecordEntity.tablename()+" WHERE customer_id =" +id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(!resultSet.isBeforeFirst()){
            return null;
        }else {
            while(resultSet.next()){
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnData = metaData.getColumnCount();
                Order order = new Order();
                for (int i = 1; i <columnData+1 ; i++) {
                    Field f = c.getField(metaData.getColumnName(i).toLowerCase());
                    if(f.getType()==int.class){
                        f.setInt(order, resultSet.getInt(i));
                    } else
                    if(f.getType()==byte.class){
                        f.setByte(order, resultSet.getByte(i));
                    } else
                    if(f.getType()==String.class){
                        f.set(order, resultSet.getString(i));
                    }  else
                    if(f.getType()==Date.class){
                        f.set(order, resultSet.getDate(i));
                    }
                }
                orderList.add(order);
            }
        }
        return orderList;
    }
    public boolean updateOrderStatus(String stat , int id) throws SQLException, ClassNotFoundException {
        Connection connection = DataBaseProvider.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE orders SET stat="+stat+" WHERE id = " + id);
        System.out.println(preparedStatement);
        preparedStatement.execute();
        connection.commit();
        return true;
    }
    public List<Product> getOrderDetails(int id) throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        List<Product> orderListNameProduct = new ArrayList<>();
        Class<ActiveRecord> c = (Class<ActiveRecord>) this.getClass();
        ActiveRecordEntity activeRecordEntity = (ActiveRecordEntity) c.getAnnotation(ActiveRecordEntity.class);
        Connection connection = DataBaseProvider.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("Select cod,nume,descriere,stock,price From products INNER JOIN OrderDetails ON products.cod = OrderDetails.product_code INNER JOIN Orders ON "+ id +" = OrderDetails.id");
        ResultSet resultSet = preparedStatement.executeQuery();
        if(!resultSet.isBeforeFirst()){
            return null;
        }else {
            while(resultSet.next()){
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnData = metaData.getColumnCount();
                Product p = new Product();
                for (int i = 1; i <columnData+1 ; i++) {
                    Field f = c.getField(metaData.getColumnName(i).toLowerCase());
                    if(f.getType()==int.class){
                        f.setInt(p, resultSet.getInt(i));
                    } else
                    if(f.getType()==byte.class){
                        f.setByte(p, resultSet.getByte(i));
                    } else
                    if(f.getType()==String.class){
                        f.set(p, resultSet.getString(i));
                    }  else
                    if(f.getType()==Date.class){
                        f.set(p, resultSet.getDate(i));
                    }
                }
                orderListNameProduct.add(p);
            }
        }
        return orderListNameProduct;
    }

}
