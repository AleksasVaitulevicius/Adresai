package address;


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
    public int add(ResidentModel model);
    public void update(int id, ResidentModel model);
    public void patch(int id, ResidentModel model);
    public void delete(int id);
    public ResidentModel getSingle(int id);
    public List<ResidentModel> getAll();
}
