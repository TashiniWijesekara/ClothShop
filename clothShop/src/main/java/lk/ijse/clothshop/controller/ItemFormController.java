package lk.ijse.clothshop.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

import java.lang.ref.PhantomReference;
import java.sql.*;
import java.util.Properties;

public class ItemFormController {

    private static final String URL = "jdbc:mysql://localhost:3306/clothshop";
    private static final Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }

    public JFXTextField quantitytxt;
    public JFXButton serch;
    public JFXButton add;
    public JFXButton delet;
    public JFXButton updte;
    public JFXTextField itemtxt;
    public JFXTextField itemname;
    public JFXTextField clothbrandtxt;
    public JFXTextField searchtxt;

    public void OnActionSearch(ActionEvent actionEvent) throws SQLException {

        String search = searchtxt.getText();

        try(Connection con = DriverManager.getConnection(URL, props)){
            String sql = "SELECT *FROM item WHERE ItemID = ?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, search);

            ResultSet resultSet = pstm.executeQuery();

            if(resultSet.next()){
                String id = resultSet.getString(1);
                String name = resultSet.getString(2);
                String quantity = resultSet.getString(3);
                String brand = resultSet.getString(4);

                itemtxt.setText(id);
                itemname.setText(name);
                quantitytxt.setText(quantity);
                clothbrandtxt.setText(brand);

                searchtxt.setText("");

            }else {
                new Alert(Alert.AlertType.ERROR, "Please check and enter the correct id !").show();
            }

        }
    }

    public void OnActionAdd(ActionEvent actionEvent) throws SQLException {

        String id = itemtxt.getText();
        String name = itemname.getText();
        int quantity = Integer.parseInt(quantitytxt.getText());
        String barnd = clothbrandtxt.getText();

        try(Connection con = DriverManager.getConnection(URL, props)){
            String sql = "INSERT  INTO item(ItemID, ItemName, Quantity, ClothBrand)" +
                    "VALUES(?, ?, ?, ?)";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, id);
            pstm.setString(2, name);
            pstm.setInt(3, quantity);
            pstm.setString(4,barnd);

            if(pstm.executeUpdate() > 0){
                new Alert(Alert.AlertType.CONFIRMATION, "Item add successfully !").show();

                itemtxt.setText("");
                itemname.setText("");
                quantitytxt.setText("");
                clothbrandtxt.setText("");

            }

        }
    }

    public void OnActionDelete(ActionEvent actionEvent) throws SQLException {

        String id = itemtxt.getText();

        try (Connection con = DriverManager.getConnection(URL, props)){
            String sql = "DELETE FROM item WHERE ItemID = ?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, id);

            if(pstm.executeUpdate() > 0){
                new Alert(Alert.AlertType.CONFIRMATION, "Item remove successfull !").show();

                itemtxt.setText("");
                itemname.setText("");
                quantitytxt.setText("");
                clothbrandtxt.setText("");

            }

        }

    }

    public void OnActionUpdate(ActionEvent actionEvent) throws SQLException {

        String id = itemtxt.getText();
        String name = itemname.getText();
        int quantity = Integer.parseInt(quantitytxt.getText());
        String brand = clothbrandtxt.getText();

        try(Connection con = DriverManager.getConnection(URL, props)){
            String sql = "UPDATE item SET ItemName = ?, Quantity = ?, ClothBrand = ? WHERE ItemID = ?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, name);
            pstm.setInt(2, quantity);
            pstm.setString(3, brand);
            pstm.setString(4, id);

            if (pstm.executeUpdate() > 0){
                new Alert(Alert.AlertType.CONFIRMATION, "Item update succcessfully !").show();

                itemtxt.setText("");
                itemname.setText("");
                quantitytxt.setText("");
                clothbrandtxt.setText("");
            }

        }

    }
}
