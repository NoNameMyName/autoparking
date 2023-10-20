package org.frontend;

import org.backend.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import java.io.File;
import javafx.stage.FileChooser;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.scene.control.Alert.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class UI_Controller implements Initializable {
    @FXML
    private Button sub_car_button, car_out_button;
    @FXML
    private TextField surname_field, phone_num_field, car_brand_field, car_model_field, car_num_field, search_field;
    @FXML
    private ChoiceBox<String> find_drop_choice_box, find_take_choice_box1, find_take_choice_box2;
    private final String[] find_drop_string = { "Surname", "Car Brand", "Car Model", "Licence plate" };
    private final String[] find_take_string = { "Surname", "Parking Spot" };
    private final String[] car_brands_string = { "Acura", "Alfa Romeo", "Aston Martin", "Audi", "BMW", "Bentley",
            "Bugatti", "Buick", "Cadillac", "Chevrolet", "Chrysler", "Ferrari", "Fiat", "Ford", "GMC", "Genesis",
            "Honda", "Hyundai", "Infiniti", "Jaguar", "Jeep", "Kia", "Lamborghini", "Land Rover", "Lexus", "Maserati",
            "Mazda", "Mercedes-Benz", "Mini", "Mitsubishi", "Nissan", "Porsche", "Ram", "Rolls-Royce", "Smart",
            "Subaru", "Tesla", "Toyota", "Volkswagen", "Volvo" };

    @FXML
    private Label warn1, warn2, warn3, warn4, warn5;

    @FXML
    private TableView<Car> table;

    @FXML
    private TableColumn<Car, String> surname_column;
    @FXML
    private TableColumn<Car, String> car_brand_column;
    @FXML
    private TableColumn<Car, String> car_model_column;
    @FXML
    private TableColumn<Car, String> car_num_column;
    @FXML
    private TableColumn<Car, String> date_of_park_column;
    @FXML
    private TableColumn<Car, Integer> spot_num_column;

    private Car car;
    private LocalDateTime park_data;
    private ParkingSystem park_spot;
    int place_ID;

    public void sub_car(ActionEvent actionEvent) {
        car = new Car();
        if (place_ID == 20) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("There is no free parking spots");
            alert.showAndWait();
            error_check(true);
            sub_car_button.setText("Please wait. No spots");
        } else {
            error_check(false);
            car.setPersonSurname(surname_field.getText());
            car.setPersonTelephoneNumber(phone_num_field.getText());
            car.setCarMark(car_brand_field.getText());
            car.setCarModel(car_model_field.getText());
            car.setCarNumber(car_num_field.getText());

            Car new_car = new Car(
                    surname_field.getText(),
                    phone_num_field.getText(),
                    car_brand_field.getText(),
                    car_model_field.getText(),
                    car_num_field.getText().toUpperCase()
                    );
                
            cars.add(new_car);

            surname_field.clear();
            phone_num_field.clear();
            car_brand_field.clear();
            car_model_field.clear();
            car_num_field.clear();
            sub_car_button.setText("Submitted");
            warn2.setOpacity(0.0);
            warn3.setOpacity(0.0);
            warn5.setOpacity(0.0);
        }

    }

    ObservableList<Car> cars = FXCollections.observableArrayList();

    public void addCarIn(ActionEvent actionEvent) {
        Car old_car = new Car("Savluk", "0991599936", "Mazda", "Miata", "AE6942KI");
        table.getItems().add(old_car);
    }

    public void out_car(ActionEvent actionEvent) {
        car_out_button.setText("Proceed");

    }

    private int poss;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        table.getColumns().clear();

        {
            surname_column.setCellValueFactory(new PropertyValueFactory<>("personSurname"));
            car_brand_column.setCellValueFactory(new PropertyValueFactory<>("carMark"));
            car_model_column.setCellValueFactory(new PropertyValueFactory<>("carModel"));
            car_num_column.setCellValueFactory(new PropertyValueFactory<>("carNumber"));
            date_of_park_column.setCellValueFactory(new PropertyValueFactory<>("park_data"));
            spot_num_column.setCellValueFactory(new PropertyValueFactory<>("park_spot"));
        }

        table.getColumns().addAll(surname_column, car_brand_column, car_model_column, car_num_column,
                date_of_park_column, spot_num_column);

        table.setItems(cars);

        find_drop_choice_box.getItems().addAll(find_drop_string);
        find_take_choice_box1.getItems().addAll(find_take_string);

        search_field.setDisable(true);

        find_drop_choice_box.valueProperty().addListener((observable, oldValue, newValue) -> {
            poss = Arrays.asList(find_drop_string).indexOf(newValue);
            search_field.setDisable(false);
            switch (poss) {
                case 0:
                    search_field.setPromptText("ex. Savluk");
                    break;
                case 1:
                    search_field.setPromptText("ex. Tesla");
                    break;
                case 2:
                    search_field.setPromptText("ex. S, 3, X, Y");
                    break;
                case 3:
                    search_field.setPromptText("ex. AA0420CC");
                    break;
            }
        });

        search_field.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredTable(newValue.toLowerCase());
        });

        find_take_choice_box2.setDisable(true);

        find_take_choice_box1.valueProperty().addListener((observable, oldValue, newValue) -> {
            find_take_choice_box2.getItems().clear();
            if (newValue.equals("Surname")) {
                find_take_choice_box2.setDisable(false);
                List<String> allSurnames = cars.stream()
                        .map(Car::getPersonSurname)
                        .collect(Collectors.toList());

                Set<String> uniqueSurnames = new HashSet<>(allSurnames);

                find_take_choice_box2.getItems().addAll(uniqueSurnames);
            } else {
                find_take_choice_box2.setDisable(false);
                // Later at finish
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
                warn2.setStyle("");
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
                if (userInput.equalsIgnoreCase(brand)) {
                    warn3.setOpacity(0.0);
                    warn3.setStyle("");
                    error_check(false);
                    break;
                } else {
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
                warn5.setStyle("");
                error_check(false);
            } else {
                warn5.setOpacity(1.0);
                warn5.setStyle("-fx-text-fill: #ff0000");
                error_check(true);
            }
        });
    }

    public void error_check(boolean has_error) {

        if (has_error) {
            sub_car_button.setOpacity(0.7);
            sub_car_button.setDisable(true);
        } else {
            sub_car_button.setOpacity(1);
            sub_car_button.setDisable(false);
        }
    }

    public void filteredTable(String searchText) {
        FilteredList<Car> filteredList = new FilteredList<>(cars, p -> true);

        filteredList.setPredicate(car -> {
            if (searchText == null || searchText.isEmpty()) {
                return true;
            }

            switch (poss) {
                case 0:
                    return car.getPersonSurname().toLowerCase().contains(searchText);
                case 1:
                    return car.getCarMark().toLowerCase().contains(searchText);
                case 2:
                    return car.getCarModel().toLowerCase().contains(searchText);
                case 3:
                    return car.getCarNumber().toLowerCase().contains(searchText);
                default:
                    return false;
            }
        });

        table.setItems(filteredList);
    }
}