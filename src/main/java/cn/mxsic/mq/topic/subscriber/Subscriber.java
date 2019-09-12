package cn.mxsic.mq.topic.subscriber;

import cn.mxsic.mq.MQ;
import cn.mxsic.mq.topic.util.AsynchTopicExample;
import cn.mxsic.mq.topic.util.SampleUtilities;

import javax.jms.*;

public class Subscriber  extends Thread {  
		  
         private class TextListener implements MessageListener {  
             final SampleUtilities.DoneLatch  monitor = new SampleUtilities.DoneLatch();

             public void onMessage(Message message) {
                 if (message instanceof TextMessage) {  
                     TextMessage  msg = (TextMessage) message;  
  
                     try {  
                         System.out.println(" : " + msg.getText());
                     } catch (JMSException e) {  
                         System.out.println("Exception in onMessage(): " + e.toString());  
                     }  
                 } else {  
                     monitor.allDone();
                 }  
             }  
         }
        /** 
         * Runs the thread. 
         */  
        public void run() {  
            ConnectionFactory       topicConnectionFactory = null;  
            Connection              topicConnection = null;  
            Session                 topicSession = null;  
            Topic                   topic = null;  
            MessageConsumer         topicSubscriber = null;  
            TextListener            topicListener = null;  
            MQ						mq = new MQ();
            try {  
                topicConnectionFactory = AsynchTopicExample.getJmsConnectionFactory();
                topicConnection = topicConnectionFactory.createConnection();  
                topicSession = topicConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);  
                topic = topicSession.createTopic(mq.getTopicName());  
            } catch (Exception e) {  
                System.out.println("Connection problem: " + e.toString());  
                if (topicConnection != null) {  
                    try {  
                        topicConnection.close();  
                    } catch (JMSException ee) {}  
                }  
                System.exit(1);  
            }   
  
            try {  
                topicSubscriber = topicSession.createConsumer(topic);  
                topicListener = new TextListener();  
                topicSubscriber.setMessageListener(topicListener);  
                topicConnection.start();                  
               
                /* 
                 * Asynchronously process messages. 
                 * Block until publisher issues a control message indicating 
                 * end of publish stream. 
                 */  
                topicListener.monitor.waitTillDone();  
            } catch (JMSException e) {  
                System.out.println("Exception occurred: " + e.toString());  
//                exitResult = 1;  
            } finally {  
                if (topicConnection != null) {  
                    try {  
                        topicConnection.close();  
                    } catch (JMSException e) {  
//                        exitResult = 1;  
                    }  
                }  
            }  
        }         
    }  