import javax.swing.*;

import java.sql.*;
import java.util.Random;

public class OnlineReservationSystem {
    JFrame frame;
    JTextField userField;
    JPasswordField passField;
    String loggedUser=" ";

    String url="jdbc:mysql://127.0.0.1:3306/task_one";
    String user="root";
    String pass="Adshaya@5232";

    JTextField passengerField, trainField, fromField, toField, dateField;

    JTextField pnrField;

    public OnlineReservationSystem() {
        frame = new JFrame("Online Reservation System");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        loginPage();
        frame.setVisible(true);
    }

    //LOGIN
    void loginPage(){
        frame.getContentPane().removeAll();

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel title = new JLabel("Login Page");
        title.setBounds(230, 30, 200, 30);

        JLabel user = new JLabel("Username:");
        user.setBounds(150, 120, 100, 30);

        userField = new JTextField();
        userField.setBounds(250, 120, 180, 30);

        JLabel pass = new JLabel("Password:");
        pass.setBounds(150, 170, 100, 30);

        passField = new JPasswordField();
        passField.setBounds(250, 170, 180, 30);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(250, 240, 100, 35);

        JButton signupBtn = new JButton("Sign Up");
        signupBtn.setBounds(250, 290, 100, 35);

        loginBtn.addActionListener(e -> loginUser());
        signupBtn.addActionListener(e -> signupUser());

        panel.add(title);
        panel.add(user);
        panel.add(userField);
        panel.add(pass);
        panel.add(passField);
        panel.add(loginBtn);
        panel.add(signupBtn);

        frame.add(panel);
        frame.revalidate();
        frame.repaint();
    }

