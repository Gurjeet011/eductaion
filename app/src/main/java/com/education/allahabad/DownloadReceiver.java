package com.education.allahabad;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class DownloadReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
            // Update UI here, for example, hide the download icon
            // You can use LocalBroadcastManager to send an event to your activity or fragment
            // Or update the UI directly if you're in the same context
        }
    }
}
