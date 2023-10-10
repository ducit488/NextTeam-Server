/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package nextteam.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nextteam.Global;
import nextteam.Global;
import nextteam.models.Club;

/**
 *
 * @author bravee06
 */
public class ClubServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        PrintWriter out = response.getWriter();
        String command = request.getParameter("cmd");
        if (command.equals("list")) {
            String res = Global.clubDAO.getListClubs().toString();
            out.print(res);
            out.flush();
        }
        String json = "";
        if (command.equals("add")) {

            String name = request.getParameter("name");
            String subname = request.getParameter("subname");
            int categoryId;
            String description = request.getParameter("description");
            String avatarUrl = request.getParameter("avatarUrl");
            String bannerUrl = request.getParameter("bannerUrl");
            String isActive_Raw = request.getParameter("isActive");
            System.out.println(isActive_Raw);
            boolean isActive = Boolean.parseBoolean(isActive_Raw);
            System.out.println(isActive);
            int movementPoint;
            double balance;
            try{
                movementPoint = Integer.parseInt(request.getParameter("movementPoint"));
            }catch(NumberFormatException e){
                movementPoint = 0;
            }
            try{
                balance = Double.parseDouble(request.getParameter("balance"));
            }catch(NumberFormatException e){
                balance = 0;
            }
            try{
                categoryId = Integer.parseInt(request.getParameter("categoryId"));
            }catch(NumberFormatException e){
                categoryId = 0;
            }
           
            Club c = new Club(name, subname, categoryId, description, avatarUrl, bannerUrl, movementPoint, balance,isActive);
            System.out.println(c.isIsActive());
            int added = Global.clubDAO.addClub(c);
            
            if (added == 1) {
                json = "[{ \"status\": \"success\"}]";
            } else {
      
                json = "[{ \"status\": \"failed\"}]";

            }
            out.print(json);
            out.flush();

        }

        int id = Integer.parseInt(request.getParameter("id"));
        if (command.equals("update")) {

            String name = request.getParameter("name");
            String subname = request.getParameter("subname");
            int categoryId;
            String description = request.getParameter("description");
            String avatarUrl = request.getParameter("avatarUrl");
            String bannerUrl = request.getParameter("bannerUrl");
            boolean isActive = Boolean.parseBoolean(request.getParameter("isActive"));
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

            Club c = new Club(name, subname, categoryId, description, avatarUrl, bannerUrl, movementPoint, balance, isActive);
            System.out.println(c);
            int updated = Global.clubDAO.updateClub(c, id);

            if (updated == 1) {
                json = "[{ \"status\": \"success\"}]";

                out.print(json);
            } else {
                json = "[{ \"status\": \"failed\"}]";
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print(json);
            }

        } else if (command.equals("num")) {
                
        } else {

            int deleted = Global.clubDAO.deleteClub(id);

            if (deleted == 1) {
                json = "[{ \"status\": \"success\"}]";

            } else {
                json = "[{ \"status\": \"failed\"}]";

            }
            out.print(json);
            out.flush();
        }

    }

}
