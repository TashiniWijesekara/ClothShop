package lk.ijse.clothshop.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.clothshop.dto.tm.Customer;
import lk.ijse.clothshop.dto.tm.Orders;
import lk.ijse.clothshop.dto.tm.tm.CustomerTM;
import lk.ijse.clothshop.dto.tm.tm.OrdersTM;
import lk.ijse.clothshop.model.CustomerModel;
import lk.ijse.clothshop.model.OrderModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class OrdertableController implements Initializable {

    public TableView tableview;
    public TableColumn orderidcolumn;
    public TableColumn customeridcolum;
    public TableColumn orderdatecolumn;
    public TableColumn itemcolumn;
    public TableColumn tempricecolumn;
    public TableColumn amountcolumn;
    public TableColumn pricecolumn;
    public TableColumn bonuscolumn;

    private void setCellValueFactory() {
        orderidcolumn.setCellValueFactory(new PropertyValueFactory<>("orderid"));
        customeridcolum.setCellValueFactory(new PropertyValueFactory<>("Customerid"));
        orderdatecolumn.setCellValueFactory(new PropertyValueFactory<>("orderdate"));
        itemcolumn.setCellValueFactory(new PropertyValueFactory<>("item"));
        tempricecolumn.setCellValueFactory(new PropertyValueFactory<>("itemprice"));
        amountcolumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        pricecolumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        bonuscolumn.setCellValueFactory(new PropertyValueFactory<>("bonus"));
    }

    private void getAll() {
        try {
            ObservableList<OrdersTM> obList = FXCollections.observableArrayList();
            List<Orders> places = OrderModel.getAll();

            for (Orders place : places) {
                obList.add(new OrdersTM(
                        place.getOrderid(),
                        place.getOrderdate(),
                        place.getPrice(),
                        place.getCustomerid(),
                        place.getItem(),
                        place.getItemprice(),
                        place.getBonus(),
                        place.getAmount()
                ));
            }
            tableview.setItems(obList);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getAll();setCellValueFactory();
    }
}


