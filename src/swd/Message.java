package swd;

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

public class Message {
    Stage stage;
    VBox vx_content;
    HBox hx_btn;
    Label lb;
    
    String message;
    
    final Button OK = new Button("Ok");
    final Button Close = new Button("Закрыть");
    
    public Message(Stage st,String msg){
        stage = st;
        message = msg;
    }
    
    private void initControlError(){
        vx_content = new VBox();
        hx_btn = new HBox();
    }
    
    private void setControlError(){
        hx_btn.getChildren().addAll(OK);
        lb = new Label(message);
        vx_content.getChildren().addAll(lb,hx_btn);
    }
    
    private void setStyleError(){
        lb.setAlignment(Pos.BASELINE_CENTER);
        vx_content.setSpacing(5);
        vx_content.setPadding(new Insets(10));
        hx_btn.setAlignment(Pos.BASELINE_CENTER);
        hx_btn.setSpacing(5);
    }
    
    private void setEventError(){
        OK.setOnAction((ActionEvent event) -> {
            stage.close();
        });
    }
    
    public void showErrorMessageDialog(){
        initControlError();
        setControlError();
        setStyleError();
        setEventError();
        
        Scene scene = new Scene(vx_content);
        stage.setScene(scene);
        stage.setTitle("Ошибка");
        stage.setResizable(false);
        stage.show();
    }
}
