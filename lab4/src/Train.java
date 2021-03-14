//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.universe.SimpleUniverse;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.media.j3d.AmbientLight;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.Timer;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

public class Train implements ActionListener {
    private final TransformGroup trainTransformGroup = new TransformGroup();
    private final Transform3D trainTransform3D = new Transform3D();
    private float angle = 0.0F;

    private Train() throws IOException {
        Timer timer = new Timer(50, this);
        timer.start();
        BranchGroup scene = this.createSceneGraph();
        SimpleUniverse u = new SimpleUniverse();
        u.getViewingPlatform().setNominalViewingTransform();
        u.addBranchGraph(scene);
    }

    public static void main(String[] args) throws IOException {
        new Train();
    }

    private BranchGroup createSceneGraph() throws IOException {
        BranchGroup root = new BranchGroup();
        this.trainTransformGroup.setCapability(18);
        this.emitTrain();
        root.addChild(this.trainTransformGroup);
        Background background = new Background(new Color3f(1.0F, 1.0F, 1.0F));
        BoundingSphere sphere = new BoundingSphere(new Point3d(0.0D, 0.0D, 0.0D), 100000.0D);
        background.setApplicationBounds(sphere);
        root.addChild(background);
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0D, 0.0D, 0.0D), 100.0D);
        DirectionalLight light = new DirectionalLight(new Color3f(1.0F, 0.5F, 0.4F), new Vector3f(0.8F, 0.8F, 0.0F));
        light.setInfluencingBounds(bounds);
        root.addChild(light);
        Color3f ambientColor = new Color3f(1.0F, 1.0F, 1.0F);
        AmbientLight ambientLightNode = new AmbientLight(ambientColor);
        ambientLightNode.setInfluencingBounds(bounds);
        root.addChild(ambientLightNode);
        return root;
    }

    private void emitTrain() throws IOException {
        this.emitAnimatedBodyTransformer(TrainBody.getFrontCylinderPart());
        this.emitAnimatedBodyTransformer(TrainBody.getFrontWindow());
        Box backTrainPartBody = TrainBody.getBackPart();
        Transform3D backTrainPartTransform = new Transform3D();
        backTrainPartTransform.setTranslation(new Vector3f(-0.3F, 0.0F, 0.0F));
        TransformGroup backTrainPartTransformGroup = new TransformGroup();
        backTrainPartTransformGroup.setTransform(backTrainPartTransform);
        backTrainPartTransformGroup.addChild(backTrainPartBody);
        this.trainTransformGroup.addChild(backTrainPartTransformGroup);
        Box topTrainPartBody = TrainBody.getTopPart();
        Transform3D topTrainPartTransform = new Transform3D();
        topTrainPartTransform.setTranslation(new Vector3f(-0.8F, 0.4F, 0.0F));
        TransformGroup topTrainPartTransformGroup = new TransformGroup();
        topTrainPartTransformGroup.setTransform(topTrainPartTransform);
        topTrainPartTransformGroup.addChild(topTrainPartBody);
        this.trainTransformGroup.addChild(topTrainPartTransformGroup);
        Box frontPartBody = TrainBody.getFrontPart();
        Transform3D frontTrainPartTransform = new Transform3D();
        frontTrainPartTransform.setTranslation(new Vector3f(0.7F, -0.15F, 0.0F));
        TransformGroup frontTrainPartTransformGroup = new TransformGroup();
        frontTrainPartTransformGroup.setTransform(frontTrainPartTransform);
        frontTrainPartTransformGroup.addChild(frontPartBody);
        this.trainTransformGroup.addChild(frontTrainPartTransformGroup);
        this.emitSmallWheel(0.6F, -0.27F, -0.3F);
        this.emitSmallWheel(0.6F, -0.27F, 0.3F);
        this.emitSmallWheel(0.4F, -0.27F, -0.3F);
        this.emitSmallWheel(0.4F, -0.27F, 0.3F);
        this.emitSmallWheel(0.2F, -0.27F, -0.3F);
        this.emitSmallWheel(0.2F, -0.27F, 0.3F);
        this.emitSmallWheel(0.0F, -0.27F, -0.3F);
        this.emitSmallWheel(0.0F, -0.27F, 0.3F);
        this.emitSmallWheel(-0.2F, -0.27F, -0.3F);
        this.emitSmallWheel(-0.2F, -0.27F, 0.3F);
        this.emitWheel(-0.9F, -0.15F, -0.3F);
        this.emitWheel(-0.9F, -0.15F, 0.3F);
        Transform3D transformScale = new Transform3D();
        transformScale.setScale(0.2D);
        this.trainTransformGroup.setTransform(transformScale);
    }

    private void emitWheel(float x, float y, float z) throws IOException {
        TransformGroup trainWheelTransformGroup = new TransformGroup();
        Transform3D wheelTransform = new Transform3D();
        wheelTransform.rotX(1.57D);
        wheelTransform.setTranslation(new Vector3f(x, y, z));
        trainWheelTransformGroup.setTransform(wheelTransform);
        trainWheelTransformGroup.addChild(TrainBody.getWheel());
        this.trainTransformGroup.addChild(trainWheelTransformGroup);
    }

    private void emitSmallWheel(float x, float y, float z) throws IOException {
        TransformGroup trainWheelTransformGroup = new TransformGroup();
        Transform3D wheelTransform = new Transform3D();
        wheelTransform.rotX(1.57D);
        wheelTransform.setTranslation(new Vector3f(x, y, z));
        trainWheelTransformGroup.setTransform(wheelTransform);
        trainWheelTransformGroup.addChild(TrainBody.getSmallWheel());
        this.trainTransformGroup.addChild(trainWheelTransformGroup);
    }

    private void emitAnimatedBodyTransformer(Cylinder body) {
        Transform3D bodyTransform = new Transform3D();
        bodyTransform.rotX(1.57D);
        bodyTransform.setTranslation(new Vector3f(0.55F, 0.0F, 0.0F));
        TransformGroup bodyTransformGroup = new TransformGroup();
        bodyTransformGroup.setTransform(bodyTransform);
        bodyTransformGroup.addChild(body);
        this.trainTransformGroup.addChild(bodyTransformGroup);
    }

    public void actionPerformed(ActionEvent e) {
        this.trainTransform3D.rotY((double)this.angle);
        this.trainTransform3D.setScale(0.5D);
        this.trainTransformGroup.setTransform(this.trainTransform3D);
        this.angle = (float)((double)this.angle + 0.05D);
    }
}
