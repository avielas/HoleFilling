package hole_filling;

import java.util.*;

/**
 * This class represents the Boundary Tracing algorithm and implement its functionality (see wikipedia for more background).
 * This algorithm run when you set isOptimized=true at HoleFillingLib.fillHole() function call, to optimize the hole filling algorithm.
 * It approximates the result in O(n) to a high degree of accuracy
 */
public class SquareTracing {

    /***
     * This function implements the algorithm which pass boundary pixels by order (neighbor by neighbor) and return them
     * @param srcImage - holed image
     * @return List<Pixel> ordered pixels list (neighbor by neighbor)
     */
    public static List<Pixel> getBoundary(HoledImage srcImage) {
        Pixel[][] image = srcImage.getGrayscalePixels();

        List<Pixel> pixels = new ArrayList<>();
        //cache - to prevent adding pixels which already visited
        Set<Pixel> cache = new HashSet<>();

        DirectedPixel startPixel = getStartingPixel(srcImage);
        DirectedPixel currPixel = new DirectedPixel(startPixel);

        do {
            if (currPixel.getVal() == -1f || srcImage.getHole().getBoundary().contains(new Pixel(currPixel.getY(), currPixel.getX(), currPixel.val))){
                if(!cache.contains(new Pixel(currPixel.getY(), currPixel.getX(), currPixel.val))) {
                    pixels.add(image[currPixel.getY()][currPixel.getX()]);
                    cache.add(new Pixel(currPixel.getY(), currPixel.getX(), currPixel.val));
                }
                goLeft(currPixel);
            } else {
                goRight(currPixel);
            }
        } while (!startPixel.equals(currPixel));

        return pixels;
    }

    /**
     * Find the most bottom hole pixel
     * @param image - holed image
     * @return DirectedPixel which is the most bottom hole pixel
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