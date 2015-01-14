package swd.logic;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import swd.DAO.Factory;

@Entity
@Table(name="S_I")
public class S_I {
    private Long id;
    private short id_s;
    private Long id_i;
    private Long id_f;
    private short count;
    
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name="ID")
    public Long getId(){
        return id;
    }
    
    public void setId(Long i){
        id = i;
    }
    
    @Column(name="Service_id")
    public short getId_s(){
        return id_s;
    }
    
    public void setId_s(short i_s){
        id_s = i_s;
    }
    
    @Column(name="Invoice_id")
    public Long getId_i(){
        return id_i;
    }
    
    public void setId_i(Long i_i){
        id_i = i_i;
    }
    
    @Column(name="Firm_id")
    public Long getId_f(){
        return id_f;
    }
    
    public void setId_f(Long i_f){
        id_f = i_f;
    }
    
    @Column(name="Count")
    public short getCount(){
        return count;
    }
    
    public void setCount(short c){
        count = c;
    }
}
