package com.colab.obcards.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;

import com.colab.obcards.Deck;

public class FileManager {
	
	AssetManager assetManager;
	Context context;
	XmlParser xml;
	
	public FileManager(AssetManager assetManager, Context context)
	{
		this.assetManager = assetManager;
		this.context = context;
		xml = new XmlParser();
	}
	
	public void MovePremadeDecksToExternalStorage()
	{
		File sdcard = Environment.getExternalStorageDirectory();
		File saveLoc = new File(sdcard.getAbsolutePath(), "data/com.colab.obcards/decks/");
		
		if(!saveLoc.exists())
			saveLoc.mkdirs();
		
		try {
			String[] query = assetManager.list("");
			
			for(int i=0; i < query.length; i++)
			{
				InputStream in = assetManager.open("Edition_2.xml");
				FileOutputStream out = context.openFileOutput("Edition_2.xml", 0);
				
				int read = 0;
				byte[] bytes = new byte[1];
				
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
	
	public Deck LoadDeck(String name)
	{
		String filePath = "data/com.colab.obcards/decks/"+ StringToFile(name, ".xml");
		File sdcard = Environment.getExternalStorageDirectory();
		File path = new File(sdcard.getAbsolutePath(), filePath);
		
		
		FileInputStream in = null;
		Deck loadedDeck = null;
		
		try {
			in = context.openFileInput("Edition_2.xml");
			
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
			loadedDeck = new Deck("ERROR");
			
			String[] strings = context.fileList();
			String str = "";
			
			
			for(int i=0;i<strings.length;i++)
			{
				str+=strings[i];
			}
			
			loadedDeck.addCard(context.getFilesDir().toString() + ";" + str);
			return loadedDeck;
		}
	}
	
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
	
}
