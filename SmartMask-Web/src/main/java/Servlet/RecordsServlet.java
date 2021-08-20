/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Config.Util;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Model.Records;
import DAOmodel.RecordsDAO;
import java.util.Calendar;
import java.util.TimeZone;
import javax.servlet.annotation.WebServlet;
import org.json.JSONObject;

/**
 *
 * @author Michelle
 */
@WebServlet(name = "RecordsS", urlPatterns = {"/RecordsS"})

public class RecordsServlet extends HttpServlet {

    RecordsDAO recordsDAO = new RecordsDAO();
    Records records = new Records();
    Util u = new Util();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response, String jsonResult)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println(jsonResult);

        }
    }
    String jsonResult = "";
    int averageApi = 0;
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @param jsonResult
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("GET RECORDS");
//        int type = Integer.parseInt(request.getParameter("T"));
//        switch (type) {
//            case 1: {
        ////INSERT BD///      
        u.zonaHoraria();

        String JSON = "{\"Records\":{\"temperature\":" + (request.getParameter("t")) + ","
                + "\"ppminternal\":" + Integer.parseInt(request.getParameter("pi")) + ","
                + "\"ppmexternal\":" + Integer.parseInt(request.getParameter("pe")) + ","
                + "\"pressure\":" + (request.getParameter("p")) + ","
                + "\"altitude\":" + (request.getParameter("al")) + ","
                + "\"humidity\":" + (request.getParameter("h")) + ","
                + "\"alert\":" + (request.getParameter("a")) + ","
                + "\"mask_code\":" + Integer.parseInt(request.getParameter("m")) + "}}";
//                String JSON = "{\"Records\":{\"temperature\":" + (request.getParameter("t")) + ","
//                        + "\"ppminternal\":" + (request.getParameter("ppm")) + ","
//                        + "\"alert\":" + (request.getParameter("a")) + ","
//                        + "\"mask_code\":" + Integer.parseInt(request.getParameter("m")) + "}}";
        System.out.println(JSON);
        records.setJson(JSON);
        //averageApi = (Integer.parseInt(request.getParameter("ppminternal")) + Integer.parseInt(request.getParameter("ppmexternal"))) / 2;
        jsonResult = "Outcome = " + recordsDAO.addRecords(records) + " for the String " + JSON;
//                break;
//            }
//            case 2: {
//
//                break;
//            }
//            default:
//                break;
//        }
        System.out.println(jsonResult);
        processRequest(request, response, jsonResult);
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
        System.out.println("POST RECORDS");
        int type = Integer.parseInt(request.getParameter("Type"));
        switch (type) {
            case 1: {
                ////INSERT BD///              
                //http://localhost:8081/Mask/RecordsServlet?Type=1&ppminternal=98&ppmexternal=112&mask_code=52018102&alert=0
                String JSON = "{\"Records\":{\"ppmexternal\":" + Integer.parseInt(request.getParameter("ppmexternal")) + ","
                        + "\"ppminternal\":" + Integer.parseInt(request.getParameter("ppminternal")) + ","
                        + "\"alert\":" + (request.getParameter("alert")) + ","
                        + "\"mask_code\":" + Integer.parseInt(request.getParameter("mask_code")) + "}}";
                records.setJson(JSON);
                averageApi = (Integer.parseInt(request.getParameter("ppminternal")) + Integer.parseInt(request.getParameter("ppmexternal"))) / 2;
                jsonResult = "Resultado = " + recordsDAO.addRecords(records) + " para la cadena " + JSON;
                break;
            }
            case 2: {

                break;
            }
            default:
                break;
        }
        System.out.println(jsonResult);

        processRequest(request, response, jsonResult);
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
