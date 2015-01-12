package swd;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import swd.bank.BankFX;
import swd.documents.DocumentFX;
import swd.firm.FirmFX;
import swd.service.ServiceFX;
import swd.users.UsersFX;

public class AdminPanelFX {
    Stage stage;
    
    GridPane gpane;
    
    Button btn_firm;
    Button btn_users;
    Button btn_bank;
    Button btn_service;
    //Button btn_docs;
    
    Image img_firm;
    Image img_users;
    Image img_bank;
    Image img_service;
    //Image img_docs;
    
    public AdminPanelFX(Stage st){
        stage = st;
    }
    
    public void initControl(){
        gpane = new GridPane();
        btn_firm = new Button();
        btn_users = new Button();
        btn_bank = new Button();
        btn_service = new Button();
        
        img_firm = new Image(getClass().getResourceAsStream("icon/firm64.png"));
        img_users = new Image(getClass().getResourceAsStream("icon/users64.png"));
        img_bank = new Image(getClass().getResourceAsStream("icon/banks64.png"));
        img_service = new Image(getClass().getResourceAsStream("icon/service64.png"));
    }
    
    public void setControl(){
        btn_firm.setGraphic(new ImageView(img_firm));
        btn_users.setGraphic(new ImageView(img_users));
        btn_bank.setGraphic(new ImageView(img_bank));
        btn_service.setGraphic(new ImageView(img_service));
        
        gpane.add(btn_firm, 0, 0);
        gpane.add(btn_users, 1, 0);
        gpane.add(btn_bank, 2, 0);
        gpane.add(btn_service, 3, 0);
    }
    
    public void setEvent(){
        btn_firm.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                try {
                    Stage st = new Stage();
                    FirmFX firm = new FirmFX(st);
                    firm.show();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        btn_users.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                Stage st = new Stage();
                UsersFX usr = new UsersFX(st);
                usr.show();
            }
        });
        
        btn_bank.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                try {
                    Stage st = new Stage();
                    BankFX usr = new BankFX(st);
                
                    usr.show();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        btn_service.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                try {
                    Stage st = new Stage();
                    ServiceFX service = new ServiceFX(st);
                
                    service.show();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
    
    public void setStyleControl(){
        btn_firm.setMaxSize(70, 70);
        btn_firm.setMinSize(70, 70);
        btn_users.setMaxSize(70, 70);
        btn_users.setMinSize(70, 70);
        btn_service.setMaxSize(70, 70);
        btn_service.setMinSize(70, 70);
        btn_bank.setMaxSize(70, 70);
        btn_bank.setMinSize(70, 70);
        
        gpane.setPadding(new Insets(10));
        gpane.setVgap(5);
        gpane.setHgap(5);
    }
    
    public void show(){
        initControl();
        setControl();
        setEvent();
        setStyleControl();
        
        Scene scene = new Scene(gpane);
        stage.setScene(scene);
        stage.setTitle("Административная панель");
        stage.setResizable(false);
        stage.show();
    }
     
    
}
