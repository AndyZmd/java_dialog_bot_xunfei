package wx;

import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;

import java.io.IOException;

/**
 * Created by zmd on 2018/6/28.
 */
public class wxclient {
    public static void main(String[] args) throws IOException {

        String url = "https://localhost:443/wx";
        ClientResource client = new ClientResource(url);
        //创建表单
        Form form = new Form();
        form.add("name", "测试一下");
        //以post方式提交表单
        Representation representation = client.post(form);
        System.out.println(representation.getText());
    }
}
