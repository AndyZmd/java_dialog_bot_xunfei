package wx;

import org.restlet.Request;
import org.restlet.data.Form;
import org.restlet.data.Parameter;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ServerResource;
import my.sendClient;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

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
    protected Representation get() {
        Request request = getRequest();
        Form form = request.getResourceRef().getQueryAsForm();
        String signature = form.getFirstValue("signature");
        String timestamp = form.getFirstValue("timestamp");
        String nonce = form.getFirstValue("nonce");
        String echostr = form.getFirstValue("echostr");
        if(CheckUtil.checkSignature(signature,timestamp,nonce)){
            return new StringRepresentation(echostr);
        }
        return new StringRepresentation("");
    }
    @Override
    protected Representation post(Representation entity) {
        try {
            String etext = entity.getText();
            String respMessage = null;
            // xml请求解析
            Map<String, String> requestMap = CheckUtil.xmlToMap(etext);
            // 发送方帐号（open_id）
            String fromUserName = requestMap.get("FromUserName");
            // 公众帐号
            String toUserName = requestMap.get("ToUserName");
            // 消息类型
            String msgType = requestMap.get("MsgType");
            // 消息内容
            String content = requestMap.get("Content");
            // 文本消息
            //自动回复
            TextMessage text = new TextMessage();
            text.setContent("the text is" + content);
            text.setToUserName(fromUserName);
            text.setFromUserName(toUserName);
            text.setCreateTime(new Date().getTime() + "");
            text.setMsgType(msgType);
            respMessage = CheckUtil.textMessageToXml(text);
            return new StringRepresentation(respMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
