package wx;

import org.json.JSONException;
import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.ServerResource;
import my.sendClient;

import java.io.IOException;

/**
 * Created by zmd on 2018/6/28.
 */
public class wxResource extends ServerResource {
    public static sendClient sendClient =null;

    public static sendClient getsc(){
        if(sendClient==null){
            sendClient = new sendClient();
        }
        return sendClient;
    }
    @Override
    protected Representation post(Representation entity) {
        Form form = new Form(entity);
        String name = form.getFirstValue("massage");
        try {
            String send = getsc().send(name);
            System.out.println(send);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
