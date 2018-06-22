package my;

import com.iflytek.cloud.speech.*;
import org.json.JSONException;

import java.io.IOException;

/**
 * Created by zmd on 2018/6/20.
 */
public class mySr {
    public static final String APPID = "5b28bef4";

    static mySr mySr;

    boolean mIsLoop = true;

    public static void main(String[] args) throws InterruptedException {
        SpeechUtility.createUtility("appid=" + APPID);
        getMySr().loop();
    }
    /*
    获取mysr
     */
    public static mySr getMySr(){
        if (mySr==null){
            mySr = new mySr();
        }
        return mySr;
    }
    /*
    唤醒主线程
     */
    public void waitupLoop(){
        synchronized(this){
            mySr.this.notify();
        }
    }
    public void Recognize() {
        if (SpeechRecognizer.getRecognizer() == null)
            SpeechRecognizer.createRecognizer();
        sr();
    }
    public boolean onLoop() {
        Recognize();
        return true;
    }
    /*
    线程等待
     */
    public void loop() {
        while (mIsLoop) {
            try {
                if (onLoop()) {
                    synchronized(this){
                        this.wait();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public  void sr() {
        SpeechRecognizer recognizer = SpeechRecognizer.createRecognizer();
        recognizer.setParameter(SpeechConstant.AUDIO_SOURCE, "1");
        recognizer.setParameter(SpeechConstant.RESULT_TYPE, "plain" );
        recognizer.setParameter(SpeechConstant.ASR_PTT, "0");
        recognizer.startListening(recListener);
    }
    /**
     * 听写监听器
     */
    public  RecognizerListener recListener = new RecognizerListener() {

        public void onBeginOfSpeech() {
            DebugLog.Log( "onBeginOfSpeech enter" );
            DebugLog.Log("*************开始录音*************");
        }
        public void onEndOfSpeech() {
            DebugLog.Log( "onEndOfSpeech enter" );
        }
        public void onVolumeChanged(int volume) {
//            DebugLog.Log( "onVolumeChanged enter" );
//            if (volume > 0)
//                DebugLog.Log("*************音量值:" + volume + "*************");
        }
        public void onResult(RecognizerResult results, boolean islast) {
            DebugLog.Log( "onResult enter" );
            String text = results.getResultString();
            DebugLog.Log( "语音结果是------"+text );
            sendClient sc = new sendClient();
            try {
                String send = sc.send(text);
                mySs ms = new mySs();
                ms.speak(send);
            } catch (IOException e) {
                DebugLog.Log( "报错了1");
            } catch (JSONException e) {
                DebugLog.Log( "报错了2");
            }
            waitupLoop();
        }
        public void onError(SpeechError error) {
            DebugLog.Log("****出错了*****" + error.getErrorCode()
                    + "*************");
            waitupLoop();
        }
        public void onEvent(int eventType, int arg1, int agr2, String msg) {
            DebugLog.Log( "onEvent enter" );
        }
    };
}
