package cn.mysic.thread.ball;

import javax.swing.*;
import java.awt.*;

/**
 * Created by liuchuan on 2/21/17.
 */
public class Bounce {
    public static void main(String[] args){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame jFrame = new BounceFrame();
                jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                jFrame.setVisible(true);
            }
        });
    }

}
