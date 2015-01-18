package swd.documents;

import com.itextpdf.text.DocumentException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import swd.DAO.Factory;
import swd.logic.Invoice;
import swd.logic.S_I;
import swd.logic.Service;

public class DocumentFX {
    private Long firm_id;
    private String firm_name;
    private LocalDate ld;
    
    private short service_inx;
    
    private Stage stage;
    private VBox vx_content;
    private GridPane gpane;
    private HBox hx_btn;
    
    private DatePicker dp;
    
    private ChoiceBox chbx_count_name;
    private ChoiceBox chbx_name_service;
    
    private TextField tf_count;
    private TextField tf_sum;
    private Label lbl_sum;
    
    private Button btn_result;
    private Button btn_print;
    private Button btn_close;
    
    private CreatePDF pdf;
    
    public DocumentFX(Stage st,Long f_id, String f_n){
        stage = st;
        firm_id = f_id;
        firm_name = f_n;
    }
    
    private void initControl(){
        vx_content = new VBox();
        gpane = new GridPane();
        hx_btn = new HBox();
        
        dp = new DatePicker();
        
        chbx_count_name = new ChoiceBox();
        chbx_name_service = new ChoiceBox();
        
        tf_count = new TextField();
        tf_sum = new TextField();
        lbl_sum = new Label();
        
        btn_result = new Button("Подсчет");
        btn_print = new Button("Сформировать");
        btn_close = new Button("Отмена");
    }
    
    private void setControl(){
        gpane.add(new Label("Дата: "), 0, 0);
        gpane.add(dp, 1, 0);
        addServiceIntoCB();
        gpane.add(new Label("Наименова услуг: "), 0, 1);
        gpane.add(chbx_name_service, 1, 1);
        gpane.add(new Label("Кол-во оказаных услуг: "), 0 , 2);
        gpane.add(tf_count, 1, 2);
        gpane.add(btn_result, 2, 2);
        
        gpane.add(new Label("Всего по счету: "), 0, 3);
        gpane.add(lbl_sum, 1, 3);
        
        gpane.add(new Label("Всего по счету прописью: "), 0, 4);
        gpane.add(tf_sum, 1, 4);
        
        hx_btn.getChildren().addAll(btn_print,btn_close);
        vx_content.getChildren().addAll(gpane,hx_btn);
    }
    
    private void addServiceIntoCB(){
        try{
            List<Service> all_service = Factory.getInstance().getServiceDAO().getAllServicesName();
            ObservableList<Service> oListService = FXCollections.observableList(all_service);
            
            chbx_name_service.setItems(oListService);            
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    private void print() throws SQLException, DocumentException, IOException{
        try{
            //Счет фактура
            Invoice inv = new Invoice();
            
            Instant instant = Instant.from(ld.atStartOfDay(ZoneId.of("GMT")));
            Date date = Date.from(instant);
            
            inv.setDate(date);
            Factory.getInstance().getInvoiceDAO().addInvoice(inv);
            
            S_I si = new S_I();
            si.setId_s(service_inx);
            si.setId_f(firm_id);
            si.setId_i(inv.getId());
            
            si.setCount(Short.parseShort(tf_count.getText()));
            
            Factory.getInstance().getS_IDAO().addS_I(si);
            
            pdf = new CreatePDF(firm_id,si,inv.getId(),si.getId_s(),tf_sum.getText().toString()); 
            pdf.make_invoice();
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    private void getResult() throws SQLException{
        try{
            String count = tf_count.getText();
            Service srv = Factory.getInstance().getServiceDAO().getServiceById(service_inx);
            float cost = srv.getCost();
            float result = cost * Float.parseFloat(count);
            lbl_sum = new Label(String.valueOf(result));
            gpane.add(lbl_sum, 1, 3);
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    private void setEvent(){
        btn_result.setOnAction((ActionEvent event) -> {
            try {
                getResult();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
                
        btn_print.setOnAction((ActionEvent event) -> {
            try {
                print();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        
        dp.setOnAction((ActionEvent event) -> {
            try{
                ld = dp.getValue();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        });
        
        chbx_name_service.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                try {
                    service_inx = newValue.shortValue();
                    service_inx++;
                    System.out.println(service_inx);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                System.out.println(newValue.shortValue());
            }
        });
        
        btn_close.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                try{
                    stage.close();
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });
    }
    
    
    private void setStyleControl(){
        gpane.setPadding(new Insets(5));
        gpane.setHgap(5);
        gpane.setVgap(5);
        hx_btn.setSpacing(5);
        hx_btn.setAlignment(Pos.BASELINE_RIGHT);
        vx_content.setSpacing(5);
        vx_content.setPadding(new Insets(15));
    }
    
    public void show(){
        initControl();
        setControl();
        setStyleControl();
        setEvent();
        
        Scene scene = new Scene(vx_content);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Выписка документа");
        stage.show();
    }
}
