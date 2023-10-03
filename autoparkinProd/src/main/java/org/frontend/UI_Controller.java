package org.frontend;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class UI_Controller implements Initializable {
    @FXML
    private Button sub_car_btn, car_find_btn, car_out_btn;
    @FXML
    private TextField surname_box, phone_num_box, car_brand_box, car_model_box, car_num_box;
    @FXML
    private ChoiceBox<String> find_drop_chbx, find_take_chbx;
    private final String[] find_drop_strng = {"Surname", "Car Brand", "Car model", "Licence plate"};
    private final String[] find_take_strng = {"Surname", "Parking Spot"};

    @FXML
    private TableView table_info;

    @FXML
    private TableColumn surname_col, car_brand_col, car_model_col, car_num_col, date_of_park_col, spot_num_col;

    public class owner_info{
        private String surname = null;
        private int phonenumber = 0;
        private String brand_of_car = null;
        private String model_of_car = null;
        private String plate_number = null;
        private int place_ID = 0;
    }
    owner_info info = new owner_info();
    public void sub_car(ActionEvent actionEvent) {
        info.surname = surname_box.getText();
        info.phonenumber = Integer.parseInt(phone_num_box.getText());
        info.brand_of_car = car_brand_box.getText();
        info.model_of_car = car_model_box.getText();
        info.plate_number = car_num_box.getText();

        if(info.surname.contains(" ")) System.out.println("GAY");


        sub_car_btn.setText("Submited");
    }
    public void find_car(ActionEvent actionEvent) {
        car_find_btn.setText("Trying to find");
        String drop_to_table = find_drop_chbx.getValue();
        System.out.println(drop_to_table);
    }
    public void out_car(ActionEvent actionEvent) {
        car_out_btn.setText("Proceed");
        String take_the_car = find_take_chbx.getValue();
        System.out.println(take_the_car);
    }


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        find_drop_chbx.getItems().addAll(find_drop_strng);
        find_take_chbx.getItems().addAll(find_take_strng);
    }
}