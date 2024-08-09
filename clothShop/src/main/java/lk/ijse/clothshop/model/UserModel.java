package lk.ijse.clothshop.model;

import lk.ijse.clothshop.db.DBConnection;
import lk.ijse.clothshop.dto.tm.Customer;
import lk.ijse.clothshop.dto.tm.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserModel {
    public static List<User> getAll() throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM user";

        List<User> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            data.add(new User(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)
            ) {
            });
        }

        return data;
    }
}
