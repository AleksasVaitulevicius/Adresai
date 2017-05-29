package address;


import java.util.*;
import spark.Request;
import spark.Response;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Aleksas
 */
public class AddressController {

    private static final int HTTP_BAD_REQUEST = 400;
    private static final int HTTP_NOT_FOUND = 404;

    private static String validateNumerical(String value) {
        if (value == null || value.equals("")) {
            return "laukas negali buti tuscias";
        }
        try {
            Integer.parseInt(value);
        } catch (Exception exp) {
            return "laukas privalo buti skaicius";
        }
        return null;
    }

    private static String validateModel(AddressModel model) {
        if (model.country == null || model.country.equals("")) {
            return "country laukas turi buti uzpildytas";
        }
        if (model.city == null || model.city.equals("")) {
            return "city laukas turi buti uzpildytas";
        }
        if (model.street == null || model.street.equals("")) {
            return "street laukas negali buti tuscias";
        }
        String result = validateNumerical(model.buildingNr);
        if (result != null) {
            return "buildingNr " + result;
        }
        result = validateNumerical(model.flatNr);
        if (result != null) {
            return "flatNr " + result;
        }
        result = validateNumerical(model.ZIPCode);
        if (result != null) {
            return "ZIPCode " + result;
        }
        return null;
    }

    public static Object AddModel(Request request, Response response, IAddressService service, CompaniesService companyService) {
        AddressModel model = JsonTransformer.fromJson(request.body(), AddressModel.class);
        String error = validateModel(model);
        if (error != null) {
            response.status(HTTP_BAD_REQUEST);
            return new ErrorMessage(error + " SERVER:" + Comments.SERVER);
        }
        
        String companyResponse, result;
        companyResponse = "";
        int id = service.add(model);
        if(id == -1)
            return new ErrorMessage("Could not connect to database" + " SERVER:" + Comments.SERVER);
        result = "{id:" + service.add(model) + ", errors:[";
        for(Company company: model.companies){
            try{
                companyResponse = companyService.addCompany(company);
            }
            catch(Exception exp){
                result += "{message:" + exp.getLocalizedMessage() + "},";
            }
            if(!companyResponse.contains("Company successfully added id: ")){
                result += "{message:" + companyResponse + "},";
                continue;
            }
            company.companyId = Integer.parseInt(companyResponse.substring(31));
        }
        return result.substring(0, result.length() - 2) + "], SERVER:" + Comments.SERVER + "}";
    }

    public static Object UpdateModel(Request request, Response response, IAddressService service) {
        AddressModel model;
        try{
            model = JsonTransformer.fromJson(request.body(), AddressModel.class);
        }
        catch(Exception ex)
        {
            response.status(HTTP_BAD_REQUEST);
            return new ErrorMessage(ex.getMessage() + ", SERVER:" + Comments.SERVER);
        }
        String error = validateModel(model);
        if (error != null) {
            response.status(HTTP_BAD_REQUEST);
            return new ErrorMessage(error + ", SERVER:" + Comments.SERVER);
        }
        try {
            String id = request.params("id");
            service.update(Integer.parseInt(id), model);
            return "OK" + ", SERVER:" + Comments.SERVER;
        } catch (Exception e) {
            response.status(HTTP_NOT_FOUND);
            return new ErrorMessage("Nepavyko rasti adreso su ID: " + request.params("id") + ", SERVER:" + Comments.SERVER);
        }
    }

    public static Object PatchModel(Request request, Response response, IAddressService service) {
        AddressModel model = JsonTransformer.fromJson(request.body(), AddressModel.class);
        
        if(model.ZIPCode != null && validateNumerical(model.ZIPCode) != null)
            return new ErrorMessage("ZIPCode " + validateNumerical(model.ZIPCode) + ", SERVER:" + Comments.SERVER);
        if(model.flatNr != null && validateNumerical(model.flatNr) != null)
            return new ErrorMessage("FlatNr " + validateNumerical(model.flatNr) + ", SERVER:" + Comments.SERVER);
        if(model.buildingNr != null && validateNumerical(model.buildingNr) != null)
            return new ErrorMessage("BuildingNr " + validateNumerical(model.buildingNr) + ", SERVER:" + Comments.SERVER);
        
        try {
            String id = request.params("id");
            service.patch(Integer.parseInt(id), model);
            return "OK" + ", SERVER:" + Comments.SERVER;
        } catch (Exception e) {
            response.status(HTTP_NOT_FOUND);
            return new ErrorMessage("Nepavyko rasti adreso su ID: " + request.params("id") + ", SERVER:" + Comments.SERVER);
        }
    }

