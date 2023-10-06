/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nextteam;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import nextteam.utils.database.ClubDAO;
import nextteam.utils.database.EventDAO;
import nextteam.utils.database.EventRegistrationDAO;
import nextteam.utils.database.LocationDAO;
import nextteam.utils.database.MajorDAO;
import nextteam.utils.database.OtpCodeDAO;
import nextteam.utils.database.PublicNotificationDAO;
import nextteam.utils.database.UserDAO;

/**
 *
 * @author vnitd
 */
public class Global {

    public static String server = "localhost";
    public static String database = "NextTeam";
    public static String username = "sa";
    public static String password = "Bao.thang.1912";

    private static Connection conn;

    public static String workingPath;

    public static ClubDAO clubDAO;
    public static MajorDAO major;
    public static EventDAO eventDao;
    public static UserDAO user;
    public static OtpCodeDAO otpCode;
    public static PublicNotificationDAO publicNotification;
    public static EventRegistrationDAO eventRegistration;
    public static LocationDAO location;

    public static Connection generateConnection() {
        try {
            Class<?> clazz = Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            DriverManager.registerDriver((Driver) clazz.getDeclaredConstructor().newInstance());
            String url = "jdbc:sqlserver://" + server + ";databaseName=" + database + ";user=" + username + ";password=" + password + ";encrypt=true;trustServerCertificate=true";
            return DriverManager.getConnection(url);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static void setDAOConnection() {
        conn = generateConnection();
        if (conn == null) {
            throw new RuntimeException("Error while trying connect to SQL Server!");
        }
        clubDAO = new ClubDAO(conn);
        major = new MajorDAO(conn);
        eventDao = new EventDAO(conn);
        user = new UserDAO(conn);
        otpCode = new OtpCodeDAO(conn);
        publicNotification = new PublicNotificationDAO(conn);
        eventRegistration = new EventRegistrationDAO(conn);
        location = new LocationDAO(conn);
    }

    public static void closeDAOConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            conn = null;
        }
    }

    public static void main(String[] args) {
        System.out.println(generateConnection());

    }
}
