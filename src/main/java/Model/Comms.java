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

    //This class is now a Singleton.
    private Comms(){
        System.out.println("Enumerating Devices ...");
        for(String s: NRSerialPort.getAvailableSerialPorts()){
            System.out.println("Availible port: "+s);
        }
        String port = "/dev/ttyACM0";
        int baudRate = 115200;
        NRSerialPort serial = new NRSerialPort(port, baudRate);

        //TODO: If statement is untested. Try with actual hardware.
        if(!serial.isConnected()){
            serial.connect();
        }

        ins = new DataInputStream(serial.getInputStream());
        outs = new DataOutputStream(serial.getOutputStream());


        try {
            serial.addEventListener(new SerialPortEventListener() {
                @Override
                public void serialEvent(SerialPortEvent serialPortEvent) {
                    if(serialPortEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
                        try {
                            //Print the number of bytes of the message
                            System.out.println("Able to read " + ins.available() + "b");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } catch (TooManyListenersException e) {
            e.printStackTrace();
        }


    }

    private static Comms localInstance;
    public static Comms getInstance(){
        if (localInstance == null){
            localInstance = new Comms();
        }
        return localInstance;
    }


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

    void sendEnableStatus(int jointNum, boolean status){

    }

    void attachIDtoJoint(int jointNum, int jointID){

    }

}
