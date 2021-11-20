package hole_filling;

import java.util.*;

/**
 *
 * @author nayef
 */
public class SquareTracing {

    public static List<Pixel> getBoundary(HoledImage srcImage) {
        Pixel[][] image = srcImage.getGrayscalePixels();

        List<Pixel> pixels = new ArrayList<>();
        //cache - to prevent adding pixels which already visited
        Set<String> cache = new HashSet<>();
        //boundaryByKeys - for checking if DirectedPixel is on the boundary (isBoundary() function
        //works just with Pixel)
        Set<String> boundaryByKeys = createBoundaryByKeys(srcImage.getHole().getBoundary());

        DirectedPixel startPixel = getStartingPixel(srcImage);
        DirectedPixel currPixel = new DirectedPixel(startPixel);

        do {
            String currPixelKey = currPixel.getY() + "$" + currPixel.getX();
            if (currPixel.getVal() == -1f || boundaryByKeys.contains(currPixelKey)){
                if(!cache.contains(currPixelKey)) {
                    pixels.add(image[currPixel.getY()][currPixel.getX()]);
                    cache.add(currPixelKey);
                }
                goLeft(currPixel);
            } else {
                goRight(currPixel);
            }
        } while (!startPixel.equals(currPixel));

        return pixels;
    }

    private static Set<String> createBoundaryByKeys(Set<Pixel> boundary) {
        Set<String> boundaryByKeys = new HashSet<>();
        for(Pixel p : boundary)
            boundaryByKeys.add(p.getY() + "$" + p.getX());
        return boundaryByKeys;
    }

    /**
     * Find the most bottom pixel
     * @param image
     * @return
     */
    private static DirectedPixel getStartingPixel(HoledImage image) {
        Pixel[][] pixels = image.getGrayscalePixels();
        for (int y = pixels.length - 1; y >= 0; y--) {
            for (int x = pixels[y].length - 1; x >= 0; x--) {
                if (pixels[y][x].getVal() == -1f || image.getHole().isBoundary(pixels[y][x])) {
                    return new DirectedPixel(x, y, pixels[y][x].getVal());
                }
            }
        }
        return null;
    }

    private static void stepForward(DirectedPixel pixel) {
        if (pixel.getDirection() == Direction.UP) {
            pixel.setY(pixel.getY()-1);
        }
        else if (pixel.getDirection() == Direction.RIGHT) {
            pixel.setX(pixel.getX()+1);
        }
        else if (pixel.getDirection() == Direction.DOWN) {
            pixel.setY(pixel.getY()+1);
        }
        else if (pixel.getDirection() == Direction.LEFT) {
            pixel.setX(pixel.getX()-1);
        }
    }

    public static void goLeft(DirectedPixel pixel) {
        // rotate face to left and move step forward
        pixel.rotateLeft();
        stepForward(pixel);
    }

    public static void goRight(DirectedPixel pixel) {
        // rotate face to right and move step forward
        pixel.rotateRight();
        stepForward(pixel);
    }
}