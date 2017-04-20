
import java.util.ArrayList;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * @author Aleksas
 */
@WebService(targetNamespace = "http://addresswebservice/resident", serviceName = "Resident")
public class ResidentController {
    private final IResidentService service = new ResidentService();
    
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
    
    private String validateAdd(String id, String name, String surname, String IDCode, String AddressID){
        if(id == null)
            return "Error 400: illegal id";
        if(name == null)
            return "Error 400: Name field cannot be empty";
        if(surname == null)
            return "Error 400: Surname field cannot be empty";
        if(!validateNumerical(IDCode))
            return "Error 400: Illegal IDCode";
        if(AddressID == null)
            return "Error 400: Illegal AddressID";
        return null;
    }
    
    @WebMethod
    public ResidentResponse AddRModel(String id, String name, String surname, String IDCode, String addressID){
        ResidentResponse response = new ResidentResponse();
        String valid = validateAdd(id, name, surname, IDCode, addressID);
        if(valid != null){
            response.message = "".concat(valid);
            response.status = 400;
            return response;
        }
        if(service.getSingle(id) == null)
            response.status = 201;
        else
            response.status = 204;
        service.add(new ResidentModel(id, name, surname, IDCode, addressID));
        return response;
    }
    
    @WebMethod
    public ResidentResponse DeleteRModel(String id){
        ResidentResponse response = new ResidentResponse();
        if(id == null){
            response.message = "Error 400: illegal id";
            response.status = 400;
            return response;
        }
        if(service.getSingle(id) == null){
            response.message = "Error 404: resident was not found";
            response.status = 404;
            return response;
        }
        service.delete(id);
        response.status = 204;
        return response;
    }
    
    @WebMethod
    public ResidentResponse GetRModel(String id){
        ResidentResponse response = new ResidentResponse();
        if(id == null){
            response.message = "Error 404: illegal id";
            response.status = 400;
            return response;
        }
        ResidentModel model = service.getSingle(id);
        if(model == null){
            response.message = "Error 404: adress was not found";
            response.status = 404;
            return response;
        }
        List<ResidentModel> list = new ArrayList<>();
        list.add(model);
        response.content = list;
        response.status = 200;
        return response;
    }
    
    @WebMethod
    public ResidentResponse GetRAll(){
        ResidentResponse response = new ResidentResponse();
        response.content = service.getAll();
        response.status = 200;
        return response;
    }
}
