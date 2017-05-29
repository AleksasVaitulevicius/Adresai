package address;

import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.io.*;
import java.net.*;
import java.util.*;


/**
 * @author Aleksas
 */
public class ResidentService implements IResidentService {
    
    private Map<Integer, ResidentModel> DB = new HashMap<>();
    
    public URL url;

    ResidentService() {
        try {
            url = new URL("http://localhost:7778");
        } catch (Exception e) {
        }
    }
    
//    public ResidentService()
//    {
//        DB.put(1, new ResidentModel(1, "Petras", "Petraitis", "11111111", "1"));
//        DB.put(2, new ResidentModel(2, "Jonas", "Jonaitis", "22222222", "2"));
//        DB.put(3, new ResidentModel(3, "Stasys", "Povilaitis", "22222222", "2"));
//        DB.put(4, new ResidentModel(4, "Darius", "Girenas", "5", "7"));
//    }
    
    @Override
    public int add(ResidentModel model){
        HttpURLConnection conn;
        try{
            URL url = new URL(this.url, "/resident");
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(2000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent", "Addresses");
            conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "application/json");
        }
        catch(Exception exp){
            return -1;
        }
        conn.setDoOutput(true);
        try (DataOutputStream writer = new DataOutputStream(conn.getOutputStream())) {
            writer.writeBytes(JsonTransformer.gson.toJson(model));
            writer.flush();
        }
        catch(Exception exp){
            return -1;
        }
        
        try {
            return Integer.parseInt(JsonTransformer.fromJson(new InputStreamReader(conn.getInputStream()), String.class));
        } catch (IOException ex) {
            return -1;
        }
//        int ID = 0;
//        while(DB.containsKey(ID))
//            ID++;
//        model.ID = ID;
//        DB.put(ID, model);
//        return ID;
    }
    
    @Override
    public void update(int id, ResidentModel model){
        model.ID = id;
        HttpURLConnection conn;
        try{
            URL url = new URL(this.url, "/resident/" + id);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(2000);
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("User-Agent", "Addresses");
            conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Accept", "application/json");
        }
        catch(Exception exp){
            return;
        }
        conn.setDoOutput(true);
        try (DataOutputStream writer = new DataOutputStream(conn.getOutputStream())) {
            writer.writeBytes(JsonTransformer.gson.toJson(model));
            writer.flush();
        }
        catch(Exception exp){
        }
        //DB.put(id, model);
    }
    
    @Override
    public void patch(int id, ResidentModel model){
        ResidentModel oldModel = getSingle(id);
        if(model.name != null)
            oldModel.name = model.name;
        if(model.surname != null)
            oldModel.surname = model.surname;
        if(model.IDCode != null)
            oldModel.IDCode = model.IDCode;
        if(model.addressID != null)
            oldModel.addressID = model.addressID;
        update(id, model);
        //DB.put(id, model);
    }
    
    @Override
    public void delete(int id){
        //DB.remove(id);
        HttpURLConnection conn;
        try{
            URL url = new URL(this.url, "/resident/" + id);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(2000);
            conn.setRequestMethod("DELETE");
            conn.setRequestProperty("User-Agent", "Addresses");
            conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            conn.setRequestMethod("DELETE");
            conn.setRequestProperty("Accept", "application/json");
        }
        catch(Exception exp){
        }
    }
    
    @Override
    public ResidentModel getSingle(int id){
        //return DB.get(id);
        try{
            URL url = new URL(this.url, "/resident/" + id);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(2000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }
            return JsonTransformer.fromJson(new InputStreamReader(conn.getInputStream()), ResidentModel.class);
        }
        catch(Exception exp){
            return null;
        }
    }

    @Override
    public List<ResidentModel> getAll(){
//        Collection<ResidentModel> values = DB.values();
//        List<ResidentModel> models = new ArrayList<>();
//        for(ResidentModel model : values){
//            models.add(model);
//        }
//        return models;
        try{
            URL url = new URL(this.url, "/resident");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(2000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }
            List<ResidentModel> residents = new ArrayList (Arrays.asList(JsonTransformer.fromJson(new InputStreamReader(conn.getInputStream()), ResidentModel[].class)));
            return residents;
        }
        catch(Exception exp){
            return null;
        }
    }
}
