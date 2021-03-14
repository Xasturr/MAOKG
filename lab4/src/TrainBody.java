//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.image.TextureLoader;
import java.awt.Color;
import java.awt.Container;
import java.io.IOException;
import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.vecmath.Color3f;
import javax.vecmath.Color4f;

public class TrainBody {
    private static final int primFlags = 3;

    public TrainBody() {
    }

    public static Box getTopPart() throws IOException {
        return new Box(0.3F, 0.1F, 0.3F, 3, getBackPartAppearence());
    }

    public static Box getBackPart() throws IOException {
        return new Box(0.8F, 0.3F, 0.3F, 3, getBackPartAppearence());
    }

    public static Box getFrontPart() throws IOException {
        return new Box(0.3F, 0.15F, 0.27F, 3, getFrontPartAppearence());
    }

    public static Cylinder getFrontCylinderPart() throws IOException {
        return new Cylinder(0.3F, 0.5F, 3, getFrontPartAppearence());
    }

    public static Cylinder getFrontWindow() throws IOException {
        return new Cylinder(0.2F, 0.51F, 3, getWindowAppearence());
    }

    public static Cylinder getWheel() throws IOException {
        return new Cylinder(0.2F, 0.05F, 3, getWheelAppearence());
    }

    public static Cylinder getSmallWheel() throws IOException {
        return new Cylinder(0.08F, 0.05F, 3, getWheelAppearence());
    }

    private static Appearance createTexturedAppearance(String texturePath) throws IOException {
        Texture texture = (new TextureLoader(texturePath, "LUMINANCE", new Container())).getTexture();
        texture.setBoundaryModeS(3);
        texture.setBoundaryModeT(3);
        texture.setBoundaryColor(new Color4f(0.0F, 1.0F, 1.0F, 0.0F));
        TextureAttributes attributes = new TextureAttributes();
        attributes.setTextureMode(2);
        Appearance appearance = new Appearance();
        appearance.setTexture(texture);
        appearance.setTextureAttributes(attributes);
        return appearance;
    }

    private static Appearance getBackPartAppearence() throws IOException {
        Color3f emissive = new Color3f(new Color(0, 0, 0));
        Color3f ambient = new Color3f(new Color(50, 50, 200));
        Color3f diffuse = new Color3f();
        Color3f specular = new Color3f(new Color(0, 0, 0));
        Appearance appearance = createTexturedAppearance("resources\\metal.jpg");
        appearance.setMaterial(new Material(ambient, emissive, diffuse, specular, 1.0F));
        return appearance;
    }

    private static Appearance getWheelAppearence() throws IOException {
        Color3f emissive = new Color3f(new Color(0, 0, 0));
        Color3f ambient = new Color3f(new Color(100, 100, 100));
        Color3f diffuse = new Color3f();
        Color3f specular = new Color3f(new Color(0, 0, 0));
        Appearance appearance = createTexturedAppearance("resources\\wheel.jpg");
        appearance.setMaterial(new Material(ambient, emissive, diffuse, specular, 1.0F));
        return appearance;
    }

    private static Appearance getFrontPartAppearence() throws IOException {
        Color3f emissive = new Color3f(new Color(0, 0, 0));
        Color3f ambient = new Color3f(new Color(200, 200, 200));
        Color3f diffuse = new Color3f();
        Color3f specular = new Color3f(new Color(0, 0, 0));
        Appearance appearance = createTexturedAppearance("resources\\metal.jpg");
        appearance.setMaterial(new Material(ambient, emissive, diffuse, specular, 1.0F));
        return appearance;
    }

    private static Appearance getWindowAppearence() throws IOException {
        Color3f emissive = new Color3f(new Color(0, 0, 0));
        Color3f ambient = new Color3f(new Color(200, 200, 200));
        Color3f diffuse = new Color3f(Color.blue);
        Color3f specular = new Color3f();
        Appearance appearance = createTexturedAppearance("resources\\glass.jpg");
        appearance.setMaterial(new Material(ambient, emissive, diffuse, specular, 1.0F));
        return appearance;
    }
}
