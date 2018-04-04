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

    //TODO: Make Joint Type class/enum
    private double setPoint;
    private boolean jointEnabled; //Should the motor ever try to turn?
    private double kp, ki, kd;

    private HashMap<String,Double> dhParams; //d, theta, r, alpha


    public Joint(int jointNum){
        this.jointNum = jointNum;
        this.jointEnabled = true;
    }

/*    public void init(){

    }
    */

    //TODO: Basic setters/getters
    public void updateSetpoint(double newPoint){
        this.setPoint = newPoint;
        Comms.getInstance().sendJointUpdate(this.jointNum,newPoint);
    }
    public double getSetpoint(){ return this.setPoint; }

    public void setActiveStatus(boolean activeStatus) {
        jointEnabled = activeStatus;
//        Comms.getInstance().sendEnableStatus(this.jointNum,activeStatus);
        System.out.println("Joint " + (jointNum) + " is " + activeStatus);
    }

    public int getCAN_ID() {
        return CAN_ID;
    }
    public void setCAN_ID(int CAN_ID) {
        this.CAN_ID = CAN_ID;
        Comms.getInstance().attachIDtoJoint(this.jointNum,CAN_ID);
    }


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
