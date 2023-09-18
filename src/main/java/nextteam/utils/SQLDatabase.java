/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nextteam.utils;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vnitd
 */
public abstract class SQLDatabase {

    private Connection conn;
    private String server;
    private String database;
    private String user;
    private String password;

    /**
     * Create connection when calling to this constructor
     *
     * @param server
     * @param database
     * @param user
     * @param password
     */
    public SQLDatabase(String server, String database, String user, String password) {
        this.server = server;
        this.database = database;
        this.user = user;
        this.password = password;
    }

    public void setConnection() {
        try {
            Class<?> clazz = Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            DriverManager.registerDriver((Driver) clazz.getDeclaredConstructor().newInstance());
            String url = "jdbc:sqlserver://" + server + ";databaseName=" + database + ";user=" + user + ";password=" + password + ";encrypt=true;trustServerCertificate=true";
            conn = DriverManager.getConnection(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return conn;
    }

    /**
     * Check if it did connect
     *
     * @return
     */
    public boolean isConnected() {
        return conn != null;
    }

    /**
     * Close connection
     */
    public void close() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Statement getStatement() {
        Statement statement = null;
        try {
            statement = conn.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(SQLDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return statement;
    }

    private boolean checkNString(String des) {
        for (int i = 0; i < des.length(); i++) {
            if (des.charAt(i) >= 128) {
                return true;
            }
        }
        return false;
    }

    private PreparedStatement getPreparedStatement(String sql, Object... values) {
        PreparedStatement statement = null;
        if (conn != null)
            try {
            statement = conn.prepareStatement(sql);
            for (int i = 0; i < values.length; i++) {
                if (values[i] == null) {
                    if (values[i] instanceof Date) {
                        statement.setNull(i + 1, Types.DATE);
                    } else {
                        statement.setNull(i + 1, Types.NULL);
                    }
                } else if (values[i] instanceof Character) {
                    statement.setString(i + 1, values[i] + "");
                } else if (values[i] instanceof Integer) {
                    statement.setInt(i + 1, (int) values[i]);
                } else if (values[i] instanceof Double) {
                    statement.setDouble(i + 1, (double) values[i]);
                } else if (values[i] instanceof String) {
                    if (checkNString((String) values[i])) {
                        statement.setNString(i + 1, (String) values[i]);
                    } else {
                        statement.setString(i + 1, (String) values[i]);
                    }
                } else {
                    statement.setString(i + 1, values[i].toString());
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return statement;
    }

    public void executePreparedStatement(String sql, Object... values) {
        try {
            getPreparedStatement(sql, values).execute();
        } catch (SQLException ex) {
            Logger.getLogger(SQLDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public int executeUpdatePreparedStatement(String sql, Object... values) {
        int i = -1;
        try {
            i = getPreparedStatement(sql, values).executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SQLDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }

        return i;
    }

    public ResultSet executeQueryPreparedStatement(String sql, Object... values) {
        ResultSet rs = null;
        try {
            rs = getPreparedStatement(sql, values).executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(SQLDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
}
