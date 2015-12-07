package geopixel.rest;
 
import geopixel.model.external.ChoroplethMapDescription;
import geopixel.model.external.GeoJsonChoroplethMap;
import geopixel.service.DataBase;
import geopixel.service.DataBaseService;
import geopixel.thematic.Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/json")
public class JSONService {
	
	 @GET
     @Path("/")
     @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
     public Response thematicEndpoint() {
             String result = "/thematic";
             return Response.status(200).entity(result).build();
     }


     @GET
     @Path("/connect")
     @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
     public Response Connect() {
     		DataBase db = new DataBase();
     		Connection conn = null;
     		db=DataBaseService.getPostgresParameters();
     		try {
					conn=DataBaseService.connect(db);
				} catch (IOException e) {
					e.printStackTrace();
				}        
     	                	
        return Response.status(200).entity(conn).build();
     }
     
     @GET
     @Path("/checkData")
     @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
     public Response checkData(
    		@QueryParam("tablename") String tableName,     		
      		@QueryParam("whereclause") String whereClause){
     	
     	String result = "";
     	
     	try {
				result = Controller.checkData(tableName,whereClause);
			} catch (SQLException  | IOException e) {
				e.printStackTrace();
			} 
     	
        return Response.status(200).entity(result).build();
     }
     
     @GET
     @Path("/getData")
     @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
     public Response getData(
     		@QueryParam("tablename") String tableName,
     		@QueryParam("geocolumn") String geometryColumn,
     		@QueryParam("whereclause") String whereClause){
     		
    	if(whereClause == null || whereClause == "")
    		whereClause= " val > '1900-01-01' ";
    		

     	String geoJson = "";
     	
     	try {
				geoJson = Controller.getFeatures(tableName,geometryColumn,whereClause);
			} catch (SQLException  | IOException e) {
				e.printStackTrace();
			} 
     	
        return Response.status(200).entity(geoJson).build();
     }
     /***
      * Group by service using one table and the column that will be grouped
      * @param tableName table name
      * @param column column that will be grouped
      * @return distinct elements
      */
     @GET
     @Path("/groupBy")
     @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
     public Response groupBy(
     		@QueryParam("tablename") String tableName,
     		@QueryParam("column") String column,
     		@QueryParam("whereclause") String whereClause){
    	 
    	 if(whereClause == null || whereClause == "")
     		whereClause= " val > '1900-01-01' ";
    	 
     	String geoJson = "";
     	
     	try {
				geoJson = Controller.getGroupBy(tableName, whereClause,column);
			} catch (SQLException  | IOException e) {
				e.printStackTrace();
			} 
     	
        return Response.status(200).entity(geoJson).build();
     }
     
     
     /***
      * Count how many data has date after val. This service could be used to check if there is necessity to upgrade mobile's database
      * 
      * @param tableName the name of the table that will be checked
      * @param val validation date of the POI
      * @return number of POIs that has validation date after "val"
      * 
      * e.g. http://localhost:8080/RotasBR/rest/json/countData?tablename=%22POI%22&val=%272015-10-30%27
      */
     
     @GET
     @Path("/countData")
     @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
     public Response countData(
     		@QueryParam("tablename") String tableName,
     		@QueryParam("val") String val,
     		@QueryParam("whereclause") String whereClause){

    	 if(val == null || val == "")
     		val= " '1900-01-01' ";
    	 
    	 if(whereClause == null || whereClause == "")
     		whereClause= " contexto != '' ";
    	 
    	 
     	String geoJson = "";
     	
     	try {
				geoJson = Controller.countData(tableName,val, whereClause);
			} catch (SQLException  | IOException e) {
				e.printStackTrace();
			} 
     	
        return Response.status(200).entity(geoJson).build();
     }
     
     
}