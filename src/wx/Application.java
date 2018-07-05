package wx;

import org.restlet.Component;
import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;
/*
新建个sever 用来接收微信公众号的消息 转发到 后端的机器人
 */
public class Application extends org.restlet.Application {

    public static void main(String[] args) throws Exception {
        Component component = new Component();
        component.getServers().add(Protocol.HTTP,8182);
        component.getDefaultHost().attach(new Application(null));
        component.start();
    }
    public Application(Context context) {
        super(context);
    }
    @Override
    public Restlet createInboundRoot() {
        Router router = new Router(this.getContext());
        //绑定资源路径"wx"到对应的处理资源类(wxResource)
        router.attach("/wx", wxResource.class);
        return router;
    }
}
