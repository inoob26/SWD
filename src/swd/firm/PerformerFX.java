package swd.firm;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import swd.DAO.Factory;
import swd.Message;
import swd.logic.Bank;
import swd.logic.Performer;

public class PerformerFX {
    private Long id;
    private String name;
    private String director;
    private String phone;
    private String address;
    private String bin_iin;
    private String iik;
    private short bank_id;
    
    short bank_index;
    
    Stage stage;
    
    VBox vx_content;
    GridPane gpane;
    HBox hx_btn;
    
    TextField tf_name;
    TextField tf_director;
    TextField tf_phone;
    TextField tf_address;
    TextField tf_bin_iin;
    TextField tf_iik;
    TextField tf_bik;
    
    ChoiceBox cb_name_bank;
    
    Button btn_save;
    Button btn_cancel;
    
    Performer perf;
    
    public PerformerFX(Stage st){
        try {
            stage = st;
            perf = Factory.getInstance().getPerformerDAO().getPerformerById((byte) 1);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void initControll(){
        vx_content = new VBox();
        gpane = new GridPane();
        hx_btn = new HBox();
        
        tf_name = new TextField();
        tf_director = new TextField();
        tf_phone = new TextField();
        tf_address = new TextField();
        tf_bin_iin = new TextField();
        tf_iik = new TextField();
        tf_bik = new TextField();
        
        cb_name_bank = new ChoiceBox();
        
        btn_save = new Button("Изменить");
        btn_cancel = new Button("Отмена");
    }
    
    private void setControll(){
        hx_btn.getChildren().addAll(btn_save,btn_cancel);
        
        gpane.add(new Label("Наименование организации: "), 0, 0);
        gpane.add(tf_name, 1, 0);
        gpane.add(new Label("Директор: "), 0, 1);
        gpane.add(tf_director, 1, 1);
        gpane.add(new Label("Телефон: "), 0, 2);
        gpane.add(tf_phone, 1, 2);
        gpane.add(new Label("Адрес : "), 0, 3);
        gpane.add(tf_address, 1, 3);
        gpane.add(new Label("БИН/ИИН: "), 0, 4);
        gpane.add(tf_bin_iin, 1, 4);
        gpane.add(new Label("ИИК: "), 0, 5);
        gpane.add(tf_iik, 1, 5);
        gpane.add(new Label("Банк: "), 0, 6);
        gpane.add(cb_name_bank, 1, 6);
        gpane.add(new Label("БИК: "), 0, 7);
        gpane.add(tf_bik, 1, 7);
        
        vx_content.getChildren().addAll(gpane,hx_btn);
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
    
    private void setDefaultBank(short inx) throws SQLException{
        try{
            Bank bank = Factory.getInstance().getBankDAO().getBankById(inx);
            tf_bik.setText(bank.getBik());
            tf_bik.setDisable(true);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    private void setDefaultValue() throws SQLException{
        try{
            tf_name.setText(perf.getName());
            tf_director.setText(perf.getDirector());
            tf_phone.setText(perf.getPhone());
            tf_address.setText(perf.getAddress());
            tf_bin_iin.setText(perf.getBinIin());
            tf_iik.setText(perf.getIik());
            bank_index = perf.getBankId();
            //bank_index--;
            setDefaultBank(bank_index);
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    private void setEvent(){
        btn_save.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                try{
                    perf.setName(tf_name.getText());
                    perf.setAddress(tf_address.getText());
                    perf.setDirector(tf_director.getText());
                    perf.setPhone(tf_phone.getText());
                    perf.setBinIin(tf_bin_iin.getText());
                    perf.setIik(tf_iik.getText());
                    perf.setBankId(bank_index);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        
        btn_cancel.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                stage.close();
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
    }
    
    private void setStyle(){
        //vx_content.setAlignment(Pos.BASELINE_RIGHT);
        vx_content.setSpacing(5);
        vx_content.setPadding(new Insets(10));
        gpane.setVgap(5);
        gpane.setHgap(5);
        hx_btn.setSpacing(5);
        hx_btn.setAlignment(Pos.BASELINE_RIGHT);
    }
    
    public void show(){
        try{
            initControll();
            setControll();
            setEvent();
            setStyle();
            
            setDefaultValue();
            addBanksIntoCB();
            
            Scene scene = new Scene(vx_content);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Исполнитель");
            stage.show();
        }catch(Exception ex){
            Stage st = new Stage();
            Message msg = new Message(st,"Ошибка 131");
            msg.showErrorMessageDialog();
        }
    }
}
