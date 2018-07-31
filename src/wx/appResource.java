package wx;

import my.mySz;
import my.sendClient;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.json.JSONException;
import org.restlet.data.Disposition;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.ext.fileupload.RestletFileUpload;
import org.restlet.representation.FileRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ServerResource;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * 微信小程序 服务端 语音转换
 * Created by zmd on 2018/7/23.
 */
public class appResource extends ServerResource {
    public static my.sendClient sendClient =null;
    public static mySz mz =null;
    public static sendClient getsc(){
        if(sendClient==null){
            sendClient = new sendClient();
        }
        return sendClient;
    }
    public static mySz gettm(){
        if(mz==null){
            mz = new mySz();
        }
        return mz;
    }
    @Override
    protected Representation get() {
        String substring = "我无法回答你";
        Form form = this.getQuery();
        String filename = form.getFirstValue("sendtext");
        try {
            substring = getsc().send(filename);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new StringRepresentation(substring);
    }
    @Override
    protected Representation post(Representation entity) {
        Form form = this.getQuery();
        String filename = form.getFirstValue("sendtext");
        System.out.println(filename);
        return new StringRepresentation("666666");
    }
}
