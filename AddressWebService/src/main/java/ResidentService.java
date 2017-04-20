
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Aleksas
 */
public class ResidentService implements IResidentService {
    Map<String, ResidentModel> DB = new HashMap<>();
    
    @Override
    public void add(ResidentModel model){
        DB.put(model.ID, model);
    }
    
    @Override
    public void delete(String id){
        DB.remove(id);
    }
    
    @Override
    public ResidentModel getSingle(String id){
        return DB.get(id);
    }

    @Override
    public List<ResidentModel> getAll(){
        Collection<ResidentModel> values = DB.values();
        List<ResidentModel> models = new ArrayList<>();
        for(ResidentModel model : values){
            models.add(model);
        }
        return models;
    }
}
