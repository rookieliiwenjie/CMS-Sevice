import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.mange_cms.MangeCmsApplication;
import com.xuecheng.mange_cms.dao.CmsPageRepostitory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = MangeCmsApplication.class)
@RunWith(SpringRunner.class)
public class DemoTest {
    @Autowired
    CmsPageRepostitory cmsPageRepostitory;

    @Test
    public void testFindAll() {
        List<CmsPage> list = cmsPageRepostitory.findAll();
        System.out.println(list);
    }

    @Test
    public void testPageFind() {
        Pageable pageable = PageRequest.of(1, 3);
        Page<CmsPage> cmsPages = cmsPageRepostitory.findAll(pageable);
        System.out.println(cmsPages);
    }

    @Test
    public void testSava() {
        CmsPage cmsPage = new CmsPage();
        cmsPage.setPageName("测试");
        cmsPage.setSiteId("1");
        CmsPage rows = cmsPageRepostitory.save(cmsPage);
        System.out.println(rows);
    }

    @Test
    public void testDelete() {
        CmsPage cmsPage = new CmsPage();
        cmsPage.setPageName("测试");
        cmsPageRepostitory.deleteById("5d1cc6f5f53e6050f8ee9fa8");

    }

    @Test
    public void updateTest() {
        Optional<CmsPage> optional = cmsPageRepostitory.findByPageName("test");
       /*if(optional.isPresent()){
           CmsPage cmsPage = optional.get();
           cmsPage.setPageName("test");
           cmsPageRepostitory.save(cmsPage);
       }*/
        if (optional.isPresent()) {
            System.out.println(optional);
        }
    }

    @Test
    public void testFindAllExample() {
        int page = 0;
        int pagesize = 10;
        Pageable pageable = PageRequest.of(page, pagesize);
        CmsPage cmsPage = new CmsPage();
        cmsPage.setPageAliase("轮播");
        //cmsPage.setSiteId("5a751fab6abb5044e0d19ea1");
        //定义条件匹配器
        ExampleMatcher  exampleMatcher = ExampleMatcher.matching()
                .withMatcher("pageAliase",ExampleMatcher.GenericPropertyMatchers.contains());
        //定义example

        Example<CmsPage> example = Example.of(cmsPage, exampleMatcher);
        Page<CmsPage> all = cmsPageRepostitory.findAll(example, pageable);
        List<CmsPage> contexnt = all.getContent();
        System.out.println(contexnt);
        System.out.println(all);
    }
}
