package Controller;

//import Model.RobotArm;
import Model.Comms;
import Model.GsonHandler;
import Model.Joint;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Box;
import util.MagicNumbers;

import java.io.*;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class MainController {

    @FXML AnchorPane anchorViz;


    @FXML GridPane configGrid;

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


    @FXML JFXTextField home1;
    @FXML JFXTextField home2;
    @FXML JFXTextField home3;
    @FXML JFXTextField home4;
    @FXML JFXTextField home5;

    @FXML JFXTextField can1;
    @FXML JFXTextField can2;
    @FXML JFXTextField can3;
    @FXML JFXTextField can4;
    @FXML JFXTextField can5;

    @FXML JFXTextField kp1;
    @FXML JFXTextField kp2;
    @FXML JFXTextField kp3;
    @FXML JFXTextField kp4;
    @FXML JFXTextField kp5;

    @FXML JFXTextField ki1;
    @FXML JFXTextField ki2;
    @FXML JFXTextField ki3;
    @FXML JFXTextField ki4;
    @FXML JFXTextField ki5;

    @FXML JFXTextField kd1;
    @FXML JFXTextField kd2;
    @FXML JFXTextField kd3;
    @FXML JFXTextField kd4;
    @FXML JFXTextField kd5;

    HashMap<String,String> FXData = new HashMap<>();

    GsonHandler gs;


//    RobotArm robotArm;

    public void initialize() {

//        robotArm = new RobotArm();
        gs = new GsonHandler();

        jointCtrl1.valueProperty().addListener((obs, oldValue, newValue) -> {
            slider1(newValue.doubleValue());
//            robotArm.updateJointPosition(1, newValue.doubleValue());
        });

        jointCtrl2.valueProperty().addListener((obs, oldValue, newValue) -> {
            slider2(newValue.doubleValue());
//            robotArm.updateJointPosition(2, newValue.doubleValue());
        });

        jointCtrl3.valueProperty().addListener((obs, oldValue, newValue) -> {
            slider3(newValue.doubleValue());
//            robotArm.updateJointPosition(3, newValue.doubleValue());
        });

        jointCtrl4.valueProperty().addListener((obs, oldValue, newValue) -> {
            slider4(newValue.doubleValue());
//            robotArm.updateJointPosition(4, newValue.doubleValue());
        });

        jointCtrl5.valueProperty().addListener((obs, oldValue, newValue) -> {
            slider5(newValue.doubleValue());
//            robotArm.updateJointPosition(5, newValue.doubleValue());
        });

        jointCtrl6.valueProperty().addListener((obs, oldValue, newValue) -> {
            slider6(newValue.doubleValue());
//            robotArm.updateJointPosition(6, newValue.doubleValue());
        });


        button1.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
//                System.out.println("Button is " + button1.isSelected());
//                robotArm.setActiveStatus(1, button1.isSelected());
            }
        });
        button2.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
//                System.out.println("Button is " + button2.isSelected());
//                robotArm.setActiveStatus(2, button2.isSelected());
            }
        });
        button3.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
//                System.out.println("Button is " + button3.isSelected());
//                robotArm.setActiveStatus(3, button3.isSelected());
            }
        });


        button4.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
//                System.out.println("Button is " + button4.isSelected());
//                robotArm.setActiveStatus(4, button4.isSelected());
            }
        });

        button5.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
