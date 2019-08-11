import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.mange_cms.MangeCmsApplication;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;

@SpringBootTest(classes = MangeCmsApplication.class)
@RunWith(SpringRunner.class)
public class GridFsTemplteDemo {
    @Autowired
    GridFsTemplate gridFsTemplate;
    @Autowired
    GridFSBucket gridFSBucket;

    @Test
    public void testTemplate() throws FileNotFoundException {
        File file = new File("c:/index_banner.ftl");
        FileInputStream fileInputStream = new FileInputStream(file);
        ObjectId objectId = gridFsTemplate.store(fileInputStream, "index_demo");
        System.out.println(objectId);
    }

    @Test
    public void downloadDemo() throws IOException {
        //根据id查询文件
        GridFSFile fsFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is("5d4f105d42af08bf683b0c48")));
        //打开下载流
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(fsFile.getObjectId());
        //获取流对象
        GridFsResource gridFsResource = new GridFsResource(fsFile, gridFSDownloadStream);
        FileOutputStream fileOutputStream = new FileOutputStream(new File("C://Users//lwj32//Desktop//indem.ftl"));
        fileOutputStream.write(IOUtils.toByteArray(gridFsResource.getInputStream()));
        fileOutputStream.close();
       /* String s = IOUtils.toString(gridFsResource.getInputStream(),"utf-8");
        System.out.println(s);*/


    }
}
