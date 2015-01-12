package swd.firm;

import java.sql.SQLException;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import swd.DAO.Factory;
import swd.SWD;
import swd.UserPanelFX;
import swd.logic.Bank;
import swd.logic.Firm;

public class EditFirmFX {
    private Long id;
    private String name;
    private String phone;
    private String address;
    private String contract;
    private String rull;
    private String bin_iin;
    private String iik;
    private short bank_id;
    
    Stage stage;
    Stage stage_parent;
    
    byte flag_pane;
    
    short bank_index;
            
    VBox vbox;
    GridPane gpane;
    
    HBox hx_rull;
    HBox hx_contract;
    HBox hx_btn;
    
    TextField tf_name;
    TextField tf_phone;
    TextField tf_contract;
    TextField tf_address;
    TextField tf_IIN_BIN;
    TextField tf_IIK;
    
    ChoiceBox cb_name_bank;
    
    TextField tf_BIK;
    TextField tf_bank;

    ToggleGroup group_rull;
    RadioButton rb_cash;
    RadioButton rb_cashless;
    
    Button btn_generate;
    Button btn_add;
    Button btn_cancel;
    
    public EditFirmFX(Stage st,Stage st_p, Long i,String n, String p,String a,
                    String c,String r,String b_i,String ik,short b_id,byte f_p){
        stage = st;
        stage_parent = st_p;
        id = i;
        name = n;
        phone = p;
        address = a;
        contract = c;
        rull = r;
        bin_iin = b_i;
        iik = ik;
        bank_id = b_id;
        flag_pane = f_p;
    }
    
    public void initControl(){
        vbox = new VBox();
        gpane = new GridPane();
        hx_rull = new HBox();
        hx_contract = new HBox();
        hx_btn = new HBox();
        
        tf_name = new TextField();
        
        tf_phone = new TextField();

        tf_contract = new TextField();
        
        cb_name_bank = new ChoiceBox();
        
        group_rull = new ToggleGroup();
        rb_cashless = new RadioButton("Безналичный расчет");
        rb_cash = new RadioButton("Наличный расчет");
        
        tf_address = new TextField();
        tf_IIN_BIN = new TextField();

        tf_IIK = new TextField();
        
        tf_BIK = new TextField();
        
        tf_bank = new TextField();

        btn_add = new Button("Изменить");
        btn_cancel = new Button("Отмена");
    }
    
    public void setControl() throws SQLException{  
        tf_name.setText(name);
        gpane.add(new Label("Наименование ораганизации: "), 0, 0);
        gpane.add(tf_name, 1, 0);
        
        gpane.add(new Label("Тел: "),0,1);
        gpane.add(tf_phone, 1, 1);
        tf_phone.setText(phone);
        
        gpane.add(new Label("Договор №: "), 0, 2);
        gpane.add(tf_contract,1,2);
        tf_contract.setText(contract);
        
        checkRull(rull);
        
        gpane.add(new Label("Условия оплаты по договору: "), 0, 3);
        rb_cash.setToggleGroup(group_rull);
        rb_cashless.setToggleGroup(group_rull);

        hx_rull.getChildren().addAll(rb_cash,rb_cashless);
        gpane.add(hx_rull, 1, 3);
        
        gpane.add(new Label("Адрес: "), 0, 4);
        gpane.add(tf_address, 1, 4);
        tf_address.setText(address);
        
        hx_btn.getChildren().addAll(btn_add,btn_cancel);
        
        gpane.add(new Label("ИИН/БИН: "), 0, 5);
        gpane.add(tf_IIN_BIN, 1, 5);
        tf_IIN_BIN.setText(bin_iin);
        
        gpane.add(new Label("ИИК: "), 0, 6);
        gpane.add(tf_IIK,1,6);
        tf_IIK.setText(iik);
        
        checkBankIndex(bank_id);
        setDefaultBank(bank_index);
        addBanksIntoCB();
        
        gpane.add(new Label("Банк: ") ,0,7);
        gpane.add(cb_name_bank ,1,7);
        
        gpane.add(new Label("БИК: "), 0, 8);
        gpane.add(tf_BIK, 1, 8);
                
        vbox.getChildren().addAll(gpane,hx_btn);
        
    }
    
