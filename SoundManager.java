import javax.sound.sampled.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class SoundManager {
    private Map<String,Clip> soundClips;
    private static SoundManager instance;

    private SoundManager() {
        soundClips=new HashMap<>();
        loadSounds();
    }

    public static SoundManager getInstance() {
        if (instance==null) {
            instance=new SoundManager();
        }
        return instance;
    }

    private void loadSounds() {
        try {
            loadSound("gameStart", "C:\\Users\\User\\OneDrive\\Desktop\\Sky Survivor\\src\\game-music-loop.wav");
            loadSound("starCollect", "C:\\Users\\User\\OneDrive\\Desktop\\Sky Survivor\\src\\collect-star.wav");
            loadSound("immunityCollect", "C:\\Users\\User\\OneDrive\\Desktop\\Sky Survivor\\src\\collect-star.wav");
        } catch (Exception e) {
            System.out.println("Error loading sounds: " + e.getMessage());
        }
    }

    private void loadSound(String name,String filePath) {
        try {
            File soundFile=new File(filePath);
            if (soundFile.exists()) {
                AudioInputStream audioStream=AudioSystem.getAudioInputStream(soundFile);
                Clip clip=AudioSystem.getClip();
                clip.open(audioStream);
                soundClips.put(name, clip);
            } else {
                System.out.println("Sound file not found: " + filePath);
            }
        } catch (Exception e) {
            System.out.println("Error loading sound " + name + ": " + e.getMessage());
        }
    }

    public void playSound(String soundName) {
        Clip clip=soundClips.get(soundName);
        if (clip!=null) {
            try {
                clip.setFramePosition(0);
                clip.start();
            } catch (Exception e) {
                System.out.println("Error playing sound " + soundName + ": " + e.getMessage());
            }
        }
    }

    public void stopSound(String soundName) {
        Clip clip=soundClips.get(soundName);
        if (clip!=null && clip.isRunning()) {
            clip.stop();
        }
    }

    public void stopAllSounds() {
        for (Clip clip:soundClips.values()) {
            if (clip!=null && clip.isRunning()) {
                clip.stop();
            }
        }
    }
}
