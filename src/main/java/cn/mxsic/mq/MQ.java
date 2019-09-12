package cn.mxsic.mq;


import org.activemq.ActiveMQConnection;

public class MQ {
	private String username = ActiveMQConnection.DEFAULT_USER;
	private String password = ActiveMQConnection.DEFAULT_PASSWORD;
	private String url = ActiveMQConnection.DEFAULT_BROKER_URL;
	private String topicName = "MyTopic";
	private String queueName = "MyQueue";

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the topicName
	 */
	public String getTopicName() {
		return topicName;
	}

	/**
	 * @param topicName the topicName to set
	 */
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	/**
	 * @return the queueName
	 */
	public String getQueueName() {
		return queueName;
	}

	/**
	 * @param queueName the queueName to set
	 */
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

}
