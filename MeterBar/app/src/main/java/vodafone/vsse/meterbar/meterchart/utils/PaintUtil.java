package vodafone.vsse.meterbar.meterchart.utils;

import android.graphics.Paint;
import android.graphics.Typeface;

import vodafone.vsse.meterbar.meterchart.models.MeterText;
import vodafone.vsse.meterbar.meterchart.models.MeterValueText;

/**
 * Created by AboElEla on 4/4/2016.
 *
 * Paint Utility
 */
public class PaintUtil {

    /**
     * Create paint object for text drawing according to given meter text
     * @param meterText
     * @return
     */
    public static Paint createTextPaint(MeterValueText meterText)
    {
        Paint paint = new Paint();

        paint.setColor(meterText.getTextColor());
        paint.setTextSize(meterText.getFontSize());
        paint.setTextSize(meterText.getFontSize());

        if(meterText.isBold() && meterText.isItalic()) {
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD | Typeface.ITALIC));
        }
        else if(meterText.isBold())
        {
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        }
        else if(meterText.isItalic())
        {
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.ITALIC));
        }

        return paint;
    }

    /**
     * Check if point is inside given rectangle or not
     *
     * @param x
     * @param y
     * @param left
     * @param top
     * @param right
     * @param bottom
     * @return
     */
    public static boolean isPointInsideRect(float x, float y,  float left, float top, float right, float bottom )
    {
        if(x >= left && x <= right)
        {
            if(y >= top && y <= bottom)
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if given point is inside circle or not
     * @param x
     * @param y
     * @param centerX
     * @param centerY
     * @param radius
     * @return
     */
    public static boolean isPointInsideCircle(float x, float y, float centerX, float centerY, float radius)
    {
        if(Math.abs(centerX - x) <= radius && Math.abs(centerY - y) <=radius ) {
            return true;
        }

        return false;
    }
}
