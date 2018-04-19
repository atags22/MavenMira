package Model;


import gnu.io.NRSerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.TooManyListenersException;

public class Comms {

    private DataInputStream ins;
    private DataOutputStream outs;
    private static Comms instance = null;
    private static NRSerialPort serial;

    //This class is now a Singleton.
    private Comms(){
        System.out.println("Enumerating Devices ...");
        for(String s: NRSerialPort.getAvailableSerialPorts()){
            System.out.println("Availible port: "+s);
        }
        String port = "/dev/ttyACM0";
        int baudRate = 115200;
        serial = new NRSerialPort(port, baudRate);
        serial.connect();

        ins = new DataInputStream(serial.getInputStream());
        outs = new DataOutputStream(serial.getOutputStream());
    }

    public static Comms getInstance() {
        if(instance == null) {
            instance = new Comms();
        }
        return instance;
    }

    public void initJoints(ArrayList<Joint> joints){
        for(Joint j: joints){
            sendJointInit(j);
        }
    }

    public void sendJointInit(Joint j){
        //Info: jointNum, jointID, encoderOffset, pid constants *3, get ack back
        int jointNum = j.getJointNum(); //1 through 6
        int jointID = j.getCAN_ID();
        int encoderOffset = j.getOffset();
        double kp = j.getKp();
        double ki = j.getKi();
        double kd = j.getKd();

        //TODO: Cap values that we think are already capped

        String jointStr = "J" + Integer.toString(jointNum);

        String jointIDStr = Integer.toString(jointID);
        while(jointIDStr.length() < 2){
            jointIDStr = "0" + jointIDStr;
        }

        String offsetStr = Integer.toString(encoderOffset);
        while(offsetStr.length() < 4){
            offsetStr = "0" + offsetStr;
        }

        String kpStr = turnDoubleIntoFormattedString(kp);
        String kdStr = turnDoubleIntoFormattedString(kd);
        String kiStr = turnDoubleIntoFormattedString(ki);

        String toSend = jointStr + jointIDStr + offsetStr + kpStr + kiStr + kdStr;
        System.out.println(toSend);
        try {
            outs.writeBytes(toSend);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Write Out Failed");
        }

        byte[] buff = {'0'};
        //System.out.println("Waiting for ACK");
        while(buff[0] != 89){
            try {
                ins.read(buff);
                //System.out.println(buff[0]);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Read Failed");
            }
        }
        //System.out.println("ACK Received");
    }

    public String turnDoubleIntoFormattedString(double d){
        String dStr = Double.toString(d);
        String[] split = dStr.split("\\.");
        String tens = split[0];
        String decimals = split[1];
        while(tens.length() < 2){
            tens = "0" + tens;
        }
        while(decimals.length() < 2){
            decimals = decimals + "0";
        }
        dStr = tens + decimals;
        return dStr;
    }

    public String readBuff(int size){
        String str = "";
        while(str.length() < size) {
            byte[] buff = {'1'};
            try {
                ins.read(buff);
            } catch (IOException e) {
                e.printStackTrace();
            }
            str = str + (char) buff[0];
        }
        return str;
    }

    public void readJointUpdate(ArrayList<Joint> joints){
        for(Joint j: joints){
            String buff = readBuff(4);
            System.out.println(buff);
            j.setCurPoint(Integer.parseInt(buff));
            readBuff(1); //Apparently there's a space? Hmm. Oh well.
        }
    }

    public void disconnect(){
        serial.disconnect();
    }

    public void io(ArrayList<Joint> joints){
        String buff = readBuff(13);//Make initialized! come through properly
        System.out.println(buff);
        while(true) {
            readJointUpdate(joints);
            int j1 = joints.get(0).getSetPoint();
            int j2 = joints.get(1).getSetPoint();
            int j3 = joints.get(2).getSetPoint();
            int j4 = joints.get(3).getSetPoint();
            int j5 = joints.get(4).getSetPoint();
            int j6 = joints.get(5).getSetPoint();
            sendJointUpdate(j1, j2, j3, j4, j5, j6);
        }
    }

    public void sendJointUpdate(int j1, int j2, int j3, int j4, int j5, int j6){
        ArrayList<String> jointStrs = new ArrayList<>();
        jointStrs.add(Integer.toString(j1));
        jointStrs.add(Integer.toString(j2));
        jointStrs.add(Integer.toString(j3));
        jointStrs.add(Integer.toString(j4));
        jointStrs.add(Integer.toString(j5));
        jointStrs.add(Integer.toString(j6));
        String outStr = "";
        for(String jStr: jointStrs){
            while(jStr.length() < 4){
                jStr = "0" + jStr;
            }
            outStr = outStr + jStr;
        }
        System.out.println(outStr);
        try {
            outs.writeBytes(outStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    void sendEnableStatus(int jointNum, boolean status){

    }

    void attachIDtoJoint(int jointNum, int jointID){

    }

//    //TODO: Change this function so it updates an instance variable with the joint's new position,
//// //and then triggers a flush: sends all the arm data to the board
//    void sendJointUpdate(int jointNum, double newAngle){
//        //convert double to 12-bit int
//        //int b = ins.read();
////        int b = (int) ((newAngle / 360.) * 255.);
//        if ((47 < newAngle) && (newAngle < 53)) {
//            newAngle = 50;
//        }
//        System.out.print("In val = " + newAngle);
//        int sendInt = (int) (newAngle * 8192 / 100.);
//        if (sendInt < 1000) {
//            sendInt = 1000;
//        }
//        String toSend = Integer.toString(sendInt);
//        System.out.println("Int Value: " + toSend);
//        try {
//            outs.write(toSend.getBytes());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

}
