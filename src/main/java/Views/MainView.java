package Views;
import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame{
    private JButton btnConfiguration, btnTransactions, btnQueries;

    public MainView(){
        super("DDBMS");
        makeInterface();
    }

    private void makeInterface() {
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);

        JLabel title = new JLabel("BRO Star", JLabel.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 40));
        btnConfiguration = new JButton("DDB Configuration");
        btnTransactions = new JButton("Transactions");
        btnQueries = new JButton("Queries");

        Font font = new Font("Tahoma", Font.BOLD, 14);
        btnConfiguration.setFont(font);
        btnTransactions.setFont(font);
        btnQueries.setFont(font);

        title.setBounds(100, 50, 600, 80);
        btnConfiguration.setBounds(200, 200, 400, 80);
        btnTransactions.setBounds(200, 300, 400, 80);
        btnQueries.setBounds(200, 400, 400, 80);

        add(title);
        add(btnConfiguration);
        add(btnTransactions);
        add(btnQueries);
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
}
