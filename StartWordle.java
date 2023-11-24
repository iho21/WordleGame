package wordle_gui;

import java.util.*;

public class StartWordle {
	public static void main(String args[]) {
		
		//create
		Scanner input = new Scanner(System.in);
		//create title
		System.out.println("Wordle");
		
		
		//create string array
		String[] words = {"SHAKE", "SHARE", "PANIC", "AMUSE", "SHADE"};
		
		int randomNum = (int) Math.random();
		
		int wIndex = (int) (randomNum * words.length);
		
//		System.out.println(randomNum);
//		System.out.println(words.length);
//		System.out.println(wIndex);
		
		String correct = words[wIndex];
		
		WordleState state = new WordleState(correct);
		
		DisplayWordle display = new DisplayWordle(state, input);
		
		//create loop to keep track of game (if it's still going)
		
		while(!state.isGameOver()) {
			//check if the letters are in the right position
			//display layout
			display.print();
			//checks if the word is 5 letters
			display.promptGuess();
		}
		
		if(state.didWin()) {
			System.out.println("Congrats you won!");
		}
		else {
			System.out.println("Sorry try again, maybe next time!");
			System.out.println("This was the right word" + state.getWord());
		}
		input.close();
		
		
	}

}