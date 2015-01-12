package swd.logic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="Rull")
public class Rull {
    private byte id;
    private String name;
    
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
}
