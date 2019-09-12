package cn.mxsic.thread.ball;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by liuchuan on 2/21/17.
 */
public class BounceFrame extends JFrame {
    private BallComponent ballComponent;
    public static final int STEPS = 1000;
    public static final int DELAY = 3;

    public BounceFrame(){
        setTitle("Bounce");

        ballComponent = new BallComponent();
        add(ballComponent, BorderLayout.CENTER);
        JPanel buttonJPanel = new JPanel();
        addButton(buttonJPanel,"Start",new ActionListener(){
            public void actionPerformed(ActionEvent actionEvent) {
               addBall();
            }
        });
        addButton(buttonJPanel, "Close", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        add(buttonJPanel,BorderLayout.SOUTH);
        pack();
    }

    private void addButton(Container c,String title,ActionListener listener) {
        JButton jButton = new JButton(title);
        c.add(jButton);
        jButton.addActionListener(listener);
    }

    public void addBall(){
//        try{
            for (int i = 0; i < 10 ; i++){
                BallRunnable ballRunnable = new BallRunnable(ballComponent,2*5*i,2*5*i);
                Thread thread = new Thread(ballRunnable);
                thread.start();
            }
//            Ball ball = new Ball();
//            ballComponent.add(ball);
//            for (int i = 1; i<= STEPS;i++){
//                ball.move(ballComponent.getBounds());
//                ballComponent.paint(ballComponent.getGraphics());
//                Thread.sleep(DELAY);
//            }

//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    class BallRunnable implements Runnable {
        private BallComponent ballComponent;
        private int x;
        private int y;
        public BallRunnable(BallComponent ballComponent,int x,int y) {
            this.ballComponent = ballComponent;
            this.x = x;
            this.y = y;
        }

        @Override
        public void run() {
            try{
                Ball ball = new Ball();
                ball.setXSIZE(this.x);
                ball.setYSIZE(this.y);
                this.ballComponent.add(ball);
                for (int i = 1; i <= STEPS; i++){
                    ball.move(ballComponent.getBounds());
                    ballComponent.repaint();
                    Thread.sleep(DELAY);
                }
            }catch (InterruptedException e){

            }
        }
    }
}
