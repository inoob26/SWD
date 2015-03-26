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
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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

public class AddFirmFX {
    private Stage stage;
    private Stage stage_parent;
    private String rull;
    private short bank_index;
    private byte flag_pane;            
    private VBox vbox;
    private GridPane gpane;    
    private HBox hx_rull;
    private HBox hx_contract;
    private HBox hx_btn;    
    private TextField tf_name;
    private TextField tf_phone;
    private TextField tf_contract;
    private TextField tf_address;
    private TextField tf_IIN_BIN;
    private TextField tf_IIK;    
    private ChoiceBox cb_name_bank;    
    private TextField tf_BIK;
    private TextField tf_bank;
    private ToggleGroup group_rull;
    private RadioButton rb_cash;
    private RadioButton rb_cashless;    
    private Button btn_generate;
    private Button btn_add;
    private Button btn_cancel;
    
    public AddFirmFX(Stage st,Stage st_p,byte f_p){
        stage = st;
        stage_parent = st_p;
        flag_pane = f_p;
    }
    
    private void initControl(){
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
        
        bank_index = 0;
        
        btn_add = new Button("Добавить Организацию");
        btn_cancel = new Button("Отмена");
    }
    
    private void setControl(){        
        gpane.add(new Label("Наименование ораганизации: "), 0, 0);
        gpane.add(tf_name, 1, 0);
        
        gpane.add(new Label("Тел: "),0,1);
        gpane.add(tf_phone, 1, 1);
        
        gpane.add(new Label("Договор №: "), 0, 2);
        gpane.add(tf_contract,1,2);
        
        gpane.add(new Label("Условия оплаты по договору: "), 0, 3);
        rb_cash.setToggleGroup(group_rull);
        rb_cash.setSelected(true);
        rb_cashless.setToggleGroup(group_rull);

        hx_rull.getChildren().addAll(rb_cash,rb_cashless);
        gpane.add(hx_rull, 1, 3);
        
        gpane.add(new Label("Адрес: "), 0, 4);
        gpane.add(tf_address, 1, 4);
        
        hx_btn.getChildren().addAll(btn_add,btn_cancel);
        
        gpane.add(new Label("ИИН/БИН: "), 0, 5);
        gpane.add(tf_IIN_BIN, 1, 5);
        
        gpane.add(new Label("ИИК: "), 0, 6);
        gpane.add(tf_IIK,1,6);
        checkBankIndex(bank_index);
        addBanksIntoCB();
        
        gpane.add(new Label("Банк: ") ,0,7);
        gpane.add(cb_name_bank ,1,7);
        
        gpane.add(new Label("БИК: "), 0, 8);
        gpane.add(tf_BIK, 1, 8);
                
        vbox.getChildren().addAll(gpane,hx_btn);
        
    }
    
    private void addBanksIntoCB(){
        try{
            List<Bank> all_banks = Factory.getInstance().getBankDAO().getAllBanksName();
            ObservableList<Bank> oListBank = FXCollections.observableList(all_banks);
            
            cb_name_bank.setItems(oListBank); 
            short i = 0;
            cb_name_bank.setValue(oListBank.get(i));
            setDefaultBank(++i);
        } catch(Exception ex){
            ex.printStackTrace();
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
    
    private void checkBankIndex(short b_inx){
        if(b_inx == 0){
            bank_index = 1;
        } else {
            bank_index = b_inx;
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
    
    //Добваление фирмы в БД
    private void add(){
        try{
           Firm f = new Firm();
           f.setName(tf_name.getText());
           f.setPhone(tf_phone.getText());
           f.setAddress(tf_address.getText());
           f.setContract(tf_contract.getText());           
           f.setRull(rull);
           f.setBin_iin(tf_IIN_BIN.getText().toUpperCase());
           f.setIik(tf_IIK.getText().toUpperCase());
           f.setBank_id(bank_index);
           f.setPerformer_id((short) 1);
           Factory.getInstance().getFirmDAO().addFirm(f);
           
           stage.close();
           
           checkFlagPane(flag_pane);
           
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    private void setEvent(){  
        //текстовые поля
        tf_name.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                swd.Check ch = new swd.Check();
                System.out.println(ch.check_TextfieldFirmTitle(tf_name.getText()));
            }
        });
        
        tf_phone.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                swd.Check ch = new swd.Check();
                System.out.println(ch.check_TextfieldPhone(tf_phone.getText()));
            }
        });
        
        tf_address.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                swd.Check ch = new swd.Check();
                System.out.println(ch.check_TextfieldAddress(tf_address.getText()));
            }
        });
        
        
        //Кнопки
        btn_add.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                add();
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
    
    private void setStyleControl(){
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
            stage.setTitle("Добавление Новой Организации");
            stage.setScene(scene);
            stage.setResizable(false);            
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();            
        } catch(Exception ex){
            ex.printStackTrace();
        }
        
    }
}