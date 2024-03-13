package Views;
import Extra.Routines;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {
    private JLabel lblTitle, lblLogo;
    private JButton btnConfiguration, btnTransactions, btnQueries;

    public MainView(){
        super("DDBMS");
        makeInterface();
        setStyles();
    }

    private void makeInterface() {
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);

        lblTitle = new JLabel("BRO Star", JLabel.CENTER);
        lblLogo = new JLabel(Routines.resizeImage("src/main/resources/logo.png", 60, 60));

        btnConfiguration = new JButton("DDB Configuration");
        btnTransactions = new JButton("Transactions");
        btnQueries = new JButton("Queries");

        lblTitle.setBounds(100, 40, 600, 120);
        lblLogo.setBounds(620, 40, 80, 80);
        btnConfiguration.setBounds(200, 200, 400, 80);
        btnTransactions.setBounds(200, 300, 400, 80);
        btnQueries.setBounds(200, 400, 400, 80);

        add(lblTitle);
        add(lblLogo);
        add(btnConfiguration);
        add(btnTransactions);
        add(btnQueries);
    }

    public void setStyles() {
        Color[] colors = new Color[]{new Color(249, 247, 247), new Color(63, 114, 175), new Color(17, 45, 78)};

        lblTitle.setFont(new Font("calibri", Font.BOLD, 110));
        Font font = new Font("calibri", Font.BOLD, 24);
        btnConfiguration.setFont(font);
        btnTransactions.setFont(font);
        btnQueries.setFont(font);

        getContentPane().setBackground(colors[0]);
        btnQueries.setBackground(colors[1]);
        btnTransactions.setBackground(colors[1]);
        btnConfiguration.setBackground(colors[1]);

        btnQueries.setForeground(colors[0]);
        btnTransactions.setForeground(colors[0]);
        btnConfiguration.setForeground(colors[0]);
        lblTitle.setForeground(colors[2]);
    }

    public void start() {
        setVisible(true);
    }

    public JButton getBtnConfiguration() {
        return btnConfiguration;
    }

    public JButton getBtnTransactions() {
        return btnTransactions;
    }

    public JButton getBtnQueries() {
        return btnQueries;
    }

    public void paint(Graphics g) {
        super.paint(g);
//        g.setColor(Color.BLACK);
//        g.fillOval(10, 50, 50, 50);
//        g.fillOval(10, 90, 50, 50);
//        g.fillOval(20, 70, 150, 50);
    }
}
