
import java.util.ArrayList;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Aleksas
 */
@WebService(targetNamespace = "http://localhost:8080/AddressWebService/addresses")
public class Program {
    IAddressService service = new AddressService();
    
    @WebMethod
    public Response AddModel(int id, String country, String city, String street, int buildingNr, int flatNr, int ZIPCode){
        Response response = new Response();
        if(service.getSingle(id) == null)
            response.status = 201;
        else
            response.status = 204;
        AddressModel model = new AddressModel();
        model.ID = id;
        model.country = country;
        model.city = city;
        model.street = street;
        model.buildingNr = buildingNr;
        model.flatNr = flatNr;
        model.ZIPCode = ZIPCode;
        service.add(model);
        return response;
    }
    @WebMethod
    public Response DeleteModel(int id){
        Response response = new Response();
        if(service.getSingle(id) == null){
            response.message = "Error 404: adress was not found";
            response.status = 404;
            return response;
        }
        service.delete(id);
        response.status = 204;
        return response;
    }
    @WebMethod
    public Response GetModel(int id){
        AddressModel model = service.getSingle(id);
        Response response = new Response();
        if(model == null){
            response.message = "Error 404: adress was not found";
            response.status = 404;
            return response;
        }
        List<AddressModel> list = new ArrayList<>();
        list.add(model);
        response.content = list;
        response.status = 200;
        return response;
    }
    @WebMethod
    public Response GetAll(){
        Response response = new Response();
        response.content = service.getAll();
        response.status = 200;
        return response;
    }
}
