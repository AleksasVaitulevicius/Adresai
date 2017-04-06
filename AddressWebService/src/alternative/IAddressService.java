
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
public interface IAddressService {
    public void add(AddressModel model);
    public void delete(int id);
    public AddressModel getSingle(int id);
    public List<AddressModel> getAll();
}
