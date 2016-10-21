/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Stefan
 */
public class AEXBanner extends Application
{
    public static final int NANO_TICKS = 20000000; 
    public final int HEIGHT = 100;
    public final int WIDTH = 300;
    // FRAME_RATE = 1000000000/NANO_TICKS = 50;

    private Text text;
    private BannerController bannerController;
    private AnimationTimer animationTimer;

    @Override
    public void start(Stage primaryStage)
    {
        try {
            bannerController = new BannerController(this);
        } catch (RemoteException ex) {
            Logger.getLogger(AEXBanner.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        text = new Text();
        text.setFill(Color.BLACK);
        text.setFont(new Font("Arial", 18));

        Pane root = new Pane();
        root.getChildren().add(text);
        Scene scene = new Scene(root, WIDTH, HEIGHT);

        primaryStage.setTitle("AEX banner");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.toFront();

        // Start animation: text moves from right to left
        animationTimer = new AnimationTimer()
        {
            private long prevUpdate;

            @Override
            public void handle(long now)
            {
                setStocks(bannerController.getFonds());
                long lag = now - prevUpdate;
                if (lag >= NANO_TICKS)
                {
                    // calculate new location of text
                    // TODO
                    if(text.getLayoutX() >= scene.getWidth())
                    {
                        text.relocate(0 - text.getLayoutBounds().getWidth(), scene.getHeight() / 2);
                    }
                    else
                    {
                        text.relocate(text.getLayoutX()+ 3, scene.getHeight() / 2);   
                        //System.out.println("moved to: " + text.getX() );
                    }
                    
                    //text.relocate(textPosition,0);
                    prevUpdate = now;
                }
            }
            
            @Override
            public void start()
            {
                prevUpdate = System.nanoTime();
                text.relocate(0 - text.getLayoutBounds().getWidth(), scene.getHeight() / 2);
                setStocks("COMP: VALUE");
                super.start();
            }
        };
        animationTimer.start();
    }

    public void setStocks(String stocks) {
        text.setText(stocks);
    }

    @Override
    public void stop() {
        animationTimer.stop();
        bannerController.stop();
    }
}
