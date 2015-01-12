package swd.service;

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
import swd.logic.Service;

public class DelServiceFX {
    private short id;
    private String name;
    VBox vx_content;
    HBox hx_btn;
    
    Stage stage;
    Stage stage_parent;
    Scene scene;
    
    Button btn_del;
    Button btn_cancel;
    
    public DelServiceFX(Stage st, Stage st_p,short i,String n){
        stage = st;
        stage_parent = st_p;
        id = i;
        name = n;
    }
    
    public void initControl(){
        vx_content = new VBox();
        hx_btn = new HBox();
        
        scene = new Scene(vx_content);
        stage = new Stage();
        
        btn_del  = new Button("Удалить");
        btn_cancel = new Button("Отмена");
    }
    
    public void setControl(){
        hx_btn.getChildren().addAll(btn_del,btn_cancel);
        vx_content.getChildren().addAll(new Label("Вы действительно хотите удалить: " + name + "?"),hx_btn);
    }
    public void setEvent(){
        btn_del.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                try{
                    Service ser = Factory.getInstance().getServiceDAO().getServiceById(id);
                    Factory.getInstance().getServiceDAO().deleteService(ser);
                    stage.close();
                    
                    Stage st_ser = new Stage();
                    ServiceFX serFX = new ServiceFX(st_ser);
                    stage_parent.close();
                    serFX.show();
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        btn_cancel.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                try{
                    stage.close();
                } catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });
    }
    
    public void setStyleControl(){
        vx_content.setPadding(new Insets(10));
        vx_content.setSpacing(5);
        hx_btn.setAlignment(Pos.BASELINE_CENTER);
        hx_btn.setSpacing(5);
    }
    public void show(){
        try{
            initControl();
            setControl();
            setEvent();
            setStyleControl();

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Удаление услуги");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
