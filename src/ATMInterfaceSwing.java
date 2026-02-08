import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ATMInterfaceSwing {
    static double balance = 10000;
    static ArrayList<String> history = new ArrayList<>();
    static JFrame frame = new JFrame("ATM Interface");
    public static void main(String[] args) {

        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        showLoginPage();
        frame.setVisible(true);
    }
    static void showLoginPage() {

        frame.getContentPane().removeAll();
        frame.setLayout(new GridLayout(4, 1, 10, 10));

        JLabel title = new JLabel("ATM Login", JLabel.CENTER);

        JTextField txtUser = new JTextField(10);
        JPasswordField txtPin = new JPasswordField(10);

        JButton btnLogin = new JButton("LOGIN");

        btnLogin.addActionListener(e -> {
            menuPage();
        });

        JPanel userPanel = new JPanel();
        userPanel.add(new JLabel("User ID: "));
        userPanel.add(txtUser);

        JPanel pinPanel = new JPanel();
        pinPanel.add(new JLabel("PIN: "));
        pinPanel.add(txtPin);

        frame.add(title);
        frame.add(userPanel);
        frame.add(pinPanel);
        frame.add(btnLogin);

        frame.revalidate();
        frame.repaint();
    }

    static void menuPage() {

        frame.getContentPane().removeAll();
        frame.setLayout(new GridLayout(6, 1, 10, 2));

        JLabel title = new JLabel("ATM Main Menu", JLabel.CENTER);

        JButton btnWithdraw = new JButton("Withdraw");
        JButton btnDeposit = new JButton("Deposit");
        JButton btnTransfer = new JButton("Transfer");
        JButton btnHistory = new JButton("Transaction History");
        JButton btnLogout = new JButton("Logout");

        btnWithdraw.addActionListener(e -> operationPage("Withdraw"));
        btnDeposit.addActionListener(e -> operationPage("Deposit"));
        btnTransfer.addActionListener(e -> operationPage("Transfer"));
        btnHistory.addActionListener(e -> historyPage());
        btnLogout.addActionListener(e -> showLoginPage());

        frame.add(title);
        frame.add(btnWithdraw);
        frame.add(btnDeposit);
        frame.add(btnTransfer);
        frame.add(btnHistory);
        frame.add(btnLogout);

        frame.revalidate();
        frame.repaint();
    }

    static void operationPage(String operation) {

        frame.getContentPane().removeAll();
        frame.setLayout(new GridLayout(6, 1, 10, 2));

        JLabel title = new JLabel(operation + " Page", JLabel.CENTER);

        JLabel result = new JLabel("Enter Details Below", JLabel.CENTER);

        JTextField txtAmount = new JTextField(8);

        JTextField txtReceiver = new JTextField(8);

        JButton btnSubmit = new JButton("Submit");
        JButton btnBack = new JButton("Back");

        JPanel amountPanel = new JPanel();
        amountPanel.add(new JLabel("Amount: "));
        amountPanel.add(txtAmount);

        JPanel receiverPanel = new JPanel();
        receiverPanel.add(new JLabel("Receiver: "));
        receiverPanel.add(txtReceiver);

        btnSubmit.addActionListener(e -> {

            try {
                double amount = Double.parseDouble(txtAmount.getText());

                if (operation.equals("Withdraw")) {

                    if (amount <= balance) {
                        balance -= amount;
                        history.add("Withdrawn: ₹" + amount);
                        result.setText("Withdraw Success! Balance: ₹" + balance);
                    } else {
                        result.setText("Insufficient Balance!");
                    }

                } else if (operation.equals("Deposit")) {

                    balance += amount;
                    history.add("Deposited: ₹" + amount);
                    result.setText("Deposit Success! Balance: ₹" + balance);

                } else if (operation.equals("Transfer")) {

                    String acc = txtReceiver.getText();

                    if (amount <= balance) {
                        balance -= amount;
                        history.add("Transferred ₹" + amount + " to " + acc);
                        result.setText("Transfer Success! Balance: ₹" + balance);
                    } else {
                        result.setText("Insufficient Balance!");
                    }
                }

                txtAmount.setText("");
                txtReceiver.setText("");

            } catch (Exception ex) {
                result.setText("Enter Valid Input!");
            }
        });

        btnBack.addActionListener(e -> menuPage());

        frame.add(title);
        frame.add(result);

        if (operation.equals("Transfer")) {
            frame.add(receiverPanel);
        }

        frame.add(amountPanel);
        frame.add(btnSubmit);
        frame.add(btnBack);

        frame.revalidate();
        frame.repaint();
    }

    static void historyPage() {

        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout());

        JLabel title = new JLabel("Transaction History", JLabel.CENTER);

        JTextArea txtHistory = new JTextArea();
        txtHistory.setEditable(false);

        if (history.isEmpty()) {
            txtHistory.setText("No Transactions Yet!");
        } else {
            StringBuilder sb = new StringBuilder();
            for (String record : history) {
                sb.append(record).append("\n");
            }
            txtHistory.setText(sb.toString());
        }

        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(e -> menuPage());

        frame.add(title, BorderLayout.NORTH);
        frame.add(new JScrollPane(txtHistory), BorderLayout.CENTER);
        frame.add(btnBack, BorderLayout.SOUTH);

        frame.revalidate();
        frame.repaint();
    }
    }