    public static Object DeleteModel(Request request, Response response, IAddressService service) {
        try {
            String id = request.params("id");
            service.delete(Integer.parseInt(id));
            return "OK" + ", SERVER:" + Comments.SERVER;
        } catch (Exception e) {
            response.status(HTTP_NOT_FOUND);
            return new ErrorMessage("Nepavyko rasti adreso su ID: " + request.params("id") + ", SERVER:" + Comments.SERVER);
        }
    }

    public static Object GetModel(Request request, Response response, IAddressService service, CompaniesService compService) {
        try {
            String id = request.params("id");
            AddressModel model = service.getSingle(Integer.parseInt(id));
            if (model == null) {
                throw new Exception();
            }
            int iterator;
            for(iterator = 0; iterator != model.companies.size(); iterator++){
                try{
                    model.companies.set(iterator, compService.getSingle(model.companies.get(iterator).companyId));
                }
                catch(Exception exp){
                    model.companies.set(iterator, new Company(model.companies.get(iterator).companyId));
                }
            }
            return model;
        } catch (Exception e) {
            response.status(HTTP_NOT_FOUND);
            return new ErrorMessage("Nepavyko rasti adreso su ID: " + request.params("id") + ", SERVER:" + Comments.SERVER);
        }
    }

    public static Object GetCompaniesByAddress(Request request, Response response, IAddressService service, CompaniesService compService) {
        AddressModel model;
        List<Company> allCompanies;
        List<Company> companies = new ArrayList();
        try {
            String id = request.params("id");
            model = service.getSingle(Integer.parseInt(id));
            if (model == null) {
                throw new Exception();
            }
        } catch (Exception e) {
            response.status(HTTP_NOT_FOUND);
            return new ErrorMessage("Nepavyko rasti adreso su ID: " + request.params("id") + ", SERVER:" + Comments.SERVER);
        }
        try {
            allCompanies = compService.getAll();
            if (allCompanies == null) {
                throw new Exception();
            }
        } catch (Exception ex) {
            return new ErrorMessage("Klaida naudojant paslauga \"companies\"" + ", SERVER:" + Comments.SERVER);
        }
        for(Company company:allCompanies)
        {
            if(model.city.equals(company.city) &&
               model.street.equals(company.address.substring(0, company.address.lastIndexOf(' '))) &&
               model.buildingNr.equals(company.address.substring(company.address.lastIndexOf(' ') + 1)))
            {
                companies.add(company);
            }
        }
        
        return companies;
    }
    
    public static Object GetCompaniesByCity(Request request, Response response, IAddressService service, CompaniesService compService) {

        List<Company> companies = new ArrayList();
        try {
            companies = compService.getAll(request.params("city"));
        }catch(RuntimeException ex)
        {
            return companies;
        }
        catch (Exception ex) {
            return new ErrorMessage("Klaida naudojant paslauga \"companies\"" + ", SERVER:" + Comments.SERVER);
        }
        
        return companies;
    }
    
    public static Object GetResidentsByCity(Request request, Response response, IAddressService addressService, IResidentService residentService) {
        
        String city = request.params("city");
        List<ResidentModel> residents = new ArrayList();
        for(ResidentModel res: residentService.getAll())
        {
            AddressModel address = addressService.getSingle(Integer.parseInt(res.addressID));
            if (address.city.equals(city))
            {
                residents.add(res);
            }
        }
        return residents;
        
    }
    
    public static Object GetResidents(Request request, Response response, IAddressService addressService, IResidentService residentService) {
        AddressModel address;
        try {
            String id = request.params("id");
            address = addressService.getSingle(Integer.parseInt(id));
            if (address == null) {
                throw new Exception();
            }
        } catch (Exception e) {
            response.status(HTTP_NOT_FOUND);
            return new ErrorMessage("Nepavyko rasti adreso su ID: " + request.params("id") + ", SERVER:" + Comments.SERVER);
        }
        List<ResidentModel> residents = new ArrayList();
        for(ResidentModel res: residentService.getAll())
        {
            if (Integer.parseInt(res.addressID) == address.ID)
            {
                residents.add(res);
            }
        }
        return residents;
    }
    
    public static Object GetAll(Request request, Response response, IAddressService service, CompaniesService compService) {
        List<AddressModel> addresses = service.getAll();
        int iterator;
        for(AddressModel address: addresses){
            for(iterator = 0; iterator != address.companies.size(); iterator++){
                try{
                    address.companies.set(iterator, compService.getSingle(address.companies.get(iterator).companyId));
                }
                catch(Exception exp){
                    address.companies.set(iterator, new Company(address.companies.get(iterator).companyId));
                }
            }
        }
        return addresses;
    }

    public static Object Error(Request request, Response response, IAddressService service) {
        response.status(HTTP_BAD_REQUEST);
        return new ErrorMessage("Truksta ID lauko" + ", SERVER:" + Comments.SERVER);
    }
}
