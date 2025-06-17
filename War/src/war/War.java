package war;

import java.util.*;

public class War {

	public static void main(String[] args) {
		
		playGame();
		
	}
	public static ArrayList<Card> makeDeck() { //create a deck of cards for use
		ArrayList<Card> deck = new ArrayList<Card>();
		for(int i = 1; i<=52; i++) {	
			deck.add(new Card(i));
		}
		return deck;
	}
	public static void playGame() {
		//scanner used to manage game speed
		Scanner scan = new Scanner(System.in);
		
		//create a deck of cards in an ArrayList
		ArrayList<Card> dealingDeck = makeDeck();
		
		//shuffle the deck to randomize order of cards
		Collections.shuffle(dealingDeck);
		
		//create two players, each with one half of the deck as their starting hand
		Player p1 = new Player(new ArrayList<Card>(dealingDeck.subList(0, dealingDeck.size()/2)));
		Player p2 = new Player(new ArrayList<Card>(dealingDeck.subList(dealingDeck.size()/2, dealingDeck.size())));
		
		//ask for input to begin
		System.out.println("Press enter to start game");
		scan.nextLine();
		
		//loop rounds until one player no longer has a hand
		while(!p1.getHand().isEmpty() && !p2.getHand().isEmpty()) {
			
			//create record of cards which are used in the round, to be awarded to the winning player
			ArrayList<Card> roundCards = new ArrayList<Card>();
			
			//both players draw a card
			Card p1Card = p1.drawCard();
			Card p2Card = p2.drawCard();
			
			//both cards are recorded for rewards
			roundCards.add(p1Card);
			roundCards.add(p2Card);
			
			//info printout (cards drawn)
			System.out.println(p1Card+" | "+p2Card);
			
			//check for winner or war
			if(p1Card.isGreaterThan(p2Card)) { //case for p1 win
				//info printout
				System.out.println("P1 wins this round!");
				
				//award P1 their won cards
				p1.addToBottomOfDeck(roundCards);
				
			} else if(p2Card.isGreaterThan(p1Card)) { //case for p2 win
				//info printout
				System.out.println("P2 wins this round!");
				
				//award P2 their won cards
				p2.addToBottomOfDeck(roundCards);
				
			} else { //case for a war
				
				//start a war loop until there is a definite war winner (handling double, triple, and so on # of wars)
				while(p1Card.isEqualTo(p2Card)) {
					//info printout
					System.out.print("WAR!");
					
					//wait for input
					scan.nextLine();
					
					//remove old sacrifice cards from repeat wars (avoiding duplication)
					roundCards.removeAll(p1.getSacrifices());
					roundCards.removeAll(p2.getSacrifices());
					
					//both methods MUST be executed, so their values are stored then called
					boolean p1AllSacris = p1.createSacrifices(p1Card);
					boolean p2AllSacris = p2.createSacrifices(p2Card);
					
					//if not stored, java might skip one because the second condition being checked may be unnessecary
					if(p1AllSacris&&p2AllSacris) {
						//info printout
						System.out.println("Laying all sacrifices");
						
					} else {
						//info printout only for who is out of cards
						if(p1.getSacrifices().size()<3) System.out.println("P1 is out of cards to fully sacrifice!");
						else System.out.println("P2 is out of cards to fully sacrifice!");
						
						//the # of cards off from normal # of sacks (3)
						int difference = Math.abs(p2.getSacrifices().size()-p1.getSacrifices().size());
						
						//print out the real # of sacked cards
						if(difference>3) {
							System.out.println("No extra cards to sacrifice!");
							System.out.println("Playing with sacrifice cards!");
						} else {
							System.out.println("Sacrificed "+(3-difference)+" instead!");
						}
						
						for(int i=0; i<difference; i++) {
							if(p1AllSacris) {
								p1.addToTopOfDeck(p1.getSacrifices().remove(0));
							} else {
								p2.addToTopOfDeck(p2.getSacrifices().remove(0));
							}
						}
					}
					
					//add sacrifices to played cards
					roundCards.addAll(p1.getSacrifices());
					roundCards.addAll(p2.getSacrifices());
					
					//draw a card for p1 and 2
					p1Card = p1.drawCard();
					p2Card = p2.drawCard();
					
					roundCards.add(p1Card);
					roundCards.add(p2Card);
					
					//wait for input
					scan.nextLine();
					
					//display chosen cards after input
					System.out.println(p1Card+" | "+p2Card);
					
				} //end war loop
				
				//check for winning players card
				if(p1Card.isGreaterThan(p2Card)) {
					//info printout
					System.out.println("P1 wins the war!:");
					
					//wait for input
					scan.nextLine();
					
					//print which sacrifices the player already had which they saved and the ones they didn't and won
					System.out.println("Won:");
					p2.printSacrifices();
					System.out.println("Saved:");
					p1.printSacrifices();
					
					//add the won cards to winning players hand
					p1.addToBottomOfDeck(roundCards);
				} else {
					//same as above but for P2 (not P1)
					System.out.println("P2 wins this war!");
					scan.nextLine();
					System.out.println("Won:");
					p1.printSacrifices();
					System.out.println("Saved:");
					p2.printSacrifices();
					p2.addToBottomOfDeck(roundCards);
				}
				
				//print line (for clarity)
				System.out.println();
				
				//clear sacrifice records (avoiding duplication)
				p1.getSacrifices().clear();
				p2.getSacrifices().clear();
				
			} //end round card checking conditional
			
			//roud is over, print player deck sizes
			System.out.println("Decks:");
			System.out.print(p1.getHand().size()+" cards");
			System.out.println("  |  "+p2.getHand().size()+" cards");			
			
			if(p1.getHand().size()+p2.getHand().size()!=52) {
				System.out.println(p1.getHand());
				System.out.println(p2.getHand());
				System.out.println("Round Cards:\n"+roundCards);
				break;
			}
			
			//wait for input on next round
			scan.nextLine();
			
		} //end game loop
		
		//game loop is over, check for definite winner (note that if both players hands are empty, p1 wins)
		if(p2.getHand().isEmpty()) {
			System.out.println("========P1 WINS THE GAME========");
		} else if(p1.getHand().isEmpty()) {
			System.out.println("========P2 WINS THE GAME========");
		} else {
			//print error message for when there is no winner (for whatever reason)
			System.out.println("========GAME ERROR========");
		}
		
		//close scanner and end method
		scan.close();
	}

}
