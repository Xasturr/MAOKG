package sample;

import javafx.scene.paint.Color;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.shape.*;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root,300, 250);
        scene.setFill(Color.rgb(0,128,0));

        //

        Line eye1 = new Line(50, 20, 100, 20);
        root.getChildren().add(eye1);
        eye1.setStroke(Color.rgb(4,4,251));
        eye1.setStrokeWidth(5.0);

        Line eye2 = new Line(190, 20, 240, 20);
        root.getChildren().add(eye2);
        eye2.setStroke(Color.rgb(4,4,251));
        eye2.setStrokeWidth(5.0);

        Polygon nose = new Polygon(140, 20, 85, 160, 225, 160);
        root.getChildren().add(nose);
        nose.setFill(Color.rgb(255,255,0));

        Polyline polyline = new Polyline(new double[]{
                20.0, 15.0, 70.0, 205.0, 240.0, 205.0, 280.0, 20.0
        });
        root.getChildren().add(polyline);
        polyline.setStroke(Color.rgb(255,0,0));
        polyline.setStrokeWidth(5.0);

        //

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}