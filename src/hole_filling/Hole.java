package hole_filling;

import java.util.HashSet;
import java.util.Set;

/**
 * This class represents a hole inside an image. Each hole described by pixels, boundary, and connected type
 */
public class Hole {
    private Set<Pixel> pixels;
    private Set<Pixel> boundary;
    private int connectedType;

    /**
     *
     * @param cType - connected type which should be 4 or 8
     */
    public Hole(int cType){
        this.connectedType = cType;
        pixels = new HashSet();
        boundary = new HashSet();
    }

    public Set<Pixel> getPixels() {
        return pixels;
    }

    public Set<Pixel> getBoundary() {
        return boundary;
    }

    public void addToPixels(Pixel p){
        pixels.add(p);
    }

    public void addToBoundary(Pixel p){
        boundary.add(p);
    }

    public int getConnectedType() {
        return connectedType;
    }

    public boolean isHole(Pixel p){
        return pixels.contains(p);
    }

    public boolean isBoundary(Pixel p){
        return boundary.contains(p);
    }
}
