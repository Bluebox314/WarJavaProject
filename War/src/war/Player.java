package war;

import java.util.*;

public class Player {
	
	/**ArrayList of all the cards the player owns
	 * 
	 */
	private ArrayList<Card> hand;
	/**ArrayList of sacrificed cards when a tie occurs
	 * 
	 */
	private ArrayList<Card> sacrifices;
	
	
	/**Constructs a player object with parameter hand stored in the hand variable, and an empty sacrifices ArrayList
	 * 
	 * @param hand The ArrayList hand to be stored in the hand variable
	 */
	public Player(ArrayList<Card> hand) {
		this.hand = hand;
		sacrifices = new ArrayList<Card>();
	}
	
	
	/**Returns the Player object's hand
	 * 
	 * @return The hand variable for the player
	 */
	public ArrayList<Card> getHand() {
		return hand;
	}
	/**Sets the Player object's hand
	 * 
	 * @param hand The hand to be stored in the Player's hand variable
	 */
	public void setHand(ArrayList<Card> hand) {
		this.hand = hand;
	}
	/**Retunrs the Player object's sacrifices
	 * 
	 * @return The sacrifices variable for the player
	 */
	public ArrayList<Card> getSacrifices() {
		return sacrifices;
	}
	/**Sets the Player object's sacrifices
	 * 
	 * @param sacrifices The sacrifices to be stored in the Player's sacrifices variable
	 */
	public void setSacrifices(ArrayList<Card> sacrifices) {
		this.sacrifices = sacrifices;
	}
	/**Returns a string representation of this Object
	 * 
	 */
	public String toString() {
		return "hand=\n"+hand+"\n"
			  +"sacrifices=\n"+sacrifices;
	}
	
	
	
	/**"Draws" the first card in the players hand by removing the first card and returning it, to be stored in it's own
	 * variable.
	 * 
	 * @return The first card in the hand
	 */
	public Card drawCard() {
		return hand.remove(0);
	}
	
	/**"Draws" the first card in the players sacrifices by removing the first card and returning it, to be stored in it's own
	 * variable.
	 * 
	 * @return The first card in the hand
	 */
	public Card drawFromSacrifices() {
		return sacrifices.remove(0);
	}
	
	/**Adds a single parameter card to the (effective) bottom of the players hand
	 * 
	 * @param card the card to be added
	 */
	public void addToBottomOfDeck(Card card) {
		hand.add(card);
	}
	
	/**Adds a single parameter card to the (effective) top of the players hand
	 * 
	 * @param card the card to be added
	 */
	public void addToTopOfDeck(Card card) {
		hand.add(0, card);
	}
	
	/**Adds all cards in the ArrayList cards o the (effective) bottom of the players hand
	 * 
	 * @param cards The ArrayList to be added
	 */
	public void addToBottomOfDeck(ArrayList<Card> cards) {
		hand.addAll(cards);
	}
	
	/**Adds all cards in the ArrayList cards o the (effective) top of the players hand
	 * 
	 * @param cards The ArrayList to be added
	 */
	public void addToTopOfDeck(ArrayList<Card> cards) {
		hand.addAll(0, cards);
	}
	
	/**Draws 3 cards from the players hand to be placed in the sacrifices list. If the player doesn't have enough cards to add
	 * to sacrifices with at least one to spare, the maximum number of cards is added instead, leaving one card in hand. If
	 * the method is called with an empty hand, attempt to draw from old sacrifices, but if there are none (ie. a draw on 
	 * literal last card in hand) put the emergencyCard back in the deck.
	 * 
	 * @param emergencyCard The current card that is tied, catching the above decribed edge case
	 * @return Returns true if 3 cards could be drawn with at least one in the hand to spare
	 */
	public boolean createSacrifices(Card emergencyCard) {
		for(int i=0; i<3; i++) {
			if(hand.size()>1) {
				sacrifices.add(0, drawCard());
			} else {
				if(hand.isEmpty()) {
					if(sacrifices.isEmpty()) {
						addToTopOfDeck(emergencyCard);
					} else {
						addToTopOfDeck(drawFromSacrifices());
					}
				}
				return false;
			}
		}
		return true;
	}
	
	/**Prints off all cards in the sacrifices ArrayList, with an individual line for each
	 * 
	 */
	public void printSacrifices() {
		for(Card c: sacrifices) {
			System.out.println(c);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
