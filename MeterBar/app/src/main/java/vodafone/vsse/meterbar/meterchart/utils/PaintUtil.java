package vodafone.vsse.meterbar.meterchart.utils;

import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * Wrap text into lines according to given line width
     *
     * @param text
     * @param paint
     * @param lineWidth
     * @return
     */
    public static List<String> wrapTextIntoLines(String text, Paint paint, int lineWidth)
    {
        ArrayList<String> lines = new ArrayList<>();
        int textWidth;
        if(text != null)
        {
            Rect bounds = new Rect();
            paint.getTextBounds(text, 0, text.length(), bounds);
            textWidth = bounds.width();
            if(textWidth < lineWidth)
            {
                lines.add(text);
                return lines;
            }

            int spaceIndex = -1;
            String oneLine = "";
            String word;
            do
            {
                spaceIndex = text.indexOf(" ");
                if(spaceIndex > -1)
                {
                    word = text.substring(0, spaceIndex + 1);
                    bounds = new Rect();
                    paint.getTextBounds(oneLine + word, 0, oneLine.length() + word.length(), bounds);
                    textWidth = bounds.width();
                    if(textWidth <= lineWidth)
                    {
                        oneLine += word;
                    }
                    else
                    {
                        lines.add(oneLine);
                        oneLine = new String(word);
                    }

                    text = text.substring(spaceIndex + 1);
                }
                else
                {
                    oneLine += text;
                    lines.add(oneLine);
                    text = "";
                }
            }while (text.length() > 0);
        }
        return lines;
    }
}
