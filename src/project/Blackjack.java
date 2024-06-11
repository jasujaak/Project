/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project;

/**
 *
 * @author akshi
 */

import java.util.Scanner;

public class Blackjack {
    public static void main(String[] args) {
        Deck deck = new Deck();
        Hand playerHand = new Hand();
        Hand dealerHand = new Hand();
        Scanner scanner = new Scanner(System.in);

 
        initialDeal(playerHand, dealerHand, deck);

        System.out.println("Dealer's hand: " + dealerHand);
        System.out.println("Player's hand: " + playerHand);

     
        playerTurn(playerHand, deck, scanner);

       
        if (!playerHand.isBust()) {
            dealerTurn(dealerHand, deck);
        }

        
        determineWinner(playerHand, dealerHand);

        scanner.close();
    }

    private static void initialDeal(Hand playerHand, Hand dealerHand, Deck deck) {
        playerHand.addCard(deck.draw());
        playerHand.addCard(deck.draw());
        dealerHand.addCard(deck.draw());
        dealerHand.addCard(deck.draw());
    }

    private static void playerTurn(Hand playerHand, Deck deck, Scanner scanner) {
        while (true) {
            System.out.print("Hit or Stand? (h/s): ");
            String action = scanner.nextLine();

            if (action.equalsIgnoreCase("h")) {
                playerHand.addCard(deck.draw());
                System.out.println("Player's hand: " + playerHand);

                if (playerHand.isBust()) {
                    System.out.println("Player busts! Dealer wins.");
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
                System.out.println("Dealer busts! Player wins.");
                return;
            }
        }
    }

    private static void determineWinner(Hand playerHand, Hand dealerHand) {
        int playerValue = playerHand.getValue();
        int dealerValue = dealerHand.getValue();

        System.out.println("Player's final hand: " + playerHand);
        System.out.println("Dealer's final hand: " + dealerHand);

        if (playerValue > dealerValue && !playerHand.isBust()) {
            System.out.println("Player wins!");
        } else if (dealerValue > playerValue && !dealerHand.isBust()) {
            System.out.println("Dealer wins!");
        } else if (playerValue == dealerValue) {
            System.out.println("It's a tie!");
        }
    }
}
