
import java.io.Serializable;

/**
 * @author Aleksas
 */
public class ResidentModel implements Serializable {
    private static final long serialVersionUID = -5577579081118070434L;
    
    public String ID;
    public String name;
    public String surname;
    public String IDCode;
    public String AddressID;
    
    public ResidentModel(String ID, String name, String surname, String IDCode, String AddressID){
        this.ID = ID;
        this.name = name;
        this.surname = surname;
        this.IDCode = IDCode;
        this.AddressID = AddressID;
    }
    
    @Override
    public String toString(){
        return ID + ":" + name + " " + surname + " " + IDCode + " " + AddressID;
    }
}
