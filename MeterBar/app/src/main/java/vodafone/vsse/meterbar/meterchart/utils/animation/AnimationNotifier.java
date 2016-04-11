package vodafone.vsse.meterbar.meterchart.utils.animation;

import android.os.Handler;
import android.os.Message;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by AboElEla on 4/3/2016.
 *
 * This notifier is for animation. This is the delayer for animation effect
 *
 */
public class AnimationNotifier {
    public AnimationNotifier(int timeDelayInMilliSeconds, AnimationListener listener)
    {
        this.timeDelayInMilliSeconds = timeDelayInMilliSeconds;
        this.listener = listener;
    }

    /**
     * Start Animation
     */
    public void startAnimation()
    {
        handler = new Handler();
        animationRunnable.run();
    }

    private Runnable animationRunnable = new Runnable() {
        @Override
        public void run() {

            // notify animation of step
            listener.notifyAnimationStep(animationTags);

            // check if animation finished or should post repeated event
            if( ! listener.isAnimationFinished(animationTags) )
            {
                handler.postDelayed(animationRunnable, timeDelayInMilliSeconds);
            }
        }
    };

    public void addAnimationTag(String key, Object tag) {
        animationTags.put(key, tag);
    }


    private Handler handler;
    private int timeDelayInMilliSeconds;
    private AnimationListener listener;
    private HashMap<String, Object> animationTags = new HashMap<>();
}
