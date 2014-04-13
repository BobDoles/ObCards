package com.colab.obcards.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.colab.obcards.Deck;


public class XmlParser {
	
	public enum Type {
		CONFIG, DECK
	}
	
	public XmlParser() {}
	
	public Object parse(InputStream in, Type type) throws XmlPullParserException, IOException
	{
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser parser = factory.newPullParser();
			
			parser.setInput(in, "UTF-8");
			parser.nextTag();
			
			//if(type == Type.DECK)
				return readDeck(parser);
			/*else if(type == Type.CONFIG)
				return readConfig(parser);*/
				
		}
		finally
		{
			in.close();
		}
	}
	
	

	
	private Deck readDeck(XmlPullParser parser) throws XmlPullParserException, IOException
	{
		parser.require(XmlPullParser.START_TAG, null, "Deck");
		String name = null;
		boolean custom = false;
		ArrayList<String> cards = null;
		
		while(parser.next() != XmlPullParser.END_TAG)
		{
			if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
			
			String tagName = parser.getName();
			
			if(tagName.equals("name"))
			{
				name = readName(parser);
			}
			else if (tagName.equals("custom"))
			{
				custom = Boolean.parseBoolean(readText(parser));
			}
			else if(tagName.equals("cards"))
			{
				cards = readCards(parser);
			}
		}
		
		return new Deck(name, custom, cards);
	}
	
	private String readName(XmlPullParser parser) throws IOException, XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, null, "name");
		String name = readText(parser);
		parser.require(XmlPullParser.END_TAG, null, "name");
		return name;
	}

	private String readText(XmlPullParser parser) throws XmlPullParserException, IOException {
		String result ="";
		
		if (parser.next() == XmlPullParser.TEXT) {
	        result = parser.getText();
	        parser.nextTag();
	    }

		
		return result;
	}

	private ArrayList<String> readCards(XmlPullParser parser) throws XmlPullParserException, IOException
	{
		ArrayList<String> cards = new ArrayList<String>();
		
		parser.require(XmlPullParser.START_TAG, null, "cards");
		
		while(parser.next() != XmlPullParser.END_TAG)
		{
			if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
			
			String tagName = parser.getName();
			
			if(tagName.equals("string"))
			{
				cards.add(readText(parser));
			}
		}

		return cards;
	}
}
