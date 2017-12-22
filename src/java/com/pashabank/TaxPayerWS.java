/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pashabank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Huseyn
 */
@WebService(serviceName = "TaxPayerWS")
public class TaxPayerWS {
    
    private String sendgetmethod(String id_number)
    {
        String result="";
        try {
            String url = "http://e-taxes.gov.az/isvatpayer/"+id_number;
            //2900378021
            
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));                                                    
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }            
            in.close();
            
            JSONParser parser = new JSONParser();            
            JSONObject json = (JSONObject) parser.parse(response.toString());
            //String message = (String) json.get("MESSAGE");
            result=(String) json.get("RESULT");
            
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(TaxPayerWS.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TaxPayerWS.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(TaxPayerWS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

   
    @WebMethod
    public String send(@WebParam(name = "id_number") String id_number) {
       TaxPayerWS s=new TaxPayerWS();
       return s.sendgetmethod(id_number);
        
    }
}
