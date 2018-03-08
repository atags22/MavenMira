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

    int jointNum;

    //TODO: MAke this an instance of CAN_ID object
    private int CAN_ID;
    //TODO: Make Joint Type class/enum
    private double setPoint;
    private boolean jointEnabled; //Should the motor ever try to turn?

    private HashMap<String,Double> dhParams; //d, theta, r, alpha
    private HashMap<String,Double> pidConstants; //kp, ki, kd

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
}
