package swd.service;

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
import swd.logic.Service;

public class EditServiceFX {
    private short id;
    private String name;
    private String unit;
    private float cost;
    
    
    Stage stage;
    Stage stage_parent;
    Scene scene;
    
    VBox vx_content;
    GridPane gpane;
    HBox hx_btn;
    
    TextField tf_name;
    TextField tf_unit;
    TextField tf_cost;
    
    Button btn_save;
    Button btn_cancel;
    public EditServiceFX(Stage st,Stage st_p,short i,String n,String u, float c){
        stage = st;
        stage_parent = st_p;
        id = i;
        name = n;
        unit = u;
        cost = c;
    }
    
    public void initControl(){
        vx_content = new VBox();
        gpane = new GridPane();
        hx_btn = new HBox();
        
        scene = new Scene(vx_content);

        tf_name = new TextField();
        tf_unit = new TextField();
        tf_cost = new TextField();
        
        btn_save = new Button("Сохранить");
        btn_cancel = new Button("Отмена");
    }
    
    public void setControl(){
        tf_name.setText(name);
        tf_unit.setText(unit);
        tf_cost.setText(String.valueOf(cost));
        
        gpane.add(new Label("Наименование: "), 0, 0);
        gpane.add(tf_name, 1, 0);
        
        gpane.add(new Label("Единица измерения: "), 0, 1);
        gpane.add(tf_unit, 1, 1);
        
        gpane.add(new Label("Цена за единицу: "), 0, 2);
        gpane.add(tf_cost, 1, 2);
        
        hx_btn.getChildren().addAll(btn_save,btn_cancel);
        
        vx_content.getChildren().addAll(gpane,hx_btn);
    }
    
    public void setEvent(){
        btn_save.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                try{
                    Service service = Factory.getInstance().getServiceDAO().getServiceById(id);
                    service.setName(tf_name.getText());
                    service.setUnit(tf_unit.getText());
                    service.setCost(Float.parseFloat(tf_cost.getText()));
                    Factory.getInstance().getServiceDAO().updateService(service);
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
        
        gpane.setPadding(new Insets(5));
        gpane.setVgap(5);
        gpane.setHgap(5);
        
        hx_btn.setSpacing(5);
        hx_btn.setAlignment(Pos.BASELINE_RIGHT);
        
        tf_name.setMinWidth(200);
        tf_unit.setMinWidth(200);
        tf_cost.setMinWidth(200);
    }
    
    public void show(){
        try{
            initControl();
            setControl();
            setEvent();
            setStyleControl();

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Редактирование услуги");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