    //SIGNUP
    void signupUser() {

        String username = userField.getText();
        String password = String.valueOf(passField.getPassword());

        try {
            Connection con = DriverManager.getConnection(url, user, pass);
            String query = "INSERT INTO login(username,password) VALUES (?,?)";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, username);
            pst.setString(2, password);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(frame, "Signup Successful! Now Login.");
            con.close();
        } catch (SQLIntegrityConstraintViolationException ex) {
            JOptionPane.showMessageDialog(frame, "Username already exists!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //LOGIN FUNCTION
    void loginUser() {
        String username = userField.getText();
        String password= String.valueOf(passField.getPassword());

        try {
            Connection con = DriverManager.getConnection(url, user, pass);

            String query = "SELECT * FROM login WHERE username=? AND password=?";
            PreparedStatement pst = con.prepareStatement(query);

            pst.setString(1, username);
            pst.setString(2, password);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                loggedUser =   username;

                JOptionPane.showMessageDialog(frame, "Login Successful! Welcome " + loggedUser);

                showMainMenu();

            } else {
                JOptionPane.showMessageDialog(frame,
                        "Invalid Username or Password!");
            }

            con.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //MAIN MENU
    void showMainMenu() {

        frame.getContentPane().removeAll();

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel welcome = new JLabel("Welcome, " + loggedUser);
        welcome.setBounds(150, 50, 300, 30);

        JButton reservationBtn = new JButton("Reservation Form");
        reservationBtn.setBounds(150, 130, 200, 50);

        JButton cancelBtn = new JButton("Cancellation Form");
        cancelBtn.setBounds(150, 210, 200, 50);

        reservationBtn.addActionListener(e -> showReservationForm());

        cancelBtn.addActionListener(e -> showCancellationForm());

        panel.add(welcome);
        panel.add(reservationBtn);
        panel.add(cancelBtn);

        frame.add(panel);
        frame.revalidate();
        frame.repaint();
    }

    //RESERVATION FORM
    void showReservationForm() {

        frame.getContentPane().removeAll();
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel title = new JLabel("Reservation Form");
        title.setBounds(200, 30, 250, 30);

        JLabel passengerLabel = new JLabel("Passenger Name:");
        passengerLabel.setBounds(100, 100, 120, 30);

        passengerField = new JTextField();
        passengerField.setBounds(250, 100, 200, 30);

        JLabel trainLabel = new JLabel("Train Name:");
        trainLabel.setBounds(100, 150, 120, 30);

        trainField = new JTextField();
        trainField.setBounds(250, 150, 200, 30);

        JLabel fromLabel = new JLabel("From Station:");
        fromLabel.setBounds(100, 200, 120, 30);

        fromField = new JTextField();
        fromField.setBounds(250, 200, 200, 30);

        JLabel toLabel = new JLabel("To Station:");
        toLabel.setBounds(100, 250, 120, 30);

        toField = new JTextField();
        toField.setBounds(250, 250, 200, 30);

        JLabel dateLabel = new JLabel("Journey Date:");
        dateLabel.setBounds(100, 300, 120, 30);

        dateField = new JTextField();
        dateField.setBounds(250, 300, 200, 30);

        JButton submitBtn = new JButton("Submit Reservation");
        submitBtn.setBounds(200, 370, 180, 40);

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(20, 20, 80, 30);

        submitBtn.addActionListener(e -> reserveTicket());
        backBtn.addActionListener(e -> showMainMenu());

        panel.add(title);
        panel.add(passengerLabel);
        panel.add(passengerField);
        panel.add(trainLabel);
        panel.add(trainField);
        panel.add(fromLabel);
        panel.add(fromField);
        panel.add(toLabel);
        panel.add(toField);
        panel.add(dateLabel);
        panel.add(dateField);
        panel.add(submitBtn);
        panel.add(backBtn);

        frame.add(panel);
        frame.revalidate();
        frame.repaint();
    }

    //PNR GENERATOR
    int generatePNR() {
        Random r = new Random();
        return 100000 + r.nextInt(900000);
    }

    //RESERVE TICKETS
    void reserveTicket() {

        int pnr = generatePNR();

        try {
            Connection con = DriverManager.getConnection(url, user, pass);

            String query = "INSERT INTO reservations VALUES(?,?,?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(query);

            pst.setInt(1, pnr);
            pst.setString(2, loggedUser);
            pst.setString(3, passengerField.getText());
            pst.setString(4, trainField.getText());
            pst.setString(5, fromField.getText());
            pst.setString(6, toField.getText());
            pst.setString(7, dateField.getText());

            pst.executeUpdate();

            JOptionPane.showMessageDialog(frame,
                    "Ticket Reserved Successfully!\nPNR Number: " + pnr);

            con.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //CANCELLATION FORM
    void showCancellationForm() {

        frame.getContentPane().removeAll();
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel title = new JLabel("Cancellation Form");
        title.setBounds(200, 50, 250, 30);

        JLabel pnrLabel = new JLabel("Enter PNR Number:");
        pnrLabel.setBounds(120, 150, 150, 30);

        pnrField = new JTextField();
        pnrField.setBounds(270, 150, 180, 30);

        JButton fetchBtn = new JButton("Fetch Ticket");
        fetchBtn.setBounds(200, 220, 150, 40);

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(20, 20, 80, 30);

        fetchBtn.addActionListener(e -> fetchTicket());

        backBtn.addActionListener(e -> showMainMenu());

        panel.add(title);
        panel.add(pnrLabel);
        panel.add(pnrField);
        panel.add(fetchBtn);
        panel.add(backBtn);

        frame.add(panel);
        frame.revalidate();
        frame.repaint();
    }

    //FETCH TICKETS
    void fetchTicket() {

        int pnr = Integer.parseInt(pnrField.getText());

        try {
            Connection con = DriverManager.getConnection(url, user, pass);

            String query = "SELECT * FROM reservations WHERE pnr=?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, pnr);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                String details =
                        "Passenger: " + rs.getString("passenger_name") +
                                "\nTrain: " + rs.getString("train_name") +
                                "\nFrom: " + rs.getString("from_station") +
                                "\nTo: " + rs.getString("to_station") +
                                "\nDate: " + rs.getString("journey_date");

                int confirm = JOptionPane.showConfirmDialog(frame,
                        details + "\n\nCancel this ticket?",
                        "Confirm Cancellation",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {

                    String delQuery = "DELETE FROM reservations WHERE pnr=?";
                    PreparedStatement delpst = con.prepareStatement(delQuery);
                    delpst.setInt(1, pnr);

                    delpst.executeUpdate();

                    JOptionPane.showMessageDialog(frame,
                            "Ticket Cancelled Successfully!");
                }

            } else {
                JOptionPane.showMessageDialog(frame,
                        "Invalid PNR Number!");
            }

            con.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new OnlineReservationSystem();
    }

}
