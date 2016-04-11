package vodafone.vsse.meterbar.meterchart.utils;

import android.util.Log;

import vodafone.vsse.meterbar.meterchart.models.MeterBarChunkModel;
import vodafone.vsse.meterbar.meterchart.models.MeterBarModel;

/**
 * Created by AboElEla on 4/1/2016.
 */
public class LogUtil {

    /**
     * Log Bar
     * @param meterBarModel
     */
    public static void logBar(MeterBarModel meterBarModel)
    {
        if(isLogEnabled)
        {
            Log.d(LOG_TAG, "------Bar Values-----");
            Log.d(LOG_TAG, "Bar ID = " + meterBarModel.getId());
            Log.d(LOG_TAG, "Bar Chunks Total Value = " + meterBarModel.getChunksTotalValue());
            Log.d(LOG_TAG, "Bar Max Value = " + meterBarModel.getBarMaxValue());
            Log.d(LOG_TAG, "---------------------");
        }
    }

    /**
     * Log Chunk
     * @param meterBarChunkModel
     */
    public static void logChunk(MeterBarChunkModel meterBarChunkModel)
    {
        if(isLogEnabled)
        {
            Log.d(LOG_TAG, "------Chunk Values-----");
            Log.d(LOG_TAG, "Chunk ID = " + meterBarChunkModel.getId());
            Log.d(LOG_TAG, "Chunk Up Text = " + meterBarChunkModel.getUpText());
            Log.d(LOG_TAG, "Chunk Middle Text = " + meterBarChunkModel.getMiddleText());
            Log.d(LOG_TAG, "Chunk Down Text = " + meterBarChunkModel.getDownText());
            Log.d(LOG_TAG, "-----------------------");
        }
    }

    /**
     * Log given string
     * @param str
     */
    public static void logString(String str)
    {
        if(isLogEnabled)
        {
            Log.d(LOG_TAG, str);
        }
    }

    public final static boolean isLogEnabled = true;
    public final static String LOG_TAG = "Meter_Chart_Log";
}
