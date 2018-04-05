import Controller.MainController;
import Model.Comms;
import Model.Joint;
import Model.Xform;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

import java.util.ArrayList;


public class Main extends Application {



    MainController mainController;
    final Xform axisGroup = new Xform();


    Parent root;

    final Xform world = new Xform();
    final PerspectiveCamera camera = new PerspectiveCamera(true);
    final Xform cameraXform = new Xform();
    final Xform cameraXform2 = new Xform();
    final Xform cameraXform3 = new Xform();
    private static final double CAMERA_INITIAL_DISTANCE = -450;
    private static final double CAMERA_INITIAL_X_ANGLE = 70.0;
    private static final double CAMERA_INITIAL_Y_ANGLE = 320.0;
    private static final double CAMERA_NEAR_CLIP = 0.1;
    private static final double CAMERA_FAR_CLIP = 10000.0;


    private static final double AXIS_LENGTH = 250.0;
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader();
        root = fxmlLoader.load(getClass().getResource("mainView.fxml"));
        primaryStage.setTitle("MIRA Console");
        Pane p = fxmlLoader.load(getClass().getResource("mainView.fxml").openStream());
        mainController = fxmlLoader.getController();
        mainController.initialize();


        //buildCamera();
        buildAxes();
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();



    }

    private void buildCamera() {
//        TabPane tabs = (TabPane) root.getChildrenUnmodifiable().get(0);
//        Tab viz = tabs.getTabs().get(1);
//        AnchorPane anchorViz = (AnchorPane)(viz.getContent());


        mainController.getAnchorViz().getChildren().addAll(cameraXform);
        cameraXform.getChildren().add(cameraXform2);
        cameraXform2.getChildren().add(cameraXform3);
        cameraXform3.getChildren().add(camera);
        cameraXform3.setRotateZ(180.0);

        camera.setNearClip(CAMERA_NEAR_CLIP);
        camera.setFarClip(CAMERA_FAR_CLIP);
        camera.setTranslateZ(CAMERA_INITIAL_DISTANCE);
        cameraXform.ry.setAngle(CAMERA_INITIAL_Y_ANGLE);
        cameraXform.rx.setAngle(CAMERA_INITIAL_X_ANGLE);
    }


    private void buildAxes() {
        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.DARKRED);
        redMaterial.setSpecularColor(Color.RED);

        final PhongMaterial greenMaterial = new PhongMaterial();
        greenMaterial.setDiffuseColor(Color.DARKGREEN);
        greenMaterial.setSpecularColor(Color.GREEN);

        final PhongMaterial blueMaterial = new PhongMaterial();
        blueMaterial.setDiffuseColor(Color.DARKBLUE);
        blueMaterial.setSpecularColor(Color.BLUE);

        final Box xAxis = new Box(AXIS_LENGTH, 1, 1);
        final Box yAxis = new Box(1, AXIS_LENGTH, 1);
        final Box zAxis = new Box(1, 1, AXIS_LENGTH);

        xAxis.setMaterial(redMaterial);
        yAxis.setMaterial(greenMaterial);
        zAxis.setMaterial(blueMaterial);

        axisGroup.getChildren().addAll(xAxis, yAxis, zAxis);
        axisGroup.setVisible(true);
        world.getChildren().addAll(axisGroup);
    }


    public static void main(String[] args) {
        //usbHidTest myTest = new usbHidTest();
        System.out.println("started");
//        Comms comm = Comms.getInstance();
        Joint a = new Joint(1, 2, 2000, 1.2, 22.3, 1.49);
        Joint b = new Joint(2, 4, 100, 1., 13., 1.8);
        Joint c = new Joint(3, 1, 4000, 21.9, 2.23, 1.60);
        Joint d = new Joint(4, 0, 5000, 21.9, 2.23, 1.60);
        Joint e = new Joint(5, 8, 200, 21.9, 2.23, 1.60);
        Joint f = new Joint(6, 6, 4180, 21.9, 2.23, 1.60);

        ArrayList<Joint> jointList = new ArrayList<>();
        jointList.add(a);
        jointList.add(b);
        jointList.add(c);
        jointList.add(d);
        jointList.add(e);
        jointList.add(f);

        for(Joint j: jointList){
//            comm.sendJointInit(j);
        }
        System.out.println("Initialization Complete!!");
        launch(args);


    }
}

