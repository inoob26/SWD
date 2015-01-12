package swd;

import java.sql.SQLException;
import java.util.List;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import swd.DAO.Factory;
import swd.logic.Users;

public class SWD extends Application {
    private byte rull_flag;
    
    Stage stage;
    
    VBox vx_content;
    HBox hx_btn;
    GridPane gpane;
    
    TextField tf_login;
    PasswordField tf_password;
    
    Button btn_sig_in;
    Button btn_cancel;
    
    private void initControl(){
        vx_content = new VBox();
        hx_btn = new HBox();
        gpane = new GridPane();
        
        tf_login = new TextField();
        tf_password = new PasswordField();
        
        btn_sig_in = new Button("Вход в систему");
        btn_cancel = new Button("Закрыть");
    }
    
    private void setControl(){
        btn_sig_in.setDisable(true);
        gpane.add(new Label("Имя пользователя: "), 0, 0);
        gpane.add(tf_login, 1, 0);
        
        gpane.add(new Label("Пароль: "), 0, 1);
        gpane.add(tf_password, 1, 1);
        
        hx_btn.getChildren().addAll(btn_sig_in,btn_cancel);
        
        vx_content.getChildren().addAll(gpane,hx_btn);
    }
    
    private void checkRull(byte flag){
        Stage st = new Stage();
        if(flag == 1){    
            AdminPanelFX admin_pane = new AdminPanelFX(st);
            admin_pane.show();
        }
        if(flag == 2){
            UserPanelFX usr_pane = new UserPanelFX(st);
            usr_pane.show();
        }
    }
    
    private void checkTF(String text){
        if(text.isEmpty()){
            btn_sig_in.setDisable(true);
        } else {
            btn_sig_in.setDisable(false);
        }
    }
    
    private boolean checkPasswordEqual(String pass1,String pass2){
        if(pass1 == null ? pass2 == null : pass1.equals(pass2)){
            return true;
        } else {
            return false;
        }
    }
    
    private void checkUsers(String user) throws SQLException{
        List<Users> usr = Factory.getInstance().getUserDAO().getAllUsers();
             
        String pass1;
        String pass2;
        boolean flag_pass;
        for(int i=0;i<usr.size();i++){
            if(usr.get(i).getLogin().equals(user)){
                pass1 = tf_password.getText();
                pass2 = usr.get(i).getPassword();
                
                System.out.println();
                
                flag_pass = checkPasswordEqual(pass1,pass2);
                
                if(flag_pass == true){
                    if(usr.get(i).getFlag().equals("admin")){
                    rull_flag = 1;
                    }
                    if(usr.get(i).getFlag().equals("user")) {
                        rull_flag = 2;
                    }
                } else{
                    Stage st = new Stage();
                    Message msg = new Message(st,"Ошибка логина или пароля!");
                    rull_flag = 0;
                    msg.showErrorMessageDialog();
                }
            }
        }
    }
    
    private void setEvent() throws SQLException{
        tf_password.setOnKeyTyped(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                checkTF(tf_password.getText());
            }
        
        });
        
        btn_sig_in.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                try{
                    String lg = tf_login.getText();
                    
                    checkUsers(lg);
                    
                    stage.close();
                    
                    checkRull(rull_flag);
                    
                } catch(Exception ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Неверный логин или пароль", JOptionPane.OK_OPTION);
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
    
    private void setStyleControl(){
        hx_btn.setAlignment(Pos.BASELINE_CENTER);
        gpane.setVgap(5);
        gpane.setHgap(5);
        vx_content.setSpacing(5);
        vx_content.setPadding(new Insets(10));
        hx_btn.setSpacing(5);
    }
    
    @Override
    public void start(Stage st) {
        try {
            stage = st;
            
            initControl();
            setControl();
            setEvent();
            setStyleControl();

            Scene scene = new Scene(vx_content);
            //stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Вход в систему");
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args){
        launch(args);
    }
}