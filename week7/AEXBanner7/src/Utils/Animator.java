/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.text.Text;

/**
 *
 * @author Stefan
 */
public class Animator extends AnimationTimer
{
    private Text text;
    private Scene scene;
    
    public Animator(Text text, Scene scene)
    {
        this.text = text;
        this.scene = scene;
    }
    
    @Override
    public void handle(long now)
    {
        if(text.getX() - (text.getWrappingWidth() / 2) >= scene.getWidth())
        {
            text.relocate(0 - (text.getWrappingWidth() / 2), scene.getHeight() / 2);
        }
        else
        {
            System.out.println(text.getX() + 1);
            text.relocate(text.getX() + 1, scene.getHeight() / 2);   
            //System.out.println("moved to: " + text.getX() );
        }
    }   
}
