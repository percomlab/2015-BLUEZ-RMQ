package tw.nccu.wearable.MessageExchangeAgent;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

public class MessageReceiver {
	private static String TEST_TOPIC;
	private static String SERVER_URI;
	private ConnectionFactory factory;
	private Channel channel;
	private QueueingConsumer consumer;

	public MessageReceiver(){
		TEST_TOPIC = "CONTENT";
		SERVER_URI = "amqp://admin:admin@140.119.163.199";
	}
	public MessageReceiver(String topic, String uri){
		TEST_TOPIC = topic;
		SERVER_URI = uri;
	}
	public void createConnection() throws KeyManagementException, NoSuchAlgorithmException, URISyntaxException, IOException{
		factory = new ConnectionFactory();
		factory.setUri(SERVER_URI);
		Connection connection = factory.newConnection();
		channel = connection.createChannel();

		channel.exchangeDeclare(TEST_TOPIC, "fanout");
		String queueName = channel.queueDeclare().getQueue();
		channel.queueBind(queueName, TEST_TOPIC, "");

		consumer = new QueueingConsumer(channel);
		channel.basicConsume(queueName, true, consumer);
	}
	public void closeConnection() throws IOException{
		channel.close();
	}
	public String getMessage() throws ShutdownSignalException, ConsumerCancelledException, InterruptedException{
		QueueingConsumer.Delivery delivery = consumer.nextDelivery();
		String message = new String(delivery.getBody());
		return message;
	}
}
