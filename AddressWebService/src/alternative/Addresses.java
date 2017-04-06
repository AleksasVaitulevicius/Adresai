import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author Aleksas
 */
@WebService(targetNamespace = "http://localhost:8080/AddressWebService/addresses", serviceName= "addresses")
public class Addresses {
    IAddressService service = new AddressService();
    
    @WebMethod
    public void AddAddress(@WebParam(name = "country") String country,
                           @WebParam(name = "city") String city,
                           @WebParam(name = "street") String street,
                           @WebParam(name = "buildingNr") int buildingNr,
                           @WebParam(name = "flatNr")int flatNr)
    {
        service.add(new AddressModel(0, country, city, street, buildingNr, flatNr));
    }
    
    
    @WebMethod
    public void DeleteAddress(@WebParam(name = "ID")int id)
            throws NullPointerException
    {
        service.delete(id);
    }
    @WebMethod
    public AddressModel GetAddress(@WebParam(name = "ID")int id)
            throws NullPointerException
    {
        return service.getSingle(id);
    }
    @WebMethod
    public List<AddressModel> GetAll(){
        return service.getAll();
    }
}
