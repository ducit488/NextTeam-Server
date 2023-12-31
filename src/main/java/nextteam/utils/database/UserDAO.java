/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nextteam.utils.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;
import nextteam.Global;
import nextteam.models.Club;
import nextteam.models.User;
import nextteam.models.UserCard;
//import nextteam.utils.ConvertPassword;
import nextteam.utils.SQLDatabase;
import nextteam.utils.encryption.BCrypt;
import org.apache.http.ParseException;

/**
 *
 * @author vnitd
 */
public class UserDAO extends SQLDatabase {

    public UserDAO(Connection connection) {
        super(connection);
    }

    public ArrayList<User> getListUsers() {
        ArrayList<User> list = new ArrayList<>();
        ResultSet rs = executeQueryPreparedStatement("SELECT * FROM users");
        try {
            while (rs.next()) {
                // list.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3),
                // rs.getString(4), rs.getString(5), rs.getString(6), rs.getNString(7),
                // rs.getNString(8), rs.getString(9), rs.getString(10), rs.getString(11),
                // rs.getString(12), rs.getString(13), rs.getString(14), rs.getString(15),
                // rs.getString(16), rs.getString(17), rs.getString(18),
                // rs.getBoolean(19)),rs.getBoolean(20));
                list.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getNString(7), rs.getNString(8), rs.getString(9), rs.getString(10),
                        rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14), rs.getString(15),
                        rs.getString(16), rs.getString(17), rs.getString(18), rs.getBoolean(19), rs.getBoolean(20)));
            }

        } catch (Exception e) {

        }
        return list;
    }

    public ArrayList<UserCard> getUsersCardList(String clubId) {
        ArrayList<UserCard> list = new ArrayList<>();
        ResultSet rs = executeQueryPreparedStatement("SELECT u.id, u.avatarUrl, u.firstname, u.lastname, u.username, u.gender FROM users AS u JOIN engagements AS e ON u.id = e.userId WHERE e.clubId = ? AND e.status != 0", clubId);
        try {
            while (rs.next()) {
                list.add(new UserCard(rs.getInt(1), rs.getString(2), rs.getNString(3) + " " + rs.getNString(4),
                        rs.getString(5), rs.getString(6)));
            }
        } catch (Exception e) {

        }
        return list;
    }

    public ArrayList<UserCard> getUsersCardListForManage(String clubId) {
        ArrayList<UserCard> list = new ArrayList<>();
        String status;
        ResultSet rs = executeQueryPreparedStatement("SELECT u.id, u.avatarUrl, u.firstname, u.lastname, u.username, u.gender, d.id, e.status  FROM users as u JOIN engagements as e ON u.id = e.userId JOIN departments as d ON e.departmentId = d.id WHERE e.clubId = ? ", clubId);
        try {
            while (rs.next()) {
                if (rs.getInt("status") == 0) {
                    status = "inactive";
                } else {
                    status = "active";
                }
                list.add(new UserCard(rs.getInt(1), rs.getString(2), rs.getNString(3) + " " + rs.getNString(4),
                        rs.getString(5), rs.getString(6), rs.getInt(7), status));
            }
        } catch (Exception e) {

        }
        return list;
    }

    public User getUserInfoById(User t) {
        User ketQua = null;
        try {
            ResultSet rs = executeQueryPreparedStatement("SELECT * FROM users WHERE id=?", t.getId());
            if (rs.next()) {
                ketQua = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getNString(7), rs.getNString(8), rs.getString(9), rs.getString(10),
                        rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14), rs.getString(15),
                        rs.getString(16), rs.getString(17), rs.getString(18), rs.getBoolean(19), rs.getBoolean(20));
            }

        } catch (Exception e) {
        }
        return ketQua;
    }

    public User getListUserByIdString(String t) {
        User ketQua = null;
        try {
            ResultSet rs = executeQueryPreparedStatement("SELECT * FROM users WHERE id=?", t);
            if (rs.next()) {
                ketQua = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getNString(7), rs.getNString(8), rs.getString(9), rs.getString(10),
                        rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14), rs.getString(15),
                        rs.getString(16), rs.getString(17), rs.getString(18), rs.getBoolean(19), rs.getBoolean(20));
            }
        } catch (Exception e) {
        }
        return ketQua;
    }

    public int delete(String id) {
        int ketQua = 0;
        ketQua = executeUpdatePreparedStatement("DELETE from users  WHERE id=?", id);
        return ketQua;
    }

    public int insert(final User t) {
        int ketQua = 0;
        ketQua = executeUpdatePreparedStatement(
                "INSERT INTO users (email, username, password, avatarUrl, bannerUrl, firstname, lastname, phoneNumber,major,academicYear,gender,dob,homeTown,facebookUrl,linkedInUrl)  VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                t.getEmail(),
                t.getUsername(),
                t.getPassword(),
                t.getAvatarURL(),
                t.getBannerURL(),
                t.getFirstname(),
                t.getLastname(),
                t.getPhoneNumber(),
                t.getMajor(),
                t.getAcademicYear(),
                t.getGender(),
                t.getDob(),
                t.getHomeTown(),
                t.getFacebookUrl(),
                t.getLinkedInUrl());
        return ketQua;
    }

    public int register(final User t) {
        int ketQua = 0;
        ketQua = executeUpdatePreparedStatement(
                "INSERT INTO users (email, avatarUrl, username, password, firstname, lastname, phoneNumber, gender)  VALUES (?,?,?,?,?,?,?,?)",
                t.getEmail(),
                t.getAvatarURL(),
                t.getUsername(),
                t.getPassword(),
                t.getFirstname(),
                t.getLastname(),
                t.getPhoneNumber(),
                t.getGender());
        return ketQua;
    }

    public int insertAll(ArrayList<User> arr) {
        int dem = 0;
        for (final User user : arr) {
            dem += this.insert(user);
        }
        return dem;
    }

    public int update(User t) {
        int ketQua = 0;
        System.out.println("dob: " + t.getDob());
        ketQua = executeUpdatePreparedStatement(
                "UPDATE users  SET  email=?, username=?, firstname=?, lastname=?, phoneNumber=?,major=?,academicYear=?,gender=?,dob=?,homeTown=?,facebookUrl=?,linkedInUrl=? WHERE id=?",
                t.getEmail(),
                t.getUsername(),
                t.getFirstname(),
                t.getLastname(),
                t.getPhoneNumber(),
                t.getMajor(),
                t.getAcademicYear(),
                t.getGender(),
                t.getDob(),
                t.getHomeTown(),
                t.getFacebookUrl(),
                t.getLinkedInUrl(),
                t.getId());
        return ketQua;

    }

    public int updateAvatar(User t) {
        int ketQua = 0;
        System.out.println("update avatar in dao: " + t.getAvatarURL());
        ketQua = executeUpdatePreparedStatement(
                "UPDATE users  SET  avatarUrl=? WHERE id=?",
                t.getAvatarURL(),
                t.getId());
        return ketQua;
    }

    public int updatePassword(User t) {
        int ketQua = 0;
        ketQua = executeUpdatePreparedStatement(
                "UPDATE users  SET  password=? WHERE id=?",
                t.getPassword(),
                t.getId());
        return ketQua;
    }

    public User login(String email, String password) {
        User ketQua = null;
        try {
            ResultSet rs = executeQueryPreparedStatement("SELECT * FROM users WHERE email=?", email);
            if (rs.next()) {
                String hashedPw = rs.getString("password");
                if (BCrypt.checkpw(password, hashedPw)) {
                    // public User(int id, String email, String username, String password, String
                    // avatarURL, String bannerURL, String firstname, String lastname, String
                    // studentCode, String phoneNumber, String major, String academicYear, String
                    // gender, String dob, String homeTown, String facebookUrl, String linkedInUrl,
                    // String createdAt, String updatedAt, boolean isActive) {
                    return new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
                            rs.getString(6), rs.getNString(7), rs.getNString(8), rs.getString(9), rs.getString(10),
                            rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14), rs.getString(15),
                            rs.getString(16), rs.getString(17), rs.getString(18), rs.getBoolean(19), rs.getBoolean(20));
                }
            }
        } catch (Exception e) {

        }

        return ketQua;
    }

    public User selectByEmail(String email) {
        User ketQua = null;
        try {
            ResultSet rs = executeQueryPreparedStatement("SELECT * FROM users WHERE email=?", email);
            if (rs.next()) {
                return new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getNString(7), rs.getNString(8), rs.getString(9), rs.getString(10),
                        rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14), rs.getString(15),
                        rs.getString(16), rs.getString(17), rs.getString(18), rs.getBoolean(19), rs.getBoolean(20));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean checkAvailableEmail(User u) {
        try {
            ResultSet rs = executeQueryPreparedStatement("SELECT * FROM users WHERE email=? AND id != ?", u.getEmail(), u.getId());
            if (rs.next()) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean checkAvailableStuCode(User u) {
        try {
            ResultSet rs = executeQueryPreparedStatement("SELECT * FROM users WHERE username=? AND id != ?", u.getUsername(), u.getId());
            if (rs.next()) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean StudentCodeCheck(String studentCode) {
        try {
            ResultSet rs = executeQueryPreparedStatement("SELECT * FROM users WHERE username = ?", studentCode);
            // System.out.println("aaa");
            if (rs.next()) {
                // System.out.println(rs.getInt(1));
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public ArrayList<User> getListMember(String clubId) {
        ArrayList<User> list = new ArrayList<>();
        ResultSet rs = executeQueryPreparedStatement("""
                SELECT *
                FROM users
                WHERE id IN (SELECT userId FROM engagements WHERE clubId = ?);""", clubId);
        try {

            while (rs.next()) {
                // public Club(int id, String name, String subname, int categoryId, String
                // description, String avatarUrl, String bannerUrl, int movementPoint, double
                // balance, Date createdAt, Date updatedAt) {

                list.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getNString(7), rs.getNString(8), rs.getString(9), rs.getString(10),
                        rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14), rs.getString(15),
                        rs.getString(16), rs.getString(17), rs.getString(18), rs.getBoolean(19), rs.getBoolean(20)));

            }

        } catch (Exception e) {
            Logger.getLogger(ClubDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    // block user
    public int blockUser(String id) {
        int ketQua = 0;
        ketQua = executeUpdatePreparedStatement(
                "UPDATE users  SET  isActive='false' WHERE id=?",
                id);
        return ketQua;

    }

    // unblock user
    public int unblockUser(String id) {
        int ketQua = 0;
        ketQua = executeUpdatePreparedStatement(
                "UPDATE users  SET  isActive='true' WHERE id=?",
                id);
        return ketQua;

    }

    // dct manager
    public int dct_manager(String clubId, String userId) {
        int ketQua = 0;
        ketQua = executeUpdatePreparedStatement(
                "UPDATE engagements\n"
                + "SET roleId = 2\n"
                + "WHERE  clubId='" + clubId + "' and userId='" + userId + "'");
        return ketQua;
    }

    // dct member
    public int dct_member(String clubId, String userId) {
        int ketQua = 0;
        ketQua = executeUpdatePreparedStatement(
                "UPDATE engagements\n"
                + "SET roleId = 1\n"
                + "WHERE  clubId='" + clubId + "' and userId='" + userId + "'");
        return ketQua;
    }

    // test connection
    public void changePassword(int id, String password) {
        password = Global.getHashedPassword(password);
        executeUpdatePreparedStatement("UPDATE users SET password=? WHERE id=?", password, id);
    }

    public User getUserById(int id) {
        ResultSet rs = executeQueryPreparedStatement("SELECT * FROM users WHERE id=?", id);
        try {
            if (rs.next()) {
                return new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getNString(7), rs.getNString(8), rs.getString(9), rs.getString(10),
                        rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14), rs.getString(15),
                        rs.getString(16), rs.getString(17), rs.getString(18), rs.getBoolean(19), rs.getBoolean(20));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public String getUserRoleById(String id, String clubId) {
        ResultSet rs = executeQueryPreparedStatement("select r.name from engagements e    join    roles   r   on  e.roleId=r.id   where userId =  ?   and clubId=?", id, clubId);
        try {
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public static void main(String... args) {
        System.out.println(new UserDAO(Global.generateConnection()).getUserRoleById("1", "1"));
    }

}
