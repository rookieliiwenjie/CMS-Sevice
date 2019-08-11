import com.xuecheng.FreeMarkerApplication;
import com.xuecheng.test.freemaker.model.Student;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SpringBootTest(classes = FreeMarkerApplication.class)
@RunWith(SpringRunner.class)

public class FreeMarkTest1 {
    @Test
    public void testGenerateHtml() throws IOException, TemplateException {
        Configuration configuration = new Configuration(Configuration.getVersion());
        String path = this.getClass().getResource("/").getPath();
        configuration.setDirectoryForTemplateLoading(new File(path + "/templates/"));
        Template template = configuration.getTemplate("test1.ftl");
        String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, getMap());
        System.out.println(content);
        InputStream inputStream = IOUtils.toInputStream(content);
        FileOutputStream fileOutputStream = new FileOutputStream(new File("C:\\Users\\lwj32\\Downloads\\test.html"));
        IOUtils.copy(inputStream, fileOutputStream);
        inputStream.close();
        fileOutputStream.close();
    }

    public Map getMap() {
        Map map = new HashMap();
        map.put("name", "lwj");
        List<Student> stu = new ArrayList<>();
        Student student = new Student();
        student.setAge(12);
        stu.add(student);
        map.put("stu", stu);
        HashMap<String, Student> hashMap = new HashMap<>();
        hashMap.put("sutmap", student);
        map.put("stuss", hashMap);

        return map;
    }

    @Test
    public void Test2() throws IOException, TemplateException {
        Configuration configuration = new Configuration( Configuration.getVersion());
        String content = "<!DOCTYPE html> <html> <head> <title>Hello World!</title> </head> <body> Hello ${name}! </body> </html>";
        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
        stringTemplateLoader.putTemplate("tem",content);
        configuration.setTemplateLoader(stringTemplateLoader);
        Template template = configuration.getTemplate("tem","utf-8");
        String x = FreeMarkerTemplateUtils.processTemplateIntoString(template, getMap());
        InputStream inputStream = IOUtils.toInputStream(x);
        FileOutputStream fileOutputStream = new FileOutputStream(new File("C:\\Users\\lwj32\\Downloads\\test1.html"));
        IOUtils.copy(inputStream,fileOutputStream);
        inputStream.close();
        fileOutputStream.close();
    }
}
