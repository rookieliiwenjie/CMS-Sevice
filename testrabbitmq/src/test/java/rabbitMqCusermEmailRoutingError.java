import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class rabbitMqCusermEmailRoutingError {
    private static final String QUEUEEMAIL = "queues_email";
    private static final String EXCHANGE ="exchange_routing";
    private static final String RoutKeyError = "error";
    public static void main (String args[]){

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setVirtualHost("/");
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        Connection connection = null;
        try {
            //创建连接
            connection = connectionFactory.newConnection();
            //场景管道
            Channel channel = connection.createChannel();
            //声明队列
            channel.queueDeclare(QUEUEEMAIL,true,false,false,null);
            //声明交换机
            channel.exchangeDeclare(EXCHANGE,BuiltinExchangeType.DIRECT);
            //消费方法
            //绑定交换机和队列绑定
            /**
             * Bind a queue to an exchange, with no extra arguments.
             * @see AMQP.Queue.Bind
             * @see AMQP.Queue.BindOk
             * @param queue the name of the queue
             * @param exchange the name of the exchange
             * @param routingKey the routing key to use for the binding
             * @return a binding-confirm method if the binding was successfully created
             * @throws IOException if an error is encountered
             */
            channel.queueBind(QUEUEEMAIL,EXCHANGE,RoutKeyError);
            DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
                //当接受到消息时此方法别调用
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    /**
                     * String consumerTag,用来标识消费者在监听队列时设置
                     *   Envelope envelope, 信封 获取交换机
                     *    AMQP.BasicProperties properties, 消息属性
                     *        byte[] body 消息内容
                     */
                    //获取交换机
                    String exchange = envelope.getExchange();
                    //消息id，mq在chanal中的标识,确认消息接受
                    long dekuverTag = envelope.getDeliveryTag();
                    String message =   new String(body);
                    System.out.println(message);

                }
            };
            //监听队列
            /**
             * Start a non-nolocal, non-exclusive consumer, with
             * a server-generated consumerTag.
             * @param queue the name of the queue
             * @param autoAck true if the server should consider messages
             * acknowledged once delivered; false if the server should expect
             * explicit acknowledgements
             * @param callback an interface to the consumer object
             * @return the consumerTag generated by the server
             * @throws IOException if an error is encountered
             * @see AMQP.Basic.Consume
             * @see AMQP.Basic.ConsumeOk
             * @see #basicConsume(String, boolean, String, boolean, boolean, Map, Consumer)
             */
            channel.basicConsume(QUEUEEMAIL,true,defaultConsumer);
            System.out.println("接受成功");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

    }
}