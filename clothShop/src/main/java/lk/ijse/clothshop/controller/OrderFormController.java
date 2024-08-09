package lk.ijse.clothshop.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrderFormController implements Initializable {

    private static final String URL = "jdbc:mysql://localhost:3306/clothshop";
    private static final Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }

    public JFXTextField orderidtxt;
    public JFXTextField customerid;
    public JFXTextField pricetxt;
    public DatePicker datetxt;
    public JFXTextField searchtxt;
    public JFXComboBox customeridcombotxt;
    public JFXTextField amounttxt;
    public JFXComboBox itemtxt;
    public JFXTextField itemprice;
    public JFXTextField bonus;

    public void OnActionSearch(ActionEvent actionEvent) throws SQLException {

        String search = searchtxt.getText();

        try (Connection con = DriverManager.getConnection(URL, props)){
            String sql = "SELECT * FROM orders WHERE OrderId = ?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, search);

            ResultSet resultSet = pstm.executeQuery();

            if(resultSet.next()){
                String orderid = resultSet.getString(1);
                String orderdate = resultSet.getString(2);
                String price = resultSet.getString(3);
                String custid = resultSet.getString(4);
                String item = resultSet.getString(5);
                String itempice = resultSet.getString(6);
                String bons = resultSet.getString(7);
                String amount = resultSet.getString(8);

                datetxt.setValue(LocalDate.parse(orderdate));
                orderidtxt.setText(orderid);
                customeridcombotxt.setValue(custid);
                pricetxt.setText(price);
                itemtxt.setValue(item);
                itemprice.setText(itempice);
                bonus.setText(bons);
                amounttxt.setText(amount);

                searchtxt.setText("");

            }

        }

    }

    public void OnActionAdd(ActionEvent actionEvent) throws SQLException {


        String orderid = orderidtxt.getText();
        String date = String.valueOf(datetxt.getValue());
        double price = Double.parseDouble(pricetxt.getText());
        String customrid = String.valueOf(customeridcombotxt.getValue());
        String itm = String.valueOf(itemtxt.getValue());
        double itmpr = Double.parseDouble(itemprice.getText());
        double bons = Double.parseDouble(bonus.getText());
        String amount = amounttxt.getText();

        if(isint(orderid)& isint(customrid)) {
            try (Connection con = DriverManager.getConnection(URL, props)) {
                String sql = "INSERT INTO orders(OrderId, OrderDate, Price, CustID, item, itemprice, bonus, amount)" +
                        "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

                PreparedStatement pstm = con.prepareStatement(sql);

                pstm.setString(1, orderid);
                pstm.setDate(2, Date.valueOf(date));
                pstm.setDouble(3, price);
                pstm.setString(4, customrid);
                pstm.setString(5, itm);
                pstm.setDouble(6, itmpr);
                pstm.setDouble(7, bons);
                pstm.setString(8, amount);


                if (pstm.executeUpdate() > 0) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Order add successfully !").show();
                    orderidtxt.setText("");
                    datetxt.setValue(null);
                    pricetxt.setText("");
                    customeridcombotxt.setValue(null);
                    itemtxt.setValue(null);
                    itemprice.setText("");
                    bonus.setText("");
                    amounttxt.setText("");

                    searchtxt.setText("");

                }


            }
        }else {
            new Alert(Alert.AlertType.WARNING, "Please check & enter valid value !").show();
        }


    }

    public void OnActionDelete(ActionEvent actionEvent) throws SQLException {

        String search = orderidtxt.getText();

        try (Connection con = DriverManager.getConnection(URL, props)){

            String sql = "DELETE FROM orders WHERE OrderId = ?";

            PreparedStatement pstm = con.prepareStatement(sql);

            pstm.setString(1, search);

            if (pstm.executeUpdate() > 0){
                new Alert(Alert.AlertType.CONFIRMATION, "Order add successfully !").show();
                orderidtxt.setText("");
                datetxt.setValue(null);
                pricetxt.setText("");
                customeridcombotxt.setValue(null);
                itemtxt.setValue(null);
                itemprice.setText("");
                bonus.setText("");
                amounttxt.setText("");

                searchtxt.setText("");
            }

        }

    }

    public void OnActionUpdate(ActionEvent actionEvent) throws SQLException {

        String orderid = orderidtxt.getText();
        String date = String.valueOf(datetxt.getValue());
        double price = Double.parseDouble(pricetxt.getText());
        String customrid = String.valueOf(customeridcombotxt.getValue());
        String itm = String.valueOf(itemtxt.getValue());
        double itprice  = Double.parseDouble(itemprice.getText());
        double bons  = Double.parseDouble(bonus.getText());
        String amount = amounttxt.getText();

        try (Connection con = DriverManager.getConnection(URL, props)){
            String sql = "UPDATE orders SET OrderDate = ?, Price = ?, CustID = ?, item = ?, itemprice = ?, bonus = ?, amount = ? WHERE OrderId = ?";

            PreparedStatement pstm = con.prepareStatement(sql);

            pstm.setDate(1, Date.valueOf(date));
            pstm.setDouble(2, price);
            pstm.setString(3, customrid);
            pstm.setString(4, itm);
            pstm.setDouble(5, itprice);
            pstm.setDouble(6, bons);
            pstm.setString(7, amount);
            pstm.setString(8, orderid);



            if (pstm.executeUpdate() > 0){
                new Alert(Alert.AlertType.CONFIRMATION, "Order update successfully !").show();

                orderidtxt.setText("");
                datetxt.setValue(null);
                pricetxt.setText("");
                customeridcombotxt.setValue(null);
                itemtxt.setValue(null);
                itemprice.setText("");
                bonus.setText("");
                amounttxt.setText("");

                searchtxt.setText("");

            }

        }

    }

    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        List<String> items = new ArrayList<>();
        List<String> item = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(URL, props)){
            String sql = "SELECT * FROM customer";

            PreparedStatement pstm = con.prepareStatement(sql);

            ResultSet resultSet = pstm.executeQuery();

            if(resultSet.next()){
                String id = resultSet.getString(1);
                items.add(id);
            }

            customeridcombotxt.getItems().addAll(items);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try (Connection conn = DriverManager.getConnection(URL, props)){
            String sqll = "SELECT * FROM item";

            PreparedStatement pstm = conn.prepareStatement(sqll);

            ResultSet resultSet = pstm.executeQuery();

            if(resultSet.next()){
                String id = resultSet.getString(2);
                item.add(id);
            }

            itemtxt.getItems().addAll(item);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void OnActionplaceorder(ActionEvent actionEvent) {

    }

    public void OnActionpayment(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/placeorder_form.fxml"));
        Parent root1 = null;
        try {
            root1 = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.show();
    }

    public void plusbtnonaction(ActionEvent actionEvent) {


        if (bonus.getText()!=null){
            double iteprice = Double.parseDouble(itemprice.getText());
            double bons = Double.parseDouble(bonus.getText());
            int amount = Integer.parseInt(amounttxt.getText());

            double min = ((iteprice*amount)/100)*bons;
            double tot = (iteprice*amount)-min;

            pricetxt.setText(String.valueOf(tot));

        }else {
            double iteprice = Double.parseDouble(itemprice.getText());
            int amount = Integer.parseInt(amounttxt.getText());

            double tot = (iteprice*amount);

            pricetxt.setText(String.valueOf(tot));
        }
    }

    public static boolean isname(String name) {
        String regex = "^[a-z]*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }
    public static boolean isint(String intnum) {
        String regex = "^[0-9]*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(intnum);
        return matcher.matches();
    }

    public void OnActioncheckorder(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Ordertable.fxml"));
        Parent root1 = null;
        try {
            root1 = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.show();
    }
}
