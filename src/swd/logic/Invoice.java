package swd.logic;

import java.time.LocalDate;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name="Invoice")
public class Invoice {
    private Long id;
    private Date data;
    
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
    
    
    @Temporal(TemporalType.DATE)
    @Column(name="Date")
    public Date getDate(){
        return data;
    }
    
    
    public void setDate(Date d){
        data = d;
    }
}
