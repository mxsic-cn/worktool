package cn.mysic.mq.queue.consumer;

import cn.mysic.mq.MQ;
import org.activemq.ActiveMQConnectionFactory;

import javax.jms.*;


public class Consumer {
		public static void main(String[] args) throws JMSException {
			MQ mq=new MQ();
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
					mq.getUsername(),
					mq.getPassword(),
					"tcp://127.0.0.1:61616");
			System.out.println(" ");
			Connection connection = connectionFactory.createConnection();
			connection.start();

            Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);  
            Destination destination = session.createQueue("queue");
            System.out.println(" ");
            MessageConsumer consumer = session.createConsumer(destination);
            while(true){
            	System.out.println(" ");
            	TextMessage message = (TextMessage) consumer.receive(2000);
            	if(null!=message){
            		System.out.println(" "+message.getText());
                  session.commit();
            	}
            }

//        	session.close();
//        	connection.close();
			
		}
}
