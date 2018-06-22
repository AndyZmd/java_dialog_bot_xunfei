package my;

import com.iflytek.cloud.speech.*;

/**
 * Created by zmd on 2018/6/21.
 */
public class mySs {
    public static final String APPID = "5b28bef4";
    public static String flag = "";
    public void speak(String massage) {
        SpeechUtility.createUtility("appid=" + APPID);
        SpeechSynthesizer speechSynthesizer = SpeechSynthesizer.createSynthesizer();
        // 设置发音人
        speechSynthesizer.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");
        //启用合成音频流事件，不需要时，不用设置此参数
        speechSynthesizer.setParameter(SpeechConstant.SPEED,"50");
        speechSynthesizer.setParameter(SpeechConstant.VOLUME,"80");
        flag = massage;
        speechSynthesizer.startSpeaking(massage,synthesizeToUriListener);
    }
    /**
     * 合成监听器
     */
    public static SynthesizerListener synthesizeToUriListener = new SynthesizerListener() {
        @Override
        public void onBufferProgress(int i, int i1, int i2, String s) {
        }
        @Override
        public void onSpeakBegin() {
            DebugLog.Log(flag);
        }
        @Override
        public void onSpeakProgress(int i, int i1, int i2) {

        }
        @Override
        public void onSpeakPaused() {

        }
        @Override
        public void onSpeakResumed() {

        }
        @Override
        public void onCompleted(SpeechError speechError) {

        }
        @Override
        public void onEvent(int i, int i1, int i2, int i3, Object o, Object o1) {
            System.out.println("123123123123");
        }
    };
}
