package poker;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Tim Barber
 */

/*
 * Suits: ♥,♦,♣,♠
 */
public class PokerTester {

    private static Deck testDeck = new Deck("Standard"); // Init main deck
    private static Player[] players;
    private static Player player1 = new Player(); // Make player 1
    private static Player player2 = new Player(); // Make player 2
    private static Player player3 = new Player(); // Make player 3
    private static Scanner keyboard = new Scanner(System.in); // create input obj
    private static Random random = new Random();

    public static void main(String[] args) throws Exception {
        player1.setName("Bob"); // Name player 1
        player2.setName("Joe"); // Name player 2
        player3.setName("Jim"); // Name player 3

        players = new Player[3]; // Init player list

        players[0] = player1;   //give it values
        players[1] = player2;
        players[2] = player3;

        shuffleDeck(testDeck); //shuffle deck
        System.out.println("Shuffled.");

        players = chooseButtons(players); // set button positions
        System.out.println("Dealer: " + players[0].getName());
        System.out.println("Little: " + players[players.length - 1].getName());
        System.out.println("Big: " + players[(players.length - 2) % players.length].getName());

        offerCut(players[0]); //offer cut
        System.out.println(testDeck);

        dealHands(testDeck, players); // deal in
        System.out.println("Players dealt in.");

        System.out.println("\n" + player1); // toString() of players
        System.out.println(player2);
        System.out.println(player3);

        System.out.println("\n" + testDeck); // toString() of main deck
        System.out.println(testDeck.getSize()); // num cards in main deck
        System.out.println(testDeck.testDuplicates()); // Make sure there are no dupes
    }

    public static void offerCut(Player playerToCut) {
        int cutAmt = 0;
        int tries = 0;
        do {
            if (tries < 3) {
                System.out.print(playerToCut.getName() + ", how many cards to cut?: ");
            } else {
                System.out.print("I swear " + playerToCut.getName() + ", quit messing around: ");
            }
            cutAmt = keyboard.nextInt();
            tries++;
        } while (cutAmt < 0 || cutAmt > 52);
        testDeck.cut(cutAmt);
    }

    public static void shuffleDeck(Deck deckToShuffle) {
        int shuffles = random.nextInt(3) + 1;
        int cuts = random.nextInt(5) + 1;
        int overhands = random.nextInt(6) + 1;

        while (shuffles > 0) {
            deckToShuffle.shuffle();
            shuffles--;
        }
        while (cuts > 0) {
            deckToShuffle.cut(random.nextInt(51) + 1);
            cuts--;
        }
        while (overhands > 0) {
            deckToShuffle.overhandShuffle();
            overhands--;
        }
    }

    public static void dealHands(Deck deck, Player[] players) {

        //Make a hand list as large as the number of players
        Hand[] hands = deck.deal(players.length);

        // Give each player their hand
        int index = 0;
        for (Player player : players) {
            hands[index].setPlayerName(player.getName());
            player.setCards(hands[index]);
            index++;
        }

    }

    public static Player[] chooseButtons(Player[] players) {
        int index = random.nextInt(players.length);
        players[index].makeDealer(true);
        Player[] temp = new Player[players.length];
        // Not only does this set the buttons, but it 'rotates' the
        // player list so that the dealer is at the beginning
        int i = 0;
        for (Player player : players) {
            int newIndex = i - index;
            if (newIndex < 0) {
                newIndex += temp.length;
            }
            temp[newIndex] = player;
            i++;
        }
        temp[(temp.length - 1) % temp.length].makeLittle(true);
        temp[(temp.length - 2) % temp.length].makeBig(true);
        return temp;
    }

}
/*
 * The MIT License
 *
 * Copyright 2018 Tim Barber.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */