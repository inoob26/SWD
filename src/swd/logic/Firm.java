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
@Table(name="Firm")
public class Firm {
    private Long id;
    private String name;
    private String phone;
    private String address;
    private String contract;
    private String rull;
    private String bin_iin;
    private String iik;
    private short bank_id;
    private short performer_id;

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

    @Column(name="Name")
    public String getName(){
    	return name;
    }

    public void setName(String n){
    	name = n;
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

    @Column(name="Contract")
    public String getContract(){
    	return contract;
    }

    public void setContract(String c){
    	contract = c;
    }

    @Column(name="Rull")
    public String getRull(){
    	return rull;
    }

    public void setRull(String r){
    	rull = r;
    }

    @Column(name="BIN_IIN")
    public String getBin_iin(){
    	return bin_iin;
    }

    public void setBin_iin(String b_i){
    	bin_iin = b_i;
    }

    @Column(name="IIK")//s
    public String getIik(){
    	return iik;
    }

    public void setIik(String i){
    	iik = i;
    }

    @Column(name="BANK_id")//short
    public short getBank_id(){
    	return bank_id;
    }

    public void setBank_id(short b_i){
    	bank_id = b_i;
    }

    @Column(name="Performer_id")
    public short getPerformer_id(){
    	return performer_id;
    }

    public void setPerformer_id(short p_i){
    	performer_id = p_i;
    }
    
    public static ArrayList<TableColumn<Firm , ?>> getColumn(TableView table){
        
        ArrayList<TableColumn<Firm , ?>> columns = new ArrayList<TableColumn<Firm , ?>>();
   
        String[] varibleNames = { "name", "bin_iin" ,"iik"};
        
        TableColumn<Firm , String> nameCol = new TableColumn<>("Наименование Орг.");
        TableColumn<Firm , String> bin_iinCol = new TableColumn<>("БИН/ИИН");
        TableColumn<Firm , String> iikCol = new TableColumn<>("Расчетный счет");
        
        int i = 0;
        
        nameCol.setCellValueFactory(new PropertyValueFactory<Firm, String>(varibleNames[i++]));
        bin_iinCol.setCellValueFactory(new PropertyValueFactory<Firm, String>(varibleNames[i++]));
        iikCol.setCellValueFactory(new PropertyValueFactory<Firm, String>(varibleNames[i++]));
        
        nameCol.setPrefWidth(300);
        bin_iinCol.setPrefWidth(200);
        iikCol.setPrefWidth(200);
        
        columns.add(nameCol);
        columns.add(bin_iinCol);
        columns.add(iikCol);
        
        return columns;
    }
}
