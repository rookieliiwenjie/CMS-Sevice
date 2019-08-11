package com.xuecheng.test.freemaker.controller;

import com.xuecheng.test.freemaker.model.Student;
import freemarker.template.Template;
import org.apache.commons.io.IOUtils;
import org.assertj.core.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.sql.Date;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/freemaker")
@Controller
public class testController {
    @Autowired
    RestTemplate restTemplate;
    @GetMapping("/test")
    public String GetName(Map<String, Object> map) {
        map.put("name", "lwj");
        List<Student> stu = new ArrayList<>();
        Student student = new Student();
        student.setAge(12);
        stu.add(student);
        map.put("stu",stu);
        HashMap<String,Student> hashMap = new HashMap<>();
        hashMap.put("sutmap",student);
        map.put("stuss",hashMap);

        return "test1";

    }
    @GetMapping("/banner")
    public String banner(Map<String, Object> map) {

        ResponseEntity<Map> forEntiy = restTemplate.getForEntity("http://localhost:31001/cms/config/one/5a791725dd573c3574ee333f",Map.class);
        Map body = forEntiy.getBody();
        System.out.println(body);
        map.putAll(body);
        return "index_banner";

    }
}
