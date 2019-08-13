import com.rabbitmq.client.*;
import com.rabbitmq.client.impl.AMQBasicProperties;

import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.TimeoutException;

//生产者入门程序
public class rabbitmqHeader {
    private static final String QUEUEEMAIL = "queues_email";
    private static final String QUEUESMS = "queues_sms";
    private static final String EXCHANGE ="exchange_topic";
    private static final String RoutKey_EMAIL = "inform.#.email.#";
    private static final String RoutKey_SMS = "inform.#.sms.#";
    public static void main(String args[]){
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
             * @throws IOException if an error is encountered
             */
            channel.queueDeclare(QUEUEEMAIL,true,false,false,null);
            channel.queueDeclare(QUEUESMS,true,false,false,null);
            //声明交互机
            /**
             * Actively declare a non-autodelete, non-durable exchange with no extra arguments
             * @see AMQP.Exchange.Declare
             * @see AMQP.Exchange.DeclareOk
             * @param exchange the name of the exchange
             * @param type the exchange type
             * @return a declaration-confirm method to indicate the exchange was successfully declared
             * @throws IOException if an error is encountered
             */
            channel.exchangeDeclare(EXCHANGE, BuiltinExchangeType.TOPIC);
            //队列和交换机绑定
            /**
             * Bind an exchange to an exchange.
             * @see AMQP.Exchange.Bind
             * @see AMQP.Exchange.BindOk
             * @param destination the name of the exchange to which messages flow across the binding
             * @param source the name of the exchange from which messages flow across the binding
             * @param routingKey the routing key to use for the binding发布订阅模式下为空
             * @param arguments other properties (binding parameters)
             * @return a binding-confirm method if the binding was successfully created
             * @throws IOException if an error is encountered
             */
            Map<String,Object> map = new Hashtable<>();
            map.put("inform-emal","emial");
            Map<String,Object> mapsms = new Hashtable<>();
            mapsms.put("inform-sms","sms");
            channel.queueBind(QUEUEEMAIL,EXCHANGE,"",map);
            channel.queueBind(QUEUESMS,EXCHANGE,"",mapsms);
            for(int i = 0;i<5 ;i++){
                String message = "publish to user email";
                //队列绑定交换机
                AMQP.BasicProperties.Builder pro = new AMQP.BasicProperties.Builder();
                Map<String,Object> headers = new Hashtable<>();
                headers.put("inform-emal","emial");
                pro.headers(headers);
                channel.basicPublish(EXCHANGE,"",pro.build(),message.getBytes());
                /**
                 * Publish a message.
                 *
                 * Publishing to a non-existent exchange will result in a channel-level
                 * protocol exception, which closes the channel.
                 *
                 * Invocations of <code>Channel#basicPublish</code> will eventually block if a
                 * <a href="http://www.rabbitmq.com/alarms.html">resource-driven alarm</a> is in effect.
                 *
                 * @see AMQP.Basic.Publish
                 * @see <a href="http://www.rabbitmq.com/alarms.html">Resource-driven alarms</a>
                 * @param exchange the exchange to publish the message to
                 * @param routingKey the routing key
                 * @param mandatory true if the 'mandatory' flag is to be set
                 * @param immediate true if the 'immediate' flag is to be
                 * set. Note that the RabbitMQ server does not support this flag.
                 * @param props other properties for the message - routing headers etc
                 * @param body the message body
                 * @throws IOException if an error is encountered
                 */

            }
            for(int i = 0;i<5 ;i++){
                String message = "publish to user sms";
                //队列绑定交换机
                channel.basicPublish(EXCHANGE,"inform.sms",null,message.getBytes());
                /**
                 * Publish a message.
                 *
                 * Publishing to a non-existent exchange will result in a channel-level
                 * protocol exception, which closes the channel.
                 *
                 * Invocations of <code>Channel#basicPublish</code> will eventually block if a
                 * <a href="http://www.rabbitmq.com/alarms.html">resource-driven alarm</a> is in effect.
                 *
                 * @see AMQP.Basic.Publish
                 * @see <a href="http://www.rabbitmq.com/alarms.html">Resource-driven alarms</a>
                 * @param exchange the exchange to publish the message to
                 * @param routingKey the routing key
                 * @param mandatory true if the 'mandatory' flag is to be set
                 * @param immediate true if the 'immediate' flag is to be
                 * set. Note that the RabbitMQ server does not support this flag.
                 * @param props other properties for the message - routing headers etc
                 * @param body the message body
                 * @throws IOException if an error is encountered
                 */

            }
            for(int i = 0;i<5 ;i++){
                String message = "publish to user sms and email";
                //队列绑定交换机
                channel.basicPublish(EXCHANGE,"inform.sms.email",null,message.getBytes());
                /**
                 * Publish a message.
                 *
                 * Publishing to a non-existent exchange will result in a channel-level
                 * protocol exception, which closes the channel.
                 *
                 * Invocations of <code>Channel#basicPublish</code> will eventually block if a
                 * <a href="http://www.rabbitmq.com/alarms.html">resource-driven alarm</a> is in effect.
                 *
                 * @see AMQP.Basic.Publish
                 * @see <a href="http://www.rabbitmq.com/alarms.html">Resource-driven alarms</a>
                 * @param exchange the exchange to publish the message to
                 * @param routingKey the routing key
                 * @param mandatory true if the 'mandatory' flag is to be set
                 * @param immediate true if the 'immediate' flag is to be
                 * set. Note that the RabbitMQ server does not support this flag.
                 * @param props other properties for the message - routing headers etc
                 * @param body the message body
                 * @throws IOException if an error is encountered
                 */

            }
            System.out.println("发送成功");


        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
