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

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
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
    @FXML JFXToggleButton button6;



    @FXML JFXTextField home1;
    @FXML JFXTextField home2;
    @FXML JFXTextField home3;
    @FXML JFXTextField home4;
    @FXML JFXTextField home5;
    @FXML JFXTextField home6;

    @FXML JFXTextField can1;
    @FXML JFXTextField can2;
    @FXML JFXTextField can3;
    @FXML JFXTextField can4;
    @FXML JFXTextField can5;
    @FXML JFXTextField can6;

    @FXML JFXTextField kp1;
    @FXML JFXTextField kp2;
    @FXML JFXTextField kp3;
    @FXML JFXTextField kp4;
    @FXML JFXTextField kp5;
    @FXML JFXTextField kp6;

    @FXML JFXTextField ki1;
    @FXML JFXTextField ki2;
    @FXML JFXTextField ki3;
    @FXML JFXTextField ki4;
    @FXML JFXTextField ki5;
    @FXML JFXTextField ki6;

    @FXML JFXTextField kd1;
    @FXML JFXTextField kd2;
    @FXML JFXTextField kd3;
    @FXML JFXTextField kd4;
    @FXML JFXTextField kd5;
    @FXML JFXTextField kd6;



    public ArrayList<Joint> joints = new ArrayList<>();
    public GsonHandler gs = new GsonHandler();
    public Comms comm;// = Comms.getInstance();
    public HashMap<String,String> FXData = new HashMap<>();


    public void initialize() {

        loadData(); //loads data from file or makes default values for gui

        jointCtrl1.valueProperty().addListener((obs, oldValue, newValue) -> {
            slider1(newValue.intValue());
            joints.get(0).setSetPoint(newValue.intValue()); //updates setpoint for a given joint value
        });

        jointCtrl2.valueProperty().addListener((obs, oldValue, newValue) -> {
            slider2(newValue.intValue());
            joints.get(1).setSetPoint(newValue.intValue());
        });

        jointCtrl3.valueProperty().addListener((obs, oldValue, newValue) -> {
            slider3(newValue.intValue());
            joints.get(2).setSetPoint(newValue.intValue());
        });

        jointCtrl4.valueProperty().addListener((obs, oldValue, newValue) -> {
            slider4(newValue.intValue());
            joints.get(3).setSetPoint(newValue.intValue());
        });

        jointCtrl5.valueProperty().addListener((obs, oldValue, newValue) -> {
            slider5(newValue.intValue());
            joints.get(4).setSetPoint(newValue.intValue());
        });

        jointCtrl6.valueProperty().addListener((obs, oldValue, newValue) -> {
            slider6(newValue.intValue());
            joints.get(5).setSetPoint(newValue.intValue());
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

    public static ArrayList<Joint> makeJointsDefault(){ // default values for joints in case there is no file saved
        Joint a = new Joint(1, 2, 2000, 1.2, 22.3, 1.49);
        Joint b = new Joint(2, 4, 100, 1., 13., 1.8);
        Joint c = new Joint(3, 1, 4000, 21.9, 2.23, 1.60);
        Joint d = new Joint(4, 0, 5000, 21.9, 2.23, 1.60);
        Joint e = new Joint(5, 8, 200, 21.9, 2.23, 1.60);
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

    public AnchorPane getAnchorViz() {

        return anchorViz;
    }

    public void slider1(int newValue){
        jointVal1.setText(String.format("%d",newValue));
    }
    public void slider2(int newValue){
        jointVal2.setText(String.format("%d",newValue));
    }
    public void slider3(int newValue){
        jointVal3.setText(String.format("%d",newValue));
    }
    public void slider4(int newValue){
        jointVal4.setText(String.format("%d",newValue));
    }
    public void slider5(int newValue){
        jointVal5.setText(String.format("%d",newValue));
    }
    public void slider6(int newValue){
        jointVal6.setText(String.format("%d",newValue));
    }

    @FXML
    public void saveData(){ //attach to save button in gui
        updateJointsFromGui();
        gs.writeJson(joints);
    }

    @FXML
    public void loadData(){ //attach to load button in gui
        try {
            joints = gs.readJson("db.json"); //tries to initialize joints variable
        } catch (FileNotFoundException e) {
            joints = makeJointsDefault(); //default case under condition that file is not loaded
        }
        for(Joint j : joints){
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
    public void sendInit(){ //attach to init button in gui
        comm.initJoints(joints);
        System.out.println(comm.readBuff(12)); //receive the Initialize ACK and print out "Initialized!"
    }

    @FXML
    public void updateJointsFromGui(){
        joints.get(0).updateJoint(1, Integer.parseInt(can1.getText()), Integer.parseInt(home1.getText()), Double.parseDouble(kp1.getText()), Double.parseDouble(ki1.getText()), Double.parseDouble(kd1.getText()));
        joints.get(1).updateJoint(2, Integer.parseInt(can2.getText()), Integer.parseInt(home2.getText()), Double.parseDouble(kp2.getText()), Double.parseDouble(ki2.getText()), Double.parseDouble(kd2.getText()));
        joints.get(2).updateJoint(3, Integer.parseInt(can3.getText()), Integer.parseInt(home3.getText()), Double.parseDouble(kp3.getText()), Double.parseDouble(ki3.getText()), Double.parseDouble(kd3.getText()));
        joints.get(3).updateJoint(4, Integer.parseInt(can4.getText()), Integer.parseInt(home4.getText()), Double.parseDouble(kp4.getText()), Double.parseDouble(ki4.getText()), Double.parseDouble(kd4.getText()));
        joints.get(4).updateJoint(5, Integer.parseInt(can5.getText()), Integer.parseInt(home5.getText()), Double.parseDouble(kp5.getText()), Double.parseDouble(ki5.getText()), Double.parseDouble(kd5.getText()));
        //joints.get(6).updateJoint(6, Integer.parseInt(can6.getText()), Integer.parseInt(home6.getText()), Double.parseDouble(kp6.getText()), Double.parseDouble(ki6.getText()), Double.parseDouble(kd6.getText()));
    }

    public void toggleMenu(){
        JFXPopup p = new JFXPopup();

    }

    //    @FXML
//    void sendInit(){
//        System.out.println("Start");
//        String joint1 = "Enabled: " + button1.isSelected() +
//                " Encoder offset: " + home1.getText() +
//                " CAN ID: " + can1.getText() +
//                " kp: " + kp1.getText() +
//                " ki: " + ki1.getText() +
//                " kd: " + kd1.getText();
//        System.out.println(joint1);
//
//    }

    //    @FXML
//    public void saveData(){
//        System.out.println("Save data");
//        byte a[];
//        byte b[];
//
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
//        try {
//            outputStream.write(home1.getText().getBytes());
//            outputStream.write('\n');
//            outputStream.write(home2.getText().getBytes());
//            outputStream.write('\n');
//            outputStream.write(home3.getText().getBytes());
//            outputStream.write('\n');
//            outputStream.write(home4.getText().getBytes());
//            outputStream.write('\n');
//            outputStream.write(home5.getText().getBytes());
//            outputStream.write('\n');
//
//        }
//
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        byte data[] = outputStream.toByteArray();
//
//        System.out.println(Arrays.toString(data));
//        FileOutputStream out = null;
//        try {
//            out = new FileOutputStream("armDat.data");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        try {
//            out.write(data);
//            out.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }

//    void loadData(){
//        //call the GSON loader
//        for(Node n : configGrid.getChildren()){
//            if(n instanceof AnchorPane){
//
//            }
//        }
//    }
}


