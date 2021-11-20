
# Project environment

* I used Java 17 (openjdk-17.0.1) to build and run the project at Windows 11 machine


# Hole Filling Library

This library fills a hole in an image by doing the following steps:
* Convert the source RGB image to a grayscale image by taking the average value of (R,G,B) values
* Convert an RGB pixel from [0-255] to [0.0-1.0] range
* Assign -1 value to every pixel which defined as hole in the mask and intensity value (0.5)
* Finds the boundary of the hole (4/8 connected-type)
* Applies the given formula to every pixel in the hole
* Save the filled image in source image directory


# How to use the command line utility?

* Command line utility location is HoleFilling\out\production\HoleFilling
* Firstly run: 
  * java HoleFilling -h 
* More useful examples:
  * java HoleFilling C:\path_to_dir\test1.png C:\path_to_dir\test1Mask.png 3 0.01 8 true
  * java HoleFilling C:\path_to_dir\test2.jpg C:\path_to_dir\test1Mask.jpg 3 0.01 8 false


# How to use the library?

* Add the following JAR file to your project:
  * HoleFilling\out\artifacts\HoleFilling_jar\HoleFilling.jar
* See HoleFilling.java for example. Also, you can read the library's documentation 

# Tests images

You can use images at HoleFilling\tests directory with the utility