package boundary_tracing;

import hole_filling.HoledImage;
import hole_filling.Pixel;

import java.util.*;

/**
 *
 * @author nayef
 */
public class SquareTracing {

    public static List<Pixel> getBoundary(HoledImage srcImage) {
        Pixel[][] image = srcImage.getGrayscalePixels();

        List<Pixel> pixels = new ArrayList<>();
        Set<String> cache = new HashSet<>();

        DirectedPixel startPixel = getStartingPixel(srcImage);
        DirectedPixel currPixel = new DirectedPixel(startPixel);

        do {
            if (currPixel.getVal() == -1f || srcImage.getHole().isBoundary(currPixel)){
                if(!cache.contains(currPixel.getY() + "$" + currPixel.getX())) {
                    pixels.add(image[currPixel.getY()][currPixel.getX()]);
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