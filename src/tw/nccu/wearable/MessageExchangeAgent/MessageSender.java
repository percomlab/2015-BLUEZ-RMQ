package tw.nccu.wearable.MessageExchangeAgent;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class MessageSender {
	private String TEST_TOPIC;
	private String SERVER_URI;
	private ConnectionFactory factory;
	private Connection connection;
	private Channel channel;

	public MessageSender() {
		TEST_TOPIC = "CONTENT";
		SERVER_URI = "amqp://admin:admin@140.119.163.199";
	};
	public MessageSender(String topic, String uri) {
		TEST_TOPIC = topic;
		SERVER_URI = uri;
	};
	public void createConnection() throws java.io.IOException,
			KeyManagementException, NoSuchAlgorithmException,
			URISyntaxException {
		factory = new ConnectionFactory();
		factory.setUri(SERVER_URI);
		connection = factory.newConnection();
		channel = connection.createChannel();
		channel.exchangeDeclare(TEST_TOPIC, "fanout");
	}

	public void setServerUri(String uri) throws IOException, KeyManagementException, NoSuchAlgorithmException, URISyntaxException {
		SERVER_URI = uri;
		closeConnection();
		createConnection();
	}

	public void setTopic(String topic) throws KeyManagementException, NoSuchAlgorithmException, IOException, URISyntaxException {
		TEST_TOPIC = topic;
		closeConnection();
		createConnection();
	}

	public void sendMessage(String str) throws IOException {
		channel.basicPublish(TEST_TOPIC, "", null, str.getBytes());
	}

	public void closeConnection() throws IOException {
		channel.close();
		connection.close();
	}
}
