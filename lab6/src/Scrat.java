import com.sun.j3d.loaders.Scene;
import com.sun.j3d.loaders.objectfile.ObjectFile;
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;

import javax.media.j3d.*;
import javax.swing.*;
import javax.vecmath.*;
import java.io.FileNotFoundException;
import java.util.Hashtable;

public class Scrat extends JFrame {
    public Canvas3D myCanvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());

    public Scrat() throws FileNotFoundException {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SimpleUniverse su = new SimpleUniverse(myCanvas3D);
        su.getViewingPlatform().setNominalViewingTransform();

        createSceneGraph(su);
        addLight(su);

        OrbitBehavior ob = new OrbitBehavior(myCanvas3D);
        ob.setSchedulingBounds(
            new BoundingSphere(
                new Point3d(0.0, 0.0, 0.0),
                Double.MAX_VALUE
            )
        );

        su.getViewingPlatform().setViewPlatformBehavior(ob);

        setTitle("scrat");
        setSize(700, 700);

        getContentPane().add("Center", myCanvas3D);

        setVisible(true);
    }

    public static void setToMyDefaultAppearance(Appearance app, Color3f color) {
        app.setMaterial(new Material(color, color, color, color, 150.0f));
    }

    public static void main(String[] args) throws FileNotFoundException {
        new Scrat();
    }

    public void createSceneGraph(SimpleUniverse su) throws FileNotFoundException {
        ObjectFile f = new ObjectFile(ObjectFile.RESIZE);
        BoundingSphere bs = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), Double.MAX_VALUE);
        BranchGroup scratBranchGroup = new BranchGroup();
        // Background scratBackground = new Background(new Color3f(-1.0f, -1.0f, 1.0f));

        Scene scratScene = f.load("resources/scrat.obj");
        Scene catScene = f.load("resources/cat.obj");

        Hashtable roachNamedObjects = scratScene.getNamedObjects();
        Hashtable catNamedObjects = catScene.getNamedObjects();

        // start animation
        Transform3D startTransformation = new Transform3D();
        startTransformation.setScale(2.0 / 6);

        Transform3D combinedStartTransformation = new Transform3D();
        combinedStartTransformation.mul(startTransformation);

        TransformGroup scratStartTransformGroup = new TransformGroup(combinedStartTransformation);

        int movesCount = 100; // moves count
        int movesDuration = 500; // moves for 0,3 seconds
        int startTime = 0; // launch animation after timeStart seconds

        Appearance catAppearance = new Appearance();

        Shape3D cat = (Shape3D) catNamedObjects.get("20430_cat");
        catScene.getSceneGroup().removeAllChildren();

        TransformGroup wholeCat = new TransformGroup();
        Transform3D transform3D = new Transform3D();
        transform3D.rotX(-Math.PI / 2);

        transform3D.setScale(0.1);
        wholeCat.setTransform(transform3D);
        wholeCat.addChild(cat);

        catAppearance.setTexture(getTexture("web.png"));
        TextureAttributes texAttr = new TextureAttributes();
        texAttr.setTextureMode(TextureAttributes.COMBINE);
        catAppearance.setTextureAttributes(texAttr);
        catAppearance.setMaterial(getMaterial());
        cat.setAppearance(catAppearance);

        scratBranchGroup.addChild(wholeCat);

        Appearance taleAppearance = new Appearance();
        setToMyDefaultAppearance(taleAppearance, new Color3f(0.4f, 0.3f, 0.2f));

        Alpha taleRotAlpha = new Alpha(
            movesCount,
            Alpha.INCREASING_ENABLE,
            startTime,
            0,
            movesDuration,
            0,
            0,
            0,
            0,
            0
        );

        Shape3D tale = (Shape3D) roachNamedObjects.get("tale");
        tale.setAppearance(taleAppearance);
        TransformGroup taleTransformGroup = new TransformGroup();
        taleTransformGroup.addChild(tale.cloneTree());

        Transform3D taleRotAxis = new Transform3D();
        taleRotAxis.set(new Vector3d(0.0, 0.0, 0.0));
        taleRotAxis.setRotation(new AxisAngle4d(0, 0, -0.1, Math.PI / 2));

        RotationInterpolator taleRotationInterap = new RotationInterpolator(
                taleRotAlpha,
                taleTransformGroup,
                taleRotAxis,
                0.0f,
                (float) Math.PI * 2
        );
        taleRotationInterap.setSchedulingBounds(bs);
        taleTransformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        taleTransformGroup.addChild(taleRotationInterap);

        Appearance handAppearance = new Appearance();
        setToMyDefaultAppearance(handAppearance, new Color3f(0.3f, 0.2f, 0.1f));

        Alpha rightHandAlpha = new Alpha(
                movesCount,
                Alpha.INCREASING_ENABLE,
                startTime,
                0,
                movesDuration,
                0,
                0,
                0,
                0,
                0
        );

        Shape3D rightHand = (Shape3D) roachNamedObjects.get("right_hand");
        rightHand.setAppearance(handAppearance);
        TransformGroup rightHandTransformGroup = new TransformGroup();
        rightHandTransformGroup.addChild(rightHand.cloneTree());

        Transform3D rightHandRotateAxis = new Transform3D();
        rightHandRotateAxis.rotZ(Math.PI / 8);

        RotationInterpolator wheelRotationInter = new RotationInterpolator(
                rightHandAlpha,
                rightHandTransformGroup,
                rightHandRotateAxis,
                0.0f,
                (float) Math.PI / 2
        );

        wheelRotationInter.setSchedulingBounds(bs);
        rightHandTransformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        rightHandTransformGroup.addChild(wheelRotationInter);

        Alpha leftHandAlpha = new Alpha(
                movesCount,
                Alpha.INCREASING_ENABLE,
                startTime,
                0,
                movesDuration,
                0,
                0,
                0,
                0,
                0
        );

        Shape3D leftHand = (Shape3D) roachNamedObjects.get("left_hand");
        leftHand.setAppearance(handAppearance);

        TransformGroup leftHandTransformGroup = new TransformGroup();
        leftHandTransformGroup.addChild(leftHand.cloneTree());

        Transform3D leftHandRotateAxis = new Transform3D();
        leftHandRotateAxis.rotZ(Math.PI / 8);

        RotationInterpolator leftHandRotationInter = new RotationInterpolator(
                leftHandAlpha,
                leftHandTransformGroup,
                leftHandRotateAxis,
                0.0f,
                (float) - Math.PI / 2
        );

        leftHandRotationInter.setSchedulingBounds(bs);
        leftHandTransformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        leftHandTransformGroup.addChild(leftHandRotationInter);

        // body
        TransformGroup sceneGroup = new TransformGroup();
        sceneGroup.addChild(taleTransformGroup);
        sceneGroup.addChild(rightHandTransformGroup);
        sceneGroup.addChild(leftHandTransformGroup);

        TransformGroup bodyTransformGroup = new TransformGroup();
        Shape3D nShape = (Shape3D) roachNamedObjects.get("body");
        nShape.setAppearance(handAppearance);
        bodyTransformGroup.addChild(nShape.cloneTree());
        sceneGroup.addChild(bodyTransformGroup.cloneTree());

        Appearance eyeAppearance = new Appearance();
        setToMyDefaultAppearance(eyeAppearance, new Color3f(1.0f, 1.0f, 1.0f));

        Appearance noseAppearance = new Appearance();
        setToMyDefaultAppearance(noseAppearance, new Color3f(0.0f, 0.0f, 0.0f));

        TransformGroup tgLeftEye = new TransformGroup();
        Shape3D leftEyeShape = (Shape3D) roachNamedObjects.get("left_eye");
        leftEyeShape.setAppearance(eyeAppearance);

        tgLeftEye.addChild(leftEyeShape.cloneTree());
        sceneGroup.addChild(tgLeftEye.cloneTree());

        TransformGroup tgLeftEye1 = new TransformGroup();
        Shape3D leftEyeShape1 = (Shape3D) roachNamedObjects.get("left_eye1");
        leftEyeShape1.setAppearance(noseAppearance);

        tgLeftEye1.addChild(leftEyeShape1.cloneTree());
        sceneGroup.addChild(tgLeftEye1.cloneTree());

        TransformGroup tgRightEye = new TransformGroup();
        Shape3D rightEyeShape = (Shape3D) roachNamedObjects.get("right_eye");
        rightEyeShape.setAppearance(eyeAppearance);

        tgRightEye.addChild(rightEyeShape.cloneTree());
        sceneGroup.addChild(tgRightEye.cloneTree());

        TransformGroup tgRightEye1 = new TransformGroup();
        Shape3D rightEyeShape1 = (Shape3D) roachNamedObjects.get("right_eye1");
        rightEyeShape1.setAppearance(noseAppearance);

        tgRightEye1.addChild(rightEyeShape1.cloneTree());
        sceneGroup.addChild(tgRightEye1.cloneTree());

        TransformGroup noseTransformGroup = new TransformGroup();
        Shape3D noseShape = (Shape3D) roachNamedObjects.get("nose");
        noseShape.setAppearance(noseAppearance);

        noseTransformGroup.addChild(noseShape.cloneTree());
        sceneGroup.addChild(noseTransformGroup.cloneTree());

        Appearance nutAppearance = new Appearance();
        setToMyDefaultAppearance(nutAppearance, new Color3f(0.0f, 0.2f, 0.0f));

        TransformGroup nutTransformGroup = new TransformGroup();
        Shape3D nutShape = (Shape3D) roachNamedObjects.get("nut");
        nutShape.setAppearance(nutAppearance);

        nutTransformGroup.addChild(nutShape.cloneTree());
        sceneGroup.addChild(nutTransformGroup.cloneTree());

        TextureLoader loader = new TextureLoader("resources//forest.jpg", myCanvas3D);
        Background scratBackground = new Background(loader.getImage());
        scratBackground.setImageScaleMode(Background.SCALE_FIT_ALL);

        BoundingSphere backgroundBounds = new BoundingSphere(new Point3d(9.0, 0.0, 0.0), 0.0);
        scratBackground.setApplicationBounds(backgroundBounds);

        TransformGroup whiteTransformGroup = translate(
                scratStartTransformGroup,
                new Vector3f(0.0f, 0.0f, -0.5f)
        );

        scratBranchGroup.addChild(rotate(whiteTransformGroup, new Alpha(10, 5000)));
        scratStartTransformGroup.addChild(sceneGroup);

        // adding the scrat background to branch group
        BoundingSphere bounds = new BoundingSphere(new Point3d(120.0, 250.0, 100.0), Double.MAX_VALUE);
        scratBackground.setApplicationBounds(bounds);
        scratBranchGroup.addChild(scratBackground);
        scratBranchGroup.compile();

        su.addBranchGraph(scratBranchGroup);
    }

    public void addLight(SimpleUniverse su) {
        BranchGroup branchLightGroup = new BranchGroup();
        BoundingSphere bounds = new BoundingSphere(
            new Point3d(0.0, 0.0, 0.0),
            100.0
        );

        Color3f colour = new Color3f(1.0f, 1.0f, 1.0f);
        Vector3f direction = new Vector3f(-1.0f, 0.0f, -0.5f);
        DirectionalLight light = new DirectionalLight(colour, direction);

        light.setInfluencingBounds(bounds);
        branchLightGroup.addChild(light);

        su.addBranchGraph(branchLightGroup);
    }

    private TransformGroup translate(Node node, Vector3f vector) {
        Transform3D transform3D = new Transform3D();
        transform3D.setTranslation(vector);

        TransformGroup transformGroup = new TransformGroup();
        transformGroup.setTransform(transform3D);

        transformGroup.addChild(node);

        return transformGroup;
    }

    private TransformGroup rotate(Node node, Alpha alpha) {
        TransformGroup transformGroup = new TransformGroup();
        transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        RotationInterpolator interpolator = new RotationInterpolator(alpha, transformGroup);
        interpolator.setSchedulingBounds(
            new BoundingSphere(
                new Point3d(0.0, 0.0, 0.0),
                1.0
            )
        );

        transformGroup.addChild(interpolator);
        transformGroup.addChild(node);

        return transformGroup;
    }

    Texture getTexture(String path) {
        TextureLoader textureLoader = new TextureLoader("resources\\" + path, "LUMINANCE", myCanvas3D);
        Texture texture = textureLoader.getTexture();
        texture.setBoundaryModeS(Texture.WRAP);
        texture.setBoundaryModeT(Texture.WRAP);
        texture.setBoundaryColor(new Color4f(0.0f, 1.0f, 0.0f, 0.0f));
        return texture;
    }

    Material getMaterial() {
        Material material = new Material();

        material.setAmbientColor(new Color3f(0.33f, 0.26f, 0.23f));
        material.setDiffuseColor(new Color3f(0.50f, 0.11f, 0.00f));
        material.setSpecularColor(new Color3f(0.95f, 0.73f, 0.00f));
        material.setShininess(0.3f);
        material.setLightingEnable(true);

        return material;
    }

}

