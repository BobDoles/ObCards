package com.colab.obcards;
import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.colab.obcards.util.FileManager;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends Activity {
	public TextView t;
	public TextView intro;
	View myView;
	int x;

	/** Create Object For SiteList Class */
	SitesList sitesList = null;
	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		  // Look up the AdView as a resource and load a request.
	    AdView adView = (AdView)this.findViewById(R.id.adView);
	    AdRequest adRequest = new AdRequest.Builder().build();
	    adView.loadAd(adRequest);
	    
	    
	    final Animation in = new AlphaAnimation(0.0f, 1.0f);
	    in.setDuration(1500);

	    final Animation out = new AlphaAnimation(1.0f, 0.0f);
	    out.setDuration(1500);
	    
		final Animation anim = AnimationUtils.loadAnimation(this, R.anim.slide_in_top);
	    
		t = (TextView)findViewById(R.id.textView1);
		intro = (TextView)findViewById(R.id.textView2);
		intro.startAnimation(anim);
		t.setText("");

		
		FileManager fm = new FileManager(getAssets(), this);
		//fm.MovePremadeDecksToExternalStorage();
		final Deck deck = fm.LoadDeck("Edition 1");
		
		
		myView = (View)findViewById(R.id.entireScreen);
		myView.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				intro.setText("");
				
				
				// TODO Auto-generated method stub
				x++;
				//System.out.println(deck.getRandomCard());
				t.startAnimation(anim);
				try{
					t.setText(deck.getRandomCard());
				}
				catch(IndexOutOfBoundsException e){
					t.setText("Card does not exist");
				}
				return false;
			}
		});
	    
	    out.setAnimationListener(new AnimationListener() {

	        @Override
	        public void onAnimationEnd(Animation animation) {
	            t.startAnimation(in);
	    		intro.setText("");
	        }

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
	    });
	    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
	   // t.setText("Step One: blast egg");
		return true;
	}
		
}
