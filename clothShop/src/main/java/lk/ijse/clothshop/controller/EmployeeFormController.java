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
import lk.ijse.clothshop.dto.tm.Employeetable;
import lk.ijse.clothshop.dto.tm.tm.EmployeeTM;
import lk.ijse.clothshop.model.EmployeetableModel;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmployeeFormController implements Initializable {

    private static final String URL = "jdbc:mysql://localhost:3306/clothshop";
    private static final Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }

    public JFXTextField txtSearch;
    public JFXButton addbtn;
    public JFXButton updatebtn;
    public JFXTextField empidtxt;
    public JFXTextField firstnametxt;
    public JFXTextField addresstxt;
    public JFXTextField lastnametxt ;
    public JFXTextField contactnumbertxt;
    public JFXTextField nictxt;
    public JFXButton deletebtn;
    public Button searchbtn;
    public JFXComboBox usericombotxt;

    public TableView tableview;
    public TableColumn empidcolumn;
    public TableColumn firstnamecolumn;
    public TableColumn lastnamecolumn;
    public TableColumn addresscolumn;
    public TableColumn contactcolumn;
    public TableColumn niccolumn;
    public TableColumn useridcolumn;

    public void txtseazrchKeyReleased(KeyEvent keyEvent) {
    }


    public void addbtnonaction(ActionEvent actionEvent) throws SQLException {

        LocalDate localDate = LocalDate.now();

        java.sql.Date date = java.sql.Date.valueOf(localDate);

        String emid = empidtxt.getText();
        String fname = firstnametxt.getText();
        String lasname = lastnametxt.getText();
        String address = addresstxt.getText();
        String contact = contactnumbertxt.getText();
        String nic = nictxt.getText();
        String userid = String.valueOf(usericombotxt.getValue());


        if(isint(emid)&isname(fname)&isname(lasname)&isint(contact)&isint(userid)) {
            try (Connection con = DriverManager.getConnection(URL, props)) {
                String sql = "INSERT INTO employee(EmpId, FirstName, LastName, EmpAddress, DateOfJoing, Contact, NIC, UserId)" +
                        "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

                PreparedStatement pstm = con.prepareStatement(sql);
                pstm.setString(1, emid);
                pstm.setString(2, fname);
                pstm.setString(3, lasname);
                pstm.setString(4, address);
                pstm.setDate(5, date);
                pstm.setString(6, contact);
                pstm.setString(7, nic);
                pstm.setString(8, userid);

                if (pstm.executeUpdate() > 0) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Employee add successfully !").show();
                    empidtxt.setText("");
                    firstnametxt.setText("");
                    lastnametxt.setText("");
                    addresstxt.setText("");
                    contactnumbertxt.setText("");
                    nictxt.setText("");

                    usericombotxt.setValue(null);

                }

            }
        }else{
            new Alert(Alert.AlertType.WARNING, "Please check & enter Valiud value !").show();
        }
    }

    public void updatebtnonaction(ActionEvent actionEvent) throws SQLException {

        String emid = empidtxt.getText();
        String fname = firstnametxt.getText();
        String lasname = lastnametxt.getText();
        String address = addresstxt.getText();
        String contact = contactnumbertxt.getText();
        String nic = nictxt.getText();

        try (Connection con = DriverManager.getConnection(URL,props)){
            String sql = "UPDATE employee SET FirstName =  ?, LastName = ?, EmpAddress = ?, Contact = ?, NIC = ? WHERE EmpId = ?";

            PreparedStatement pstm =  con.prepareStatement(sql);
            pstm.setString(1, fname);
            pstm.setString(2, lasname);
            pstm.setString(3, address);
            pstm.setString(4, contact);
            pstm.setString(5, nic);
            pstm.setString(6, emid);

            if(pstm.executeUpdate() > 0){
                new Alert(Alert.AlertType.CONFIRMATION, "Employee updated successfully !").show();

                empidtxt.setText("");
                firstnametxt.setText("");
                lastnametxt.setText("");
                addresstxt.setText("");
                contactnumbertxt.setText("");
                nictxt.setText("");

                usericombotxt.setValue(null);

            }

        }
    }

    public void deletebtnonaction(ActionEvent actionEvent) throws SQLException {

        String serch = empidtxt.getText();

        try (Connection con = DriverManager.getConnection(URL, props)){
            String sql = "DELETE FROM employee WHERE EmpId = ?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, serch);

            if(pstm.executeUpdate() > 0){
                new Alert(Alert.AlertType.CONFIRMATION, "Employee deleted successfully !");

                empidtxt.setText("");
                firstnametxt.setText("");
                lastnametxt.setText("");
                addresstxt.setText("");
                contactnumbertxt.setText("");
                nictxt.setText("");

                usericombotxt.setValue(null);
            }

        }

    }

    public void searchbtnonaction(ActionEvent actionEvent) throws SQLException {
        String search = txtSearch.getText();

        try (Connection con = DriverManager.getConnection(URL, props)){
            String sql = "SELECT * FROM employee WHERE EmpId = ?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, search);

            ResultSet resultSet = pstm.executeQuery();

            if(resultSet.next()){
                String emid = resultSet.getString(1);
                String fname = resultSet.getString(2);
                String lasname = resultSet.getString(3);
                String address = resultSet.getString(4);
                String contact = resultSet.getString(6);
                String nic = resultSet.getString(7);

                empidtxt.setText(emid);
                firstnametxt.setText(fname);
                lastnametxt.setText(lasname);
                addresstxt.setText(address);
                contactnumbertxt.setText(contact);
                nictxt.setText(nic);

                txtSearch.setText("");

            }

        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        List <String> items = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(URL, props)){
            String sql = "SELECT * FROM user";

            PreparedStatement pstm = con.prepareStatement(sql);

            ResultSet resultSet = pstm.executeQuery();

            if(resultSet.next()){
                String id = resultSet.getString(1);
                items.add(id);
            }

            usericombotxt.getItems().addAll(items);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        getAll(); setCellValueFactory();

    }

    private void setCellValueFactory() {
        empidcolumn.setCellValueFactory(new PropertyValueFactory<>("EmpId"));
        firstnamecolumn.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        lastnamecolumn.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        addresscolumn.setCellValueFactory(new PropertyValueFactory<>("Address"));
        contactcolumn.setCellValueFactory(new PropertyValueFactory<>("contact"));
        niccolumn.setCellValueFactory(new PropertyValueFactory<>("nic"));
        useridcolumn.setCellValueFactory(new PropertyValueFactory<>("userid"));
    }

    private void getAll() {
        try {
            ObservableList<EmployeeTM> obList = FXCollections.observableArrayList();
            List<Employeetable> places = EmployeetableModel.getAll();

            for (Employeetable place : places) {
                obList.add(new EmployeeTM(
                        place.getEmpId(),
                        place.getFirstName(),
                        place.getLastName(),
                        place.getAddress(),
                        place.getContact(),
                        place.getNic(),
                        place.getUserid()
                ));
            }
            tableview.setItems(obList);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
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

}
