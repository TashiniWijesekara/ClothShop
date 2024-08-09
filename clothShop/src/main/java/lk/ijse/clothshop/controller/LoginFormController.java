package lk.ijse.clothshop.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class LoginFormController {

    private static final String URL = "jdbc:mysql://localhost:3306/clothshop";
    private static final Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }

    public AnchorPane pane;
    public TextField usernametxt;
    public TextField passwordtxt;

    public void setOnActionlogin(ActionEvent event) throws IOException {
        String username = usernametxt.getText();
        String password = passwordtxt.getText();

        try(Connection con = DriverManager.getConnection(URL, props)){
            String sql = "SELECT * FROM password";

            PreparedStatement pstm  = con.prepareStatement(sql);

            ResultSet resultSet = pstm.executeQuery();
            if(resultSet.next()){
                String usernam = resultSet.getString(1);
                String pass   = resultSet.getString(2);

                if(username.equals(usernam) & password.equals(pass)){
                    System.out.println("Hriii");
                    Stage stage = (Stage) pane.getScene().getWindow();
                    stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AdminDashBordForm.fxml"))));
                    stage.setTitle("Dashboard");
                    stage.centerOnScreen();
                    stage.show();
                }else{
                    new Alert(Alert.AlertType.WARNING, "Please check & enter the correct username & password !").show();
                }

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}


