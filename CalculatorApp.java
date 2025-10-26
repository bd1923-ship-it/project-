import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * CalculatorApp is a graphical user interface (GUI) calculator built using Java Swing.
 * It supports basic arithmetic operations: addition, subtraction, multiplication, and division.
 * All components and logic are contained within this single class.
 */
public class CalculatorApp implements ActionListener {

    // --- GUI Components ---
    private JFrame frame;
    private JTextField displayField;
    private JPanel buttonPanel;

    // Buttons for numbers and operators
    private JButton[] numberButtons = new JButton[10];
    private JButton[] functionButtons = new JButton[9];
    private JButton addButton, subButton, mulButton, divButton;
    private JButton decButton, equButton, delButton, clrButton, negButton;

    // --- Calculation State Variables ---
    private double num1 = 0, num2 = 0, result = 0;
    private char operator;

    /**
     * Constructor sets up the calculator frame and components.
     */
    public CalculatorApp() {
        // 1. Initialize Frame
        frame = new JFrame("Java Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420, 550);
        frame.setLayout(null); // Use absolute layout for the main frame

        // 2. Initialize Display Field (TextField)
        displayField = new JTextField();
        displayField.setBounds(50, 25, 300, 50);
        displayField.setFont(new Font("Inter", Font.BOLD, 25));
        displayField.setEditable(false); // User cannot type directly into the display field
        displayField.setHorizontalAlignment(JTextField.RIGHT);
        displayField.setBorder(BorderFactory.createEtchedBorder());
        frame.add(displayField);

        // 3. Initialize Function Buttons
        addButton = new JButton("+");
        subButton = new JButton("-");
        mulButton = new JButton("*");
        divButton = new JButton("/");
        decButton = new JButton(".");
        equButton = new JButton("=");
        delButton = new JButton("DEL");
        clrButton = new JButton("CLR");
        negButton = new JButton("(-)"); // For changing sign

        functionButtons[0] = addButton;
        functionButtons[1] = subButton;
        functionButtons[2] = mulButton;
        functionButtons[3] = divButton;
        functionButtons[4] = decButton;
        functionButtons[5] = equButton;
        functionButtons[6] = delButton;
        functionButtons[7] = clrButton;
        functionButtons[8] = negButton;

        // Apply style and listeners to function buttons
        for (JButton button : functionButtons) {
            button.addActionListener(this);
            button.setFont(new Font("Inter", Font.BOLD, 20));
            button.setFocusable(false);
            button.setBackground(new Color(255, 160, 0)); // Orange color for operators
            button.setForeground(Color.WHITE);
            button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
        }

        // 4. Initialize Number Buttons
        for (int i = 0; i < 10; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].addActionListener(this);
            numberButtons[i].setFont(new Font("Inter", Font.BOLD, 20));
            numberButtons[i].setFocusable(false);
            numberButtons[i].setBackground(new Color(64, 64, 64)); // Dark gray for numbers
            numberButtons[i].setForeground(Color.WHITE);
            numberButtons[i].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
        }

        // 5. Initialize Button Panel
        buttonPanel = new JPanel();
        buttonPanel.setBounds(50, 100, 300, 350);
        buttonPanel.setLayout(new GridLayout(5, 4, 10, 10)); // 5 rows, 4 columns, 10px spacing

        // --- Layout Buttons in the Panel (Standard Calculator Layout) ---
        // Row 1: CLR, DEL, NEG, DIV
        buttonPanel.add(clrButton);
        buttonPanel.add(delButton);
        buttonPanel.add(negButton);
        buttonPanel.add(divButton);

        // Row 2: 7, 8, 9, MUL
        buttonPanel.add(numberButtons[7]);
        buttonPanel.add(numberButtons[8]);
        buttonPanel.add(numberButtons[9]);
        buttonPanel.add(mulButton);

        // Row 3: 4, 5, 6, SUB
        buttonPanel.add(numberButtons[4]);
        buttonPanel.add(numberButtons[5]);
        buttonPanel.add(numberButtons[6]);
        buttonPanel.add(subButton);

        // Row 4: 1, 2, 3, ADD
        buttonPanel.add(numberButtons[1]);
        buttonPanel.add(numberButtons[2]);
        buttonPanel.add(numberButtons[3]);
        buttonPanel.add(addButton);

        // Row 5: 0, Decimal, Equals
        buttonPanel.add(numberButtons[0]);
        buttonPanel.add(decButton);
        buttonPanel.add(equButton);

        frame.add(buttonPanel);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    /**
     * Handles all button click events.
     * @param e The ActionEvent object generated by the button click.
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        // --- 1. Number and Decimal Input ---
        for (int i = 0; i < 10; i++) {
            if (e.getSource() == numberButtons[i]) {
                displayField.setText(displayField.getText().concat(String.valueOf(i)));
                return;
            }
        }

        if (e.getSource() == decButton) {
            // Only add decimal if one doesn't already exist in the current number
            if (!displayField.getText().contains(".")) {
                displayField.setText(displayField.getText().concat("."));
            }
            return;
        }

        // --- 2. Operator Selection ---
        if (e.getSource() == addButton || e.getSource() == subButton ||
            e.getSource() == mulButton || e.getSource() == divButton) {

            // Try to parse the current display value as the first number (num1)
            try {
                num1 = Double.parseDouble(displayField.getText());
            } catch (NumberFormatException ex) {
                // If display is empty or invalid, treat it as 0
                num1 = 0;
            }

            // Set the operator based on the button clicked
            if (e.getSource() == addButton) operator = '+';
            else if (e.getSource() == subButton) operator = '-';
            else if (e.getSource() == mulButton) operator = '*';
            else if (e.getSource() == divButton) operator = '/';

            // Clear the display for the next number (num2)
            displayField.setText("");
            return;
        }

        // --- 3. Equals Button ---
        if (e.getSource() == equButton) {
            // Get the second number (num2) from the display field
            try {
                // If display is empty, num2 is 0. Otherwise, parse the number.
                String currentText = displayField.getText();
                num2 = currentText.isEmpty() ? 0 : Double.parseDouble(currentText);

                switch (operator) {
                    case '+':
                        result = num1 + num2;
                        break;
                    case '-':
                        result = num1 - num2;
                        break;
                    case '*':
                        result = num1 * num2;
                        break;
                    case '/':
                        if (num2 == 0) {
                            displayField.setText("Error: Div by Zero");
                            num1 = 0; // Reset state
                            return;
                        }
                        result = num1 / num2;
                        break;
                    default:
                        // If no operator was set (e.g., user pressed = immediately)
                        result = num2;
                        break;
                }

                // Display the result
                // We use String.valueOf(result) to show the full double value
                // Then, we check if the result is an integer (e.g., 5.0) and display it cleanly (e.g., 5)
                if (result == (long) result) {
                    displayField.setText(String.format("%d", (long) result));
                } else {
                    displayField.setText(String.valueOf(result));
                }

                // Prepare for the next calculation: num1 becomes the result
                num1 = result;
                operator = ' '; // Reset operator
            } catch (NumberFormatException ex) {
                displayField.setText("Error: Invalid Input");
            }
            return;
        }

        // --- 4. Clear Button ---
        if (e.getSource() == clrButton) {
            displayField.setText("");
            num1 = 0;
            num2 = 0;
            result = 0;
            operator = ' ';
            return;
        }

        // --- 5. Delete Button (Backspace) ---
        if (e.getSource() == delButton) {
            String string = displayField.getText();
            // If the string is not empty, remove the last character
            if (string.length() > 0) {
                displayField.setText(string.substring(0, string.length() - 1));
            }
            return;
        }

        // --- 6. Negative Button (Toggle Sign) ---
        if (e.getSource() == negButton) {
            String currentText = displayField.getText();
            try {
                double temp = Double.parseDouble(currentText);
                temp *= -1;
                // Preserve clean integer display if possible
                if (temp == (long) temp) {
                    displayField.setText(String.format("%d", (long) temp));
                } else {
                    displayField.setText(String.valueOf(temp));
                }
            } catch (NumberFormatException ex) {
                // Handle case where display is empty or error message is present
                // Do nothing or handle error gracefully
                System.out.println("Cannot negate current display content: " + currentText);
            }
        }
    }

    /**
     * Main method to launch the calculator application.
     */
    public static void main(String[] args) {
        // Use SwingUtilities.invokeLater to ensure thread safety for GUI creation
        new CalculatorApp();
    }
}
