package wx;

import java.util.List;

/**
 * Created by zmd on 2018/7/5.
 * 图文信息类
 */
public class NewsMessage {
    public String ToUserName;
    public String FromUserName;
    public String CreateTime;
    public String MsgType;

    public int ArticleCount;
    public List<ArticleMessage> Articles;

    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }

    public int getArticleCount() {
        return ArticleCount;
    }

    public void setArticleCount(int articleCount) {
        ArticleCount = articleCount;
    }

    public List<ArticleMessage> getArticles() {
        return Articles;
    }

    public void setArticles(List<ArticleMessage> articles) {
        Articles = articles;
    }
}
