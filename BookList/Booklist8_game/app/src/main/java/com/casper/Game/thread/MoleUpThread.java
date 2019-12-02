package com.casper.Game.thread;

import android.util.Log;

import com.casper.Game.GameSurfaceView;
import com.casper.Game.collection.MoleCollection;


public class MoleUpThread extends Thread {

	public boolean flag=true;
	private GameSurfaceView main_view;
	private MoleCollection m_collection;
	private static final int INDURATION=1000,DE_SPE=15,MIN_DURATION=100;
	private static int ran_duration=INDURATION;
	
	
	public MoleUpThread(GameSurfaceView main_view,MoleCollection m_collection)
	{
		this.main_view=main_view;
		this.m_collection=m_collection;
	}
	
	public void run()
	{
		Log.i("MoleUpThread","随机线程已开启");
		while(flag==true)
		{
			if(main_view.gameState==GameSurfaceView.GAME_ING)
			{
				long start_time,end_time;
				start_time=System.currentTimeMillis();

				m_collection.ran_up();
				end_time=System.currentTimeMillis();
				if(end_time-start_time<ran_duration)
				{
					try {
						Thread.sleep(ran_duration-(end_time-start_time));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public void decreadDuration()
	{
		if(ran_duration-DE_SPE>=MIN_DURATION)
		{
			ran_duration-=DE_SPE;
		}

	}
	
	public void resetDuration()
	{
		ran_duration=INDURATION;
	}
}