//                System.out.println("Button is " + button0.isSelected());
//                robotArm.setActiveStatus(5, button5.isSelected());
            }
        });

    }
    public ArrayList<Joint> makeJointsFromGUI(){ //TODO: Input validation
        Joint a = new Joint(1,
                makeCanFromString(can1.getText()),
                makeOffsetFromString(home1.getText()),
                makeKonst(kp1.getText()),
                makeKonst(ki1.getText()),
                makeKonst(kd1.getText()));
        Joint b = new Joint(2,
                makeCanFromString(can2.getText()),
                makeOffsetFromString(home2.getText()),
                makeKonst(kp2.getText()),
                makeKonst(ki2.getText()),
                makeKonst(kd2.getText()));
        Joint c = new Joint(3,
                makeCanFromString(can3.getText()),
                makeOffsetFromString(home3.getText()),
                makeKonst(kp3.getText()),
                makeKonst(ki3.getText()),
                makeKonst(kd3.getText()));
        Joint d = new Joint(4,
                makeCanFromString(can4.getText()),
                makeOffsetFromString(home4.getText()),
                makeKonst(kp4.getText()),
                makeKonst(ki4.getText()),
                makeKonst(kd4.getText()));
        Joint e = new Joint(5,
                makeCanFromString(can5.getText()),
                makeOffsetFromString(home5.getText()),
                makeKonst(kp5.getText()),
                makeKonst(ki5.getText()),
                makeKonst(kd5.getText()));

        //dummy values
        Joint f = new Joint(6, 6, 4180, 21.9, 2.23, 1.60);

        ArrayList<Joint> jointList = new ArrayList<>();
        jointList.add(a);
        jointList.add(b);
        jointList.add(c);
        jointList.add(d);
        jointList.add(e);
        jointList.add(f);

        return jointList;
    }


        int makeCanFromString(String rawInput){
            int toReturn = Integer.parseInt(rawInput);
            if(toReturn < 1){
                toReturn = 1;
            }
            if(toReturn > 63){
                toReturn = 63;
            }
            return toReturn;
        }

        int makeOffsetFromString(String rawInput){
            int toReturn = Integer.parseInt(rawInput);
            if(toReturn < -4095){
                toReturn = -4095;
            }
            if(toReturn > 4095){
                toReturn = 4095;
            }
            return toReturn;
        }

        double makeKonst(String rawInput){
            double toReturn = Double.parseDouble(rawInput);
            if(toReturn < 0){
                toReturn = 0;
            }
            if(toReturn > 99.99){
                toReturn = 99.99;
            }
            return toReturn;
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



    void loadData(){
        //call the GSON loader
        for(Node n : configGrid.getChildren()){
            if(n instanceof AnchorPane){

            }
        }
    }

    public void toggleMenu(){
        JFXPopup p = new JFXPopup();

    }

    /************************** Functions that are Callbacks from GUI ****************************/
    @FXML
    void sendInit(){
        System.out.println("Start");
        Comms comm = Comms.getInstance();
        ArrayList<Joint> jointList = makeJointsFromGUI();

            for(Joint j: jointList){
                 comm.sendJointInit(j); //sends joint init data based on what is read in from the json file
            }

    }


    @FXML
    void loadFile(){
        System.out.println("load file");
        ArrayList<Joint> newData = null;
        try{
            newData = gs.readJson("db.json");
        }
        catch(FileNotFoundException e){
            //TODO: Display helpful error message popup
            System.out.println("No such file.");
        }
        for(Joint j : newData){
            if(j.getJointNum()==1) {
                can1.setText(Integer.toString(j.getCAN_ID()));
                home1.setText(Integer.toString(j.getOffset()));
                kp1.setText(Double.toString(j.getKp()));
                ki1.setText(Double.toString(j.getKi()));
                kd1.setText(Double.toString(j.getKd()));
            }
            else if(j.getJointNum()==2){
                can2.setText(Integer.toString(j.getCAN_ID()));
                home2.setText(Integer.toString(j.getOffset()));
                kp2.setText(Double.toString(j.getKp()));
                ki2.setText(Double.toString(j.getKi()));
                kd2.setText(Double.toString(j.getKd()));
            }
            else if(j.getJointNum()==3){
                can3.setText(Integer.toString(j.getCAN_ID()));
                home3.setText(Integer.toString(j.getOffset()));
                kp3.setText(Double.toString(j.getKp()));
                ki3.setText(Double.toString(j.getKi()));
                kd3.setText(Double.toString(j.getKd()));
            }
            else if(j.getJointNum()==4){
                can4.setText(Integer.toString(j.getCAN_ID()));
                home4.setText(Integer.toString(j.getOffset()));
                kp4.setText(Double.toString(j.getKp()));
                ki4.setText(Double.toString(j.getKi()));
                kd4.setText(Double.toString(j.getKd()));
            }
            else if(j.getJointNum()==5){
                can5.setText(Integer.toString(j.getCAN_ID()));
                home5.setText(Integer.toString(j.getOffset()));
                kp5.setText(Double.toString(j.getKp()));
                ki5.setText(Double.toString(j.getKi()));
                kd5.setText(Double.toString(j.getKd()));
            }

        }
    }

    @FXML
    public void saveData(){
        System.out.println("Save data");
        ArrayList<Joint> toWrite = makeJointsFromGUI();
        gs.writeJson(toWrite);
        System.out.println(toWrite.toString());
    }




}


