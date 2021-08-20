/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apis;

import Config.Methods;
import Controller.MaskCtrl;
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
    MaskCtrl maskCtrl;

    /**
     * Creates a new instance of Project, Files, Data
     */
    public MaskApis() {
        recordsCtrl = new RecordsCtrl();
        userCtrl = new UserCtrl();
        maskCtrl = new MaskCtrl();

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

            String user_informationid = Methods.JsonToString(Jso, "id_usuario", "");

//            String[] res = Methods.validatePermit(clains[0], clains[1], 1);
//            if (res[0].equals("2")) {
            String[] res = recordsCtrl.listRecords("1", user_informationid);
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

    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/maskRecordsOne")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response maskRecordsOne(String data) {
        String message;
        System.out.println("maskRecordsOne()");
        System.out.println(data);
        JsonObject Jso = Methods.stringToJSON(data);
        if (Jso.size() > 0) {
//            String sessionToken = Methods.JsonToString(Jso, "user_token", "");
//            String[] clains = Methods.getDataToJwt(sessionToken);

            String user_informationid = Methods.JsonToString(Jso, "user_informationid", "");

//            String[] res = Methods.validatePermit(clains[0], clains[1], 1);
//            if (res[0].equals("2")) {
            String[] res = recordsCtrl.listRecords("2", user_informationid);
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

    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/maskRecordsInterval")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response maskRecordsInterval(String data) {
        String message;
        System.out.println("maskRecordsInterval()");
        System.out.println(data);
        JsonObject Jso = Methods.stringToJSON(data);
        if (Jso.size() > 0) {
//            String sessionToken = Methods.JsonToString(Jso, "user_token", "");
//            String[] clains = Methods.getDataToJwt(sessionToken);

            String id_usuario = Methods.JsonToString(Jso, "user_informationid", "");
            String stardate = Methods.JsonToString(Jso, "stardate", "");
            String enddate = Methods.JsonToString(Jso, "enddate", "");

//            String[] res = Methods.validatePermit(clains[0], clains[1], 1);
//            if (res[0].equals("2")) {
            String[] res = recordsCtrl.listRecordsInterval(id_usuario, stardate, enddate);
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

    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/sigIn")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response sigIn(String data) {
        String message;
        System.out.println("sigIn()");
        System.out.println(data);
        JsonObject Jso = Methods.stringToJSON(data);
        if (Jso.size() > 0) {
//            String sessionToken = Methods.JsonToString(Jso, "user_token", "");
//            String[] clains = Methods.getDataToJwt(sessionToken);
//
            String name = Methods.JsonToString(Jso, "name", "");
            String lastname = Methods.JsonToString(Jso, "lastname", "");
            String email = Methods.JsonToString(Jso, "email", "");
            String user = Methods.JsonToString(Jso, "user", "");
            String pass = Methods.JsonToString(Jso, "pass", "");
            String confirmpass = Methods.JsonToString(Jso, "confirmpass", "");

//            String[] res = Methods.validatePermit(clains[0], clains[1], 1);
//            if (res[0].equals("2")) {
            String[] res = userCtrl.sigIn(name, lastname, email, user, pass, confirmpass);
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
     * User
     *********************************************
     */
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/updateUser")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(String data) {
        String message;
        System.out.println("updateUser()");
        System.out.println(data);
        JsonObject Jso = Methods.stringToJSON(data);
        if (Jso.size() > 0) {
//            String sessionToken = Methods.JsonToString(Jso, "user_token", "");
//            String[] clains = Methods.getDataToJwt(sessionToken);

            String names = Methods.JsonToString(Jso, "names", "");
            String lastnames = Methods.JsonToString(Jso, "lastnames", "");
            String user = Methods.JsonToString(Jso, "user", "");
            String userinformation_id = Methods.JsonToString(Jso, "user_informationid", "");

//            String[] res = Methods.validatePermit(clains[0], clains[1], 1);
//            if (res[0].equals("2")) {
            String[] res = userCtrl.updateUser(names, lastnames, user, userinformation_id);
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
     * Mask
     *********************************************
     */
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/saveMask")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveMask(String data) {
        String message;
        System.out.println("saveMask()");
        System.out.println(data);
        JsonObject Jso = Methods.stringToJSON(data);
        if (Jso.size() > 0) {
//            String sessionToken = Methods.JsonToString(Jso, "user_token", "");
//            String[] clains = Methods.getDataToJwt(sessionToken);

            String mask_code = Methods.JsonToString(Jso, "mac", "");
            String userinformation_id = Methods.JsonToString(Jso, "user_informationid", "");

//            String[] res = Methods.validatePermit(clains[0], clains[1], 1);
//            if (res[0].equals("2")) {
            String[] res = maskCtrl.saveMask(mask_code, userinformation_id);
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

    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/selectMask")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response selectMask(String data) {
        String message;
        System.out.println("selectMask()");
        System.out.println(data);
        JsonObject Jso = Methods.stringToJSON(data);
        if (Jso.size() > 0) {
//            String sessionToken = Methods.JsonToString(Jso, "user_token", "");
//            String[] clains = Methods.getDataToJwt(sessionToken);

            String userinformation_id = Methods.JsonToString(Jso, "user_informationid", "");

//            String[] res = Methods.validatePermit(clains[0], clains[1], 1);
//            if (res[0].equals("2")) {
            String[] res = maskCtrl.selectMask(userinformation_id);
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

    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/deleteMask")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteMask(String data) {
        String message;
        System.out.println("deleteMask()");
        System.out.println(data);
        JsonObject Jso = Methods.stringToJSON(data);
        if (Jso.size() > 0) {
//            String sessionToken = Methods.JsonToString(Jso, "user_token", "");
//            String[] clains = Methods.getDataToJwt(sessionToken);

            String mask_code = Methods.JsonToString(Jso, "mac", "");

//            String[] res = Methods.validatePermit(clains[0], clains[1], 1);
//            if (res[0].equals("2")) {
            String[] res = maskCtrl.deleteMask(mask_code);
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
