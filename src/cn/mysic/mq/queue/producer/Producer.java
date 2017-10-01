package cn.mysic.mq.queue.producer;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

import com.queue.mq.MQ;

public class Producer {

	public static void main(String[] args) throws JMSException,
			InterruptedException {
		MQ mq = new MQ();
		// ConnectionFactory:���ӹ�����jms��������������
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				mq.getUsername(),
				mq.getPassword(),
				"failover://(tcp://127.0.0.1:61616)");
		// JMS �ͻ��˵�JMS Provider������
		Connection connection = connectionFactory.createConnection();
		connection.start();
		System.out.println("���ӳɹ�");
		// Session : һ�����ͻ������Ϣ���߳�
		// createSession �ĵ�һ������Boolean.FALSEָ���ǵ�ǰsession����һ������
		// ����Ϣ���Զ��ύ���㲻�ܵ���session.commit�����ύ����
		// createSession�ĵ�һ������Boolean.TRUEָ�����´�����session��һ������
		// ��������session.commit�������ύ���񣬲�����������Ϣ���ڶ��з�������
		Session session = connection.createSession(Boolean.TRUE,
				Session.AUTO_ACKNOWLEDGE);
		System.out.println("�Ự�ɹ�");
		// Desination : ��Ϣ��Ŀ�ĵ�;��Ϣ���͸�˭
		// ��ȡsessionע�����ֵmy-queue��query������
		Destination destination = session.createQueue("queue");
		System.out.println("���гɹ�");
		// essagerProduce : ��Ϣ������
		MessageProducer producer = session.createProducer(destination);
		System.out.println("�����߳ɹ�");
		// �豸���־û�
		// producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		// ����һ����Ϣ
		sendMsg(session, producer);
		connection.close();
	}

	/**
	 * ��ָ���Ự�ϣ�ͨ��ָ������Ϣ�����߷���һ����Ϣ
	 * 
	 * @param session
	 *            ��Ϣ�Ự
	 * @param producer
	 *            ��Ϣ������
	 * @throws InterruptedException
	 */
	private static void sendMsg(Session session, MessageProducer producer)
			throws JMSException, InterruptedException {
		for (int i = 0; i < 100; i++) {
			Thread.sleep(2000);
			// ����һ���ı���Ϣ
			TextMessage message = session
					.createTextMessage("Hello ActiveMQ! this is the " + i
							+ " message.");
			// ͨ����Ϣ����������Ϣ
			producer.send(message);
			System.out.println("��Ϣ����");
			session.commit();
		}

	}
}
