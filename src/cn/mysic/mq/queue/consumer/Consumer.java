package cn.mysic.mq.queue.consumer;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

import com.queue.mq.MQ;

public class Consumer {
		public static void main(String[] args) throws JMSException {
			MQ mq=new MQ();
			//ConnectionFactory : ���ӹ��� �� JMS��������������
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
					mq.getUsername(),
					mq.getPassword(),
					"tcp://127.0.0.1:61616");
			System.out.println("���ӳɹ�");
			//JMS �ͻ��˵�JMS Provider ������
			Connection connection = connectionFactory.createConnection();
			connection.start();
			 // Session�� һ�����ͻ������Ϣ���߳�     
            // createSession�ĵ�һ������Boolean.FALSEָ���ǵ�ǰsession����һ������  
            //                ����Ϣ���Զ��ύ����գ��㲻�ܵ���session.commit�����ύ����  
            // createSession�ĵ�һ������Boolean.TRUEָ�����´�����session��һ������  
            //                ��������session.commit�������ύ���񣬷�����չ�����Ϣ����  
            //                ���з������У����ٽ���һ�Σ��������ǻ����š�  
            Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);  
            //Destination : ��Ϣ��Ŀ�ĵ�; ��Ϣ���ĸ����н���
            Destination destination = session.createQueue("queue");
            System.out.println("�Ự�ɹ�");
            //�����ߣ���Ϣ������
            MessageConsumer consumer = session.createConsumer(destination);
            while(true){
            	//1����û���յ���Ϣ��������
            	System.out.println("���ڽ���");
            	TextMessage message = (TextMessage) consumer.receive(2000);
            	if(null!=message){
            		System.out.println("�յ���Ϣ��"+message.getText());
                  session.commit();
            	}
            }

//        	session.close();
//        	connection.close();
			
		}
}
