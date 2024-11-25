package koneksi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class konek {
    public static Connection GetConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC driver tidak ditemukan", e);
        }
        return DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/db_psycare",
            "root",
            ""
        );
    }
}
