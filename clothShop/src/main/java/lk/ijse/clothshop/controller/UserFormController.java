package lk.ijse.clothshop.controller;

import com.jfoenix.controls.JFXButton;
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
import lk.ijse.clothshop.dto.tm.User;
import lk.ijse.clothshop.dto.tm.tm.EmployeeTM;
import lk.ijse.clothshop.dto.tm.tm.UserTM;
import lk.ijse.clothshop.model.EmployeetableModel;
import lk.ijse.clothshop.model.UserModel;

import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserFormController implements Initializable {

    private static final String URL = "jdbc:mysql://localhost:3306/clothshop";
    private static final Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }

    public JFXButton addbtn;
    public JFXButton updatebtn;
    public JFXButton deletebtn;
    public JFXTextField idtxt;
    public JFXTextField usernametxt;
    public JFXTextField conumbertxt;
    public JFXTextField txtSearch;
    public Button searchbtn;

    public TableView tableview;
    public TableColumn idcolumn;
    public TableColumn usernamecolumn;
    public TableColumn contactnumbercolumn;

    public void addbtnonaction(ActionEvent actionEvent) throws SQLException {

        String id = idtxt.getText();
        String name = usernametxt.getText();
        String number = conumbertxt.getText();

        if(isint(id)&isname(name)&isint(number)) {
            try (Connection con = DriverManager.getConnection(URL, props)) {
                String sql = "INSERT INTO user(UserId, UserName, Contact)" +
                        "VALUES(?, ?, ?)";

                PreparedStatement pstm = con.prepareStatement(sql);
                pstm.setString(1, id);
                pstm.setString(2, name);
                pstm.setString(3, number);

                if (pstm.executeUpdate() > 0) {
                    new Alert(Alert.AlertType.CONFIRMATION, "User add successfull !").show();

                    idtxt.setText("");
                    usernametxt.setText("");
                    conumbertxt.setText("");
                }

            }
        }else {
            new Alert(Alert.AlertType.WARNING, "Please check & enter valid value !").show();
        }

    }

    public void deletebtnonaction(ActionEvent actionEvent) throws SQLException {


        String id = idtxt.getText();

        try(Connection con = DriverManager.getConnection(URL, props)){
            String sql = "DELETE FROM user WHERE UserId = ?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, id);

            if (pstm.executeUpdate() > 0){
                new Alert(Alert.AlertType.CONFIRMATION, "User delete successfull !").show();
            }
            idtxt.setText("");
            usernametxt.setText("");
            conumbertxt.setText("");

            txtSearch.setText("");
        }

    }

    public void updatebtnonaction(ActionEvent actionEvent) throws SQLException {

        String id = idtxt.getText();
        String name = usernametxt.getText();
        String conu = conumbertxt.getText();

        try (Connection con = DriverManager.getConnection(URL, props)){
            String sql = "UPDATE user SET UserName = ?, Contact = ? WHERE UserId = ?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, name);
            pstm.setString(2, conu);
            pstm.setString(3, id);

            if(pstm.executeUpdate() > 0){
                new Alert(Alert.AlertType.CONFIRMATION, "User update successfull !").show();
            }

            idtxt.setText("");
            usernametxt.setText("");
            conumbertxt.setText("");

            txtSearch.setText("");

        }

    }

    public void searchbtnonaction(ActionEvent actionEvent) throws SQLException {

        String search = txtSearch.getText();

        try (Connection con = DriverManager.getConnection(URL, props)){
            String sql = "SELECT *FROM user WHERE UserId = ?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1,search);

            ResultSet resultSet = pstm.executeQuery();

            if(resultSet.next()){
                String id = resultSet.getString(1);
                String num = resultSet.getString(2);
                String conu = resultSet.getString(3);

                idtxt.setText(id);
                usernametxt.setText(num);
                conumbertxt.setText(conu);

                txtSearch.setText("");

            }

        }

    }

    public void txtseazrchKeyReleased(KeyEvent keyEvent) {

    }

    private void setCellValueFactory() {
        idcolumn.setCellValueFactory(new PropertyValueFactory<>("userid"));
        usernamecolumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        contactnumbercolumn.setCellValueFactory(new PropertyValueFactory<>("contact"));

    }

    private void getAll() {
        try {
            ObservableList<UserTM> obList = FXCollections.observableArrayList();
            List<User> places = UserModel.getAll();

            for (User place : places) {
                obList.add(new UserTM(
                        place.getUserid(),
                        place.getUsername(),
                        place.getContact()
                ));
            }
            tableview.setItems(obList);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        getAll();setCellValueFactory();
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
