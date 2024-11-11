package koneksi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class konek {
    public static Connection GetConnection() throws SQLException {
        try {
            // Daftarkan driver JDBC secara eksplisit
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC driver not found", e);
        }

        // Buat koneksi baru setiap kali dipanggil
        return DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/db_psycare",
            "root",
            ""
        );
    }
}