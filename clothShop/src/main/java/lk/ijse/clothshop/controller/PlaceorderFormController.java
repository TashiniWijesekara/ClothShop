package lk.ijse.clothshop.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import lk.ijse.clothshop.db.DBConnection;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
//import net.sf.jasperreports.engine.util.JRProperties;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.*;
import java.util.Properties;

public class PlaceorderFormController {

    private static final String URL = "jdbc:mysql://localhost:3306/clothshop";
    private static final Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }

    public JFXTextField orderidtxt;
    public Label itemlabltxt;
    public Label amountlbltxt;
    public Label discountlbltxt;
    public Label totallabltxt;
    public JFXButton confirmpaymentbtn;

    public void orderidonaction(ActionEvent actionEvent) throws SQLException {

        String id = orderidtxt.getText();

        try (Connection con = DriverManager.getConnection(URL, props)){
            String sql = "SELECT *FROM orders WHERE OrderId = ?";

            PreparedStatement pstm = con.prepareStatement(sql);

            pstm.setString(1, id);

            ResultSet resultSet = pstm.executeQuery();

            if(resultSet.next()){
                String total = resultSet.getString(3);
                String item = resultSet.getString(5);
                String dicount = resultSet.getString(7);
                String amount = resultSet.getString(8);

                itemlabltxt.setText(item);
                amountlbltxt.setText(amount);
                discountlbltxt.setText(dicount+"%");
                totallabltxt.setText(total);

            }else{
                new Alert(Alert.AlertType.CONFIRMATION, "Please check and enter the correct id !").show();
            }


        }


    }

    public void confirmpaymentbtnonaction(ActionEvent actionEvent) throws ClassNotFoundException {

        String ID = orderidtxt.getText();

        try {
            //JRProperties.setProperty("net.sf.jasperreports.awt.ignore.missing.font", "true");
            JasperDesign jasperDesign = JRXmlLoader.load("D:\\cloth\\clothShop  Tashi\\clothShop\\src\\main\\resources\\jasper\\tashipayment.jrxml");
            JRDesignQuery query = new JRDesignQuery();
            query.setText("SELECT *FROM orders WHERE OrderId = "+ID);
            jasperDesign.setQuery(query);

            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,null, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);

        } catch (JRException | SQLException e) {
            e.printStackTrace();
        }
    }

}
