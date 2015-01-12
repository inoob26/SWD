package swd.users;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import swd.DAO.Factory;
import swd.logic.Users;

public class UsersFX {
    private Long id;
    private String login;
    private String password;
    private String flag;
    
    
    Stage stage;
    
    TableView<Users> tv_users;
    
    VBox vx_content;
    HBox hx_btn;
    
    Button btn_add;
    Button btn_edit;
    Button btn_del;
    Button btn_cancel;
    
    Image img_add;
    Image img_edit;
    Image img_del;
    
    public UsersFX(Stage st){
        stage = st;
    }
    
    public void initControls(){
        vx_content = new VBox();
        hx_btn = new HBox();
        
        tv_users = new TableView<Users>();
        
        btn_add = new Button("Добавить");
        btn_edit = new Button("Изменить");
        btn_del = new Button("Удалить");
        btn_cancel = new Button("Закрыть");
        
        img_add = new Image(getClass().getResourceAsStream("add_user32.png"));
        img_edit = new Image(getClass().getResourceAsStream("edit_user32.png"));
        img_del = new Image(getClass().getResourceAsStream("del_user32.png"));
    }
    
    public void setControl(){
        vx_content.getChildren().addAll(tv_users,hx_btn,btn_cancel);
        hx_btn.getChildren().addAll(btn_add,btn_edit,btn_del);
        
        addItems();
        tv_users.getColumns().addAll(Users.getColumn(tv_users));
    }
    
    public void addItems(){
        try{
            List<Users> users = Factory.getInstance().getUserDAO().getAllUsers();
            ObservableList<Users> oListUsers = FXCollections.observableList(users);
            
            tv_users.setItems(oListUsers);
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Ошибка I/O", JOptionPane.OK_OPTION);
        }
    }
    
    public void setEvent(){
        btn_add.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                try{
                    Stage st_add_usr = new Stage();
                    AddUsersFX add_usr = new AddUsersFX(st_add_usr,stage);
                    add_usr.show();
                } catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        
        tv_users.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                if(event.getClickCount() >= 1){
                    if(tv_users.getSelectionModel().getSelectedIndex() >= 0){
                        id = tv_users.getSelectionModel().getSelectedItem().getId();
                        login = tv_users.getSelectionModel().getSelectedItem().getLogin();
                        password = tv_users.getSelectionModel().getSelectedItem().getPassword();
                        System.out.println(password);
                        flag = tv_users.getSelectionModel().getSelectedItem().getFlag();
                        
                        btn_edit.setDisable(false);
                        btn_del.setDisable(false);
                    }
                }
            }
        });
        
        btn_edit.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                try{
                    Stage st_edit_usr = new Stage();
                    //Stage st, Stage st_p,Long i,String l,String p,byte f_r
                    EditUsersFX edit_usr = new EditUsersFX(st_edit_usr,stage,id,login,password,flag);
                    edit_usr.show();
                } catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        
        btn_del.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                try{
                    Stage st_del_usr = new Stage();
                    DelUsersFX del_usr = new DelUsersFX(st_del_usr,stage,id,login);
                    del_usr.show();
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
    
    public void setStyleControl(){
        vx_content.setAlignment(Pos.CENTER_RIGHT);
        vx_content.setPadding(new Insets(10));
        vx_content.setSpacing(5);
        
        
        hx_btn.setAlignment(Pos.BASELINE_LEFT);
        hx_btn.setSpacing(5);
                
        btn_edit.setDisable(true);
        btn_del.setDisable(true);
        
        btn_add.setGraphic(new ImageView(img_add));
        btn_edit.setGraphic(new ImageView(img_edit));
        btn_del.setGraphic(new ImageView(img_del));
    }
    
    public void show(){
        try{
            initControls();
            setControl();
            setEvent();
            setStyleControl();
            
            Scene scene = new Scene(vx_content);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setScene(scene);
            stage.setTitle("Пользователи");
            stage.setResizable(false);
            stage.show();
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
