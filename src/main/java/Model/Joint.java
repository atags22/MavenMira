package Model;

import java.util.HashMap;

public class Joint {

    private int jointNum;
    private int CAN_ID;
    private int offset;
    private double kp, ki, kd;

    private HashMap<String,Double> dhParams; //d, theta, r, alpha


    public Joint(int jointNum, int CAN_ID, int encoderOffset, double kp, double ki, double kd){
        this.jointNum = jointNum;
        this.CAN_ID = CAN_ID;
        this.offset = encoderOffset;
        this.kp = kp;
        this.kd = kd;
        this.ki = ki;
    }

    public double getKp() {
        return kp;
    }

    public void setKp(double kp) {
        this.kp = kp;
    }

    public double getKi() {
        return ki;
    }

    public void setKi(double ki) {
        this.ki = ki;
    }

    public double getKd() {
        return kd;
    }

    public void setKd(double kd) {
        this.kd = kd;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getJointNum(){
        return jointNum;
    }

    public int getCAN_ID() { return CAN_ID; }

    public void setCAN_ID(int CAN_ID) { this.CAN_ID = CAN_ID; }
}
