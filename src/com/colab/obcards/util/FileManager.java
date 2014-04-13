package com.colab.obcards.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;

import com.colab.obcards.AppConfig;
import com.colab.obcards.Deck;
import com.colab.obcards.util.XmlParser.Type;

public class FileManager {
	
	AssetManager assetManager;
	ContextWrapper context;
	XmlParser xml;
	File dir;
	
	/**
	 * Constructor
	 * @param assetManager	AssetManager that will be used.
	 * @param context	Context that will be used to create a ContextWrapper.
	 */
	public FileManager(AssetManager assetManager, Context context)
	{
		this.assetManager = assetManager;
		this.context = new ContextWrapper(context);
		xml = new XmlParser();
		dir = context.getFilesDir();
		dir.mkdirs();
	}

	/**
	 * Moves the premade decks from the assets folder into a folder
	 * on the internal storage of the mobile device. This will allow
	 * us to use the same loading method for both premade and custom
	 * decks.
	 */
	public void MovePremadeDecksToInternalStorage()
	{
		try {
			
			String[] query = assetManager.list("deck");
			File dir2 = new File(dir, "deck");
			for(int i=0; i < query.length; i++)
			{
				File dir3 = new File(dir2, query[i]);
				
				InputStream in = assetManager.open("deck/" + query[i]);
				OutputStream out = new BufferedOutputStream(new FileOutputStream(dir3));
				
				int read = 0;
				byte[] bytes = new byte[1024];
				
				while ((read = in.read(bytes)) != -1) {
					out.write(bytes, 0, read);
				}
				
				in.close();
				out.flush();
				out.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Loads a deck based on a specified string.
	 * @param name	Name of deck to be loaded
	 * @return	Loaded deck.
	 */
	public Deck LoadDeck(String name)
	{
		InputStream in = null;
		Deck loadedDeck = null;
		File dir2 = new File(dir, "deck" );
		dir2.mkdirs();
		File file = new File(dir2, StringToFile(name, ".xml"));
		try {
			
			in = new BufferedInputStream(new FileInputStream(file));
			
			loadedDeck = (Deck)xml.parse(in, Type.DECK);
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
			loadedDeck = new Deck();
			loadedDeck.addCard("ERROR LOADING DECK");
			return loadedDeck;
		}
	}
	
	/**
	 * Converts a string value into an appropriate file name.
	 * @param name	String value to be converted
	 * @param ext	File extension to be used.
	 * @return	Usable file name.
	 */
	public String StringToFile(String name, String ext)
	{
		String fileName = "";
		char[] letters = name.toCharArray();
		
		for(int i=0; i < letters.length; i++)
		{
			if(letters[i] == ' ')
				fileName += '_';
			else if (letters[i] == '.')
				break;
			else
				fileName += letters[i];
		}
		
		fileName += ext;
		
		return fileName;
	}
	
	public AppConfig LoadConfig()
	{
		FileInputStream in = null;
		AppConfig loadedConfig = null;
		
		try {
			// UPDATE THIS LINE OF CODE!
			in = context.openFileInput("config.xml");
			//loadedConfig = (AppConfig)xml.parseConfig(in);
		}
		catch (IOException e)
		{
			System.err.println(e.getMessage());
		}
		
		return loadedConfig;
	}
}
