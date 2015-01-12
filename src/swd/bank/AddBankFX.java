/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swd.bank;

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

public class AddBankFX {
    Stage stage;
    Stage stage_parent;
    VBox vx_content;
    GridPane gpane;
    HBox hx_btn;
    
    TextField tf_name;
    TextField tf_bik;
    
    Button btn_save;
    Button btn_cancel;
    
    public AddBankFX(Stage st, Stage st_p){
        stage = st;
        stage_parent = st_p;
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
    
    private void setControl(){
        gpane.add(new Label("Наименование Банка: "), 0, 0);
        gpane.add(tf_name, 1, 0);
        gpane.add(new Label("БИК банка: "), 0, 1);
        gpane.add(tf_bik, 1, 1);
        
        hx_btn.getChildren().addAll(btn_save,btn_cancel);
        
        vx_content.getChildren().addAll(gpane,hx_btn);
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
                    Bank bank = new Bank();
                    bank.setName(tf_name.getText());
                    bank.setBik(tf_bik.getText().toUpperCase());
                    Factory.getInstance().getBankDAO().addBank(bank);
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
    
    public void show(){
        initControl();
        setControl();
        setEvent();
        setStyle();
        
        Scene scene = new Scene(vx_content);
        stage.setScene(scene);
        stage.setTitle("Добавление нового Банка");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);
        stage.show();
        
    }
}
