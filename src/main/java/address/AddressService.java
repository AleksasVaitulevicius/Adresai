package address;


import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Aleksas
 */
public class AddressService implements IAddressService{
   
    private Map<Integer, AddressModel> DB = new HashMap<>();
    
    public URL url;

    AddressService() {
        try {
            url = new URL("http://localhost:7778");
        } catch (Exception e) {
        }
    }
    
//    public AddressService()
//    {
//        DB.put(0, new AddressModel(0, "Lietuva", "Vilnius", "Vilniaus g.", "5", "5", "55555", new ArrayList<>()));
//        DB.put(1, new AddressModel(1, "Lietuva", "Vilnius", "Vilniaus g.", "5", "6", "555555", new ArrayList<>()));
//        DB.put(2, new AddressModel(2, "Lietuva", "Vilnius", "Didlaukio g.", "5", "7", "555555", new ArrayList<>()));
//        DB.put(3, new AddressModel(3, "Lietuva", "Vilnius", "Vilniaus g.", "2", "", "555555", new ArrayList<>()));
//        DB.put(4, new AddressModel(4, "Lietuva", "Vilnius", "Savanoriu pr.", "13", "", "555555", new ArrayList<>()));
//        DB.put(5, new AddressModel(5, "Lietuva", "Kaunas", "Saltuvos g.", "37", "", "555555", new ArrayList<>()));
//        DB.put(6, new AddressModel(6, "Lietuva", "Klaipeda", "Vytauto g.", "2", "", "555555", new ArrayList<>()));
//        DB.put(7, new AddressModel(7, "Lietuva", "Kaunas", "Laisves pr.", "111", "5", "555555", new ArrayList<>()));
//        DB.get(3).companies.add(new Company(1));
//        DB.get(4).companies.add(new Company(2));
//        DB.get(5).companies.add(new Company(3));
//        DB.get(6).companies.add(new Company(4));
//        DB.get(6).companies.add(new Company(5));
//    }
    
    @Override
    public int add(AddressModel model){
        HttpURLConnection conn;
        try{
            URL url = new URL(this.url, "/address");
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
    public void update(int id, AddressModel model) {
        //model.ID = id;
        //DB.put(id, model);
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
    }
    
    @Override
    public void patch(int id, AddressModel model){
        AddressModel oldModel = getSingle(id);
        if(model.country != null)
            oldModel.country = model.country;
        if(model.city != null)
            oldModel.city = model.city;
        if(model.street != null)
            oldModel.street = model.street;
        if(model.buildingNr != null)
            oldModel.buildingNr = model.buildingNr;
        if(model.flatNr != null)
            oldModel.flatNr = model.flatNr;
        if(model.ZIPCode != null)
            oldModel.ZIPCode = model.ZIPCode;
        //DB.put(id, oldModel);
        update(id, model);
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
    public AddressModel getSingle(int id){
        //return DB.get(id);
        try{
            URL url = new URL(this.url, "/address/" + id);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(2000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }
            return JsonTransformer.fromJson(new InputStreamReader(conn.getInputStream()), AddressModel.class);
        }
        catch(Exception exp){
            return null;
        }
    }

    @Override
    public List<AddressModel> getAll(){
//        Collection<AddressModel> values = DB.values();
//        List<AddressModel> models = new ArrayList<>();
//        for(AddressModel model : values){
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
            List<AddressModel> residents = new ArrayList (Arrays.asList(JsonTransformer.fromJson(new InputStreamReader(conn.getInputStream()), AddressModel[].class)));
            return residents;
        }
        catch(Exception exp){
            return null;
        }
    }
}
