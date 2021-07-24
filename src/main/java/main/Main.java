package main;
import com.fasterxml.jackson.databind.ObjectMapper;
import dataTypes.*;
import database.DataBaseProvider;
import jdbc.*;

import javax.crypto.Cipher;
import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static spark.Spark.*;


public class Main {
    public static void main(String[] args) {
        port(8080);
        get("/posts",(req,res) -> "Does it work?");
        get("/customers/:id",(req,res) -> {
            String param = req.params(":id");
            int id = Integer.parseInt(param);
            ObjectMapper objMap = new ObjectMapper();
            Customer customer = new Customer();
            customer.getById(id);
            res.status(201);
            //String toJson = objMap.writeValueAsString(customer);
            return customer.toString();
        });

        get("/allCustomers",(req,res) ->{
            Customer customer = new Customer();
            List<Customer> customerList = customer.getAllCustomers();
            res.status(201);
            return customerList.toString();
        });

        get("/allOrders",(req,res) ->{
            Order order = new Order();
            List<Order> orderList = order.getAllOrders();
            res.status(201);
            return orderList.toString();
        });

        put("/changeCustomerAddress/:id",(req,res) -> {
            String param = req.params(":id");
            int id = Integer.parseInt(param);
            ObjectMapper objectMapper = new ObjectMapper();
            Customer customer = new Customer();
            customer.updateAddress("'SilverStone GrandPrix'" , id);
            res.status(201);
            //String toJson = objectMapper.writeValueAsString(customer);
            return customer.toString();
        });

        post("/customer",(req,res) -> {
            Integer x = 11;
            Customer customer = new Customer();
            customer.setId(x);
            customer.setUsername("DR3");
            customer.setFirst_name("Daniel");
            customer.setLast_name("Riccardo");
            customer.setPhone("11114");
            customer.setCountry("AUS");
            customer.setCity("Monaco");
            customer.setPostalcode("xyzzyx");
            customer.setAddress("MonacoGrandPrix");
            customer.insert();
            ObjectMapper objectMapper = new ObjectMapper();
            res.status(201);
            //String toJson = objectMapper.writeValueAsString(customer);
            return customer.toString();
        });

        delete("/customer/:id",(req,res) ->{
            String param = req.params(":id");
            int id = Integer.parseInt(param);
            Customer customer = new Customer();
            customer.deleteAfterId(id);
            ObjectMapper objectMapper = new ObjectMapper();
            res.status(201);
            //String toJson = objectMapper.writeValueAsString(customer);
            return customer.toString();
        });

        get("/orders/:id" , (req,res) -> {
            String param = req.params(":id");
            int id = Integer.parseInt(param);
            ObjectMapper objectMapper = new ObjectMapper();
            Connection connection = DataBaseProvider.getConnection();
            Order order = new Order();
            order.getById(id);
            res.status(201);
            //String toJson = objectMapper.writeValueAsString(order);
            return order.toString();
        });

        post("/order",(req,res) -> {
            Order order = new Order();
            order.setId(3);
            order.setOrder_date(new Date(2021,11,12));
            order.setShipped_date(new Date(2021,11,15));
            order.setStat("Its on the way!");
            order.setComments(" ");
            order.setCustomer_id(3);
            order.insert();
            ObjectMapper objectMapper = new ObjectMapper();
            //String toJson = objectMapper.writeValueAsString(order);
            res.status(201);
            return order.toString();
        });

        put("/order/comment/:id" , (req,res) -> {
            String param = req.params(":id");
            int id = Integer.parseInt(param);
            ObjectMapper objectMapper = new ObjectMapper();
            Order order = new Order();
            order.updateOrderComment("'TOTUL E OK!!!!'" , id);
            //String toJson = objectMapper.writeValueAsString(order);
            res.status(201);
            return order.toString();
        });

        put("/order/status/:id" , (req,res) -> {
            String param = req.params(":id");
            int id = Integer.parseInt(param);
            Order order = new Order();
            order.updateOrderStatus("'LOST!'",id);
            res.status(201);
            return order.toString();
        });

        delete("/order/:id" ,(req,res) ->{
            String param = req.params(":id");
            int id = Integer.parseInt(param);
            Order order = new Order();
            order.deleteAfterId(id);
            ObjectMapper objectMapper = new ObjectMapper();
            //String toJson = objectMapper.writeValueAsString(order);
            res.status(201);
            return order.toString();
        });

        get("/orders/customer_id/:id",(req,res) ->{
            String param = req.params(":id");
            int id = Integer.parseInt(param);
            Order order = new Order();
            List<Order> orderList = order.getOrderFromCustomerId(id);
            res.status(201);
            return orderList.toString();
        });

        get("/ordersDetails/:id",(req,res) ->{
            String param = req.params(":id");
            int id = Integer.parseInt(param);
            Product product = new Product();
            List<Product> orderListProductName = product.getOrderDetails(id);
            res.status(201);
            return orderListProductName.toString();
        });
    }
}
