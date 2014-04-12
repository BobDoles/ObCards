package com.colab.obcards.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParserException;

import android.content.res.AssetManager;

import com.colab.obcards.Deck;

public class FileManager {
	
	AssetManager assetManager;
	XmlParser xml;
	
	public FileManager(AssetManager assetManager)
	{
		this.assetManager = assetManager;
		xml = new XmlParser();
	}
	
	public Deck LoadDeck(String name)
	{
		
		String path = "/deck/Edition_1.xml";
		File file = new File(path);
		InputStream in = null;
		Deck loadedDeck = null;
		
		try {
			in = assetManager.open("Edition_1.xml");
			loadedDeck = xml.parse(in);
			
		}
		catch(IOException e) {
			System.err.println(e.getMessage());
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		finally {
			if(in != null)
			{
				try {
					in.close();
				}
				catch(Exception e)
				{
					System.err.println(e.getMessage());
				}
			}
		}
		
		if(loadedDeck != null)
			return loadedDeck;
		else
		{
			loadedDeck = new Deck("Error",false);
			loadedDeck.addCard("ERROR WITH LOADING DECK");
			return loadedDeck;
		}
	}
	
	public String StringToFile(String name)
	{
		String fileName = "";
		char[] letters = name.toCharArray();
		
		for(int i=0; i < letters.length; i++)
		{
			if(letters[i] == ' ')
				fileName += '_';
			else
				fileName += letters[i];
		}
		
		return fileName;
	}
	
}
