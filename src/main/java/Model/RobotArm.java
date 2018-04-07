package Model;

import au.edu.federation.caliko.FabrikBone2D;
import au.edu.federation.caliko.FabrikChain;
import au.edu.federation.caliko.FabrikChain2D;
import au.edu.federation.utils.Vec2f;

import java.util.ArrayList;


public class RobotArm {
    ArrayList<Joint> kinematicChain;
    FabrikChain fabrikChain;
    public static int numJoints = 6;

    public RobotArm(){
        fabrikChain = new FabrikChain2D();
        FabrikBone2D base = new FabrikBone2D(new Vec2f(3.0f,2.0f),new Vec2f(0.0f,1.0f),3.0f);


//        kinematicChain = new ArrayList<Joint>();
//        int offset, canID;
//        double kp, ki, kd;
//        for(int i = 1; i <=numJoints; i++){
//            //TODO: Load values from GUI
//            Joint j = new Joint(i);
//            kinematicChain.add(j);
//            switch(i){
//                case 1:
//                    offset = 12;
//                    canID = 13;
//                    kp = 1;
//                    ki = .2;
//                    kd = .3;
//                    break;
//                case 2:
//                    offset = 15;
//                    canID = 17;
//                    kp = 1.2;
//                    ki = .3;
//                    kd = 4;
//                    break;
//                default:
//                    offset = 22;
//                    canID = 55;
//                    kp = 7;
//                    ki = 3.2;
//                    kd = 2.3;
//                    break;
//            }
//            j.setOffset(offset);
//            j.setCAN_ID(canID);
//            j.setKp(kp);
//            j.setKi(ki);
//            j.setKd(kd);
//
//        }

        for(Joint j : kinematicChain){
            Comms.getInstance().sendJointInit(j);
        }
    }

    /*
    public void updateActiveJoints(int numActive){
        for(int i = 0; i < kinematicChain.size(); i++){
            if(i+1 < numActive){
                kinematicChain.get(i).setActive();
            }
            else{
                kinematicChain.get(i).setDisabled();
            }
        }
    }*/

    //TODO: Make CAN_ID class
    public void updateArmPosition(ArrayList<Double> newAngles){
        for(int i = 0; i < newAngles.size(); i++){
            //TODO: Should this be a try/catch in case we try to update more joints than we have?
            //kinematicChain.get(i).updateSetpoint(newAngles.get(i));
        }
    }

    public void updateJointPosition(int jointNum, double newAngle){
        //Hardcore java people would use stream or lambda function somehow
        //kinematicChain.get(jointNum-1).updateSetpoint(newAngle);

    }

    //Joint Num is always 1 indexed. Arrays are 0 indexed.
    public ArrayList<Double> getAngles(){
        ArrayList<Double> toReturn = new ArrayList<>();
        for(Joint j : this.kinematicChain){
            //toReturn.add(j.getSetpoint());
        }
        return toReturn;
    }

    public Double getAngle(int jointNum){
        //return kinematicChain.get(jointNum-1).getSetpoint();
        return 1.;
    }

    public void associateCANaddr(int jointNum, int canID){
        kinematicChain.get(jointNum-1).setCAN_ID(canID);
    }

    public void setActiveStatus(int jointNum, boolean active){
        //kinematicChain.get(jointNum-1).setActiveStatus(active);
    }
}
