
import java.util.ArrayList;
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
    Map<Integer, AddressModel> DB;

    public AddressService() {
        this.DB = new HashMap<>();
        this.DB.put(1, new AddressModel(DB.size(), "Lietuva", "Vilnius", "Vilniaus g.", 5, 5));
        this.DB.put(2, new AddressModel(DB.size(), "Lietuva", "Kaunas", "Laisves pr.", 1, 1));
    }
    
    @Override
    public void add(AddressModel model){
        model.ID = DB.size();
        DB.put(model.ID, model);
    }
    
    @Override
    public void delete(int id) throws NullPointerException
    {
       if(!DB.containsKey(id))
       {
          throw new NullPointerException(); //nepadeda :(
       }
       DB.remove(id);
    }
    
    @Override
    public AddressModel getSingle(int id) throws NullPointerException
    {
        
       if(!DB.containsKey(id)) 
       {
          throw new NullPointerException(); //nepadeda :(
       }
        return DB.get(id);
    }

    @Override
    public List<AddressModel> getAll(){
        Collection<AddressModel> values = DB.values();
        List<AddressModel> models = new ArrayList<>();
        values.forEach((model) -> {
            models.add(model);
        });
        return models;
    }
}
