import com.xuecheng.mange_cms.MangeCmsApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@SpringBootTest(classes = MangeCmsApplication.class)
@RunWith(SpringRunner.class)
public class RestTemplteDemo {
    @Autowired
    RestTemplate restTemplate;
    @Test
    public void testTemplate(){
        ResponseEntity<Map> mapResponseEntity= restTemplate.getForEntity("http://localhost:31001/cms/config/one/5a791725dd573c3574ee333f", Map.class);
        Map body = mapResponseEntity.getBody();
        System.out.println(body);
    }
}
