package vodafone.vsse.meterbar.meterchart.utils.animation;

import java.util.HashMap;

/**
 * Created by AboElEla on 4/3/2016.
 */
public interface AnimationListener {

    /**
     * Conditional method to know if animation is finished or not
     * @return
     */
    boolean isAnimationFinished(HashMap<String, Object> animationTags);

    /**
     * Notify drawing animation
     *
     */
    void notifyAnimationStep(HashMap<String, Object> animationTags);
}
