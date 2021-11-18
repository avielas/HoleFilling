package boundary_tracing;

import hole_filling.Pixel;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author nayef
 */
public class SquareTracing {

    public static Set<String> cache = new HashSet<>();

    public static List<Pixel> getBoundary(int[][] srcImage) {
        int[][] image = srcImage.clone();
        clearBorder(image);

        List<Pixel> pixels = new LinkedList();

        DirectedPixel startPixel = getStartingPixel(image);
        DirectedPixel currPixel = new DirectedPixel(startPixel);

        do {
            if (image[currPixel.getY()][currPixel.getX()] == 1) {
                if(!cache.contains(currPixel.getY() + "$" + currPixel.getX())) {
                    pixels.add(new DirectedPixel(currPixel));
                    cache.add(currPixel.getY() + "$" + currPixel.getX());
                }
                 goLeft(currPixel);
            } else {
                goRight(currPixel);
            }

        } while (!startPixel.equals(currPixel));

        return pixels;
    }

    // find the most bottom pixel
    private static DirectedPixel getStartingPixel(int[][] image) {
        for (int y = image.length - 1; y >= 0; y--) {
            for (int x = image[y].length - 1; x >= 0; x--) {
                if (image[y][x] == 1) {
                    return new DirectedPixel(x, y, image[y][x]);
                }
            }
        }
        return null;
    }

    private static void clearBorder(int[][] image) {
        for (int y = 0; y < image.length; y++) {
            for (int x = 0; x < image[y].length; x++) {
                if (y == 0 || x == 0 || y == image.length - 1 || x == image[y].length - 1) {
                    image[y][x] = 0;
                }
            }
        }
    }

    public static void faceRight(DirectedPixel pixel) {
        pixel.setDirection(pixel.getDirection().rotateRight());
    }

    private static void faceLeft(DirectedPixel pixel) {
        pixel.setDirection(pixel.getDirection().rotateLeft());
    }

    private static void stepForward(DirectedPixel pixel) {
        if (pixel.getDirection() == Direction.NORTH) {
            pixel.setY(pixel.getY()-1);
        }
        else if (pixel.getDirection() == Direction.EAST) {
            pixel.setX(pixel.getX()+1);
        }
        else if (pixel.getDirection() == Direction.SOUTH) {
            pixel.setY(pixel.getY()+1);
        }
        else if (pixel.getDirection() == Direction.WEST) {
            pixel.setX(pixel.getX()-1);
        }
    }

    public static void goLeft(DirectedPixel pixel) {
        faceLeft(pixel);
        stepForward(pixel);
    }

    public static void goRight(DirectedPixel pixel) {
        faceRight(pixel);
        stepForward(pixel);
    }
}