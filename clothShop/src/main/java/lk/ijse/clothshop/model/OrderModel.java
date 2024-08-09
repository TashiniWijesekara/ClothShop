package lk.ijse.clothshop.model;

import lk.ijse.clothshop.db.DBConnection;
import lk.ijse.clothshop.dto.tm.Customer;
import lk.ijse.clothshop.dto.tm.Orders;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderModel {
    public static List<Orders> getAll() throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM orders";

        List<Orders> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            data.add(new Orders(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8)

            ) {
            });
        }

        return data;
    }
}
