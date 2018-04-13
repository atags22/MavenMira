package Model;

import java.util.HashMap;

/**
 * Contains all info about a particular joint
 * -CAN ID
 * -Joint Type
 * -DH Parameters
 * -Current Setpoint
 * -PID Constants
 */
public class Joint {

    private int jointNum;
    private int CAN_ID;
    private int offset;
    public int setPoint;
    public int curPoint;
    private boolean jointEnabled; //Should the motor ever try to turn?
    private double kp, ki, kd;

    private HashMap<String,Double> dhParams; //d, theta, r, alpha


    public Joint (int jointNum, int jointID, int encoderOffset, double kp, double ki, double kd){
        this.jointNum = jointNum;
        this.CAN_ID = jointID;
        this.offset = encoderOffset;
        this.kp = kp;
        this.ki = ki;
        this.kd = kd;
        this.jointEnabled = true;
    }


    public void updateSetpoint(int newPoint){ this.setPoint = newPoint; }
    public int getSetpoint(){ return this.setPoint; }

    public void setActiveStatus(boolean activeStatus) {
        jointEnabled = activeStatus;
//        Comms.getInstance().sendEnableStatus(this.jointNum,activeStatus);
        System.out.println("Joint " + (jointNum) + " is " + activeStatus);
    }

    public int getCAN_ID() {
        return CAN_ID;
    }

    public void setCAN_ID(int CAN_ID) { this.CAN_ID = CAN_ID; }

    public boolean isJointEnabled() {
        return jointEnabled;
    }

    public void setJointEnabled(boolean jointEnabled) {
        this.jointEnabled = jointEnabled;
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
}
