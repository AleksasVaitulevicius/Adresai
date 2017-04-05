
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
    public void AddModel(AddressModel model){
        service.add(model);
    }
    @WebMethod
    public void DeleteModel(int id){
        service.delete(id);
    }
    @WebMethod
    public AddressModel GetModel(int id){
        return service.getSingle(id);
    }
    @WebMethod
    public List<AddressModel> GetAll(){
        return service.getAll();
    }
}
