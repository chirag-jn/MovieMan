package com.chiragjn.movieman;

import android.app.Application;
import android.content.Context;

import com.facebook.common.logging.FLog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.core.ImageTranscoderType;
import com.facebook.imagepipeline.core.MemoryChunkType;

public class MovieManApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        setupFresco();
    }

    public static Context getAppContext() {
        return context;
    }

    private void setupFresco() {
        Fresco.initialize(
                getAppContext(),
                ImagePipelineConfig.newBuilder(getAppContext())
                        .setMemoryChunkType(MemoryChunkType.BUFFER_MEMORY)
                        .setImageTranscoderType(ImageTranscoderType.JAVA_TRANSCODER)
                        .experiment().setNativeCodeDisabled(true)
                        .build());
        if (BuildConfig.DEBUG) {
            FLog.setMinimumLoggingLevel(FLog.VERBOSE);
        }
    }
}
