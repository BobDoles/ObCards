package com.colab.obcards.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

import com.colab.obcards.Deck;

public class FileManager {
	
	public static Deck LoadDeck(String name)
	{
		
		String path = "/assets/deck/" + StringToFile(name);
		File file = new File(path);
		FileInputStream in = null;
		Deck loadedDeck = null;
		
		try {
			in = new FileInputStream(file);
			loadedDeck = XmlParser.parse(in);
			
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
			loadedDeck.addCard("ERROR WITH LOADING DECK\nPlease report to developer");
			return loadedDeck;
		}
	}
	
	public static String StringToFile(String name)
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
