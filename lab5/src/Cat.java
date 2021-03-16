import com.sun.j3d.loaders.Scene;
import com.sun.j3d.loaders.objectfile.ObjectFile;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;

import javax.media.j3d.*;
import javax.swing.*;
import javax.vecmath.Color3f;
import javax.vecmath.Color4f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;
import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class Cat extends JFrame {
    static SimpleUniverse universe;
    static Scene scene;
    static Map<String, Shape3D> nameMap;
    static BranchGroup root;
    static Canvas3D canvas;

    static TransformGroup wholeCat;
    static Transform3D transform3D;

    public Cat() throws IOException {
        configureWindow();
        configureCanvas();
        configureUniverse();
        addModelToUniverse();
        setCatElementsList();
        addBoatAppearance();
        addCatAppearance();
        addImageBackground();
        addLightToUniverse();
        addOtherLight();
        ChangeViewAngle();
        root.compile();
        universe.addBranchGraph(root);
    }

    public static Scene getSceneFromFile(String location) throws IOException {
        return new ObjectFile(ObjectFile.RESIZE | ObjectFile.TRIANGULATE | ObjectFile.STRIPIFY)
                .load(new FileReader(location));
    }

    public static void main(String[] args) {
        try {
            Cat cat = new Cat();
            cat.addKeyListener(new AnimationCat(wholeCat, transform3D, cat));
            cat.setVisible(true);
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void configureWindow() {
        setTitle("Cat Animation");
        setSize(760, 640);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void configureCanvas() {
        canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        canvas.setDoubleBufferEnable(true);
        getContentPane().add(canvas, BorderLayout.CENTER);
    }

    private void configureUniverse() {
        root = new BranchGroup();
        universe = new SimpleUniverse(canvas);
        universe.getViewingPlatform().setNominalViewingTransform();
    }

    private void addModelToUniverse() throws IOException {
        scene = getSceneFromFile("resources//cat.obj");
        root = scene.getSceneGroup();
    }

    private void addLightToUniverse() {
        Bounds bounds = new BoundingSphere();
        Color3f color = new Color3f(65 / 255f, 30 / 255f, 25 / 255f);
        Vector3f lightdirection = new Vector3f(-1f, -1f, -1f);
        DirectionalLight dirlight = new DirectionalLight(color, lightdirection);
        dirlight.setInfluencingBounds(bounds);
        root.addChild(dirlight);
    }

    private void printModelElementsList(Map<String, Shape3D> nameMap) {
        for (String name : nameMap.keySet()) {
            System.out.printf("Name: %s\n", name);
        }
    }

    private void setCatElementsList() {
        nameMap = scene.getNamedObjects();
        printModelElementsList(nameMap);


        wholeCat = new TransformGroup();
        transform3D = new Transform3D();
        transform3D.rotX(-Math.PI / 2);
        transform3D.rotY(-Math.PI / 2);
        wholeCat.setTransform(transform3D);

        root.removeChild(nameMap.get("20430_cat"));
        wholeCat.addChild(nameMap.get("20430_cat"));
        wholeCat.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        root.addChild(wholeCat);
    }

    private Shape3D getBoatShape() throws IOException {
        Scene boatScene = getSceneFromFile("resources\\Boat.obj");
        Shape3D boat = (Shape3D) boatScene.getNamedObjects().get("frame0");
        boatScene.getSceneGroup().removeAllChildren();
        return boat;
    }

    Texture getTexture(String path) {
        TextureLoader textureLoader = new TextureLoader("resources\\" + path, "LUMINANCE", canvas);
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

    private void addCatAppearance() {
        Appearance appearance = new Appearance();

        appearance.setTexture(getTexture("web.png"));
        TextureAttributes texAttr = new TextureAttributes();
        texAttr.setTextureMode(TextureAttributes.COMBINE);

        appearance.setTextureAttributes(texAttr);
        appearance.setMaterial(getMaterial());

        Shape3D cat = nameMap.get("20430_cat");
        cat.setAppearance(appearance);
    }

    private void addBoatAppearance() throws IOException {
        Appearance appearance = new Appearance();

        appearance.setTexture(getTexture("white.jpg"));
        TextureAttributes texAttr = new TextureAttributes();
        texAttr.setTextureMode(TextureAttributes.COMBINE);

        appearance.setTextureAttributes(texAttr);
        //appearance.setMaterial(getMaterial());

        Shape3D boat = getBoatShape();
        boat.setAppearance(appearance);

        root.addChild(boat);
    }

    private void addImageBackground() {
        TextureLoader loader = new TextureLoader("resources\\forest.jpg", canvas);
        Background background = new Background(loader.getImage());
        background.setImageScaleMode(Background.SCALE_FIT_ALL);

        BoundingSphere bounds = new BoundingSphere(new Point3d(9.0, 0.0, 0.0), 0.0);
        background.setApplicationBounds(bounds);

        root.addChild(background);
    }

    private void ChangeViewAngle() {
        ViewingPlatform vp = universe.getViewingPlatform();
        TransformGroup vpGroup = vp.getMultiTransformGroup().getTransformGroup(0);
        Transform3D vpTranslation = new Transform3D();
        Vector3f translationVector = new Vector3f(0.0F, -0.1F, 6F);
        vpTranslation.setTranslation(translationVector);
        vpGroup.setTransform(vpTranslation);
    }

    private void addOtherLight() {
        AmbientLight ambientLight = new AmbientLight(new Color3f(Color.WHITE));
        Vector3f lightDirection = new Vector3f(-1F, -1F, -1F);
        Color3f lightColor = new Color3f(Color.BLACK);
        DirectionalLight directionalLight = new DirectionalLight(lightColor, lightDirection);

        Bounds influenceRegion = new BoundingSphere();

        ambientLight.setInfluencingBounds(influenceRegion);
        directionalLight.setInfluencingBounds(influenceRegion);

        root.addChild(ambientLight);
        root.addChild(directionalLight);
    }
}
