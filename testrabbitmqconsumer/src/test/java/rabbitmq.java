import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

//生产者入门程序
public class rabbitmq {
    private static final String QUEUE = "helloworld";
    public static void main(String args[]) throws IOException {
        //通过连接工程连接mq
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        //设置虚拟机
        connectionFactory.setVirtualHost("/");
        Connection connection = null;
        Channel channel = null;
        try {
            //建立连接
            connection = connectionFactory.newConnection();
            //创建管道
             channel = connection.createChannel();
            //声明队列
            /**
             * Like {@link Channel#queueDeclare(String, boolean, boolean, boolean, java.util.Map)} but sets nowait
             * flag to true and returns no result (as there will be no response from the server).
             * @param queue the name of the queue
             * @param durable true if we are declaring a durable queue (the queue will survive a server restart)
             * @param exclusive true if we are declaring an exclusive queue (restricted to this connection)
             * @param autoDelete true if we are declaring an autodelete queue (server will delete it when no longer in use)
             * @param arguments other properties (construction arguments) for the queue扩展参数连接时间
             * @throws java.io.IOException if an error is encountered
             */
            channel.queueDeclare(QUEUE,true,false,false,null);
            //指定交互机
            /**
             * Publish a message.
             *
             * Publishing to a non-existent exchange will result in a channel-level
             * protocol exception, which closes the channel.
             *
             * Invocations of <code>Channel#basicPublish</code> will eventually block if a
             * <a href="http://www.rabbitmq.com/alarms.html">resource-driven alarm</a> is in effect.
             *
             * @see com.rabbitmq.client.AMQP.Basic.Publish
             * @see <a href="http://www.rabbitmq.com/alarms.html">Resource-driven alarms</a>
             * @param exchange the exchange to publish the message to
             * @param routingKey the routing key如果没有使用交换机也就是使用默认交互机使用队列名称QUEUE
             * @param mandatory true if the 'mandatory' flag is to be set
             * @param immediate true if the 'immediate' flag is to be
             * set. Note that the RabbitMQ server does not support this flag.
             * @param props other properties for the message - routing headers etc
             * @param body the message body
             * @throws java.io.IOException if an error is encountered
             */
            String message = "first mq";
            channel.basicPublish("",QUEUE,null,message.getBytes());
            System.out.println("发送成功");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }finally {
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }finally {
                connection.close();
            }
        }
    }
}
