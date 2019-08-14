import com.TestRabbitMqApplication;
import com.rookit.rabbitmq.config.RabbitMqConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = TestRabbitMqApplication.class)
@RunWith(SpringRunner.class)
public class Topic_SpringBootTest {
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Test
    public void publishMessage(){
        for (int i = 0;i<6;i++){
            /**
             * Convert a Java object to an Amqp {@link Message} and send it to a specific exchange
             * with a specific routing key.
             *
             * @param exchange the name of the exchange
             * @param routingKey the routing key
             * @param message a message to send
             * @throws AmqpException if there is a problem
             */
            String message = "sms email inform to  user"+i;
            rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE,"inform.sms.email",message);
        }
    }


}
