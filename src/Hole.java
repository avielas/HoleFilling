import java.util.HashSet;
import java.util.Set;

public class Hole {
    private Set<Pixel> pixels;
    private Set<Pixel> boundary;
    private WeightFunc weightFunc;
    private int connectedType;

    public Hole(int cType, WeightFunc weightFunc){
        this.connectedType = cType;
        pixels = new HashSet();
        boundary = new HashSet();
        this.weightFunc = weightFunc;
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

    public WeightFunc getWeightFunc() {
        return weightFunc;
    }

    public int getConnectedType() {
        return connectedType;
    }

    public boolean isHole(Pixel p){
        return pixels.contains(p);
    }
}
