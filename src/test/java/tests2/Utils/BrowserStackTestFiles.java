package tests2.Utils;

/**
 * Description: This class holds the file paths for various Browser Stack default images we can utilize for image upload testing
 */
public class BrowserStackTestFiles {
    public String getFilePath(String imageName) {
        switch (imageName.toUpperCase()) {

            case "ICON":
                // dimensions: 128 x 128, type: jpeg, size: 19.4 kb
                return "C:\\Users\\hello\\Desktop\\images\\icon.jpeg";
            case "ICON2":
                // dimensions: 48 x 48, type: jpeg, size: 746 bytes
                return "C:\\Users\\hello\\Desktop\\images\\icon2.jpeg";
            case "LOGO1":
                // dimensions: 464 x 404, type: jpeg, size: 52.5kb
                return "C:\\Users\\hello\\Desktop\\images\\logo1.jpeg";
            case "LOGO2":
                // dimensions: 512 x 512, type: png, size: 182 kb
                return "C:\\Users\\hello\\Desktop\\images\\logo2.png";
            case "PERSON":
                // dimensions: 300 x 433, type: jpg, size: 20.5 kb
                return "C:\\Users\\hello\\Desktop\\images\\person.jpeg";
            case "WALLPAPER1":
                // dimensions: 1920 x 1080, type: jpg, size: 1.10 mb
                return "C:\\Users\\hello\\Desktop\\images\\wallpaper1.jpeg";
            case "WALLPAPER2":
                // dimensions: 430 x 344, type: jpg, size: 42.9 kb
                return "C:\\Users\\hello\\Desktop\\images\\wallpaper2.jpeg";
            case "WALLPAPER3":
                // dimensions: 768 x 576, type: jpg, size: 103 kb
                return "C:\\Users\\hello\\Desktop\\images\\wallpaper3.jpeg";
            case "CARTOON-ANIMATION":
                // dimensions: 140 x 200, type: gif, size: 6 kb
                return "C:\\Users\\hello\\Desktop\\images\\cartoon-animation.gif";
            default:
                return "";
        }
    }
}
