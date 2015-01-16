package swd.firm;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PerformerFX {
    private Long id;
    private String name;
    private String director;
    private String phone;
    private String address;
    private String bin_iin;
    private String iik;
    private short bank_id;
    
    Stage st;
    
    VBox vx_content;
    GridPane gpane;
    HBox hx_btn;
    
    TextField tf_name;
    TextField tf_director;
    TextField tf_phone;
    TextField tf_address;
    TextField tf_bin_iin;
    TextField tf_iik;
    TextField tf_bik;
    
    ChoiceBox cb_name_bank;
    
    Button btn_save;
    Button btn_cancel;
    
    private void initControll(){
        vx_content = new VBox();
        gpane = new GridPane();
        hx_btn = new HBox();
        
        tf_name = new TextField();
        tf_director = new TextField();
        tf_phone = new TextField();
        tf_address = new TextField();
        tf_bin_iin = new TextField();
        tf_iik = new TextField();
        tf_bik = new TextField();
        
        cb_name_bank = new ChoiceBox();
        
        btn_save = new Button("Изменить");
        btn_cancel = new Button("Отмена");
    }
    
    private void setControll(){
        gpane.add(new Label("Наименование организации: "), 0, 0);
        gpane.add(tf_name, 1, 0);
        gpane.add(new Label("Директор: "), 0, 1);
        gpane.add(tf_director, 1, 1);
        gpane.add(new Label("Телефон: "), 0, 2);
        gpane.add(tf_phone, 1, 2);
        gpane.add(new Label("БИН/ИИН: "), 0, 3);
        gpane.add(tf_director, 1, 3);
        gpane.add(new Label("БИН/ИИН: "), 0, 4);
        gpane.add(tf_bin_iin, 1, 4);
        gpane.add(new Label("ИИК: "), 0, 5);
        gpane.add(tf_iik, 1, 5);
        gpane.add(new Label("Банк: "), 0, 6);
        gpane.add(cb_name_bank, 1, 6);
        gpane.add(new Label("БИК: "), 0, 5);
        gpane.add(tf_bik, 1, 5);
    }
}
