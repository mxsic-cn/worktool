package cn.siqishangshu.applet;

/**
 * Created by liuchuan on 12/23/16.
 */
import java.applet.*;
import java.net.*;
public class AudioDemo extends Applet
{
    private AudioClip clip;
    private AppletContext context;
    public void init()
    {
        context = this.getAppletContext();
        String audioURL = this.getParameter("audio");
        if(audioURL == null)
        {
            audioURL = " o=1&height=66";
        }
        try
        {
            URL url = new URL(this.getDocumentBase(), audioURL);
            clip = context.getAudioClip(url);
        }catch(MalformedURLException e)
        {
            e.printStackTrace();
            context.showStatus("Could not load audio file!");
        }
    }
    public void start()
    {
        if(clip != null)
        {
            clip.loop();
        }
    }
    public void stop()
    {
        if(clip != null)
        {
            clip.stop();
        }
    }
}
