package swd.users;

import java.sql.SQLException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import swd.DAO.Factory;
import swd.logic.Users;

public class AddUsersFX {
    Stage stage;
    Stage stage_parent;
    
    VBox vx_content;
    GridPane gpane;
    HBox hx_btn;
    HBox hx_rull;
    
    TextField tf_login;
    PasswordField pf_password;
    
    ToggleGroup group_rull;
    RadioButton rb_admin;
    RadioButton rb_user;
    
    byte flag_rull;
    String rull;
    
    Button btn_save;
    Button btn_cancel;

    
    public AddUsersFX(Stage st, Stage st_p){
        stage = st;
        stage_parent = st_p;
        flag_rull = 2;
    }
    
    public void initControl(){
        vx_content = new VBox();
        gpane = new GridPane();
        hx_btn = new HBox();
        hx_rull = new HBox();
        tf_login = new TextField();
        pf_password = new PasswordField();
        
        group_rull = new ToggleGroup();
        rb_admin = new RadioButton("Администратор");
        rb_user = new RadioButton("Пользователь");
        
        btn_save = new Button("Сохранить");
        btn_cancel = new Button("Отмена");
    }
    
    public void setControl(){
        rb_admin.setToggleGroup(group_rull);
        rb_user.setToggleGroup(group_rull);
        rb_user.setSelected(true);
        hx_rull.getChildren().addAll(rb_user,rb_admin);
        
        gpane.add(new Label("Логин: "), 0, 0);
        gpane.add(tf_login, 1, 0);
        gpane.add(new Label("Пароль: "), 0, 1);
        gpane.add(pf_password, 1, 1);
        gpane.add(new Label("Права: "), 0, 2);
        gpane.add(hx_rull, 1, 2);
        
        hx_btn.getChildren().addAll(btn_save,btn_cancel);
        
        vx_content.getChildren().addAll(gpane,hx_btn);
    }
    
    public void check(byte ch){
        if(ch == 2){
            rull = "user";
        } else {
            rull = "admin";
        }
    }
    
    public void add() throws SQLException{
        try{
            Users usr = new Users();
            
            String login = tf_login.getText();
            String password = pf_password.getText();
            
            usr.setLogin(login);
            usr.setPassword(password);
            
            check(flag_rull);
            
            usr.setFlag(rull);
            Factory.getInstance().getUserDAO().addUser(usr);
            stage.close();
            
            Stage st_usr_fx = new Stage();
            UsersFX usr_fx = new UsersFX(st_usr_fx);
            stage_parent.close();
            usr_fx.show();
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void setEvent(){
        btn_save.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                try{
                    add();
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
        
        group_rull.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                RadioButton chk = (RadioButton)newValue.getToggleGroup().getSelectedToggle();
                
                if(chk.getText() == "Пользователь"){
                    flag_rull = 2;
                } else {
                    flag_rull = 1;
                }
            }
        });
    }
    
    public void setStyleControl(){
        vx_content.setPadding(new Insets(10));
        vx_content.setSpacing(5);
        hx_btn.setAlignment(Pos.BASELINE_RIGHT);
        hx_btn.setSpacing(5);
        hx_rull.setSpacing(5);
        gpane.setHgap(5);
        gpane.setVgap(5);
    }
    
    public void show(){
        initControl();
        setControl();
        setEvent();
        setStyleControl();
        
        Scene scene = new Scene(vx_content);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Добавление нового пользователя");
        stage.show();
    }
}
