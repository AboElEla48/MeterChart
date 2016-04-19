package vodafone.vsse.meterbar.meterchart.views;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;

import java.util.List;

import vodafone.vsse.meterbar.meterchart.models.TooltipModel;
import vodafone.vsse.meterbar.meterchart.utils.PaintUtil;

/**
 * Created by AboElEla on 4/19/2016.
 *
 * This is the view of the tooltip drawn on bar
 */
public class MeterBarTooltip
{
    public MeterBarTooltip(TooltipModel model)
    {
        tooltipModel = model;

        textPainDrawer = PaintUtil.createTextPaint(tooltipModel.getTooltipText());

        tipPainDrawer = new Paint();
        tipPainDrawer.setColor(tooltipModel.getTipColor());
        tipPainDrawer.setStyle(Paint.Style.FILL);
    }

    /**
     * Draw tooltip
     * @param canvas
     * @param x
     * @param y
     * @param barWidth
     */
    public void drawTooltip(Canvas canvas, int x, int y, int barWidth)
    {
        // wrap text into lines
        List<String> lines = PaintUtil.wrapTextIntoLines(tooltipModel.getTooltipText().getText(), textPainDrawer, barWidth);

        Rect bounds;
        int tooltipHeight = 2 * textPadding + ((lines.size() - 1) * lineSpacing); // up and down margins

        // Calculate the height of the tooltip
        int oneLineHeight;
        bounds = new Rect();
        textPainDrawer.getTextBounds(lines.get(0), 0, lines.get(0).length(), bounds);
        oneLineHeight = bounds.height();
        tooltipHeight += oneLineHeight * lines.size();

        y -= tooltipHeight;
        y -= tooltipMargin;

        // draw tip rectangle
        canvas.drawRect(x, y, x + barWidth, y + tooltipHeight, tipPainDrawer);

        // draw arrow
        int arrowX = x + (barWidth / 2) - (arrowWidth / 2);
        int arrowY = y + tooltipHeight;

        Path arrowPath = new Path();
        arrowPath.setFillType(Path.FillType.EVEN_ODD);
        arrowPath.moveTo(arrowX, arrowY);

        arrowX += (arrowWidth / 2);
        arrowY += arrowHeight;
        arrowPath.lineTo(arrowX, arrowY);

        arrowX += (arrowWidth / 2);
        arrowY -= arrowHeight;
        arrowPath.lineTo(arrowX, arrowY);

        arrowX -= arrowWidth;
        arrowPath.lineTo(arrowX, arrowY);

        arrowPath.close();
        canvas.drawPath(arrowPath, tipPainDrawer);

        // draw tip text
        y += textPadding + oneLineHeight;
        int lineWidth;
        int textX;
        for(String line : lines)
        {
            // draw text centered
            bounds = new Rect();
            textPainDrawer.getTextBounds(line, 0, line.length(), bounds);
            lineWidth = bounds.width();

            textX = x + (barWidth / 2) - (lineWidth / 2);
            canvas.drawText(line, textX, y, textPainDrawer);
            y += lineSpacing + oneLineHeight;
        }
    }

    private TooltipModel tooltipModel;
    private Paint textPainDrawer;
    private Paint tipPainDrawer;

    private final int textPadding = 10;
    private final int lineSpacing = 4;
    private final int arrowHeight  = 25;
    private final int arrowWidth  = 30;
    private final int tooltipMargin = arrowHeight + 20;
}
