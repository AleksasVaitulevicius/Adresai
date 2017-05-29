package address;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * @author Laimonas
 */
public class CompaniesService {

    public URL url;

    CompaniesService() {
        try {
            url = new URL("http://company:1234");
        } catch (Exception e) {
        }
    }
    
    public String addCompany(Company company) throws Exception {
        if(company.companyName == null)
            return "Company name is missing";
        URL url = new URL(this.url, "/companies");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(2000);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("User-Agent", "Addresses");
        conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);
        try (DataOutputStream writer = new DataOutputStream(conn.getOutputStream())) {
            writer.writeBytes(JsonTransformer.gson.toJson(company));
            writer.flush();
        }
        catch(Exception exp){
            return "Cannot connect to service company";
        }
        
        return JsonTransformer.fromJson(new InputStreamReader(conn.getInputStream()), String.class);
    }
    
    public Company getSingle(int id) throws Exception {
        URL url = new URL(this.url, "/companies/" + id);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(2000);
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + conn.getResponseCode());
        }
        return JsonTransformer.fromJson(new InputStreamReader(conn.getInputStream()), Company.class);
    }
    
    public List<Company> getAll() throws Exception {

        URL url = new URL(this.url, "/companies");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(2000);
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + conn.getResponseCode());
        }
        List<Company> companies = new ArrayList (Arrays.asList(JsonTransformer.fromJson(new InputStreamReader(conn.getInputStream()), Company[].class)));
        return companies;
    }
    
    public List<Company> getAll(String city) throws Exception
    {
        
        URL url = new URL(this.url, "/companies/city/" + city);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(2000);
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + conn.getResponseCode());
        }
        List<Company> companies = new ArrayList (Arrays.asList(JsonTransformer.fromJson(new InputStreamReader(conn.getInputStream()), Company[].class)));
        return companies;
    }
}
