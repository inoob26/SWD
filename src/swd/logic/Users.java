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
@Table(name="Users")
public class Users {
    private Long id;
    private String login;
    private String password;
    private String flag;
    
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name="ID")
    public Long getId(){
        return id;
    }
    
    private void setId(Long i){
        id = i;
    }
    
    @Column(name="Login")
    public String getLogin(){
        return login;
    }
    
    public void setLogin(String l){
        login = l;
    }
    
    @Column(name="Password")
    public String getPassword(){
        return password;
    }
    
    public void setPassword(String p){
        password = p;
    }
    
    @Column(name="Flag")
    public String getFlag(){
        return flag;
    }
    
    public void setFlag(String f){
        flag = f;
    }
    
    public static ArrayList<TableColumn<Users , ?>> getColumn(TableView tv){
        ArrayList<TableColumn<Users , ?>> columns = new ArrayList<TableColumn<Users , ?>>();
        
        String[] varibleNames = { "Login", "Flag" };
        
        TableColumn<Users , String> nameCol = new TableColumn<>("Пользователь");
        TableColumn<Users , String> flagCol = new TableColumn<>("Права");
        
        int i = 0;
        
        nameCol.setCellValueFactory(new PropertyValueFactory<Users, String>(varibleNames[i++]));
        flagCol.setCellValueFactory(new PropertyValueFactory<Users, String>(varibleNames[i++]));
        
        nameCol.setPrefWidth(200);
        flagCol.setPrefWidth(140);
        nameCol.setResizable(false);
        flagCol.setResizable(false);
        
        columns.add(nameCol);
        columns.add(flagCol);
        
        return columns;
    }
}
