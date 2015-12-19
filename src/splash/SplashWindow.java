/**
 * 
 */
package splash;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;


/**
 * 
 * @author Tony Colston(except tiny modifies done by Marcello_Feroce) http://www.javaworld.com/article/2077467/core-java/java-tip-104--make-a-splash-with-swing.html
 *
 */
class SplashWindow extends JWindow
{
    /**
     * 
     */
    private static final long serialVersionUID = 6414651124233365402L;
    public SplashWindow(URL filename, Frame f, int waitTime)
    {
        super(f);
        JLabel l = new JLabel(new ImageIcon(filename));
        getContentPane().add(l, BorderLayout.CENTER);
        pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension labelSize = l.getPreferredSize();
        setLocation(screenSize.width/2 - (labelSize.width/2),
                    screenSize.height/2 - (labelSize.height/2));
        addMouseListener(new MouseAdapter()
            {
                public void mousePressed(MouseEvent e)
                {
                    setVisible(false);
                    dispose();
                }
            });
        final int pause = waitTime;
        final Runnable closerRunner = new Runnable()
            {
                public void run()
                {
                    setVisible(false);
                    dispose();
                }
            };
        Runnable waitRunner = new Runnable()
            {
                public void run()
                {
                    try
                        {
                            Thread.sleep(pause);
                            SwingUtilities.invokeAndWait(closerRunner);
                        }
                    catch(Exception e)
                        {
                            e.printStackTrace();
                            // can catch InvocationTargetException
                            // can catch InterruptedException
                        }
                }
            };
        setVisible(true);
        Thread splashThread = new Thread(waitRunner, "SplashThread");
        splashThread.start();
        
        
    }
    public static void main(String[] args) throws InterruptedException{
        final Integer waitTime=new Integer(2500);
        JFrame frame=new JFrame();
        frame.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2, Toolkit.getDefaultToolkit().getScreenSize().height/2);
        frame.setVisible(true);
        new SplashWindow(SplashWindow.class.getResource("/medusa.jpg"),frame, 2500);
        Thread.sleep(waitTime);
        frame.setVisible(false);
    }
}