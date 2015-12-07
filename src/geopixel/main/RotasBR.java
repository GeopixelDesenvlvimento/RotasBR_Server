package geopixel.main;

import geopixel.service.DataBase;
import geopixel.service.DataBaseService;
import geopixel.thematic.Controller;
import geopixel.thematic.Dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class RotasBR { 
        
        public static void main(String[] args) throws IOException, SQLException{
        String geojson = "";
        try {
   			 geojson = Controller.getFeatures("pocos","geom","gid = 450");
   		} catch (IOException | SQLException e) {
   			e.printStackTrace();
   		}
   		
   		System.out.println(geojson);
    	}
        
}
