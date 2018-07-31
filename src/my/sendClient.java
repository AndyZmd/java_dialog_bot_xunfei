package my;

import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by zmd on 2018/6/21.
 */
public class sendClient  {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static String developerToken = "0d8aed97-23e8-41af-b12f-5b4e34a5d888";
    public String string = "";
    public String send(String args) throws IOException, JSONException {
        String url = String.format("http://localhost:%d/api/v1/core/query", 8830);
        HttpUrl httpUrl = HttpUrl.parse(url).newBuilder()
                .addQueryParameter("version", "20170726")
                .build();
        String json =
                "{\"query_text\":\""+args+"\"}";
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder().url(httpUrl).header("Authorization", "CAAP " + developerToken)
                .post(body).build();
        Response response = new OkHttpClient.Builder().build().newCall(request).execute();
        String responseText = response.body().string();

        DebugLog.Log(responseText);

        JSONObject jb=new JSONObject(responseText);
        if(!jb.getString("data").equals("{}")){
            string = jb.getJSONObject("data").getString("suggest_answer");
        }
        else {
            return "我不知道你在说什么";
        }
        return string;
    }
    public static void main(String[] args) throws IOException, JSONException {
//        sendClient sendClient = new sendClient();
//        sendClient.send("dghdgdfgdfgdf");
        //String myjson = "{\"code\":200,\"time\":1529564943003,\"msg\":\"ok\",\"data\":{\"AnswerMessage\":{\"answer\":{\"text\":\"测试\"},\"id\":\"749c8909-40fd-4c95-a2e7-6d725419ff63\",\"type\":1}}}";
    }
}
