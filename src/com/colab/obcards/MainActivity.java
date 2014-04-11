package com.colab.obcards;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

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
	public void onSaveInstanceState(Bundle savedInstanceState) {

	  // Save UI state changes to the savedInstanceState.   
	  // This bundle will be passed to onCreate if the process is  
	  // killed and restarted.
	  savedInstanceState.putInt("y", x);  
	  // etc.  
	  super.onSaveInstanceState(savedInstanceState);  
	}  
	//onRestoreInstanceState  
	    @Override  
	public void onRestoreInstanceState(Bundle savedInstanceState) {  
	  super.onRestoreInstanceState(savedInstanceState);  
	  // Restore UI state from the savedInstanceState.  
	  // This bundle has also been passed to onCreate.  
	  int y = savedInstanceState.getInt("y");
	  x = y;
	  t.setText(sitesList.getName().get(x));
	  intro.setText("");
	}

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
		/** Create a new textview array to display the results */
		TextView name[];
		TextView website[];
		TextView category[];
		
		
		
		try {
			
			/** Handling XML */
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();

			/** Send URL to parse XML Tags */
			URL sourceUrl = new URL(
					"http://www.androidpeople.com/wp-content/uploads/2010/06/example.xml");
			InputSource is = new InputSource(getResources().openRawResource(R.raw.edition1));
			/** Create handler to handle XML Tags ( extends DefaultHandler ) */
			MyXMLHandler myXMLHandler = new MyXMLHandler();
			xr.setContentHandler(myXMLHandler);
			xr.parse(new InputSource(is.getByteStream()));
			
		} catch (Exception e) {
			System.out.println("XML Pasing Excpetion = " + e);
		}

		/** Get result from MyXMLHandler SitlesList Object */
		sitesList = MyXMLHandler.sitesList;

		/** Assign textview array lenght by arraylist size */
		name = new TextView[sitesList.getName().size()];
		website = new TextView[sitesList.getName().size()];
		category = new TextView[sitesList.getName().size()];

		/** Set the result text in textview and add it to layout */
		for (int i = 0; i < sitesList.getName().size(); i++) {
			name[i] = new TextView(this);
			name[i].setText("Name = "+sitesList.getName().get(i));

			//layout.addView(name[i]);
		}
		
/*
		Intent myIntent = new Intent(this, Disclaimer.class);
		startActivity(myIntent);
*/
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
				t.setText(sitesList.getName().get((int)(Math.random() * (((int)sitesList.getName().size()) + 1))));
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
	
	private String readTextFile(InputStream inputStream) {
	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

	    byte buf[] = new byte[1024];
	    int len;
	    try {
	        while ((len = inputStream.read(buf)) != -1) {
	            outputStream.write(buf, 0, len);
	        }
	        outputStream.close();
	        inputStream.close();
	    } catch (IOException e) {

	    }
	    return outputStream.toString();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
	   // t.setText("Step One: blast egg");
		return true;
	}
	
	
}
