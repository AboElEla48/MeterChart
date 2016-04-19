package vodafone.vsse.meterbar.meterchart.views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;
import java.util.HashMap;

import vodafone.vsse.meterbar.meterchart.models.MeterBarModel;
import vodafone.vsse.meterbar.meterchart.models.MeterChartModel;
import vodafone.vsse.meterbar.meterchart.models.TooltipModel;
import vodafone.vsse.meterbar.meterchart.utils.LogUtil;
import vodafone.vsse.meterbar.meterchart.utils.animation.AnimationListener;
import vodafone.vsse.meterbar.meterchart.utils.animation.AnimationNotifier;

/**
 * Created by AboElEla on 3/24/2016.
 *
 * This custom control will draw the column chart according to given columns data
 */
public class MeterChart extends View{

    public MeterChart(Context context)
    {
        super(context);
        initView();
    }

    public MeterChart(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initView();
    }

    /**
     * init view
     */
    private void initView()
    {
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                float x = event.getX();
                float y = event.getY();

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        // Check if the click on circle info caption
                        if (isCircleVisible() && infoCircle.isTouchEventInCircleInfo(x, y)) {
                            LogUtil.logString("Circle consumed Touch event");
                            isCircleClicked = true;
                            invalidate();
                        } else {
                            LogUtil.logString("Touch: should move to bars");

                            // Check if the click on one of the bars
                            for (int i = 0; i < barsList.size(); i++) {
                                if (barsList.get(i).isTouchEventInBar(x, y)) {
                                    LogUtil.logString("Touch: bar found");

                                    // point inside bar
                                    barsList.get(i).onTouchEvent(x, y);
                                    break;
                                }
                            }

                            LogUtil.logString("Touch: event down finished");

                        }

                        return true;
                    }

