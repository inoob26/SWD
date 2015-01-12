package swd.service;

import java.awt.BorderLayout;
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
import swd.DAO.Impl.ServiceDAOImpl;

import swd.logic.Service;

public class AddServiceFX{
    private short id;
    private String name;
    private String unit;
    private float cost;
    
    Stage stage;
    Stage stage_parent;
    Scene scene;
    
    VBox vx_content;
    GridPane gpane;
    HBox hbox;
    
    TextField tf_name;
    TextField tf_unit;
    TextField tf_cost;
    
    Button btn_add;
    Button btn_cancel;
    
    public AddServiceFX(Stage st,Stage st_p){
        stage = st;
        stage_parent = st_p;
    }
    
    public void initControl(){
        vx_content = new VBox();
        gpane = new GridPane();
        hbox = new HBox();
        
        scene = new Scene(vx_content);

        tf_name = new TextField();
        

        tf_unit = new TextField();
        
        tf_cost = new TextField();
        
        btn_add = new Button("Добавить");
        btn_cancel = new Button("Отмена");
    }
    
    public void setControl(){
        gpane.add(new Label("Наименование: "), 0, 0);
        gpane.add(tf_name, 1, 0);
        
        gpane.add(new Label("Единица измерения: "), 0, 1);
        gpane.add(tf_unit, 1, 1);
        
        gpane.add(new Label("Цена за единицу: "), 0, 2);
        gpane.add(tf_cost, 1, 2);
        
        hbox.getChildren().addAll(btn_add,btn_cancel);
        
        vx_content.getChildren().addAll(gpane,hbox);
    }
    
    public void setStyleControl(){
        vx_content.setPadding(new Insets(10));
        
        gpane.setPadding(new Insets(5));
        gpane.setVgap(5);
        gpane.setHgap(5);
        
        hbox.setSpacing(5);
        hbox.setAlignment(Pos.BASELINE_RIGHT);
        
        tf_name.setMinWidth(200);
        tf_unit.setMinWidth(200);
        tf_cost.setMinWidth(200);
    }
    
    public void getData(){
        name = tf_name.getText();
        cost = Float.parseFloat(tf_cost.getText());
        unit = tf_unit.getText();
    }
    
    public void setEvent(){
        btn_add.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                try{
                    Service service = new Service();
                    
                    getData();
                    
                    service.setName(name);
                    service.setCost(cost);
                    service.setUnit(unit);
                    Factory.getInstance().getServiceDAO().addService(service);
                    stage.close();
                    
                    Stage st_service = new Stage();
                    swd.service.ServiceFX sc = new swd.service.ServiceFX(st_service);
                    stage_parent.close();
                    
                    sc.show();
                    
                } catch (Exception ex){
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
    
    public void show(){
        try{
            initControl();
            setControl();
            setEvent();
            setStyleControl();

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Добавление услуги");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
