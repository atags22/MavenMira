package Controller;

import Model.RobotArm;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXToggleButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Box;
import util.MagicNumbers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController {

    @FXML AnchorPane anchorViz;

    @FXML Slider jointCtrl1;
    @FXML Slider jointCtrl2;
    @FXML Slider jointCtrl3;

    @FXML Slider jointCtrl4;
    @FXML Slider jointCtrl5;
    @FXML Slider jointCtrl6;


    @FXML Label jointVal1;
    @FXML Label jointVal2;
    @FXML Label jointVal3;

    @FXML Label jointVal4;
    @FXML Label jointVal5;
    @FXML Label jointVal6;


    @FXML JFXToggleButton button1;
    @FXML JFXToggleButton button2;
    @FXML JFXToggleButton button3;
    @FXML JFXToggleButton button4;
    @FXML JFXToggleButton button5;

    RobotArm robotArm;

    public void initialize(){

        robotArm = new RobotArm();


        jointCtrl1.valueProperty().addListener((obs, oldValue, newValue) -> {
            slider1(newValue.doubleValue());
            robotArm.updateJointPosition(1,newValue.doubleValue());
        });

        jointCtrl2.valueProperty().addListener((obs, oldValue, newValue) -> {
            slider2(newValue.doubleValue());
            robotArm.updateJointPosition(2,newValue.doubleValue());
        });

        jointCtrl3.valueProperty().addListener((obs, oldValue, newValue) -> {
            slider3(newValue.doubleValue());
            robotArm.updateJointPosition(3,newValue.doubleValue());
        });

        jointCtrl4.valueProperty().addListener((obs, oldValue, newValue) -> {
            slider4(newValue.doubleValue());
            robotArm.updateJointPosition(4,newValue.doubleValue());
        });

        jointCtrl5.valueProperty().addListener((obs, oldValue, newValue) -> {
            slider5(newValue.doubleValue());
            robotArm.updateJointPosition(5,newValue.doubleValue());
        });

        jointCtrl6.valueProperty().addListener((obs, oldValue, newValue) -> {
            slider6(newValue.doubleValue());
            robotArm.updateJointPosition(6,newValue.doubleValue());
        });



        button1.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
//                System.out.println("Button is " + button1.isSelected());
                robotArm.setActiveStatus(1,button1.isSelected());
            }
        });
        button2.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
//                System.out.println("Button is " + button2.isSelected());
                robotArm.setActiveStatus(2,button2.isSelected());
            }
        });
        button3.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
//                System.out.println("Button is " + button3.isSelected());
                robotArm.setActiveStatus(3,button3.isSelected());
            }
        });


        button4.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
//                System.out.println("Button is " + button4.isSelected());
                robotArm.setActiveStatus(4,button4.isSelected());
            }
        });

        button5.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
//                System.out.println("Button is " + button0.isSelected());
                robotArm.setActiveStatus(5,button5.isSelected());
            }
        });
    }
    public AnchorPane getAnchorViz() {

        return anchorViz;
    }

    public void slider1(double newValue){
        jointVal1.setText(String.format("%.2f",newValue*MagicNumbers.HUNDRED_TO_360));
    }
    public void slider2(double newValue){
        jointVal2.setText(String.format("%.2f",newValue*MagicNumbers.HUNDRED_TO_360));
    }
    public void slider3(double newValue){
        jointVal3.setText(String.format("%.2f",newValue*MagicNumbers.HUNDRED_TO_360));
    }
    public void slider4(double newValue){
        jointVal4.setText(String.format("%.2f",newValue*MagicNumbers.HUNDRED_TO_360));
    }
    public void slider5(double newValue){
        jointVal5.setText(String.format("%.2f",newValue*MagicNumbers.HUNDRED_TO_360));
    }
    public void slider6(double newValue){
        jointVal6.setText(String.format("%.2f",newValue*MagicNumbers.HUNDRED_TO_360));
    }




    public void toggleMenu(){
        JFXPopup p = new JFXPopup();

    }
}


