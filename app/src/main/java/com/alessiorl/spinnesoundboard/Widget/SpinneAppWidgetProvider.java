package com.alessiorl.spinnesoundboard.Widget;

import com.alessiorl.spinnesoundboard.Activities.MainActivity;

import com.alessiorl.spinnesoundboard.Helpers.AudioPlayer;
import com.alessiorl.spinnesoundboard.Helpers.Sound;
import com.alessiorl.spinnesoundboard.R;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;


public class SpinneAppWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {

            Intent intent = new Intent(context, BackgroundSoundService.class);
            PendingIntent playIntent = PendingIntent.getService(context, 0, intent, 0);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.layout_spinne_widget_random);
            views.setOnClickPendingIntent(R.id.R_widget, playIntent);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }

    }
}

