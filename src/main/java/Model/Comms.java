package Model;


import gnu.io.NRSerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
        //System.out.println(toSend);
        try {
            outs.writeBytes(toSend);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Write Out Failed");
        }

        byte[] buff = {'0'};
        System.out.println("Waiting for ACK");
        while(buff[0] != 89){
            try {
                ins.read(buff);
                System.out.println(buff[0]);
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

    public void disconnect(){
        serial.disconnect();
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

}
