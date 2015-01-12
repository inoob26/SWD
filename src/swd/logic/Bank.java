package swd.logic;

import java.util.ArrayList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="BANK")
public class Bank {
    private short id;
    private String name;
    private String bik;

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name="ID")
    public short getId(){
    	return id;
    }
    @Column(name="Name")
    public String getName(){
    	return name;
    }
    
    @Column(name="BIK")
    public String getBik(){
    	return bik;
    }
    
    public void setId(short i){
    	id = i;
    }
    
    public void setName(String n){
    	name = n;
    }
    
    public void setBik(String b){
    	bik = b;
    }
    
    public static ArrayList<TableColumn<Bank , ?>> getColumn(TableView tv){
        ArrayList<TableColumn<Bank , ?>> columns = new ArrayList<TableColumn<Bank , ?>>();
        
        String[] varibleNames = { "name", "bik" };
        
        TableColumn<Bank , String> nameCol = new TableColumn<>("Наименование банка");
        TableColumn<Bank , String> bikCol = new TableColumn<>("БИК банка");
        
        int i = 0;
        
        nameCol.setCellValueFactory(new PropertyValueFactory<Bank, String>(varibleNames[i++]));
        bikCol.setCellValueFactory(new PropertyValueFactory<Bank, String>(varibleNames[i++]));
        
        nameCol.setPrefWidth(200);
        bikCol.setPrefWidth(140);
        
        columns.add(nameCol);
        columns.add(bikCol);
        
        return columns;
    }
}
