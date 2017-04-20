
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Aleksas
 */
public interface IResidentService {
    public void add(ResidentModel model);
    public void delete(String id);
    public ResidentModel getSingle(String id);
    public List<ResidentModel> getAll();
}
