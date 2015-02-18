package swd.bank;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import swd.DAO.Factory;
import swd.logic.Bank;

public class DeleteBankFX {
    private short id;
    private String name;
    private Stage stage;
    private Stage stage_parent;
    private VBox vx_content;
    private HBox hx_btn;
    private Button btn_cancel;
    private Button btn_del;
    
    public DeleteBankFX(Stage st,Stage s_p,short i,String n){
        stage = st;
        stage_parent = s_p;
        id = i;
        name = n;
    }
    
    private void initControl(){
        vx_content = new VBox();
        hx_btn = new HBox();
        btn_cancel = new Button("Отмена");
        btn_del = new Button("Удалить");
    }
    
    private void setControl(){
        hx_btn.getChildren().addAll(btn_del,btn_cancel);
        vx_content.getChildren().addAll(new Label("Вы действительно хотите удалить: " + name + "?"),hx_btn);
    }
    
    private void setEvent(){
        btn_del.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                try{
                    Bank bank = Factory.getInstance().getBankDAO().getBankById(id);
                    Factory.getInstance().getBankDAO().deleteBank(bank);
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
        
        btn_cancel.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                stage.close();
            }
        });
    }
    
    private void setStyle(){
        hx_btn.setSpacing(5);
        hx_btn.setAlignment(Pos.BASELINE_CENTER);
        vx_content.setPadding(new Insets(10));
        vx_content.setSpacing(5);
    }
    
    public void show(){
        initControl();
        setControl();
        setEvent();
        setStyle();
        Scene scene = new Scene(vx_content);
        
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Удаление Банка");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
}
