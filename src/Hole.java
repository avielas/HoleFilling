import java.util.HashSet;
import java.util.Set;

public class Hole {
    private Set<Pixel> pixels;
    private Set<Pixel> boundary;
    private IWeightFunc weightFunc;
    private int connectedType;

    /**
     *
     * @param cType
     * @param weightFunc
     */
    public Hole(int cType, IWeightFunc weightFunc){
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

    public IWeightFunc getWeightFunc() {
        return weightFunc;
    }

    public int getConnectedType() {
        return connectedType;
    }

    public boolean isHole(Pixel p){
        return pixels.contains(p);
    }
}
