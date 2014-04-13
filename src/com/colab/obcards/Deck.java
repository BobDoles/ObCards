package com.colab.obcards;

import java.util.ArrayList;
import java.util.Random;


public class Deck {

	
	private String name;
	private boolean custom;
	private ArrayList<String> cards;
	
	/**
	 * Default constructor
	 */
	public Deck() {
		name = "";
		custom = false;
		cards = new ArrayList<String>();
	}
	
	/**
	 * Constructor for use when creating a custom deck.
	 * @param name	Name of the deck.
	 */
	public Deck(String name) {
		this.name = name;
		custom = true;
		cards = new ArrayList<String>();
	}
	
	public Deck(String name, boolean custom)
	{
		this.name = name;
		this.custom = custom;
		cards = new ArrayList<String>();
	}
	
	public Deck(String name, boolean custom, ArrayList<String>cards)
	{
		this.name = name;
		this.custom = custom;
		this.cards = cards;
	}
	
	/**
	 * Sets custom variable
	 * @param flag	True or false
	 */
	public void setCustom(boolean flag)
	{
		custom = flag;
	}
	
	/**
	 * Adds a card to a custom deck
	 * @param message	Message for new card.
	 */
	public void addCard(String message)
	{
		cards.add(message);
	}
	
	/**
	 * Removes a card from the deck.
	 * @param id	ID of card
	 */
	public void removeCard(int id)
	{
		cards.remove(id);
	}
	
	/**
	 * Gets the name of the deck.
	 * @return	Name of deck.
	 */
	public String getName()
	{
		return name;
	}
	
	/** 
	 * Gets a random card from the deck use a SecureRandom object.
	 * @return	random card from deck.
	 */
	public String getRandomCard() 
	{
		Random rand = new Random();
		int randCard = rand.nextInt(cards.size());
			
		return cards.get(randCard);
	}
	
	/**
	 * Gets the entire deck of cards in an ArrayList.
	 * @return	ArrayList of cards
	 */
	public ArrayList<String> getDeck()
	{
		return cards;
	}
	
	/**
	 * Gets the size of the deck.
	 * @return	Size of the deck.
	 */
	public int deckSize()
	{
		return cards.size();
	}
	
	/**
	 * Checks to see if the deck is a custom deck.
	 * @return	If deck is custom.
	 */
	public boolean isCustom()
	{
		return custom;
	}
}
