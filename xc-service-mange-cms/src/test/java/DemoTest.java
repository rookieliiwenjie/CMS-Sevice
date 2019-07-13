import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.mange_cms.MangeCmsApplication;
import com.xuecheng.mange_cms.dao.CmsPageRepostitory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = MangeCmsApplication.class)
@RunWith(SpringRunner.class)
public class DemoTest {
    @Autowired
    CmsPageRepostitory cmsPageRepostitory;
    @Test
    public void testFindAll(){
      List<CmsPage> list = cmsPageRepostitory.findAll();
      System.out.println(list);
    }
    @Test
    public void testPageFind(){
        Pageable pageable = PageRequest.of(1,3);
        Page<CmsPage> cmsPages = cmsPageRepostitory.findAll(pageable);
        System.out.println(cmsPages);
    }
    @Test
    public void testSava(){
        CmsPage cmsPage = new CmsPage();
        cmsPage.setPageName("测试");
        cmsPage.setSiteId("1");
       CmsPage rows = cmsPageRepostitory.save(cmsPage);
       System.out.println(rows);
    }
    @Test
    public void testDelete(){
        CmsPage cmsPage = new CmsPage();
        cmsPage.setPageName("测试");
        cmsPageRepostitory.deleteById("5d1cc6f5f53e6050f8ee9fa8");

    }
    @Test
    public void updateTest(){
       Optional<CmsPage> optional =  cmsPageRepostitory.findByPageName("test");
       /*if(optional.isPresent()){
           CmsPage cmsPage = optional.get();
           cmsPage.setPageName("test");
           cmsPageRepostitory.save(cmsPage);
       }*/
       if (optional.isPresent()){
           System.out.println(optional);
       }
    }
}
