package com.akshata.batteryassistant;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import java.util.HashMap;
import java.util.Locale;
import java.util.UUID;
import org.apache.log4j.Logger;

/**
 * This class is used to handle android text to speech functionality. Android has inbuilt support
 * for TextToSpeech handling we just have to use it in proper way.
 *
 * @author INTERFACE
 * @version 2.0
 */
public class TextToSpeechHandler {

  //  private static final Logger logger = AppLogger.getLogger(TextToSpeechHandler.class);
  private TextToSpeech textToSpeech;
  private TTSCompletionListener ttsCompletionListener;

  /**
   * public constructor to initialize text to speech handler.
   *
   * @param context - {@link Context}
   */
  public TextToSpeechHandler(Context context) {
    textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
      @Override
      public void onInit(int status) {
        if (status == TextToSpeech.ERROR) {
//          logger.error("TextToSpeech initialization ERROR");
        } else if (status == TextToSpeech.SUCCESS) {
//          logger.debug("TextToSpeech initialization SUCCESS");
        }
      }
    });
    textToSpeech.setLanguage(Locale.getDefault());
    textToSpeech.setSpeechRate(0.90f);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
      textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener());
    }
//    logger.debug("TextToSpeechViewModel initialized.");
  }

  /**
   * This method is responsible to read specified message using android default TextToSpeech
   * implementation.
   *
   * @param message - {@link String} message which should be read.
   */
  public void playMessage(String message, TTSCompletionListener ttsCompletionListener) {
    this.ttsCompletionListener = ttsCompletionListener;
    if (!message.trim().isEmpty()) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
          textToSpeech
              .speak(message.trim(), TextToSpeech.QUEUE_ADD, null, UUID.randomUUID().toString());
        } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT_WATCH) {
          HashMap<String, String> map = new HashMap<>();
          map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, UUID.randomUUID().toString());
          textToSpeech.speak(message.trim(), TextToSpeech.QUEUE_ADD, map);
        }
      }
    } else {
//      logger.debug("Message should not be empty.");
    }
  }

  /**
   * Release text-to-speech engine which bound to this engine.
   */
  public void releaseTextToSpeechEngine() {
    if (textToSpeech != null) {
      textToSpeech.shutdown();
    }
  }

  @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
  private class UtteranceProgressListener extends android.speech.tts.UtteranceProgressListener {

    @Override
    public void onStart(String utteranceId) {
//      logger.debug("Utterance started for Id : " + utteranceId);
    }

    @Override
    public void onDone(String utteranceId) {
//      logger.debug("Utterance completed for Id : " + utteranceId);
      if (ttsCompletionListener != null) {
        ttsCompletionListener.onTTSPlatCompleted();
      }
    }

    @Override
    public void onError(String utteranceId) {
//      logger.error("Utterance interrupted for Id : " + utteranceId);
    }
  }

  /**
   * Interface is used to provide callback for TTS play completion.
   */
  public interface TTSCompletionListener {

    void onTTSPlatCompleted();
  }
}
