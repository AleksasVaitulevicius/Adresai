
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
@WebService(targetNamespace = "http://addresswebservice/address", serviceName = "Address")
public class AddressController {
    private final IAddressService service = new AddressService();
    
    private boolean validateNumerical(String value){
        if(value == null)
            return true;
        try{
            Integer.parseUnsignedInt(value);
        }
        catch(NumberFormatException exp){
            return false;
        }
        return true;
    }
    
    private String validateAdd(String id, String country, String city, String street, String buildingNr, String flatNr, String ZIPCode){
        if(id == null)
            return "Error 400: illegal id";
        if(country == null)
            return "Error 400: Country field cannot be empty";
        if(city == null)
            return "Error 400: City field cannot be empty";
        if(street == null)
            return "Error 400: Street field cannot be empty";
        if(!validateNumerical(buildingNr))
            return "Error 400: Illegal building number";
        if(!validateNumerical(flatNr))
            return "Error 400: Illegal flat number";
        if(!validateNumerical(ZIPCode))
            return "Error 400: Illegal ZIP code";
        return null;
    }
    
    @WebMethod
    public AddressResponse AddModel(String id, String country, String city, String street, String buildingNr, String flatNr, String ZIPCode){
        AddressResponse response = new AddressResponse();
        String valid = validateAdd(id, country, city, street, buildingNr, flatNr, ZIPCode);
        if(valid != null){
            response.message = "".concat(valid);
            response.status = 400;
            return response;
        }
        if(service.getSingle(id) == null)
            response.status = 201;
        else
            response.status = 204;
        service.add(new AddressModel(id, country, city, street, buildingNr, flatNr, ZIPCode));
        return response;
    }
    
    @WebMethod
    public AddressResponse DeleteModel(String id){
        AddressResponse response = new AddressResponse();
        if(id == null){
            response.message = "Error 400: illegal id";
            response.status = 400;
            return response;
        }
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
    public AddressResponse GetModel(String id){
        AddressResponse response = new AddressResponse();
        if(id == null){
            response.message = "Error 400: illegal id";
            response.status = 400;
            return response;
        }
        AddressModel model = service.getSingle(id);
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
    public AddressResponse GetAll(){
        AddressResponse response = new AddressResponse();
        response.content = service.getAll();
        response.status = 200;
        return response;
    }
}
