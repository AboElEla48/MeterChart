package vodafone.vsse.meterbar.meterchart.models;

import android.graphics.Bitmap;

/**
 * Created by AboElEla on 4/6/2016.
 */
public class MeterBarChunkHelper {
    public MeterBarChunkHelper()
    {

    }

    public MeterBarChunkHelper(Bitmap bitmap, int color, float hotAreaRadius)
    {
        setHelperBitmap(bitmap);
        setHelperColor(color);
        setHotAreaRadius(hotAreaRadius);
    }

    public Bitmap getHelperBitmap() {
        return helperBitmap;
    }

    public void setHelperBitmap(Bitmap bitmap) {
        helperBitmap = bitmap;
    }

    public int getHelperColor() {
        return helperColor;
    }

    public void setHelperColor(int helperColor) {
        this.helperColor = helperColor;
    }

    public float getHotAreaRadius() {
        return hotAreaRadius;
    }

    public void setHotAreaRadius(float hotAreaRadius) {
        this.hotAreaRadius = hotAreaRadius;
    }

    private Bitmap helperBitmap;
    private int helperColor;
    private float hotAreaRadius = 10f;

}
