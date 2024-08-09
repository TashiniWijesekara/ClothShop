package lk.ijse.clothshop.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminDashBordFormController implements Initializable {

    public BarChart <CategoryAxis, NumberAxis>areachart;
    public JFXButton userbtn;
    public JFXButton customerbtn;
    public JFXButton orderbtn;

    public void emplyOnAction(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/EmployeeForm.fxml"));
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

    public void btnemplyatndceOnAction(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/EmployeeAttendForm.fxml"));
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

    public void salaryOnAction(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/EmployeeSalaryForm.fxml"));
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

    public void suplyerOnAcTION(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/SupplierForm.fxml"));
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

    public void itmsOnAction(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/ItemForm.fxml"));
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

    public void logoutOnAction(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/LoginForm.fxml"));
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

    public void reportOnAction(ActionEvent event) {
        /*FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/"));
        Parent root1 = null;
        try {
            root1 = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.show();*/
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        XYChart.Series Org1Data = new XYChart.Series();
        Org1Data.setName("Aprail");
        Org1Data.getData().add(new XYChart.Data("1", 11));
        Org1Data.getData().add(new XYChart.Data("2", 17));
        Org1Data.getData().add(new XYChart.Data("3", 19));
        Org1Data.getData().add(new XYChart.Data("4", 15));
        Org1Data.getData().add(new XYChart.Data("5", 17));
        Org1Data.getData().add(new XYChart.Data("6", 24));
        Org1Data.getData().add(new XYChart.Data("7", 27));
        Org1Data.getData().add(new XYChart.Data("8", 28));
        Org1Data.getData().add(new XYChart.Data("9", 31));
        Org1Data.getData().add(new XYChart.Data("10", 26));
        Org1Data.getData().add(new XYChart.Data("11", 28));
        Org1Data.getData().add(new XYChart.Data("12", 29));

        XYChart.Series Org2Data = new XYChart.Series();
        Org2Data.setName("May");
        Org2Data.getData().add(new XYChart.Data("1", 10));
        Org2Data.getData().add(new XYChart.Data("2", 12));
        Org2Data.getData().add(new XYChart.Data("3", 14));
        Org2Data.getData().add(new XYChart.Data("4", 18));
        Org2Data.getData().add(new XYChart.Data("5", 7));
        Org2Data.getData().add(new XYChart.Data("6", 2));
        Org2Data.getData().add(new XYChart.Data("7", -1));
        Org2Data.getData().add(new XYChart.Data("8", -2));
        Org2Data.getData().add(new XYChart.Data("9", 8));
        Org2Data.getData().add(new XYChart.Data("10", 25));
        Org2Data.getData().add(new XYChart.Data("11", 33));
        Org2Data.getData().add(new XYChart.Data("12", 36));

        areachart.getData().add(Org1Data);
        areachart.getData().add(Org2Data);
    }

    public void userbtnonaction(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/user_form.fxml"));
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

    public void customerbtnonaction(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/CustomerForm.fxml"));
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

    public void orderbtnonaction(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/OrderForm.fxml"));
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
