package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Player {
    public static void insertPlayer(String name, String difficulty){
        String sql = "INSERT INTO player (name, difficulty) VALUES (?, ?)";
        try(Connection cn = DBManager.connect(); PreparedStatement pstmt = cn.prepareStatement(sql)){
            pstmt.setString(1, name);
            pstmt.setString(2, difficulty);
            pstmt.executeUpdate();

        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}