    private void checkBankIndex(short b_inx){
        if(b_inx == 0){
            bank_index = 1;
        } else {
            bank_index = b_inx;
        }
    }
    
    private void checkRull(String r){
        if(r == "Наличный расчет"){
            rb_cash.setSelected(true);
        } else {
            rb_cashless.setSelected(true);
        }
    }
    
    private void setDefaultBank(short inx) throws SQLException{
        try{
            Bank bank = Factory.getInstance().getBankDAO().getBankById(inx);
            tf_BIK.setText(bank.getBik());
            tf_BIK.setDisable(true);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    private void addBanksIntoCB(){
        try{
            List<Bank> all_banks = Factory.getInstance().getBankDAO().getAllBanksName();
            ObservableList<Bank> oListBank = FXCollections.observableList(all_banks);
            
            cb_name_bank.setItems(oListBank);
            cb_name_bank.setValue(oListBank.get(--bank_index));
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    private void save(){
        try{
           Firm f = Factory.getInstance().getFirmDAO().getFirmById(id);
           f.setName(tf_name.getText());
           f.setPhone(tf_phone.getText());
           f.setAddress(tf_address.getText());
           f.setContract(tf_contract.getText());           
           f.setRull(rull);
           f.setBin_iin(tf_IIN_BIN.getText().toUpperCase());
           f.setIik(tf_IIK.getText().toUpperCase());
           f.setBank_id(bank_index);
           f.setPerformer_id((short) 1);
           Factory.getInstance().getFirmDAO().updateFirm(f);
           
           stage.close();
           
           checkFlagPane(flag_pane);
           
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    private void checkFlagPane(byte f_p) throws SQLException{
        try{
            Stage st = new Stage();
            if(flag_pane == 1){
                FirmFX firm = new FirmFX(st);
                stage_parent.close();
                firm.show();
            }
            if(flag_pane == 2){
                UserPanelFX user = new UserPanelFX(st);
                stage_parent.close();
                user.show();
            }
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void setEvent(){     
        btn_add.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                save();
            }
        });
        
        btn_cancel.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                try{
                    stage.close();
                }catch(Exception e){
                }      
            }
        });
        
        cb_name_bank.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                try {
                    bank_index = newValue.shortValue();                    
                    setDefaultBank(++bank_index);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                System.out.println(newValue.shortValue());
            }
        });
        
        group_rull.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                RadioButton chk = (RadioButton)newValue.getToggleGroup().getSelectedToggle();
                
                rull = chk.getText();
            }
            
        });
    }
    
    public void setStyleControl(){
        vbox.setPadding(new Insets(5));
        vbox.setSpacing(5);
        gpane.setPadding(new Insets(5));
        gpane.setHgap(5);
        gpane.setVgap(5);
        hx_contract.setSpacing(5);
        hx_rull.setSpacing(5);
        hx_btn.setSpacing(5);
        hx_btn.setAlignment(Pos.CENTER_RIGHT);
        
        tf_name.setMinWidth(300);
        tf_contract.setMinWidth(150);
        tf_address.setMinWidth(300);
        tf_IIN_BIN.setMinWidth(300);
        tf_IIK.setMinWidth(300);
        tf_BIK.setMinWidth(300);
        tf_bank.setMinWidth(300);
    }
    
    
    public void show(){
        try{
            initControl();
            setControl();
            setStyleControl();
            setEvent();

            Scene scene = new Scene(vbox);

            stage.setTitle("Редактирование организации");
            stage.setScene(scene);
            stage.setResizable(false);
            
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
            
        } catch(Exception ex){
            ex.printStackTrace();
        }
        
    }
}
