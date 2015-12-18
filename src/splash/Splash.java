/**
 * 
 */
package splash;

import java.awt.Graphics2D;
import java.awt.SplashScreen;

import javax.swing.JFrame;

/**
 * @author Marcello_Feroce
 *
 */
public class Splash extends JFrame{

    private static final long serialVersionUID = -3510085938714667696L;
    
    private final SplashScreen splash;
    public Splash() {
        this.splash=SplashScreen.getSplashScreen();
        if(splash==null) {
            System.out.println("SplashScreen.getSplashScreen() returned null");
        }
        Graphics2D g = splash.createGraphics();
        if (g == null) {
            System.out.println("g is null");
        }
    }
    

}
