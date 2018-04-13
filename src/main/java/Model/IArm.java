package Model;

import java.util.HashSet;

public interface IArm {
    public HashSet<Joint> joints;

    public void updateJoint(int jointNum, int val);


}
