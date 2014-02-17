package com.glass.helloworld;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

import com.google.android.glass.timeline.LiveCard;
import com.google.android.glass.timeline.TimelineManager;

public class MyService extends Service {
	private static final String LIVE_CARD_ID = "Hello";
	private static final CharSequence HELLO_TEXT = "Hello World, my first Glass App!";
	private TimelineManager mTimelineManager;
	private LiveCard mLiveCard;

	public MyService() {
	}

	public void onCreate() {
		super.onCreate();
		mTimelineManager = TimelineManager.from(this);
	}

	public int onStartCommand(Intent intent, int flags, int startId) {
		RemoteViews rv = new RemoteViews(this.getPackageName(),
				R.layout.card_text);
		if (mLiveCard == null) {
			mLiveCard = mTimelineManager.createLiveCard(LIVE_CARD_ID);
			rv.setTextViewText(R.id.main_text, HELLO_TEXT);
			mLiveCard.setViews(rv);
			Intent mIntent = new Intent();
			mLiveCard.setAction(PendingIntent.getActivity(this, 0, mIntent, 0));
			mLiveCard.publish(LiveCard.PublishMode.REVEAL);
		}
		return START_STICKY;
	}

	public void onDestroy() {
		if (mLiveCard != null && mLiveCard.isPublished()) {
			mLiveCard.unpublish();
			mLiveCard = null;
		}
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		throw new UnsupportedOperationException("Not yet implemented");
	}
}
