package wx;

import org.json.JSONException;
import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ServerResource;
import my.sendClient;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * Created by zmd on 2018/6/28.
 */
public class wxResource extends ServerResource {
    public static sendClient sendClient =null;
    public static TextMessage TextMessage =null;
    public static sendClient getsc(){
        if(sendClient==null){
            sendClient = new sendClient();
        }
        return sendClient;
    }
    public static TextMessage gettm(){
        if(TextMessage==null){
            TextMessage = new TextMessage();
        }
        return TextMessage;
    }
    @Override
    protected Representation get() {
        Form form = getRequest().getResourceRef().getQueryAsForm();
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
            String content=null;
            // xml请求解析
            Map<String, String> requestMap = CheckUtil.xmlToMap(etext);
            // 发送方帐号（open_id）
            String fromUserName = requestMap.get("FromUserName");
            // 公众帐号
            String toUserName = requestMap.get("ToUserName");
            // 消息类型
            String msgType = requestMap.get("MsgType");
            //自动回复
            if(msgType.equals("text")){
                // 消息内容
                content = getsc().send(requestMap.get("Content"));//文本发送到后台机器人进行对话
            }
            else if(msgType.equals("voice")){//语音转换后的文本
                String rm2 = requestMap.get("Recognition");
                content = getsc().send(rm2.substring(0,rm2.length() - 1));//微信语音转换自带标点符号 此处要删去句尾的句号 (接语义识别接口则无需去除标点符号)
            }
            else {
                content = "我不知道你在说什么";
            }
            TextMessage text = gettm();
            text.setContent(content);//返回内容
            text.setToUserName(fromUserName);
            text.setFromUserName(toUserName);
            text.setCreateTime(new Date().getTime() + "");
            text.setMsgType("text");
            respMessage = CheckUtil.textMessageToXml(text);
            return new StringRepresentation(respMessage);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new StringRepresentation("程序出错了！");
    }
}
