import hole_filling.Pixel;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.CvType;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


public class tmp {

    // Compulsory
    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

    public static void OpenCVBasicExample(){
        System.out.println("Welcome to OpenCV " + Core.VERSION);
        Mat m = new Mat(5, 10, CvType.CV_8UC1, new Scalar(0));
        System.out.println("OpenCV Mat: " + m);
        Mat mr1 = m.row(1);
        mr1.setTo(new Scalar(1));
        Mat mc5 = m.col(5);
        mc5.setTo(new Scalar(5));
        System.out.println("OpenCV Mat data:\n" + m.dump());
    }

    public static void convertRgbToGrey(String path, String imageName){
        try {
            File input = new File(path + imageName);
            BufferedImage srcImage = ImageIO.read(input);

            byte[] srcData = ((DataBufferByte) srcImage.getRaster().getDataBuffer()).getData();
            Mat src = new Mat(srcImage.getHeight(), srcImage.getWidth(), CvType.CV_8UC3);
            src.put(0, 0, srcData);

            Mat dst = new Mat(srcImage.getHeight(),srcImage.getWidth(),CvType.CV_8UC1);
            Imgproc.cvtColor(src, dst, Imgproc.COLOR_RGB2GRAY);
            byte[] dstData = new byte[dst.rows() * dst.cols() * (int)(dst.elemSize())];
            dst.get(0, 0, dstData);
            BufferedImage dstImage = new BufferedImage(dst.cols(),dst.rows(), BufferedImage.TYPE_BYTE_GRAY);
            dstImage.getRaster().setDataElements(0, 0, dst.cols(), dst.rows(), dstData);

            File ouptut = new File(path + "Grayscale" + imageName);
            ImageIO.write(dstImage, "jpg", ouptut);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args){
//        OpenCVBasicExample();

//        String path = "C:\\Users\\aviel\\Downloads\\Hole_Filling\\";
//        String imageName = "test1.png";
//        convertRgbToGrey(path, imageName);
//        hole_filling.Pixel[][] pixels = null;

        try {

            int[][] mat = new int[8][12];

            for(int[] line : mat)
                Arrays.fill(line, 0);

            //Boundary (18)
            mat[1][4] = 1;
            mat[1][5] = 1;
            mat[2][6] = 1;
            mat[2][7] = 1;
            mat[3][8] = 1;
            mat[2][8] = 1;
            mat[1][9] = 1;
            mat[2][10] = 1;
            mat[3][10] = 1;
            mat[4][10] = 1;
            mat[5][9] = 1;
            mat[5][8] = 1;
            mat[5][7] = 1;
            mat[6][6] = 1;
            mat[5][5] = 1;
            mat[4][4] = 1;
            mat[3][4] = 1;
            mat[2][3] = 1;

            // hole_filling.Hole (13)
            mat[2][4] = -1;
            mat[2][5] = -1;
            mat[3][5] = -1;
            mat[4][5] = -1;
            mat[3][6] = -1;
            mat[4][6] = -1;
            mat[5][6] = -1;
            mat[3][7] = -1;
            mat[4][7] = -1;
            mat[4][8] = -1;
            mat[2][9] = -1;
            mat[3][9] = -1;
            mat[4][9] = -1;

            printMat(mat, mat.length, mat[0].length);

            Set<Pixel> H = new HashSet<>();
            Set<Pixel> B = new HashSet<>();

            findHole(mat, H, mat.length, mat[0].length);
            findBoundary(mat, H, B);

//            List<Pixel> points = SquareTracing.getBoundary(mat);

            System.out.println();
            //            String maskName = "test1Mask.png";
//            File input = new File(path + maskName);
//            BufferedImage buffer = ImageIO.read(input);
//            pixels = new hole_filling.Pixel[buffer.getHeight()][buffer.getWidth()];
//
//            byte[] data = ((DataBufferByte) buffer.getRaster().getDataBuffer()).getData();
//            Mat mat = new Mat(buffer.getHeight(), buffer.getWidth(), CvType.CV_8UC4);
//            mat.put(0, 0, data);
//
//            int countTot = 0;
//            int countNotMinus1 = 0;
//
//            // Modify image pixels
//            for(int i=0; i<512; i++)
//                for(int j=0; j<512; j++) {
//                    float value;
//                    countTot++;
//                    int pixel = buffer.getRGB(i, j);
//                    if(pixel != -1){
//                        value = -1f;
//                        countNotMinus1++;
//                    }
//                    else {
//                        Color color = new Color(pixel, true);
//                        //Retrieving the R G B values
//                        int red = color.getRed();
//                        int green = color.getGreen();
//                        int blue = color.getBlue();
//                        //Modifying the RGB values
////                    green = 150;
////                    blue = 150;
//                        //Creating new Color object
//                        color = new Color(red, green, blue);
//                        float greyscale = rgbToGrayscale(color);
//                        value = greyscale;
//                        //Setting new Color object to the image
////                    buffer.setRGB(i, j, color.getRGB());
//                    }
//                    pixels[i][j] = new hole_filling.Pixel(i,j,value);
//                }
//
//            System.out.println();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void findHole(int[][] mat, Set<Pixel> H, int m, int n){
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (mat[i][j] == -1) {
                    H.add(new Pixel(i, j,-1f));
                }
            }
        }
    }

    private static void findBoundary(int[][] mat, Set<Pixel> H, Set<Pixel> B){
        Set<String> cache = new HashSet<String>();
        for (Pixel p : H) {
            int x = p.getX();
            int y = p.getY();
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    // TODO - assuming hole.getConnectedType() == 4 &&
                    if (Math.abs(i) + Math.abs(j) == 2) {
                        // skip diagonal neighbors, in case of 4-connected type
                        continue;
                    }
                    int newY = y+i;
                    int newX = x+j;
                    if (!cache.contains(newY + "$" + newX) && !(i == 0 && j == 0) && !(mat[y + i][x + j] == -1)) { //
                        cache.add(newY + "$" + newX);
                        B.add(new Pixel(y + i, x + j, mat[y + i][x + j]));
                    }
                }
            }
        }
    }

    private static void printMat(int[][] mat, int m, int n) {

        StringBuilder sb = new StringBuilder();

        for(int i=0; i<m; i++) {
            for (int j = 0; j < n; j++) {
                String space = "   ";
                if(j+1 < n && mat[i][j+1] == -1)
                    space = "  ";
                sb.append(mat[i][j] + space);
            }
            sb.append("\n");
        }

        System.out.println(sb);
    }


    private static float rgbToGrayscale(Color c) {
        float avg = (c.getRed() + c.getGreen() + c.getBlue()) / 3;
        return avg / 255;
    }

}
