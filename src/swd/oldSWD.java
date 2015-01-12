/*package swd;

import java.sql.SQLException;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import swd.DAO.Factory;
import swd.bank.BankFX;
import swd.documents.Document;
import swd.firm.*;
import swd.logic.Firm;
import swd.service.ServiceFX;
import swd.users.UsersFX;

public class SWD extends Application {
    AuthorizationFX autoriz;
    EditFirmFX editFirm; 
    DelFirmFX delFirm;
    Stage stage;
    
    VBox vbox;
    
    VBox vx_content;
    HBox hbox;
    HBox hx_search;
    
    ToolBar tbar_menu;

    TextField tf_search;
    
    //firm table 
    TableView<Firm> tv_firm;
        
    Button btn_add_firm;
    Button btn_edit_firm;
    Button btn_del_firm;
    Button btn_bank;
    Button btn_service;
    Button btn_users;
    Button btn_docs;
    Button btn_invoice;
    Button btn_report;
    
    Button btn_search;
    
    Image img_add_firm;
    Image img_edit_firm;
    
    CreatePDF pdf;
    
    public void initControls(){
        //Инициализация макета компонентов
        vbox = new VBox();
        vx_content = new VBox();
        hbox = new HBox();
        hx_search = new HBox();
        
        //Инициализация компонентов
        
        ToolBar tbar_menu;

        tf_search = new TextField();

        tv_firm = new TableView<Firm>();
        
        btn_add_firm = new Button();
        btn_edit_firm = new Button();
        btn_del_firm = new Button("удалить фирму");
        btn_search = new Button("Найти");
        btn_bank = new Button("Банки");
        btn_service = new Button("Услуги");
        btn_users = new Button("Пользователи");
        btn_docs = new Button("Документ");
        btn_invoice = new Button("тест Счет фактура");
        btn_report = new Button("тест Акт");
        
        img_add_firm = new Image(getClass().getResourceAsStream("add.png"));
        img_edit_firm = new Image(getClass().getResourceAsStream("edit.png"));
        
        pdf = new CreatePDF();
    }
    
    public void showAutoriz() throws SQLException{
        try{
            Stage st_a = new Stage(); 
            autoriz = new AuthorizationFX(st_a);
            autoriz.show();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
    }
    
    public void showAddFirm(){
        //событие на добавление фирмы
        Stage stage_add_firm = new Stage();
        AddFirmFX add_firm = new AddFirmFX(stage_add_firm,stage);
        
        add_firm.show();
    }
    
    public void showService() throws SQLException{
        Stage st_service = new Stage();
        ServiceFX sc = new ServiceFX(st_service);
        sc.show();
    }
    
    public void showDocument(){
        Stage st_doc = new Stage();
        Document doc = new Document(st_doc);
        doc.show();
    }
    
    
    public void addFirmIntoTV() throws SQLException{
        try{
            List<Firm> firms = Factory.getInstance().getFirmDAO().getAllFirms();
            ObservableList<Firm> oListFirm = FXCollections.observableList(firms);
            
            tv_firm.setItems(oListFirm);
            
        } catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Ошибка I/O", JOptionPane.OK_OPTION);
        }
    }
    
    public void showBanks() throws SQLException{
        Stage st_bank = new Stage();
        BankFX bank = new BankFX(st_bank);
        bank.show();
    }
    
    public void setControl(){       
        btn_edit_firm.setDisable(true);
        btn_del_firm.setDisable(true);
        hbox.getChildren().addAll(btn_add_firm,btn_edit_firm,btn_del_firm,btn_bank,btn_service,btn_users,btn_docs,btn_invoice,btn_report);
        
        tbar_menu = new ToolBar(hbox);
        
        tv_firm.getColumns().addAll(Firm.getColumn(tv_firm));

        hx_search.getChildren().addAll(tf_search,btn_search);
        
        vx_content.getChildren().addAll(hx_search,tv_firm);

        vbox.getChildren().addAll(tbar_menu,vx_content);
        
    }

    public void setEvent(){
        tv_firm.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                if(event.getClickCount() >= 1){
                    if(tv_firm.getSelectionModel().getSelectedIndex() >= 0){
                        Stage edit_st = new Stage();
                        editFirm = new EditFirmFX(edit_st, stage, 
                        tv_firm.getSelectionModel().getSelectedItem().getId(),
                        tv_firm.getSelectionModel().getSelectedItem().getName(),
                        tv_firm.getSelectionModel().getSelectedItem().getPhone(),
                        tv_firm.getSelectionModel().getSelectedItem().getAddress(),
                        tv_firm.getSelectionModel().getSelectedItem().getContract(),
                        tv_firm.getSelectionModel().getSelectedItem().getRule_id(),
                        tv_firm.getSelectionModel().getSelectedItem().getBin_iin(),
                        tv_firm.getSelectionModel().getSelectedItem().getIik(),
                        tv_firm.getSelectionModel().getSelectedItem().getBank_id());
                                             
                        Stage del_st = new Stage();
                                
                        delFirm = new DelFirmFX(del_st, stage,
                        tv_firm.getSelectionModel().getSelectedItem().getId(),
                        tv_firm.getSelectionModel().getSelectedItem().getName()
                        );
                        
                        btn_edit_firm.setDisable(false);
                        btn_del_firm.setDisable(false);
                    }
                }
            }
        });
        
        btn_add_firm.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                try{
                    showAddFirm();
                }catch(Exception e){
                    e.printStackTrace();
                }      
            }
        });
        
        btn_bank.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                try{
                    showBanks();
                }catch(Exception e){
                    e.printStackTrace();
                }      
            }
        });
        
        btn_service.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                try{
                    showService();
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        
        btn_users.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                try{
                    Stage st_usr = new Stage();
                    UsersFX usr = new UsersFX(st_usr);
                    usr.show();
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        
        btn_docs.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent event){
                    try{
                        showDocument();
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }
                }
            });
                
        btn_invoice.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent event){
                    try{
                        pdf.make_invoice();
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }
                }
            });
                
        btn_report.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                try{
                    pdf.make_report();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

        btn_edit_firm.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                try{
                    editFirm.show();
                }catch(Exception e){
                }      
            }
        });
        
        btn_del_firm.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                try{
                    delFirm.show();
                }catch(Exception e){
                }      
            }
        });
    }
    
    public void setStyleControl(){
        hbox.setSpacing(5);
        hx_search.setSpacing(5);
        vx_content.setSpacing(5);
        vx_content.setPadding(new Insets(10));
        
        tf_search.setMinWidth(300);
        
        tv_firm.setMaxWidth(700);
        tv_firm.setMinWidth(700);
        
        btn_add_firm.setGraphic(new ImageView(img_add_firm));
        btn_edit_firm.setGraphic(new ImageView(img_edit_firm));
    }
    
    public void show() throws SQLException{
        try{
            initControls();
            setEvent();
            setControl();
            setStyleControl();
            addFirmIntoTV();
            
            Scene scene = new Scene(vbox);
            stage.setTitle("Автоматизированая Система Документооборота");
            stage.setScene(scene);
            stage.setResizable(false);
            
            stage.show();
        } catch(Exception ex){
            ex.printStackTrace();
        }
        
    }
    
    @Override
    public void start(Stage st) {
        try {
            stage = st;
            showAutoriz();
            
            initControls();
            setEvent();
            setControl();
            setStyleControl();
            addFirmIntoTV();
            
            
            
            Scene scene = new Scene(vbox);
            stage.setTitle("Автоматизированая Система Документооборота");
            stage.setScene(scene);
            stage.setResizable(false);
            
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args){
        launch(args);
    }   
}*/