/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
 
public class Blackjack {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numberOfPlayers = getNumberOfPlayers(scanner);
        List<Player> players = getPlayers(numberOfPlayers, scanner);

        Deck deck = new Deck();
        List<Hand> playerHands = new ArrayList<>();
        for (Player player : players) {
            playerHands.add(new Hand());
        }
        Hand dealerHand = new Hand();

        initialDeal(playerHands, dealerHand, deck);

        System.out.println("Dealer's hand: " + dealerHand);
        for (int i = 0; i < players.size(); i++) {
            System.out.println(players.get(i).getName() + "'s hand: " + playerHands.get(i));
        }

        for (int i = 0; i < players.size(); i++) {
            playerTurn(players.get(i), playerHands.get(i), deck, scanner);
        }

        if (playerHands.stream().noneMatch(Hand::isBust)) {
            dealerTurn(dealerHand, deck);
        }

        determineWinners(players, playerHands, dealerHand);

        scanner.close();
    }

    private static int getNumberOfPlayers(Scanner scanner) {
        int numberOfPlayers;
        while (true) {
            System.out.print("Enter the number of players (1-5): ");
            numberOfPlayers = scanner.nextInt();
            scanner.nextLine(); // consume the newline
            if (numberOfPlayers >= 1 && numberOfPlayers <= 5) {
                break;
            } else {
                System.out.println("Invalid number of players. Please enter a number between 1 and 5.");
            }
        }
        return numberOfPlayers;
    }

    private static List<Player> getPlayers(int numberOfPlayers, Scanner scanner) {
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < numberOfPlayers; i++) {
            String playerName;
            while (true) {
                System.out.print("Enter name for player " + (i + 1) + ": ");
                playerName = scanner.nextLine();
                if (playerName.matches("[a-zA-Z]+")) {
                    players.add(new Player(playerName));
                    break;
                } else {
                    System.out.println("Invalid name. Name cannot contain numbers or special characters.");
                }
            }
        }
        return players;
    }

    private static void initialDeal(List<Hand> playerHands, Hand dealerHand, Deck deck) {
        for (Hand playerHand : playerHands) {
            playerHand.addCard(deck.draw());
            playerHand.addCard(deck.draw());
        }
        dealerHand.addCard(deck.draw());
        dealerHand.addCard(deck.draw());
    }

    private static void playerTurn(Player player, Hand playerHand, Deck deck, Scanner scanner) {
        while (true) {
            System.out.print(player.getName() + ", Hit or Stand? (h/s): ");
            String action = scanner.nextLine();

            if (action.equalsIgnoreCase("h")) {
                playerHand.addCard(deck.draw());
                System.out.println(player.getName() + "'s hand: " + playerHand);

                if (playerHand.isBust()) {
                    System.out.println(player.getName() + " busts! Dealer wins.");
                    break;
                }
            } else if (action.equalsIgnoreCase("s")) {
                break;
            } else {
                System.out.println("Invalid input. Please enter 'h' for hit or 's' for stand.");
            }
        }
    }

    private static void dealerTurn(Hand dealerHand, Deck deck) {
        while (dealerHand.getValue() < 17) {
            dealerHand.addCard(deck.draw());
            System.out.println("Dealer's hand: " + dealerHand);

            if (dealerHand.isBust()) {
                System.out.println("Dealer busts! Players win.");
                return;
            }
        }
    }

    private static void determineWinners(List<Player> players, List<Hand> playerHands, Hand dealerHand) {
        int dealerValue = dealerHand.getValue();

        System.out.println("Dealer's final hand: " + dealerHand);

        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            Hand playerHand = playerHands.get(i);
            int playerValue = playerHand.getValue();

            System.out.println(player.getName() + "'s final hand: " + playerHand);

            if (playerHand.isBust()) {
                System.out.println(player.getName() + " busts! Dealer wins.");
            } else if (dealerHand.isBust() || playerValue > dealerValue) {
                System.out.println(player.getName() + " wins!");
            } else if (dealerValue > playerValue) {
                System.out.println("Dealer wins against " + player.getName() + "!");
            } else {
                System.out.println(player.getName() + " ties with the dealer.");
            }
        }
    }
}
