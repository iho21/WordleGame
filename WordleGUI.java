package wordle_gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class WordleGUI extends JFrame {
	private JTextField[][] textRows;
    private int currRow;
    private State state;
    private JPanel panel;

    public WordleGUI() {
        setTitle("Tiles GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 700);

        // Create a panel to hold the components
        JPanel mainPanel = new JPanel(new BorderLayout());
        panel = new JPanel();
        panel.setLayout(new GridLayout(6, 5));
        mainPanel.add(panel, BorderLayout.CENTER);
        // Create the input fields
        textRows = new JTextField[6][5];
        createInputField();
        
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				submitButtonClicked();
				
			}
        });
        
        mainPanel.add(submitButton, BorderLayout.SOUTH);
        
        add(mainPanel);
        currRow = 0;
        state = new State();
    }
    
    private void submitButtonClicked() {
    	int total = 0;
    	for(int i = 0; i <=4; i++) {
    		int correct = state.checkCharacter(textRows[currRow][i].getText(), i);
    		if(correct == 1) {
    			textRows[currRow][i].setBackground(Color.GREEN);
    			total++;
    		}
    		else if(correct == 2) {
    			textRows[currRow][i].setBackground(Color.YELLOW);
    		}
    		textRows[currRow][i].setEditable(false);
    		
    	}
    	if(currRow == 5 && total != 5) {
    		JOptionPane.showMessageDialog(this, "Better Luck Next Time", "MESSAGE", JOptionPane.PLAIN_MESSAGE);
    		state.gameOver(false);
    		resetGame();
    		return;
    	}
    	else if(total == 5) {
    		JOptionPane.showMessageDialog(this, "Great Job", "MESSAGE", JOptionPane.PLAIN_MESSAGE);
    		state.gameOver(false);
    		resetGame();
    		return;
    	}
    	textRows[currRow+1][0].requestFocus();
    	currRow += 1;
    	
    }
    
    private void createInputField() {
    	for(int i = 0; i <=5; i++) {
    		for(int j = 0; j<= 4; j++) {
    			JTextField inputField = new JTextField();
    			inputField.setHorizontalAlignment(JTextField.CENTER);
    			inputField.setFont(new Font("Ariel", Font.BOLD, 35));
    			inputField.addKeyListener(new TileKeyListener(i, j));
    			panel.add(inputField);
    			textRows[i][j] = inputField;
    		}
    	}
    }
    
    public void resetGame() {
    	resetInputFields();
    	currRow = 0;
    	state = new State();
    }
    
    public void resetInputFields() {
    	for(int i = 0; i <= 5; i++) {
    		for(int j = 0; j <= 4; j++) {
    			textRows[i][j].setEditable(true);
    			textRows[i][j].setText("");
    			textRows[i][j].setBackground(Color.white);
    		}
    	}
    }
    
    private class TileKeyListener implements KeyListener {
    	private int tileRow;
    	private int tileColumn;
    	
    	public TileKeyListener(int tileRow, int tileColumn) {
    		this.tileRow = tileRow;
    		this.tileColumn = tileColumn;
    	}
    	
    	@Override
    	public void keyReleased(KeyEvent e) {
    		int pos = textRows[tileRow][tileColumn].getCaretPosition();
    		textRows[tileRow][tileColumn].setText(textRows[tileRow][tileColumn].getText().toUpperCase());
    		textRows[tileRow][tileColumn].setCaretPosition(pos);
    		char c = e.getKeyChar();
    		
    		// validate keys: only allow letters for our guesses
    		if (Character.isLetter(c)) {
    			// Move focus to the next tile
    			if (tileColumn < 4) {
    				textRows[tileRow][tileColumn + 1].requestFocus();
    			}
    		}
    	}
    	
    	@Override
    	public void keyTyped(KeyEvent e) {
    		// empty placeholder
    	}
    	
    	@Override
    	public void keyPressed(KeyEvent e) {
    		// empty placeholder
    	}
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WordleGUI tilesGUI = new WordleGUI();
            tilesGUI.setVisible(true);
        });
    }
}