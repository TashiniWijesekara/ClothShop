package lk.ijse.clothshop.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import lk.ijse.clothshop.dto.tm.Customer;
import lk.ijse.clothshop.dto.tm.Employeetable;
import lk.ijse.clothshop.dto.tm.tm.CustomerTM;
import lk.ijse.clothshop.dto.tm.tm.EmployeeTM;
import lk.ijse.clothshop.model.CustomerModel;
import lk.ijse.clothshop.model.EmployeetableModel;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

public class CustomerFormController implements Initializable {

    private static final String URL = "jdbc:mysql://localhost:3306/clothshop";
    private static final Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }

    public JFXTextField customeridtxt;
    public JFXTextField customernametxt;
    public JFXTextField contacttxt;
    public JFXTextField addresstxt;
    public JFXButton btnadd;
    public JFXButton btndelet;
    public JFXButton btnupdate;
    public JFXTextField txtSearch;
    public Button searchbtn;

    public TableView tableview;
    public TableColumn customeridcolumn;
    public TableColumn customernamecolumn;
    public TableColumn contactcolumn;
    public TableColumn useridcolumn;
    public JFXComboBox useridcombotxt;

    public void OnActionUpdate(ActionEvent actionEvent) throws SQLException {

        String cusid = customeridtxt.getText();
        String custname = customernametxt.getText();
        String contact = contacttxt.getText();
        String userid = String.valueOf(useridcombotxt.getValue());

        try (Connection con = DriverManager.getConnection(URL,props)){
            String sql = "UPDATE employee SET CustName =  ?, Contact = ? WHERE CustID = ?";

            PreparedStatement pstm =  con.prepareStatement(sql);
            pstm.setString(1, custname);
            pstm.setString(2, contact);
            pstm.setString(3, cusid);


            if(pstm.executeUpdate() > 0){
                new Alert(Alert.AlertType.CONFIRMATION, "Customer updated successfully !").show();

                customeridtxt.setText("");
                customernametxt.setText("");
                contacttxt.setText("");


                useridcombotxt.setValue(null);

            }

        }

    }

    public void OnActionSearch(ActionEvent actionEvent) {
    }

    public void OnActionAdd(ActionEvent actionEvent) throws SQLException {

        String cusid = customeridtxt.getText();
        String custname = customernametxt.getText();
        String contact = contacttxt.getText();
        String userid = String.valueOf(useridcombotxt.getValue());

        try(Connection con = DriverManager.getConnection(URL, props)){
            String sql = "INSERT INTO customer(CustID, CustName, Contact, UserId)"+
                    "VALUES(?, ?, ?, ?)";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, cusid);
            pstm.setString(2, custname);
            pstm.setString(3, contact);
            pstm.setString(4, userid);


            if(pstm.executeUpdate() > 0){
                new Alert(Alert.AlertType.CONFIRMATION, "Customer add successfully !").show();
                customeridtxt.setText("");
                customernametxt.setText("");
                contacttxt.setText("");


                useridcombotxt.setValue(null);

                getAll();setCellValueFactory();

            }

        }


    }

    public void OnActionDelete(ActionEvent actionEvent) throws SQLException {

        String serch = txtSearch.getText();

        try (Connection con = DriverManager.getConnection(URL, props)){
            String sql = "DELETE FROM customer WHERE CustID = ?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, serch);

            if(pstm.executeUpdate() > 0){
                new Alert(Alert.AlertType.CONFIRMATION, "Customer deleted successfully !");

                customeridtxt.setText("");
                customernametxt.setText("");
                contacttxt.setText("");


                useridcombotxt.setValue(null);
            }

            getAll();setCellValueFactory();

        }

    }

    public void searchbtnonaction(ActionEvent actionEvent) throws SQLException {

        String search = txtSearch.getText();

        try (Connection con = DriverManager.getConnection(URL, props)){
            String sql = "SELECT * FROM customer WHERE CustID = ?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, search);

            ResultSet resultSet = pstm.executeQuery();

            if(resultSet.next()){
                String custid = resultSet.getString(1);
                String custname = resultSet.getString(2);
                String contact = resultSet.getString(3);

                customeridtxt.setText(custid);
                customernametxt.setText(custname);
                contacttxt.setText(contact);

                txtSearch.setText("");

            }

        }

    }

    public void txtseazrchKeyReleased(KeyEvent keyEvent) {

    }

    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        List<String> items = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(URL, props)){
            String sql = "SELECT * FROM user";

            PreparedStatement pstm = con.prepareStatement(sql);

            ResultSet resultSet = pstm.executeQuery();

            if(resultSet.next()){
                String id = resultSet.getString(1);
                items.add(id);
            }

            useridcombotxt.getItems().addAll(items);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        getAll();setCellValueFactory();

    }

    private void setCellValueFactory() {
        customeridcolumn.setCellValueFactory(new PropertyValueFactory<>("customerid"));
        customernamecolumn.setCellValueFactory(new PropertyValueFactory<>("cutomername"));
        contactcolumn.setCellValueFactory(new PropertyValueFactory<>("contact"));
        useridcolumn.setCellValueFactory(new PropertyValueFactory<>("userid"));
    }

    private void getAll() {
        try {
            ObservableList<CustomerTM> obList = FXCollections.observableArrayList();
            List<Customer> places = CustomerModel.getAll();

            for (Customer place : places) {
                obList.add(new CustomerTM(
                        place.getCustomerid(),
                        place.getCutomername(),
                        place.getContact(),
                        place.getUserid()
                ));
            }
            tableview.setItems(obList);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

}
