/***********************************************************************************************************************
 * @file  Convolution.java
 * @brief Reads image  selected by user and changes the colors by blurring them
 * @author Heba Beshai
 * @date   April 15, 2016
 ***********************************************************************************************************************/
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Convolution {

    public static void main(String[] args) throws IOException {
            BufferedImage inputImage, outImage; // reference for the input and output images
            int[][] red, green, blue;  // array names for the red, green, and blue image parts
            // Read the image from a file specified by the user

            inputImage = readImageFromFile();
            red = getChannel(inputImage, "red");// Obtain the red, green, and blue channels using method getChannel()
            green = getChannel(inputImage, "green");
            blue = getChannel(inputImage, "blue");

          //  double[][] filter = {
            //        {1.0 / 9, 1.0 / 9, 1.0 / 9}, {1.0 / 9, 1.0 / 9, 1.0 / 9}, {1.0 / 9, 1.0 / 9, 1.0 / 9}};

        double[][] filter = {
                {1.0 / 9, 1.0 / 9, 1.0 / 9},
                {1.0 / 9, 1.0 / 9, 1.0 / 9},
                {1.0 / 9, 1.0 / 9, 1.0/9}};
        double[][] filter2 = {
                {1, 1, 1},
                {0, 0, 0}, {-1, -1, -1}};

        /*        for(int i=0;i<3;i++){
            for(int j=0; j<3; j++)
                filter[i][j] = Math.random() - 0.5;
        }
*/
            // Pass the image and filter to convolve() which creates the resulting image
            // and returns its address
            int[][] outred;
            outred = convolve(red, filter2);
            int[][] outgreen;
            outgreen = convolve(green, filter2);
            int[][] outblue;
            outblue = convolve(blue, filter2);

        outImage = arrayToBufferedImage(outred,outgreen,outblue);
        ImageIO.write(outImage, "jpg", new File("image.jpg"));


    }


    public static BufferedImage arrayToBufferedImage(int[][] red, int[][] green, int[][] blue) {
        BufferedImage outImage = new BufferedImage(red.length, red[0].length,
                BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < red.length; i++) {
            for (int j = 0; j < red[i].length; j++) {
                int value = red[i][j] << 16 | green[i][j] << 8 | blue[i][j];
                outImage.setRGB(i, j, value);
            }
        }

        return outImage;
    }



    public static BufferedImage readImageFromFile() {
        BufferedImage img = null;
        final JFileChooser fileChooser = new JFileChooser();
        File selectedFile = null;
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));

        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
        }
        try {
            img = ImageIO.read(selectedFile);
        } catch (IOException e) {
        }
        return img;
    }

    public static int[][] getChannel(BufferedImage img, String channel) {
        int[][] table = new int[img.getWidth()][img.getHeight()];
        int flag, shift;
        if (channel.toLowerCase().equals("red")) {
            flag = 16711680;
            shift = 16;
        } else if (channel.toLowerCase().equals("green")) {
            flag = 65280;
            shift = 8;
        } else if (channel.toLowerCase().equals("blue")) {
            flag = 255;
            shift = 0;
        } else {
            return null;
        }

        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                table[i][j] = (img.getRGB(i, j) & flag) >> shift;
            }
        }
        return table;
    }

    public static int[][] convolve(int[][] image, double[][] filter) {
        int[][] outImage = new int[image.length][image[0].length];
        //does the convultion
        for (int i = 1; i < image.length - 1; i++) {         //add a minus one to reduce the boundaries
            for (int j = 1; j < image[0].length - 1; j++) { //good but also add the minus one
                //outImage[i][j] = (int) (image[i-1][j-1] * filter[0][0]);
                //need to multiple the numbers in the pixel inn convolution with filter
                //also good, but need to remember to add the minus one for the corners
                //  filter(image);
                outImage[i][j] = (int) Math.round((image[i - 1][j - 1] * filter[0][0]) + (image[i - 1][j]
                        * filter[0][1]) + (image[i - 1][j + 1] * filter[0][2]) +
                        (image[i][j - 1] * filter[1][0]) +
                        (image[i][j] * filter[1][1]) +
                        (image[i][j + 1] * filter[1][2]) +
                        (image[i + 1][j - 1] * filter[2][0]) +
                        (image[i + 1][j] * filter[2][1]) +
                        (image[i + 1][j + 1] * filter[2][2]));

                // outImage[i][j] = (int) (image[i-1][j-1] * filter[0][2]);
                // 0,0 and 0,1 etc represent the boxes on the filter that do not change
            }
        }
        return outImage;
    }

    public static void printArray(double[][] image) {
        //prints filter
        for (int i = 0; i < image.length; i++) {
            System.out.println(Arrays.toString(image[i]));
        }
    }

    public static void printA(int[][] image) {
        //prints filter
        for (int i = 0; i < image.length; i++) {
            System.out.println(Arrays.toString(image[i]));
        }
    }
}
/*
//public class Convolution {
    public static void main(String[] args) throws IOException {
        BufferedImage inputImage, outImage; // reference for the input and output images
        int[][] red, green, blue;  // array names for the red, green, and blue image parts
        int[][] gray;  // array name for the gray scale image part
        // Read the image from a file specified by the user
        inputImage = readImageFromFile();
        red = getChannel(inputImage, "red");// Obtain the red, green, and blue channels using method getChannel()
        green = getChannel(inputImage, "green");
        blue = getChannel(inputImage, "blue");


        double[][] filter = {
                {1.0 / 9, 1.0 / 9, 1.0 / 9},
                {1.0 / 9, 1.0 / 9, 1.0 / 9},
                {1.0 / 9, 1.0 / 9, 0.5}};
        for(int i=0;i<3;i++){
            for(int j=0; j<3; j++)
                filter[i][j] = .15;
        }

        red = convolve(red, filter);
        green = convolve(green, filter);
        blue = convolve(blue, filter);

        // Convert the rgb parts to gray scale
        // Write the output image to file: image.jpg
        outImage = arrayToBufferedImage(red,green,blue);
        ImageIO.write(outImage, "jpg", new File("image.jpg"));
    }
    public static int [][] convolve(int[][]image, double[][] filter) {
        int [][] outImage = new int[image.length][image[0].length];
        //does the convultion
        for (int i = 1; i < image.length-1; i++) {         //add a minus one to reduce the boundaries
            for (int j = 1; j < image[0].length-1; j++) { //good but also add the minus one
                //outImage[i][j] = (int) (image[i-1][j-1] * filter[0][0]);
                //need to multiple the numbers in the pixel inn convolution with filter
                //also good, but need to remember to add the minus one for the corners
                //  filter(image);
                outImage[i][j] = (int)Math.round((image[i-1][j-1] * filter[0][0])+(image[i-1][j-1]
                        * filter[0][1])+(image[i-1][j-1] * filter[0][2])+
                        (image[i-1][j-1] * filter[1][0])+
                        (image[i-1][j-1] * filter[1][1])+
                        (image[i-1][j-1] * filter[1][2])+
                        (image[i-1][j-1] * filter[2][0])+
                        (image[i-1][j-1] * filter[2][1])+
                        (image[i-1][j-1] * filter[2][2]));

                // outImage[i][j] = (int) (image[i-1][j-1] * filter[0][2]);
                // 0,0 and 0,1 etc represent the boxes on the filter that do not change
            }
        }
        return outImage;
    }


    // readImageFromFile() uses JFileChooser to help the user select an image and then
// returns the image as a BufferedImage
// ----------------------------------------------------------------------------------
    public static BufferedImage readImageFromFile() {
        BufferedImage img = null;
        final JFileChooser fileChooser = new JFileChooser();
        File selectedFile = null;
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));

        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
        }
        try {
            img = ImageIO.read(selectedFile);
        } catch (IOException e) {
        }
        return img;
    }

    public static BufferedImage arrayToBufferedImage(int[][] red,int[][]green,int[][]blue) {
        BufferedImage outImage = new BufferedImage(red.length, red[0].length,
                BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < red.length; i++) {
            for (int j = 0; j < red[i].length; j++) {
                int value = red[i][j] << 16 | green[i][j] << 8 | blue[i][j];
                outImage.setRGB(i, j, value);
            }
        }

        return outImage;
    }

    public static int[][] getChannel(BufferedImage img, String channel){
        int[][] table = new int[img.getWidth()][img.getHeight()];
        int flag, shift;
        if(channel.toLowerCase().equals("red")){
            flag = 16711680;
            shift = 16;
        } else if (channel.toLowerCase().equals("green")){
            flag = 65280;
            shift = 8;
        } else if (channel.toLowerCase().equals("blue")) {
            flag = 255;
            shift = 0;
        } else {
            return null;
        }

        for (int i = 0; i < img.getWidth(); i++){
            for (int j = 0; j < img.getHeight(); j++){
                table[i][j] = (img.getRGB(i, j) & flag) >> shift;
            }
        }
        return table;
    }




}
*/
