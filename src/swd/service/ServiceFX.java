package swd.service;

import java.sql.SQLException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import swd.DAO.Factory;


public class ServiceFX {
    private short id;
    private String name;
    private String unit;
    private float cost;
    
    Stage stage;
    
    VBox vbox;
    HBox hx_edit_btn;
    
    
    TableView<swd.logic.Service> tv_service;
    
    Button btn_add;
    Button btn_edit;
    Button btn_del;
    Button btn_cancel;
    
    public ServiceFX(Stage st){
        stage = st;
    }
    
    private void initControls(){
        vbox = new VBox();
  
        hx_edit_btn = new HBox();
        
        tv_service = new TableView<swd.logic.Service>();
        btn_add = new Button("Добавить");
        btn_edit = new Button("Изменить");
        btn_del = new Button("Удалить");
        btn_cancel = new Button("Отмена");
    }
    
    private void setControl(){
        
        //btn_cancel.setAlignment(Pos.BOTTOM_RIGHT);
        //VBox.setVgrow(btn_cancel, Priority.ALWAYS);
        vbox.setAlignment(Pos.CENTER_RIGHT);
        vbox.getChildren().addAll(tv_service,hx_edit_btn,btn_cancel);
        
        hx_edit_btn.getChildren().addAll(btn_add,btn_edit,btn_del);
        
        tv_service.getColumns().addAll(swd.logic.Service.getColumn(tv_service));
    }
    
    private void setEvent(){
        tv_service.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                if(event.getClickCount() >= 1){
                    if(tv_service.getSelectionModel().getSelectedIndex() >= 0){
                        id = tv_service.getSelectionModel().getSelectedItem().getId();
                        name = tv_service.getSelectionModel().getSelectedItem().getName();
                        unit = tv_service.getSelectionModel().getSelectedItem().getUnit();
                        cost = tv_service.getSelectionModel().getSelectedItem().getCost();
                        
                        btn_edit.setDisable(false);
                        btn_del.setDisable(false);
                    }
                }
            }
        });
        
        btn_add.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                try{
                    Stage st = new Stage();
                    AddServiceFX add = new AddServiceFX(st,stage);
                    add.show();
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        
        btn_edit.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                try{
                    Stage st = new Stage();
                    EditServiceFX edit = new EditServiceFX(st,stage,id,name,unit,cost);
                    edit.show();
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        
        btn_del.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                Stage st = new Stage();
                //Stage st, Stage st_p,short i,String n
                DelServiceFX del = new DelServiceFX(st,stage,id,name);
                del.show();
            }
        }); 
        
        btn_cancel.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                stage.close();
            }
        });
    }
    
    private void setStyleControl(){
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(5);
        hx_edit_btn.setSpacing(5);
        
        tv_service.setMinSize(350, 500);
        tv_service.setMaxSize(470, 500);
        tv_service.setScaleShape(true);
        
        btn_edit.setDisable(true);
        btn_del.setDisable(true);
    }
    
    private void addServiceIntoTV() throws SQLException{
        try{
            List<swd.logic.Service> services = Factory.getInstance().getServiceDAO().getAllServices();
            ObservableList<swd.logic.Service> oListsService = FXCollections.observableList(services);
            
            tv_service.setItems(oListsService);
            
        } catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Ошибка I/O", JOptionPane.OK_OPTION);
        }
    }
    
    public void show() throws SQLException{
        try{
            initControls();
            setEvent();
            setControl();
            setStyleControl();
            addServiceIntoTV();

            Scene scene = new Scene(vbox);

            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setTitle("Услуги");
            stage.setResizable(false);
            stage.show();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
