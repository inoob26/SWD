package swd.logic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="Performer")
public class Performer {
    private byte id;
    private String name;
    private String director;
    private String phone;
    private String address;
    private String bin_iin;
    private String iik;
    private short bank_id;
    
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name="ID")
    public byte getId(){
        return id;
    }
    
    @Column(name="Name")
    public String getName(){
        return name;
    }
    
    public void setName(String n){
        name = n;
    }
    
    @Column(name="director")
    public String getDirector(){
        return director;
    }
    
    public void setDirecror(String d){
        director = d;
    }
    
    @Column(name="Phone")
    public String getPhone(){
        return phone;
    }
    
    public void setPhone(String p){
        phone = p;
    }
    
    @Column(name="Address")
    public String getAddress(){
        return address;
    }
    
    public void setAddress(String a){
        address = a;
    }
    
    @Column(name="BIN_IIN")
    public String getBinIin(){
        return bin_iin;
    }
    
    public void setBinIin(String bi){
        bin_iin = bi;
    }
    
    @Column(name="IIK")
    public String getIik(){
        return iik;
    }
    
    public void setIik(String ik){
        iik = ik;
    }
    
    @Column(name="BANK_id")
    public short getBankId(){
        return bank_id;
    }
    
    public void  getBankId(short b_i){
        bank_id = b_i;
    }
}
