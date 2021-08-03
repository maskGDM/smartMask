/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apis;

import Config.Methods;
import Controller.RecordsCtrl;
import Controller.UserCtrl;
import com.google.gson.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author CleanCode
 */
@Path("maskapis")
public class MaskApis {

    @Context
    private UriInfo context;
    @Context
    private HttpServletRequest request;
//    RecordsDAO recordsDAO;
    RecordsCtrl recordsCtrl;
    UserCtrl userCtrl;

    /**
     * Creates a new instance of Project, Files, Data
     */
    public MaskApis() {
        recordsCtrl = new RecordsCtrl();
        userCtrl = new UserCtrl();
    }

    /*
     * ********************************************
     * Records
     *********************************************
     */
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/maskRecords")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response maskRecords(String data) {
        String message;
        System.out.println("maskRecords()");
        System.out.println(data);
        JsonObject Jso = Methods.stringToJSON(data);
        if (Jso.size() > 0) {
//            String sessionToken = Methods.JsonToString(Jso, "user_token", "");
//            String[] clains = Methods.getDataToJwt(sessionToken);

            String project_name = Methods.JsonToString(Jso, "id_usuario", "");

//            String[] res = Methods.validatePermit(clains[0], clains[1], 1);
//            if (res[0].equals("2")) {
            String[] res = recordsCtrl.listRecords(project_name);
            message = Methods.getJsonMessage(res[0], res[1], res[2]);
//            } else {
//                message = Methods.getJsonMessage("4", "Error in the request parameters.", "[]");
//            }
        } else {
            message = Methods.getJsonMessage("4", "Missing data.", "[]");
        }
        return Response.ok(message)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-with")
                .build();
    }

    /*
     * ********************************************
     * logIn
     *********************************************
     */
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/logIn")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response logIn(String data) {
        String message;
        System.out.println("logIn()");
        System.out.println(data);
        JsonObject Jso = Methods.stringToJSON(data);
        if (Jso.size() > 0) {
//            String sessionToken = Methods.JsonToString(Jso, "user_token", "");
//            String[] clains = Methods.getDataToJwt(sessionToken);

            String user = Methods.JsonToString(Jso, "user", "");
            String pass = Methods.JsonToString(Jso, "pass", "");
//            String[] res = Methods.validatePermit(clains[0], clains[1], 1);
//            if (res[0].equals("2")) {
            String[] res = userCtrl.logIn(user, pass);
            message = Methods.getJsonMessage(res[0], res[1], res[2]);
//            } else {
//                message = Methods.getJsonMessage("4", "Error in the request parameters.", "[]");
//            }
        } else {
            message = Methods.getJsonMessage("4", "Missing data.", "[]");
        }
        return Response.ok(message)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-with")
                .build();
    }
}
