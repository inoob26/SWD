package swd.bank;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import swd.DAO.Factory;

import swd.logic.Bank;

public class BankFX {
    short id;
    String bank_name;
    String bank_bik;
    
    Stage stage;
    VBox vx_content;
    HBox hx_btn;
    
    TableView<Bank> tv_bank;
    
    Button btn_add;
    Button btn_edit;
    Button btn_del;
    Button btn_cancel;
    
    Image img_add;
    Image img_edit;
    Image img_del;
    
    public BankFX(Stage st){
        stage = st;
    }
    
    private void initControl(){
        vx_content = new VBox();
        hx_btn = new HBox();
        
        tv_bank = new TableView<Bank>();
        
        btn_add = new Button("Добавить");
        btn_edit = new Button("Изменить");
        btn_del = new Button("Удалить");
        
        btn_cancel = new Button("Закрыть");
        
        img_add = new Image(getClass().getResourceAsStream("add_bank32.png"));
        img_edit = new Image(getClass().getResourceAsStream("edit_bank32.png"));
        img_del = new Image(getClass().getResourceAsStream("del_bank32.png"));
    }
    
    private void addBankIntoTV() throws SQLException{
        try{
            List<Bank> banks = Factory.getInstance().getBankDAO().getAllBanks();
            ObservableList<Bank> oListsBank = FXCollections.observableList(banks);
            
            tv_bank.setItems(oListsBank);
            
        } catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Ошибка I/O", JOptionPane.OK_OPTION);
        }
    }
    
    private void setControl() throws SQLException{
        vx_content.setAlignment(Pos.CENTER_RIGHT);
        vx_content.getChildren().addAll(tv_bank,hx_btn,btn_cancel);
        
        hx_btn.getChildren().addAll(btn_add,btn_edit,btn_del);
        
        tv_bank.getColumns().addAll(Bank.getColumn(tv_bank));
        
        addBankIntoTV();
    }
    
    private void setEvent(){
        tv_bank.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                if(event.getClickCount() >= 1){
                    if(tv_bank.getSelectionModel().getSelectedIndex() >= 0){
                        id = tv_bank.getSelectionModel().getSelectedItem().getId();
                        bank_name = tv_bank.getSelectionModel().getSelectedItem().getName();
                        bank_bik = tv_bank.getSelectionModel().getSelectedItem().getBik();
                        
                        btn_edit.setDisable(false);
                        btn_del.setDisable(false);
                    }
                }
            }
        });
        
        btn_add.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                try{
                    Stage st_add = new Stage();
                    AddBankFX addfx = new AddBankFX(st_add,stage);
                    addfx.show();
                } catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        
        btn_edit.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                Stage st_edit = new Stage();
                EditBankFX ed = new EditBankFX(st_edit,stage,id,bank_name,bank_bik);
                try {
                    ed.show();
                } catch (SQLException ex) {
                    Logger.getLogger(BankFX.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        btn_del.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                Stage st_del = new Stage();
                DeleteBankFX del = new DeleteBankFX(st_del,stage,id,bank_name);
                try {
                    del.show();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        btn_cancel.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                try{
                    stage.close();
                } catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
    }
    
    private void setStyle(){
        vx_content.setPadding(new Insets(10));
        vx_content.setSpacing(5);
        hx_btn.setSpacing(5);
        
        btn_edit.setDisable(true);
        btn_del.setDisable(true);
        
        btn_add.setGraphic(new ImageView(img_add));
        btn_edit.setGraphic(new ImageView(img_edit));
        btn_del.setGraphic(new ImageView(img_del));
    }
    
    public void show() throws SQLException{
        initControl();
        setControl();
        setEvent();
        setStyle();
        
        Scene scene = new Scene(vx_content);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(scene);
        stage.setTitle("Список Банков");
        stage.setResizable(false);
        stage.show();
    }
}