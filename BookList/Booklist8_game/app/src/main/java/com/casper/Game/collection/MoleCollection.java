package com.casper.Game.collection;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;


import com.casper.Game.GameSurfaceView;
import com.casper.Game.object.Hole;
import com.casper.Game.object.Mole;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;


public class MoleCollection {
	public static final int MOLECOUNT=HoleCollection.HOLECOUNT;
	public static List<Mole> collection;
	private Bitmap mole_live_bmp,mole_die_bmp;
	private HoleCollection h_col;
	
	private GameSurfaceView gsv;
	private Random random = new Random();
	
	public MoleCollection(Bitmap mole_live_bmp,Bitmap mole_die_bmp,HoleCollection h_col,GameSurfaceView gsv)
	{
		this.mole_live_bmp=mole_live_bmp;
		this.mole_die_bmp=mole_die_bmp;
		this.h_col = h_col;
		this.gsv=gsv;
		
		collection=new LinkedList<Mole>();
	}
	
	public void initialize()
	{
		for(Hole hole:h_col.collection)
		{
			collection.add(new Mole(hole,mole_live_bmp,mole_die_bmp,gsv));
		}
	}
	
	public void reinitialize()
	{
		for(Mole mole:collection)
		{
			mole.reset();
		}
	}
	 
	
	public void draw(Canvas canvas,Paint paint)
	{
		for(Mole mole:collection)
		{
			mole.draw(canvas, paint);
		}
	}
	
	public void logic()
	{
		for(Mole mole:collection)
		{
			mole.logic();
		}
	}
	
	public void ran_up()
	{
		int num = random.nextInt(collection.size()-1);
		Mole mole = collection.get(num);
		if(mole.islive==true&&mole.getState()== Mole.ACT.STAND)
		{
			mole.setState(Mole.ACT.UP);
		}
		else
		{
		}
	}
	
	public void increaSpe()
	{
		Mole.increaSpe();
	}
	public void resetSpe()
	{
		Mole.resetSpe();
	}
}
