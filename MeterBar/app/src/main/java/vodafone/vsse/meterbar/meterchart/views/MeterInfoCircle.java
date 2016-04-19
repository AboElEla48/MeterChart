package vodafone.vsse.meterbar.meterchart.views;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import vodafone.vsse.meterbar.meterchart.models.MeterInfoCircleModel;
import vodafone.vsse.meterbar.meterchart.utils.PaintUtil;

/**
 * Created by AboElEla on 3/27/2016.
 *
 * This is the custom view of meter info circle
 */
public class MeterInfoCircle{

    /**
     * init info circle model
     *
     * @param infoCircleModel
     */
    public MeterInfoCircle(MeterInfoCircleModel infoCircleModel)
    {
        meterInfoCircleModel = infoCircleModel;

        infoCirclePaintDrawer = new Paint();
        infoCirclePaintDrawer.setStyle(Paint.Style.FILL);
        infoCirclePaintDrawer.setColor(infoCircleModel.getBackgroundColor());

        infoCircleDetailsPaintDrawer = PaintUtil.createTextPaint(meterInfoCircleModel.getDetailsText());
        infoCircleCaptionPaintDrawer = PaintUtil.createTextPaint(meterInfoCircleModel.getCaptionText());

    }

    /**
     * calculate the height of circle
     * @return
     */
    public float getInfoCircleHeight()
    {
        return meterInfoCircleModel.getInfoCircleMargin() + meterInfoCircleModel.getInfoCircleRadius() + meterInfoCircleModel.getInfoCircleDetailsRadius();
    }

    /**
     * Check if touch even inside circle info or not
     * @param x
     * @param y
     * @return
     */
    public boolean isTouchEventInCircleInfo(float x, float y)
    {
        return PaintUtil.isPointInsideCircle(x, y, captionCenterX, captionCenterY, meterInfoCircleModel.getInfoCircleRadius());
    }


    /**
     * Draw circle
     * @param canvas
     * @param x
     * @param y
     */
    public void drawCircle(Canvas canvas, float x, float y, boolean showDetails)
    {
        Rect bounds = new Rect();
        String str;
        int textHeight, textWidth;
        if(showDetails)
        {
            // Draw details circle
            canvas.drawCircle(x, y,
                    meterInfoCircleModel.getInfoCircleDetailsRadius(), infoCirclePaintDrawer);

            str = meterInfoCircleModel.getDetailsText().getText();
            infoCircleDetailsPaintDrawer.getTextBounds(str, 0, str.length(), bounds);
            textHeight = bounds.height();
            textWidth = bounds.width();
            canvas.drawText(str, x - (textWidth / 2), y + (textHeight/2), infoCircleDetailsPaintDrawer);
        }

        // Draw info circle
        y += meterInfoCircleModel.getInfoCircleDetailsRadius() + meterInfoCircleModel.getInfoCircleMargin() + meterInfoCircleModel.getInfoCircleRadius();
        canvas.drawCircle(x, y, meterInfoCircleModel.getInfoCircleRadius(), infoCirclePaintDrawer);

        captionCenterX = x;
        captionCenterY = y;

        str = meterInfoCircleModel.getCaptionText().getText();
        infoCircleCaptionPaintDrawer.getTextBounds(str, 0, str.length(), bounds);
        textHeight = bounds.height();
        textWidth = bounds.width();
        canvas.drawText(str, x - (textWidth / 2), y + (textHeight/2), infoCircleCaptionPaintDrawer);
    }

    private float captionCenterX, captionCenterY;

    private MeterInfoCircleModel meterInfoCircleModel;
    private Paint infoCirclePaintDrawer;


    private Paint infoCircleCaptionPaintDrawer;
    private Paint infoCircleDetailsPaintDrawer;

}
