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
@Table(name="Service")
public class Service {
    private short id;
    private String name;
    private String unit;
    private float cost;
    
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name="ID")
    public short getId(){
        return id;
    }
    
    public void setId(short i){
        id = i;
    }
    
    @Column(name="Name")
    public String getName(){
        return name;
    }
    
    public void setName(String n){
        name = n;
    }
    
    @Column(name="Cost")
    public float getCost(){
        return cost;
    }
    
    public void setCost(float c){
        cost = c;
    }
    
    @Column(name="Unit")
    public String getUnit(){
        return unit;
    }
    
    public void setUnit(String u){
        unit = u;
    }
    
    public static ArrayList<TableColumn<Service , ?>> getColumn(TableView table){
        
        ArrayList<TableColumn<Service , ?>> columns = new ArrayList<TableColumn<Service , ?>>();
   
        String[] varibleNames = { "id" , "name", "unit" ,"cost"};
        
        TableColumn<Service , String> numberCol = new TableColumn<>("№");
        TableColumn<Service , String> nameCol = new TableColumn<>("Наименование");
        TableColumn<Service , String> unitCol = new TableColumn<>("Ед. изм.");
        TableColumn<Service , String> costCol = new TableColumn<>("Цена за единицу");
        
        int i = 0;
        
        numberCol.setCellValueFactory(new PropertyValueFactory<Service, String>(varibleNames[i++]));
        nameCol.setCellValueFactory(new PropertyValueFactory<Service, String>(varibleNames[i++]));
        unitCol.setCellValueFactory(new PropertyValueFactory<Service, String>(varibleNames[i++]));
        costCol.setCellValueFactory(new PropertyValueFactory<Service, String>(varibleNames[i++]));
        
        numberCol.setPrefWidth(30);
        nameCol.setPrefWidth(200);
        unitCol.setPrefWidth(100);
        costCol.setPrefWidth(140);
        
        columns.add(numberCol);
        columns.add(nameCol);
        columns.add(unitCol);
        columns.add(costCol);
        
        return columns;
    }
}
