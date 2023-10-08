package org.frontend;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import java.net.URL;
import java.util.ResourceBundle;

public class UI_Controller implements Initializable {
    @FXML
    private Button sub_car_button, car_find_button, car_out_button, test;
    @FXML
    private TextField surname_field, phone_num_field, car_brand_field, car_model_field, car_num_field;
    @FXML
    private ChoiceBox<String> find_drop_choice_box, find_take_choice_box1, find_take_choice_box2;
    private final String[] find_drop_string = {"Surname", "Car Brand", "Car Model", "Licence plate"};
    private final String[] find_take_string = {"Surname", "Parking Spot"};
    private final String[] car_brands_string = {"Acura", "Alfa Romeo", "Aston Martin", "Audi", "BMW", "Bentley",
            "Bugatti", "Buick", "Cadillac", "Chevrolet", "Chrysler", "Ferrari", "Fiat", "Ford", "GMC", "Genesis",
            "Honda", "Hyundai", "Infiniti", "Jaguar", "Jeep", "Kia", "Lamborghini", "Land Rover", "Lexus", "Maserati",
            "Mazda", "Mercedes-Benz", "Mini", "Mitsubishi", "Nissan", "Porsche", "Ram", "Rolls-Royce", "Smart",
            "Subaru", "Tesla", "Toyota", "Volkswagen", "Volvo"};


    @FXML
    private Label warn1, warn2, warn3, warn4, warn5;

    @FXML
    private TableView table_info;

    @FXML
    private TableColumn surname_column, car_brand_column, car_model_column,
                        car_num_column, date_of_park_column, spot_num_column;

    public class owner_info{
        private String surname = null;
        private String phone_number = null;
        private String brand_of_car = null;
        private String model_of_car = null;
        private String plate_number = null;
        private int place_ID = 0;
    }
    owner_info info = new owner_info();
    public void sub_car(ActionEvent actionEvent) {
        info.surname = surname_field.getText();
        info.phone_number = phone_num_field.getText();
        info.brand_of_car = car_brand_field.getText();
        info.model_of_car = car_model_field.getText();
        info.plate_number = car_num_field.getText();

        sub_car_button.setText("Submited");
    }
    public void test_out(ActionEvent actionEvent){
        System.out.println(info.surname);
        System.out.println(info.phone_number);
        System.out.println(info.brand_of_car);
        System.out.println(info.model_of_car);
        System.out.println(info.plate_number);
    }
    public void find_car(ActionEvent actionEvent) {
        car_find_button.setText("Trying to find");
        String drop_to_table = find_drop_choice_box.getValue();
        System.out.println(drop_to_table);
    }
    public void out_car(ActionEvent actionEvent) {
        car_out_button.setText("Proceed");
        String take_the_car = find_take_choice_box1.getValue();

        System.out.println(take_the_car);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        find_drop_choice_box.getItems().addAll(find_drop_string);
        find_take_choice_box1.getItems().addAll(find_take_string);

        find_take_choice_box2.setDisable(true);

        find_take_choice_box1.valueProperty().addListener((observable, oldValue, newValue) -> {
            find_take_choice_box2.getItems().clear();
            if (newValue.equals("Surname")) {
                find_take_choice_box2.setDisable(false);
                //Later at finish
            } else {

                find_take_choice_box2.setDisable(false);
                //Later at finish
            }
        });


        surname_field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.contains(" ")) {
                warn1.setOpacity(1.0);
                warn1.setStyle("-fx-text-fill: #ff0000");
                error_check(true);
            } else {
                warn1.setOpacity(0.0);
                error_check(false);
            }
        });

        phone_num_field.textProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue.matches("(\\d{3}-\\d{3}-\\d{2}-\\d{2}|\\d{10})")) {
                warn2.setOpacity(0.0);
                error_check(false);
            } else {
                warn2.setOpacity(1.0);
                warn2.setStyle("-fx-text-fill: #ff0000");
                error_check(true);
            }
        });

        car_brand_field.textProperty().addListener((observable, oldValue, newValue) -> {
            String userInput = car_brand_field.getText();

            for (String brand : car_brands_string) {
                if (userInput.equalsIgnoreCase(brand)){
                    warn3.setOpacity(0.0);
                    error_check(false);
                    break;
                }else {
                    warn3.setOpacity(1.0);
                    warn3.setStyle("-fx-text-fill: #ff0000");
                    error_check(true);
                }
            }
        });

        car_model_field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-Z0-9 .\\-_]*")) {
                warn4.setOpacity(1.0);
                warn4.setStyle("-fx-text-fill: #ff0000");
                error_check(true);
            } else {
                warn4.setOpacity(0.0);
                error_check(false);
            }
        });


        car_num_field.textProperty().addListener((observable, oldValue, newValue) -> {
            String userInput = car_num_field.getText().toUpperCase();

            String plateNumberPattern = "^[A-Z]{2}\\d{4}[A-Z]{2}$";
            if (userInput.matches(plateNumberPattern)) {
                warn5.setOpacity(0.0);
                error_check(false);
            }else {
                warn5.setOpacity(1.0);
                warn5.setStyle("-fx-text-fill: #ff0000");
                error_check(true);
            }
        });

    }
    public void error_check(boolean has_error){
        if(has_error){
            sub_car_button.setOpacity(0.7);
            sub_car_button.setDisable(true);
        }else{
            sub_car_button.setOpacity(1);
            sub_car_button.setDisable(false);
        }
    }
}