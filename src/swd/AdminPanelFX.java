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
import swd.firm.FirmFX;
import swd.firm.PerformerFX;
import swd.service.ServiceFX;
import swd.users.UsersFX;

public class AdminPanelFX {
    Stage stage;
    
    GridPane gpane;
    
    Button btn_firm;
    Button btn_users;
    Button btn_bank;
    Button btn_service;
    Button btn_perf;
    
    Image img_firm;
    Image img_users;
    Image img_bank;
    Image img_service;
    Image img_perf;
    
    public AdminPanelFX(Stage st){
        stage = st;
    }
    
    public void initControl(){
        gpane = new GridPane();
        btn_firm = new Button("Фирмы");
        btn_users = new Button("Пользователи");
        btn_bank = new Button("Банки");
        btn_service = new Button("Услуги");
        btn_perf = new Button("Исполнитель");
        
        img_firm = new Image(getClass().getResourceAsStream("icon/firm64.png"));
        img_users = new Image(getClass().getResourceAsStream("icon/users64.png"));
        img_bank = new Image(getClass().getResourceAsStream("icon/banks64.png"));
        img_service = new Image(getClass().getResourceAsStream("icon/service64.png"));
        img_perf = new Image(getClass().getResourceAsStream("icon/64x64/performer64.png"));
    }
    
    public void setControl(){
        btn_firm.setGraphic(new ImageView(img_firm));
        btn_users.setGraphic(new ImageView(img_users));
        btn_bank.setGraphic(new ImageView(img_bank));
        btn_service.setGraphic(new ImageView(img_service));
        btn_perf.setGraphic(new ImageView(img_perf));
        
        gpane.add(btn_firm, 0, 0);
        gpane.add(btn_users, 1, 0);
        gpane.add(btn_bank, 2, 0);
        gpane.add(btn_service, 3, 0);
        gpane.add(btn_perf, 4, 0);
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
        
        btn_perf.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                try {
                    Stage st = new Stage();
                    PerformerFX perf = new PerformerFX(st);
                
                    perf.show();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
    
    public void setStyleControl(){
        
        btn_firm.setMaxHeight(70);
        btn_users.setMinHeight(70);
        btn_service.setMinHeight(70);
        btn_bank.setMinHeight(70);
        btn_perf.setMaxHeight(70);
        
        
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
