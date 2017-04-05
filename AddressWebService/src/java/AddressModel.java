
import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Aleksas
 */
public class AddressModel implements Serializable{

	private static final long serialVersionUID = -5577579081118070434L;
        public int ID;
        public String country;
        public String city;
        public String street;
        public int buildingNr;
        public int flatNr;
        public int ZIPCode;
        
        @Override
        public String toString(){
            return ID + ":" + country + "," + city + "," + street + " " + buildingNr + "-" + flatNr + " ZIP:" + ZIPCode;
        }
}
