package Model;


import gnu.io.NRSerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.TooManyListenersException;

public class Comms {


    //TODO: Figure out how to make an HID object
    private DataInputStream ins;
    private DataOutputStream outs;
    private static Comms instance = null;

    //This class is now a Singleton.
    private Comms(){
        System.out.println("Enumerating Devices ...");
        for(String s: NRSerialPort.getAvailableSerialPorts()){
            System.out.println("Availible port: "+s);
        }
        String port = "/dev/ttyACM0";
        int baudRate = 115200;
        NRSerialPort serial = new NRSerialPort(port, baudRate);
        serial.connect();
//        //TODO: If statement is untested. Try with actual hardware.
//        if(!serial.isConnected()){
//            serial.connect();
//        }

        ins = new DataInputStream(serial.getInputStream());
        outs = new DataOutputStream(serial.getOutputStream());


//        try {
//            serial.addEventListener(new SerialPortEventListener() {
//                @Override
//                public void serialEvent(SerialPortEvent serialPortEvent) {
//                    if(serialPortEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
//                        try {
//                            //Print the number of bytes of the message
//                            System.out.println("Able to read " + ins.available() + "b");
//                            System.out.println(ins.read());
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            });
//        } catch (TooManyListenersException e) {
//            e.printStackTrace();
//        }


    }

    public static Comms getInstance() {
        if(instance == null) {
            instance = new Comms();
        }
        return instance;
    }


//TODO: Change this function so it updates an instance variable with the joint's new position,
// //and then triggers a flush: sends all the arm data to the board
    void sendJointUpdate(int jointNum, double newAngle){
        //convert double to 12-bit int
        //int b = ins.read();
//        int b = (int) ((newAngle / 360.) * 255.);
        if ((47 < newAngle) && (newAngle < 53)) {
            newAngle = 50;
        }
        System.out.print("In val = " + newAngle);
        int sendInt = (int) (newAngle * 8192 / 100.);
        if (sendInt < 1000) {
            sendInt = 1000;
        }
        String toSend = Integer.toString(sendInt);
        System.out.println("Int Value: " + toSend);
        try {
            outs.write(toSend.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
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
        //System.out.println(jointIDStr.length());
        while(jointIDStr.length() < 2){
            jointIDStr = "0" + jointIDStr;
        }
        //System.out.println(jointIDStr);


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
            outs.writeChars(toSend);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Write Out Failed");
        }

        byte[] buff = {'0'};

        System.out.println(buff[0]);
        System.out.println("Waiting for ACK");
        while(buff[0] != 'Y'){
            try {
                ins.read(buff);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Read Failed");
            }
        }
        System.out.println("ACK Received");

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
        //System.out.println(dStr);
        return dStr;
    }



    void sendEnableStatus(int jointNum, boolean status){

    }

    void attachIDtoJoint(int jointNum, int jointID){

    }

}
