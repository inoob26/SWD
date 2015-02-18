package swd.firm;

import java.sql.SQLException;
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
import swd.documents.DocumentFX;
import swd.logic.Firm;

public class FirmFX {
    EditFirmFX editFirm; 
    DelFirmFX delFirm;
    DocumentFX docs;
    
    Stage stage;
    VBox vx_content;
    HBox hx_btn;
    
    TableView<Firm> tv_firm;
    
    Button btn_add;
    Button btn_edit;
    Button btn_del;
    Button btn_docs;
    Button btn_cancel;
    
    Image img_add;
    Image img_edit;
    Image img_del;
    Image img_docs;
    
    public FirmFX(Stage st){
        stage = st;
    }
    
    private void initControl(){
        vx_content = new VBox();
        hx_btn = new HBox();
        
        tv_firm = new TableView<Firm>();
        
        btn_add = new Button("Добавить");
        btn_edit = new Button("Изменить");
        btn_del = new Button("Удалить");
        btn_docs = new Button("Документ");
        btn_cancel = new Button("Зактрыть");
        
        img_add = new Image(getClass().getResourceAsStream("add_firm32.png"));
        img_edit = new Image(getClass().getResourceAsStream("edit_firm32.png"));
        img_del = new Image(getClass().getResourceAsStream("del_firm32.png"));
        img_docs = new Image(getClass().getResourceAsStream("docs32.png"));
    }
    
    private void setControl() throws SQLException{
        try{
            tv_firm.getColumns().addAll(Firm.getColumn(tv_firm));
            addFirmIntoTV();

            hx_btn.getChildren().addAll(btn_add,btn_edit,btn_del,btn_docs);
            vx_content.getChildren().addAll(tv_firm,hx_btn,btn_cancel);
        } catch(Exception ex){
            ex.printStackTrace();
        }   
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
            (byte) 1
        );
    }
    
    private void createDelFirm(){
        Stage del_st = new Stage();
                                
        delFirm = new DelFirmFX(del_st, stage,
            tv_firm.getSelectionModel().getSelectedItem().getId(),
            tv_firm.getSelectionModel().getSelectedItem().getName(),
            (byte) 1
        );
    }
    
    private void createDocs(){
        Stage st = new Stage();
        docs = new DocumentFX(st,
            tv_firm.getSelectionModel().getSelectedItem().getId(),
            tv_firm.getSelectionModel().getSelectedItem().getName()
        );
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
                        btn_edit.setDisable(false);
                        btn_del.setDisable(false);
                        btn_docs.setDisable(false);
                    }
                }
            }
        });
        
        btn_add.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                Stage st = new Stage();
                AddFirmFX add = new AddFirmFX(st, stage,(byte) 1);
                add.show();
            }
        });
        
        btn_edit.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                editFirm.show();
            }
        });
        
        btn_del.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                delFirm.show();
            }
        });
        
        btn_docs.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                docs.show();
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
        vx_content.setAlignment(Pos.CENTER_RIGHT);
        vx_content.setSpacing(5);
        vx_content.setPadding(new Insets(10));
        
        hx_btn.setSpacing(5);
        
        btn_edit.setDisable(true);
        btn_del.setDisable(true);
        btn_docs.setDisable(true);
        
        btn_add.setGraphic(new ImageView(img_add));
        btn_edit.setGraphic(new ImageView(img_edit));
        btn_del.setGraphic(new ImageView(img_del));
        btn_docs.setGraphic(new ImageView(img_docs));
    }
    
    public void show() throws SQLException{
        try{
            initControl();
            setControl();
            setEvent();
            setStyleControl();

            Scene scene = new Scene(vx_content);
            stage.setScene(scene);
            stage.setTitle("Список огранизаций");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.show();
        } catch(Exception ex){
            ex.printStackTrace();
        }
        
    }
}
