package lk.ijse.clothshop.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import lk.ijse.clothshop.db.DBConnection;

import java.sql.*;
import java.util.Properties;

public class SupplierFormController {

    private static final String URL = "jdbc:mysql://localhost:3306/clothshop";
    private static final Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }

    public JFXTextField idtxt;
    public JFXTextField nametxt;
    public JFXTextField descriptiontxt;
    public JFXTextField contacttxt;
    public JFXTextField searchtxt;

    public void OnActionSearch(ActionEvent actionEvent) throws SQLException {

        String search = searchtxt.getText();

        try (Connection con = DBConnection.getInstance().getConnection()){
            String sql = "SELECT * FROM supplier WHERE SupplierId = ?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, search);

            ResultSet resultSet = pstm.executeQuery();

            if(resultSet.next()){
                String custid = resultSet.getString(1);
                String custname = resultSet.getString(2);
                String description = resultSet.getString(3);
                String contact = resultSet.getString(4);

                idtxt.setText(custid);
                nametxt.setText(custname);
                contacttxt.setText(contact);
                descriptiontxt.setText(description);

                searchtxt.setText("");

            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void OnActionAdd(ActionEvent actionEvent) throws SQLException {

        String id = idtxt.getText();
        String name = nametxt.getText();
        String description = descriptiontxt.getText();
        String contact = contacttxt.getText();

        try(Connection con = DriverManager.getConnection(URL, props)){
            String sql = "INSERT INTO supplier(SupplierId, SupplierName, Description, Contact)"+
                    "VALUES(?, ?, ?, ?)";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, id);
            pstm.setString(2, name);
            pstm.setString(3, description);
            pstm.setString(4, contact);


            if(pstm.executeUpdate() > 0){
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier add successfully !").show();
                idtxt.setText("");
                nametxt.setText("");
                descriptiontxt.setText("");
                contacttxt.setText("");


            }


        }
    }

    public void OnActionDelete(ActionEvent actionEvent)throws SQLException {

        String serch = idtxt.getText();

        try (Connection con = DriverManager.getConnection(URL, props)){
            String sql = "DELETE FROM supplier WHERE SupplierId = ?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, serch);

            if(pstm.executeUpdate() > 0){
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier deleted successfully !");

                idtxt.setText("");
                nametxt.setText("");
                descriptiontxt.setText("");
                contacttxt.setText("");

                searchtxt.setText("");
            }

        }

    }

    public void OnActionUpdate(ActionEvent actionEvent) throws SQLException {

        String id = idtxt.getText();
        String name = nametxt.getText();
        String description = descriptiontxt.getText();
        String contact = contacttxt.getText();

        try (Connection con = DriverManager.getConnection(URL,props)){
            String sql = "UPDATE supplier SET SupplierName =  ?, Description = ?, Contact = ? WHERE SupplierId = ?";

            PreparedStatement pstm =  con.prepareStatement(sql);
            pstm.setString(1, name);
            pstm.setString(2, description);
            pstm.setString(3, contact);
            pstm.setString(4,id);


            if(pstm.executeUpdate() > 0){
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier updated successfully !").show();

                idtxt.setText("");
                nametxt.setText("");
                descriptiontxt.setText("");
                contacttxt.setText("");

                searchtxt.setText("");

            }

        }

    }
}