                    case MotionEvent.ACTION_UP: {
                        if (isCircleClicked) {
                            isCircleClicked = false;
                            invalidate();
                        }
                        return true;
                    }
                }
                return false;
            }
        });
    }


    /**
     * Init chart with meter model
     *
     * @param meterChartModel this is the model used to init meter chart
     */
    public void initChart(MeterChartModel meterChartModel)
    {
        this.meterChartModel = meterChartModel;
        infoCircle = new MeterInfoCircle(meterChartModel.getInfoCircleModel());
    }

    /**
     * Control the visibility of info circle
     * @param isVisible flag to show/hide info circle
     */
    public void setInfoCircleVisible(boolean isVisible)
    {
        meterChartModel.setIsCircleVisible(isVisible);
        invalidate();
    }

    /**
     * Show/hide tooltip on bar
     * @param barID
     * @param isVisible
     */
    public void setBarTooltipVisible(int barID, boolean isVisible)
    {
        for( MeterBarModel meterBarModel : meterChartModel.getMeterBars())
        {
            if(meterBarModel.getId() == barID)
            {
                meterBarModel.setIsTooltipVisible(isVisible);
                break;
            }
        }

        invalidate();
    }

    /**
     * Show/hide tooltip on bar
     * @param barID
     * @param tooltipModel
     * @param isVisible
     */
    public void setBarTooltipVisible(int barID, TooltipModel tooltipModel, boolean isVisible)
    {
        for( MeterBarModel meterBarModel : meterChartModel.getMeterBars())
        {
            if(meterBarModel.getId() == barID)
            {
                meterBarModel.setTooltipModel(tooltipModel);
                meterBarModel.setIsTooltipVisible(isVisible);
                break;
            }
        }

        invalidate();
    }

    /**
     * Check if circle info is visible or not
     * @return
     */
    public boolean isCircleVisible()
    {
        return meterChartModel.isCircleVisible();
    }

    /**
     * Draw chart animated
     */
    public void drawChartAnimated(int animationDuration)
    {
        doChartAnimation(animationDuration);
    }

    /**
     * Draw chart animated with given step
     *
     * @param animationDuration
     * @param animationDownStep
     */
    public void drawChartAnimated(int animationDuration, int animationDownStep)
    {
        setAnimationStep(animationDownStep);

        doChartAnimation(animationDuration);
    }

    /**
     * Do chart animation
     * @param animationDuration
     */
    private void doChartAnimation(int animationDuration)
    {
        for(MeterBarModel meterBarModel : meterChartModel.getMeterBars()) {
            // Animate last chunk
            int size = meterBarModel.getBarChunks().size();
            double originalAnimationChunkValue = meterBarModel.getBarChunks().get(size - 1).getValue();
            meterBarModel.getBarChunks().get(size - 1).setValue(originalAnimationChunkValue +
                    meterBarModel.getBarMaxValue() - meterBarModel.getChunksTotalValue());

            LogUtil.logString("##FF Original Value of last chunk = " + originalAnimationChunkValue);

            AnimationNotifier animationNotifier = new AnimationNotifier(animationDuration, new AnimationDownListener());
            animationNotifier.addAnimationTag(Animation_Bar_Model_Key, meterBarModel);
            animationNotifier.addAnimationTag(Animation_Original_Chunk_Val_Key, originalAnimationChunkValue);

            animationNotifier.startAnimation();
        }
    }

    /**
     * Fill bars of chart
     * @param animationDuration
     */
    public void fillChart(int animationDuration)
    {
        doFillChart(animationDuration);
    }

    /**
     * fill chart with given animation step
     * @param animationDuration
     * @param animationStep
     */
    public void fillChart(int animationDuration, int animationStep)
    {
        setAnimationStep(animationStep);

        doFillChart(animationDuration);
    }

    /**
     * Do fill animation
     * @param animationDuration
     */
    private void doFillChart(int animationDuration)
    {
        for(MeterBarModel meterBarModel : meterChartModel.getMeterBars())
        {
            AnimationNotifier animationNotifier = new AnimationNotifier(animationDuration, new AnimationUpListener());
            animationNotifier.addAnimationTag(Animation_Bar_Model_Key, meterBarModel);
            animationNotifier.startAnimation();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        LogUtil.logString("Canvas: " + canvas.getWidth() + ", " + canvas.getHeight());

        // Draw Chart bars
        drawBars(canvas);

        // Draw chart circles
        drawCircle(canvas);
    }

    /**
     * Draw bars
     * @param canvas
     */
    private void drawBars(Canvas canvas)
    {
        // get the dimensions of view
        int chartWidth = getWidth();
        int chartHeight = getHeight();

        int numOfBars = meterChartModel.getMeterBars().size();

        // Draw chart views

        // Draw bars
        double barWidth = (double)chartWidth / (double)numOfBars - (meterChartModel.getBarsMargin() * (numOfBars-1));
        float x = 0f;
        float y = 0f;
        barsList = new ArrayList<>();
        for(MeterBarModel barModel : meterChartModel.getMeterBars())
        {
            MeterBar meterBar = new MeterBar(barModel, meterChartModel.getMeterChartListener());
            meterBar.drawBar(canvas, x, (float)barWidth);
            x += barWidth + meterChartModel.getBarsMargin();

            barsList.add(meterBar);
        }
    }

    /**
     * Draw chunks
     * @param canvas
     */
    private void drawCircle(Canvas canvas)
    {
        int numOfBars = meterChartModel.getMeterBars().size();

        // get the dimensions of view
        int chartWidth = getWidth();
        int chartHeight = getHeight();

        double barWidth = (double)chartWidth / (double)numOfBars - (meterChartModel.getBarsMargin() * (numOfBars-1));
        float x = 0f;
        float y = 0f;



        // If the number of bars is 2, then draw circle between 2 bars relative to small line, otherwise, draw at the middle top
        // of screen
        if(meterChartModel.isCircleVisible())
        {
            double minTotal = -1;
            MeterBarModel smallestModel= null;
            if(numOfBars == 2)
            {
                int i = 0;
                do {

                    MeterBarModel meterBarModel = meterChartModel.getMeterBars().get(i);

                    double ratio = meterBarModel.getChunksTotalValue() / meterBarModel.getBarMaxValue();
                    if( minTotal == -1 || ratio < minTotal)
                    {
                        minTotal = ratio;
                        smallestModel = meterBarModel;
                    }

                    i++;
                }while ( i < numOfBars);

                if(minTotal > 0)
                {
                    double heightUnitPerPixel = (double)canvas.getHeight() / smallestModel.getBarMaxValue();
                    float barHeight = (float) (smallestModel.getChunksTotalValue() * heightUnitPerPixel);
                    x = chartWidth / 2;
                    y = chartHeight - barHeight + infoCircle.getInfoCircleHeight();
                    infoCircle.drawCircle(canvas, x, y, isCircleClicked);
                }
            }
            else
            {
                // Draw at middle top of screen
                x = chartWidth / 2;
                y = infoCircle.getInfoCircleHeight();
                infoCircle.drawCircle(canvas, x, y, isCircleClicked);
            }
        }
    }

    /**
     * Define listener for down animation
     */
    private class AnimationDownListener implements AnimationListener
    {
        @Override
        public boolean isAnimationFinished(HashMap<String, Object> animationTags) {

            MeterBarModel meterBarModel = (MeterBarModel) animationTags.get(Animation_Bar_Model_Key);
            double originalAnimationChunkValue = ((Double)animationTags.get(Animation_Original_Chunk_Val_Key)).doubleValue();

            int size = meterBarModel.getBarChunks().size();
            LogUtil.logString("##FF Value: " + meterBarModel.getBarChunks().get(size - 1).getValue());
            if(originalAnimationChunkValue >= meterBarModel.getBarChunks().get(size - 1).getValue())
            {
                LogUtil.logString("##FF Animation Finished");
                return true;
            }

            LogUtil.logString("##FF Animation still running");
            return false;
        }

        @Override
        public void notifyAnimationStep(HashMap<String, Object> animationTags) {

            MeterBarModel meterBarModel = (MeterBarModel) animationTags.get(Animation_Bar_Model_Key);

            LogUtil.logString("Animation Step");

            //Decrease animation step to redraw bar chunks
            int size = meterBarModel.getBarChunks().size();
            double currentVal = meterBarModel.getBarChunks().get(size - 1).getValue();

            double originalAnimationChunkValue = ((Double)animationTags.get(Animation_Original_Chunk_Val_Key)).doubleValue();
            if(currentVal + animationDownStep < originalAnimationChunkValue)
            {
                currentVal = originalAnimationChunkValue;
            }
            else
            {
                currentVal += animationDownStep;
            }

            meterBarModel.getBarChunks().get(size - 1).setValue(currentVal);

            // Draw bar
            invalidate();
        }
    }


    public void setAnimationStep(int animationStep) {
        this.animationStep = animationStep;

        animationDownStep = -1 * animationStep;
    }

    public int getAnimationStep() {
        return animationStep;
    }

    /**
     * Define listener for up animation
     */
    private class AnimationUpListener implements AnimationListener
    {

        @Override
        public boolean isAnimationFinished(HashMap<String, Object> animationTags) {
            // Check if meter reached the max value
            MeterBarModel meterBarModel = (MeterBarModel) animationTags.get(Animation_Bar_Model_Key);
            return meterBarModel.getChunksTotalValue() >= meterBarModel.getBarMaxValue();
        }

        @Override
        public void notifyAnimationStep(HashMap<String, Object> animationTags) {

            // Perform one step up
            MeterBarModel meterBarModel = (MeterBarModel) animationTags.get(Animation_Bar_Model_Key);
            double step;
            if(meterBarModel.getChunksTotalValue() + animationStep > meterBarModel.getBarMaxValue())
            {
                step = meterBarModel.getBarMaxValue() - meterBarModel.getChunksTotalValue();
            }
            else
            {
                step = animationStep;
            }

            meterBarModel.getBarChunks().get(meterBarModel.getBarChunks().size() - 1).setValue(
                    meterBarModel.getBarChunks().get(meterBarModel.getBarChunks().size() - 1).getValue() + step
            );

            // Draw bar bar
            invalidate();
        }
    }

    private int animationStep = 100;
    private int animationDownStep = -1 * animationStep;

    // Hold the model representing the data of the control
    private MeterChartModel meterChartModel;
    private MeterInfoCircle infoCircle;
    private boolean isCircleClicked = false;

    private ArrayList<MeterBar> barsList;

    private final String Animation_Bar_Model_Key = "Animation_Bar_Model_Key";
    private final String Animation_Original_Chunk_Val_Key = "Animation_Original_Chunk_Val_Key";
}
