package cn.siqishangshu.applet;

import javax.swing.*;
import java.awt.*;

/**
 * Created by liuchuan on 2/17/17.
 */
public class NotHelloWorld extends JApplet {
    @Override
    public void init() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JLabel jLabel = new JLabel("Not Hello,World applet",
                        SwingConstants.CENTER);
                add(jLabel);
            }
        });
    }
}
