/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package nextteam.controllers;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nextteam.Global;
import nextteam.models.PublicNotification;
import nextteam.models.User;
import nextteam.utils.ConvertPassword;
import nextteam.utils.database.PublicNotificationDAO;
import nextteam.utils.database.UserDAO;

/**
 *
 * @author baopg
 */
@WebServlet(name = "NotificationServlet", urlPatterns = {"/notification"})
public class NotificationServlet extends HttpServlet {

    private final Gson gson = new Gson();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action.equals("add-noti")){
            addNoti(request, response);
        }else if (action.equals("list-noti")){
            listNoti(request, response);
        }else if (action.equals("search-noti")){
            searchNoti(request, response);
        }else if (action.equals("list-10-noti")){
            list10Noti(request, response);
        }else if (action.equals("view-detail")){
            viewDetail(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void addNoti(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        PublicNotification pn = this.gson.fromJson(reader, PublicNotification.class);
        response.setContentType("application/json");
        System.out.println("Yêu cầu tạo thông báo");
        PrintWriter out = response.getWriter();
        int status = new PublicNotificationDAO(Global.generateConnection()).addNotification(pn);
        System.out.println("Tạo thông báo thành công");
        String userJsonString = this.gson.toJson(pn);
        out.print(userJsonString);
        out.flush();

    }
    protected void listNoti(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String clubId = request.getParameter("clubId");
        
        PrintWriter out = response.getWriter();

        // Gọi publicNotificationsDAO để lấy danh sách publicNotifications
        List<PublicNotification> publicNotifications = Global.publicNotification.getAllPublicNotifications(clubId);
        
        // Chuyển danh sách thành dạng JSON
        String json = gson.toJson(publicNotifications);

        // Gửi JSON response về client
        out.print(json);
        out.flush();

    }
    protected void searchNoti(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String search = request.getParameter("search");
        String clubId = request.getParameter("clubId");

        PrintWriter out = response.getWriter();

        // Gọi publicNotificationsDAO để lấy danh sách publicNotifications
        List<PublicNotification> publicNotifications = Global.publicNotification.getNotificationByNameString(search,clubId);

        // Chuyển danh sách thành dạng JSON
        String json = gson.toJson(publicNotifications);

        // Gửi JSON response về client
        out.print(json);
        out.flush();

    }
    protected void list10Noti(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String clubId = request.getParameter("clubId");
        
        PrintWriter out = response.getWriter();

        // Gọi publicNotificationsDAO để lấy danh sách publicNotifications
        List<PublicNotification> publicNotifications = Global.publicNotification.get10PublicNotifications(clubId);
        
        // Chuyển danh sách thành dạng JSON
        String json = gson.toJson(publicNotifications);

        // Gửi JSON response về client
        out.print(json);
        out.flush();

    }
     protected void viewDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        BufferedReader reader = request.getReader();
        PrintWriter out = response.getWriter();

        // Gọi publicNotificationsDAO để lấy danh sách publicNotifications
        PublicNotification publicNotifications = Global.publicNotification.getNotificationByIdString(id);

        // Chuyển danh sách thành dạng JSON
        String json = gson.toJson(publicNotifications);

        // Gửi JSON response về client
        out.print(json);
        out.flush();

    }



    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
