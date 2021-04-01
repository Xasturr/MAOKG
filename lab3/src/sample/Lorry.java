package sample;

import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Lorry extends Application {
    public static void main (String args[]) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        // Scene (root, x, y)
        Scene scene = new Scene (root, 470, 310);

        //ковш
        Rectangle rect = new Rectangle(200, 30, 100, 200);
        rect.setFill(Color.DARKBLUE);
        Rotate rotate = new Rotate();
        rotate.setPivotX(rect.getX()); //Pivot X Top-Left corner
        rotate.setPivotY(rect.getY()); //Pivot Y
        rotate.setAngle(60); //Angle degrees
        //Add the transform to the node
        rect.getTransforms().add(rotate);
        root.getChildren().add(rect);

        //кузов
        rect = new Rectangle(100, 150, 200, 100);
        rect.setFill(Color.YELLOW);
        rect.setStroke(Color.BLACK);
        rect.setStrokeType(StrokeType.OUTSIDE);
        root.getChildren().add(rect);

        //вихлопна труба
        Arc arc = new Arc();
        arc.setFill(Color.DARKBLUE);
        arc.setType(ArcType.OPEN);
        arc.setCenterX(100.0f);
        arc.setCenterY(230.0f);
        arc.setRadiusX(15.0f);
        arc.setRadiusY(10.0f);
        arc.setStartAngle(90.0f);
        arc.setLength(180.0f);
        root.getChildren().add(arc);

        //мигалка
        Circle circle = new Circle(415,100,10);
        circle.setFill(Color.DARKBLUE);
        root.getChildren().add(circle);

        //кабіна
        Polygon polygon = new Polygon(300.0, 50.0,
                300.0, 250.0,
                430.0, 250.0,
                430.0, 150.0,
                400.0, 50.0);
        polygon.setFill(Color.YELLOW);
        polygon.setStroke(Color.BLACK);
        polygon.setStrokeType(StrokeType.OUTSIDE);
        root.getChildren().add(polygon);

        //вікно
        polygon = new Polygon(310.0, 60.0,
                310.0, 150.0,
                420.0, 150.0,
                390.0, 60.0);
        polygon.setFill(Color.BLUE);
        polygon.setStroke(Color.BLACK);
        polygon.setStrokeType(StrokeType.OUTSIDE);
        root.getChildren().add(polygon);

        //колеса
        circle = new Circle(150,250,30);
        root.getChildren().add(circle);
        circle = new Circle(370,250,30);
        root.getChildren().add(circle);
        circle = new Circle(150,250,5);
        root.getChildren().add(circle);
        circle.setFill(Color.YELLOW);
        circle = new Circle(370,250,5);
        root.getChildren().add(circle);
        circle.setFill(Color.YELLOW);

        //ручка
        Ellipse ellipse = new Ellipse(320,160,15, 5);
        ellipse.setFill(Color.YELLOW);
        ellipse.setStroke(Color.BLACK);
        ellipse.setStrokeType(StrokeType.OUTSIDE);
        root.getChildren().add(ellipse);

        // Animation
        int cycleCount = 2;
        int time = 2000;

        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(time), root);
        scaleTransition.setToX(2);
        scaleTransition.setToY(2);
        scaleTransition.setAutoReverse(true);

        RotateTransition rotateTransition = new RotateTransition(Duration.millis(time), root);
        rotateTransition.setByAngle(360f);
        rotateTransition.setCycleCount(cycleCount);
        rotateTransition.setAutoReverse(true);

        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(time), root);
        translateTransition.setFromX(150);
        translateTransition.setToX(50);
        translateTransition.setCycleCount(cycleCount+1);
        translateTransition.setAutoReverse(true);

        TranslateTransition translateTransition2 = new TranslateTransition(Duration.millis(time), root);
        translateTransition2.setFromX(50);
        translateTransition2.setToX(150);
        translateTransition2.setCycleCount(cycleCount+1);
        translateTransition2.setAutoReverse(true);

        ScaleTransition scaleTransition2 = new ScaleTransition(Duration.millis(time), root);
        scaleTransition2.setToX(0.1);
        scaleTransition2.setToY(0.1);
        scaleTransition2.setCycleCount(cycleCount);
        scaleTransition2.setAutoReverse(true);

        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(
                rotateTransition,
                scaleTransition,
                scaleTransition2,
                translateTransition
        );
        parallelTransition.setCycleCount(Timeline.INDEFINITE);
        parallelTransition.play();
        // End of animation


        primaryStage.setTitle("Lorry");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
