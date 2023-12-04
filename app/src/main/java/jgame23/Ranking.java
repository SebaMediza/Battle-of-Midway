package jgame23;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Ranking {
    private Connection conn;
    private Statement stmt;
    private PreparedStatement pstmt;
    private static final String query = "CREATE TABLE IF NOT EXISTS RANKING (ID INTEGER PRIMARY KEY, JUGADOR VARCHAR(150) NOT NULL, PUNTAJE INTEGER, FECHA VARCHAR(150));";
    //private static final String query = "DROP TABLE IF EXISTS RANKING;";

    public Ranking(){
        this.stmt = null;
        this.pstmt = null;
        try {
            String url = "jdbc:sqlite:ranking.db";
            this.conn = DriverManager.getConnection(url); // Si no existe crea el archivo de la base de datos
            System.out.println("Conectado a SQLite.");
            this.stmt = conn.createStatement();
            stmt.executeUpdate(query);
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void getData() {
        try {
            String sql = "SELECT * FROM RANKING ORDER BY PUNTAJE DESC LIMIT 10;";
            this.stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " "
                        + rs.getString("JUGADOR") + " " 
                        + rs.getInt("PUNTAJE") + " "
                        + rs.getString("FECHA"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insert(String nombre, int score, String fecha){
        String sql = "INSERT INTO ranking(JUGADOR, PUNTAJE, FECHA) VALUES(?,?,?)";
        try {
            this.pstmt = this.conn.prepareStatement(sql);
            this.pstmt.setString(1, nombre);
            this.pstmt.setInt(2, score);
            this.pstmt.setString(3, fecha);
            this.pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
