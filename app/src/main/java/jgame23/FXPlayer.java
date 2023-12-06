package jgame23;

import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;

public enum FXPlayer {
   INTRO("INTRO.wav"),
   SECOND_FASE("SECOND_FASE.wav"),
   YAMATO("YAMATO.wav"),
   MAIN_THEME("MAIN_THEME.wav");

   public static enum Volume {
      MUTE, LOW, MEDIUM, HIGH
   }

   public static Volume volume = Volume.LOW;

   private Clip clip;

   FXPlayer(String wav) {
      try {
         URL url = this.getClass().getClassLoader().getResource("SFX/" + wav);
         AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
         clip = AudioSystem.getClip();
         clip.open(audioInputStream);
      } catch (UnsupportedAudioFileException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (LineUnavailableException e) {
         e.printStackTrace();
      }
   }

   public void play() {
      if (volume != Volume.MUTE) {
         if (!clip.isRunning()) {
            clip.setFramePosition(0);
            clip.start();
         }
      }
   }

   static void init() {
      values();
   }
}