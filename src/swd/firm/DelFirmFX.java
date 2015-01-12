package swd.firm;

import java.sql.SQLException;
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
import swd.UserPanelFX;
import swd.logic.Firm;

public class DelFirmFX {
    byte flag_pane;
    
    Long id;
    String name;
    
    Stage stage;
    Stage stage_parent;
    
    VBox vx_content;
    HBox hx_btn;
    Button btn_cancel;
    Button btn_del;
    
    public DelFirmFX(Stage st,Stage s_p,Long i,String n,byte f_p){
        stage = st;
        stage_parent = s_p;
        id = i;
        name = n;
        flag_pane = f_p;
    }
    
    public void initControl(){
        vx_content = new VBox();
        hx_btn = new HBox();
        btn_cancel = new Button("Отмена");
        btn_del = new Button("Удалить");
    }
    
    public void setControl(){
        hx_btn.getChildren().addAll(btn_del,btn_cancel);
        vx_content.getChildren().addAll(new Label("Вы действительно хотите удалить: " + name + "?"),hx_btn);
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
        btn_del.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                try{
                    Firm firm = Factory.getInstance().getFirmDAO().getFirmById(id);
                    Factory.getInstance().getFirmDAO().deleteFirm(firm);
                    stage.close();
                    
                    checkFlagPane(flag_pane);
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
    
    public void setStyle(){
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
        stage.setTitle("Удаление фирмы");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
}
