package address;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static spark.Spark.*;

public class Comments {
    public static int SERVER;

    public static void main(String[] args) {
        int server;
        try{
            server = Integer.parseInt(args[0]);
        }
        catch(NumberFormatException ex){
            System.out.println("server id has to be integer. Got:" + args[0]);
            return;
        }
        
        SERVER = server;
        
        IAddressService address = new AddressService();
        IResidentService resident = new ResidentService();
        CompaniesService companies = new CompaniesService();
        
        port(7777);
        
        path("/address", () -> {
            get("", (req, res) -> {
                    return AddressController.GetAll(req, res, address, companies);
                } , new JsonTransformer());

            get("/:id", (req, res) -> {
                return AddressController.GetModel(req, res, address, companies);
            } , new JsonTransformer());
            
            get("/:id/companies", (req, res) -> {
                return AddressController.GetCompaniesByAddress(req, res, address, companies);
            } , new JsonTransformer());
            
            get("/:id/residents", (req, res) -> {
                return AddressController.GetResidents(req, res, address, resident);
            } , new JsonTransformer());
            
            get("/companiesbycity/:city", (req, res) -> {
                return AddressController.GetCompaniesByCity(req, res, address, companies);
            } , new JsonTransformer());
            
            get("/residentsbycity/:city", (req, res) -> {
                return AddressController.GetResidentsByCity(req, res, address, resident);
            } , new JsonTransformer());
            
            post("", (req, res) -> {
                return AddressController.AddModel(req, res, address, companies);
            } , new JsonTransformer());

            put("/:id", (req, res) -> {
                return AddressController.UpdateModel(req, res, address);
            } , new JsonTransformer());

            patch("/:id", (req, res) -> {
                return AddressController.PatchModel(req, res, address);
            } , new JsonTransformer());
            
            delete("/:id", (req, res) -> {
                return AddressController.DeleteModel(req, res, address);
            } , new JsonTransformer());
            
            put("", (req, res) -> {
                return AddressController.Error(req, res, address);
            } , new JsonTransformer());

            patch("", (req, res) -> {
                return AddressController.Error(req, res, address);
            } , new JsonTransformer());
            
            delete("", (req, res) -> {
                return AddressController.Error(req, res, address);
            } , new JsonTransformer());

        });
        
        path("/resident", () -> {
            get("", (req, res) -> {
                    return ResidentController.GetAll(req, res, resident);
                } , new JsonTransformer());

            get("/:id", (req, res) -> {
                return ResidentController.GetModel(req, res, resident);
            } , new JsonTransformer());
            
            get("/:id/address", (req, res) -> {
                return ResidentController.GetModelAddress(req, res, resident, address);
            } , new JsonTransformer());

            post("", (req, res) -> {
                return ResidentController.AddModel(req, res, resident, address);
            } , new JsonTransformer());

            put("/:id", (req, res) -> {
                return ResidentController.UpdateModel(req, res, resident, address);
            } , new JsonTransformer());

            patch("/:id", (req, res) -> {
                return ResidentController.PatchModel(req, res, resident, address);
            } , new JsonTransformer());

            delete("/:id", (req, res) -> {
                return ResidentController.DeleteModel(req, res, resident);
            } , new JsonTransformer());

            put("", (req, res) -> {
                return ResidentController.Error(req, res, resident);
            } , new JsonTransformer());

            patch("", (req, res) -> {
                return ResidentController.Error(req, res, resident);
            } , new JsonTransformer());
            
            delete("", (req, res) -> {
                return ResidentController.Error(req, res, resident);
            } , new JsonTransformer());

        });
        
        exception(Exception.class, (e, req, res) -> {
            res.status(HTTP_BAD_REQUEST);
            JsonTransformer jsonTransformer = new JsonTransformer();
            res.body(jsonTransformer.render( new ErrorMessage(/*"Gautas pranesimas nera json tipo"*/e.getLocalizedMessage()) ));
        });

        after((req, rep) -> rep.type("application/json"));

    }
    
    
}
