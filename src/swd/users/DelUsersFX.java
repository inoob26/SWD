package swd.users;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import swd.DAO.Factory;
import swd.logic.Users;

public class DelUsersFX {
    private Long id;
    private String login;
    Stage stage;
    Stage stage_parent;
    
    VBox vx_content;
    HBox hx_btn;
    Button btn_cancel;
    Button btn_del;
    
    public DelUsersFX(Stage st,Stage s_p,Long i,String l){
        stage = st;
        stage_parent = s_p;
        id = i;
        login = l;
    }
    
    public void initControl(){
        vx_content = new VBox();
        hx_btn = new HBox();
        btn_cancel = new Button("Отмена");
        btn_del = new Button("Удалить");
    }
    
    public void setControl(){
        hx_btn.getChildren().addAll(btn_del,btn_cancel);
        vx_content.getChildren().addAll(new Label("Вы действительно хотите удалить: " + login + "?"),hx_btn);
    }
    
    public void setEvent(){
        btn_del.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                try{
                    Users user = Factory.getInstance().getUserDAO().getUserById(id);
                    Factory.getInstance().getUserDAO().deleteUser(user);
                    stage.close();
                    
                    Stage st_usr = new Stage();
                    UsersFX usrfx = new UsersFX(st_usr);
                    stage_parent.close();
                    usrfx.show();
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
        stage.setTitle("Удаление пользователя");
        stage.show();
    }
}
