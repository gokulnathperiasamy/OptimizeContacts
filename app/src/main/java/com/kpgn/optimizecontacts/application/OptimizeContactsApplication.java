package com.kpgn.optimizecontacts.application;

import android.app.Application;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

public class OptimizeContactsApplication extends Application {

    private static final String TAG = OptimizeContactsApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        setFixedFontScale();
    }

    private void setFixedFontScale() {
        try {
            Configuration configuration = getResources().getConfiguration();
            DisplayMetrics metrics = new DisplayMetrics();
            WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

            configuration.fontScale = (float) 1;

            if (windowManager != null) {
                windowManager.getDefaultDisplay().getMetrics(metrics);
                metrics.scaledDensity = configuration.fontScale * metrics.density;
                getBaseContext().getResources().updateConfiguration(configuration, metrics);
            }
        } catch (Exception e) {
            Log.e(TAG, e.getLocalizedMessage());
        }
    }
}
