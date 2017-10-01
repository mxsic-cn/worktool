package com.topic.util;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

import org.apache.activemq.ActiveMQConnectionFactory;

import com.topic.publisher.Publisher;
import com.topic.subscriber.Subscriber;

/** 
 * @author Kim Haase 
 * @comment&modified by Kagula 
 * @version 1.6, 08/18/00 
 * @lastUpdateDate  09/06/11 
 */  
public class AsynchTopicExample {  
    final String  CONTROL_QUEUE = "controlQueue";  
    String        topicName = null;  
    int           exitResult = 0;  
    static MQ 	  mq = new MQ();
    public static ConnectionFactory getJmsConnectionFactory()  
            throws JMSException {  
        return new ActiveMQConnectionFactory(mq.getUsername(),
        		mq.getPassword(), mq.getUrl());  
    }  
    public void run_threads() {  
        Subscriber   subscriber = new Subscriber();  
        Publisher  publisher = new Publisher();  
        Subscriber subscriber1 = new Subscriber();
        subscriber1.start();
        publisher.start();  
        subscriber.start();  
        try {  
        	subscriber1.join();
            subscriber.join();  
            publisher.join();  
        } catch (InterruptedException e) {}  
    }  
  
    
    public static void main(String[] args) {  
        AsynchTopicExample  ate = new AsynchTopicExample();  
        ate.topicName = "MyFirstTopic";  
        System.out.println("Topic name is " + ate.topicName);  
        ate.run_threads();  
    }  
}  