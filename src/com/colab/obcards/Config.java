package com.colab.obcards;

import java.util.ArrayList;


public class Config {
	
	public static ArrayList<String> activeDecks;
	
	/**
	 * Default constructor
	 */
	public Config()
	{
		activeDecks = new ArrayList<String>();
	}
	
	/**
	 * Returns the ArrayList containing all the active decks' names.
	 * @return	Active decks
	 * 
	 */
	public ArrayList<String> getActiveDecks()
	{
		return activeDecks;
	}
	
	/**
	 * Adds a deck to the active decks.
	 * @param name	Name of the deck to be added.
	 */
	public void addDeck(String name)
	{
		activeDecks.add(name);
	}

	
	/**
	 * Cycles through active decks and finds specified deck and removes it.
	 * @param name	Name of deck to be removed.
	 */
	public void removeDeck(String name)
	{
		for(int i = 0; i < activeDecks.size(); i++)
		{
			if(activeDecks.get(i).equalsIgnoreCase(name))
			{
				activeDecks.remove(i);
				i--;
			}
		}
	}

}
