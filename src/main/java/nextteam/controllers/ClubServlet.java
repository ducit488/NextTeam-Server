/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package nextteam.controllers;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nextteam.Global;
import nextteam.models.Club;
import nextteam.models.response.ClubResponse;

/**
 *
 * @author bravee06
 */
@WebServlet(name = "ClubUserServlet", urlPatterns = { "/api/club" })
public class ClubServlet extends HttpServlet {

    private final Gson gson = new Gson();

    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        PrintWriter out = response.getWriter();
        String userId = request.getParameter("userId");
        String command = request.getParameter("cmd");
        if (command.equals("list")) {
            String res = Global.clubDAO.getListClubs().toString();
            out.print(res);
            out.flush();
        } else if (command.equals("list-res")) {
            ArrayList<ClubResponse> res = Global.clubDAO.getListClubs(userId);
            String clubsJsonString = gson.toJson(res);
            out.print(clubsJsonString);
            out.flush();
        } else if (command.equals("rank")) {
            String res = Global.clubDAO.getRankingClubs().toString();
            out.print(res);
            out.flush();
        } else if (command.equals("add")) {
            String name = request.getParameter("name");
            String subname = request.getParameter("subname");
            int categoryId;
            String description = request.getParameter("description");
            String avatarUrl = request.getParameter("avatarUrl");
            String bannerUrl = request.getParameter("bannerUrl");
            
            
           
            int movementPoint;
            double balance;
            try {
                movementPoint = Integer.parseInt(request.getParameter("movementPoint"));
            } catch (NumberFormatException e) {
                movementPoint = 0;
            }
            try {
                balance = Double.parseDouble(request.getParameter("balance"));
            } catch (NumberFormatException e) {
                balance = 0;
            }
            try {
                categoryId = Integer.parseInt(request.getParameter("categoryId"));
            } catch (NumberFormatException e) {
                categoryId = 0;
            }

            Club c = new Club(name, subname, categoryId, description, avatarUrl, bannerUrl, movementPoint, balance,
                    true);
            System.out.println(c);
            int added = Global.clubDAO.addClub(c);
            String json = "";
            if (added == 1) {
                json = "[{ \"status\": \"success\"}]";
            } else {

                 response.setStatus(HttpServletResponse.SC_NOT_FOUND); 
                response.getWriter().print("[{ \"status\": \"failed\"}]"); 
            }
            out.print(json);
            out.flush();
        } else if (command.equals("update")) {
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            String subname = request.getParameter("subname");
            int categoryId;
            String description = request.getParameter("description");
            String avatarUrl = request.getParameter("avatarUrl");
            String bannerUrl = request.getParameter("bannerUrl");
            boolean isActive = Boolean.parseBoolean(request.getParameter("isActive"));
            
            System.out.println(isActive);
            int movementPoint;
            double balance;
            try {
                movementPoint = Integer.parseInt(request.getParameter("movementPoint"));
            } catch (NumberFormatException e) {
                movementPoint = 0;
            }
            try {
                balance = Double.parseDouble(request.getParameter("balance"));
            } catch (NumberFormatException e) {
                balance = 0;
            }
            try {
                categoryId = Integer.parseInt(request.getParameter("categoryId"));
            } catch (NumberFormatException e) {
                categoryId = 0;
            }

            Club c = new Club(name, subname, categoryId, description, avatarUrl, bannerUrl, movementPoint, balance,
                    isActive);
            
            int updated = Global.clubDAO.updateClub(c, id);
            String json = "";
            if (updated == 1) {
                json = "[{ \"status\": \"success\"}]";
                out.print(json);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND); 
                response.getWriter().print("[{ \"status\": \"failed\"}]"); 
            }
            
        } else {
            int id = Integer.parseInt(request.getParameter("id"));

            int deleted = Global.clubDAO.deleteClub(id);
            String json = "";
            if (deleted == 1) {
                response.setStatus(HttpServletResponse.SC_OK); 
                response.getWriter().print("[{ \"status\": \"success\"}]"); 
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND); 
                response.getWriter().print("[{ \"status\": \"failed\"}]"); 
            }
            
        }
    }
}
