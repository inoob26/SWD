package swd.bank;

import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import swd.DAO.Factory;
import swd.logic.Bank;

public class EditBankFX {
    private short id;
    private String bank_name;
    private String bank_bik;
    private Stage stage;
    private Stage stage_parent;
    private VBox vx_content;
    private GridPane gpane;
    private HBox hx_btn;
    private TextField tf_name;
    private TextField tf_bik;
    private Button btn_save;
    private Button btn_cancel;
    
    public EditBankFX(Stage st,Stage st_p,short i,String b_n,String b_b){
        stage = st;
        stage_parent = st_p;
        id = i;
        bank_name = b_n;
        bank_bik = b_b;
    }
    
    private void initControl(){
        vx_content = new VBox();
        gpane = new GridPane();
        hx_btn = new HBox();
        tf_name = new TextField();
        tf_bik = new TextField();        
        btn_save = new Button("Сохранить");
        btn_cancel = new Button("Отмена");
    }
    
    private void setControl() throws SQLException{
        try{
            tf_name.setText(bank_name);
            tf_bik.setText(bank_bik);
            gpane.add(new Label("Наименование Банка: "), 0, 0);
            gpane.add(tf_name, 1, 0);
            gpane.add(new Label("БИК банка: "), 0, 1);
            gpane.add(tf_bik, 1, 1);
            hx_btn.getChildren().addAll(btn_save,btn_cancel);
            vx_content.getChildren().addAll(gpane,hx_btn);
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    private void setEvent(){
        btn_cancel.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                try{
                    stage.close();
                } catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        
        btn_save.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                try{
                    Bank bank = Factory.getInstance().getBankDAO().getBankById(id);
                    bank.setName(tf_name.getText());
                    bank.setBik(tf_bik.getText().toUpperCase());
                    Factory.getInstance().getBankDAO().updateBank(bank);
                    stage.close();
                    Stage st_bank = new Stage();
                    BankFX bfx = new BankFX(st_bank);
                    stage_parent.close();
                    bfx.show();
                } catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });
    }
    
    private void setStyle(){
        vx_content.setPadding(new Insets(10));
        vx_content.setSpacing(5);
        gpane.setVgap(5);
        gpane.setHgap(5);
        hx_btn.setSpacing(5);
        hx_btn.setAlignment(Pos.BASELINE_RIGHT);
    }
    
    public void show() throws SQLException{
        try{
            initControl();
            setControl();
            setEvent();
            setStyle();     
            Scene scene = new Scene(vx_content);
            stage.setScene(scene);
            stage.setTitle("Редактирование Банка: " + bank_name);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setResizable(false);
            stage.show();
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
