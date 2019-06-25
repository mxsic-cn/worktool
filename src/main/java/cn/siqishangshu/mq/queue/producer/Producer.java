package cn.siqishangshu.mq.queue.producer;

import cn.siqishangshu.mq.MQ;
import org.activemq.ActiveMQConnectionFactory;

import javax.jms.*;


public class Producer {

	public static void main(String[] args) throws JMSException,
			InterruptedException {
		MQ mq = new MQ();
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				mq.getUsername(),
				mq.getPassword(),
				"failover://(tcp://127.0.0.1:61616)");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		System.out.println("在");
		Session session = connection.createSession(Boolean.TRUE,
				Session.AUTO_ACKNOWLEDGE);
		System.out.println("�Ự�ɹ�");
		Destination destination = session.createQueue("queue");
		System.out.println("���гɹ�");
		MessageProducer producer = session.createProducer(destination);
		System.out.println("�����߳ɹ�");
		// producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		sendMsg(session, producer);
		connection.close();
	}

	/**
	 *
	 * @param session
	 * @param producer
	 * @throws InterruptedException
	 */
	private static void sendMsg(Session session, MessageProducer producer)
			throws JMSException, InterruptedException {
		for (int i = 0; i < 100; i++) {
			Thread.sleep(2000);
			TextMessage message = session
					.createTextMessage("Hello ActiveMQ! this is the " + i
							+ " message.");
			producer.send(message);
			System.out.println(" ");
			session.commit();
		}

	}
}
