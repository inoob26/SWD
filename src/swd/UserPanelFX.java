package swd;

import swd.documents.CreatePDF;
import java.sql.SQLException;
import java.util.List;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import swd.DAO.Factory;
import swd.bank.BankFX;
import swd.documents.DocumentFX;
import swd.firm.AddFirmFX;
import swd.firm.DelFirmFX;
import swd.firm.EditFirmFX;
import swd.logic.Firm;
import swd.service.ServiceFX;
import swd.users.UsersFX;

public class UserPanelFX {
    EditFirmFX editFirm; 
    DelFirmFX delFirm;
    DocumentFX docs;
    Stage stage;
    
    VBox vbox;
    
    VBox vx_content;
    HBox hbox;
    
    ToolBar tbar_menu; 
    
    //firm table 
    TableView<Firm> tv_firm;
        
    Button btn_add_firm;
    Button btn_edit_firm;
    //Button btn_del_firm;
    Button btn_bank;
    Button btn_service;
    //Button btn_users;
    Button btn_docs;
    //Button btn_invoice;
    //Button btn_report;
    
    Button btn_search;
    
    Image img_add_firm;
    Image img_edit_firm;
    //Image img_del_firm;
    Image img_bank;
    Image img_service;
    //Image img_users;
    Image img_docs;
    
    //CreatePDF pdf;
    
    public UserPanelFX(Stage st){
        stage = st;
    }
    
    public void initControls(){
        //Инициализация макета компонентов
        vbox = new VBox();
        vx_content = new VBox();
        hbox = new HBox();
        //hx_search = new HBox();
        
        //Инициализация компонентов
        
        ToolBar tbar_menu;

        tv_firm = new TableView<Firm>();
        
        btn_add_firm = new Button("Добавить");
        btn_edit_firm = new Button("Изменить");
        //btn_del_firm = new Button("удалить фирму");
        btn_bank = new Button("Банки");
        btn_service = new Button("Услуги");
        //btn_users = new Button("Пользователи");
        btn_docs = new Button("Документ");
        //btn_invoice = new Button("тест Счет фактура");
        //btn_report = new Button("тест Акт");
        
        img_add_firm = new Image(getClass().getResourceAsStream("icon/32x32/add_firm32.png"));
        img_edit_firm = new Image(getClass().getResourceAsStream("icon/32x32/edit_firm32.png"));
        img_bank = new Image(getClass().getResourceAsStream("icon/32x32/banks32.png"));
        img_service = new Image(getClass().getResourceAsStream("icon/32x32/service32.png"));
        img_docs = new Image(getClass().getResourceAsStream("icon/32x32/docs32.png"));
        
        //pdf = new CreatePDF();
    }
    
    public void setControl(){       
        btn_edit_firm.setDisable(true);
        //btn_del_firm.setDisable(true);
        hbox.getChildren().addAll(btn_add_firm,btn_edit_firm,btn_docs,btn_bank,btn_service);
        
        tbar_menu = new ToolBar(hbox);
        
        tv_firm.getColumns().addAll(Firm.getColumn(tv_firm));
        
        vx_content.getChildren().addAll(/*hx_search*/tv_firm);

        vbox.getChildren().addAll(tbar_menu,vx_content);
        
    }
    
    private void showAddFirm(){
        //событие на добавление фирмы
        Stage stage_add_firm = new Stage();
        AddFirmFX add_firm = new AddFirmFX(stage_add_firm,stage,(byte) 2);
        
        add_firm.show();
    }
    
    private void showService() throws SQLException{
        Stage st_service = new Stage();
        ServiceFX sc = new ServiceFX(st_service);
        sc.show();
    }
    
    private void createEditFirm(){
        Stage edit_st = new Stage();
        editFirm = new EditFirmFX(edit_st, stage, 
            tv_firm.getSelectionModel().getSelectedItem().getId(),
            tv_firm.getSelectionModel().getSelectedItem().getName(),
            tv_firm.getSelectionModel().getSelectedItem().getPhone(),
            tv_firm.getSelectionModel().getSelectedItem().getAddress(),
            tv_firm.getSelectionModel().getSelectedItem().getContract(),
            tv_firm.getSelectionModel().getSelectedItem().getRull(),
            tv_firm.getSelectionModel().getSelectedItem().getBin_iin(),
            tv_firm.getSelectionModel().getSelectedItem().getIik(),
            tv_firm.getSelectionModel().getSelectedItem().getBank_id(),
            (byte) 2
        );
    }
    
    private void createDelFirm(){
        Stage del_st = new Stage();
                                
        delFirm = new DelFirmFX(del_st, stage,
            tv_firm.getSelectionModel().getSelectedItem().getId(),
            tv_firm.getSelectionModel().getSelectedItem().getName(),
            (byte) 2
        );
    }
    
    private void createDocs(){
        Stage st = new Stage();
        docs = new DocumentFX(st,
            tv_firm.getSelectionModel().getSelectedItem().getId(),
            tv_firm.getSelectionModel().getSelectedItem().getName()
        );
    }
    
    private void addFirmIntoTV() throws SQLException{
        try{
            List<Firm> firms = Factory.getInstance().getFirmDAO().getAllFirms();
            ObservableList<Firm> oListFirm = FXCollections.observableList(firms);
            
            tv_firm.setItems(oListFirm);
            
        } catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Ошибка I/O", JOptionPane.OK_OPTION);
        }
    }
    
    private void showBanks() throws SQLException{
        Stage st_bank = new Stage();
        BankFX bank = new BankFX(st_bank);
        bank.show();
    }
    
    private void setEvent(){
        tv_firm.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                if(event.getClickCount() >= 1){
                    if(tv_firm.getSelectionModel().getSelectedIndex() >= 0){
                        createEditFirm();
                        createDelFirm();                        
                        createDocs();                        
                        btn_edit_firm.setDisable(false);
                        btn_docs.setDisable(false);
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
        
        btn_docs.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                docs.show();
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
        
        /*
        btn_del_firm.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                try{
                    delFirm.show();
                }catch(Exception e){
                }      
            }
        });*/
    }
    
    private void setStyleControl(){
        btn_docs.setDisable(true);
        hbox.setSpacing(5);
        //hx_search.setSpacing(5);
        vx_content.setSpacing(5);
        vx_content.setPadding(new Insets(10));
        
        tv_firm.setMaxWidth(700);
        tv_firm.setMinWidth(700);
        
        btn_add_firm.setGraphic(new ImageView(img_add_firm));
        btn_edit_firm.setGraphic(new ImageView(img_edit_firm));
        btn_bank.setGraphic(new ImageView(img_bank));
        btn_docs.setGraphic(new ImageView(img_docs));
        btn_service.setGraphic(new ImageView(img_service));
        
        btn_add_firm.setMinHeight(40);
        btn_edit_firm.setMinHeight(40);
        btn_bank.setMinHeight(40);
        btn_docs.setMinHeight(40);
        btn_service.setMinHeight(40);
    }
    
    public void show(){
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
            stage.initModality(Modality.APPLICATION_MODAL);
            
            stage.show();
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
