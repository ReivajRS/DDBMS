package Controllers;

import Models.*;
import Views.ConfigurationView;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ConfigurationController implements ActionListener {
    private DatabaseConnections databaseConnections;
    private SQLServer dbConfiguration;
    private ConfigurationView configurationView;
//    private AddFragmentController addFragmentController;
    public ConfigurationController(ConfigurationView configurationView, DatabaseConnections databaseConnections, SQLServer dbConfiguration){
        this.configurationView= configurationView;
        this.dbConfiguration = dbConfiguration;
//        addFragmentController = new AddFragmentController(configurationView.getAddFragmentDialog(), dbConfiguration);
        setListeners();
    }

    public void setVisible(boolean visible){
        fillTable();
        configurationView.setVisible(visible);
    }

    private void fillTable() {
        configurationView.getTable().getModel().getDataVector().removeAllElements();
        ArrayList<Fragment> fragments = dbConfiguration.selectFragments();
        for (Fragment fragment : fragments) {
            String[] tuple = new String[configurationView.getTable().getTable().getColumnCount()];
            tuple[0] = fragment.getIdFragment().toString();
            tuple[1] = fragment.getDistributedTable();
            tuple[2] = fragment.getDBMS();
            tuple[3] = fragment.getDB();
            tuple[4] = fragment.getURI();
            tuple[5] = fragment.getCriteria();
            tuple[6] = fragment.getCriteriaValue();
            tuple[7] = fragment.getAttributesString();
            configurationView.getTable().getModel().addRow(tuple);
        }
        configurationView.refresh();
    }

    private void setListeners() {
        configurationView.getBtnAddFragment().addActionListener(this);
        configurationView.getBtnDeleteFragment().addActionListener(this);
        configurationView.getAddFragmentDialog().getBtnAdd().addActionListener(this);
        configurationView.getAddFragmentDialog().getBtnClear().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == configurationView.getBtnAddFragment()) {
            configurationView.getAddFragmentDialog().clear();
            configurationView.getAddFragmentDialog().setVisible(true);
            return;
        }

        if (e.getSource() == configurationView.getBtnDeleteFragment()) {
            String answer = configurationView.showDeleteMessage();
            System.out.println(answer);
            if (answer.isBlank())
                return;
            if (!answer.matches("[0-9]+")) {
                JOptionPane.showMessageDialog(configurationView, "Please enter an ID");
                return;
            }
            if (!dbConfiguration.deleteFragment(Integer.parseInt(answer))) {
                JOptionPane.showMessageDialog(configurationView, "An error has occurred deleting the fragment");
                return;
            }
            JOptionPane.showMessageDialog(configurationView, "Fragment deleted successfully, restart the application to see the changes");
            fillTable();
            configurationView.refresh();
            return;
        }

        if (e.getSource() == configurationView.getAddFragmentDialog().getBtnAdd()) {
            String[] values = configurationView.getAddFragmentDialog().getValues();
            for (String s : values)
                if (s.isBlank()) {
                    JOptionPane.showMessageDialog(configurationView, "Please enter all the values");
                    return;
                }
            if (!dbConfiguration.addFragment(values)) {
                JOptionPane.showMessageDialog(configurationView, "An error has occurred adding the fragment");
                return;
            }
            JOptionPane.showMessageDialog(configurationView, "Fragment added successfully, restart the application to see the changes");
            fillTable();
            configurationView.refresh();
            return;
        }

        if (e.getSource() == configurationView.getAddFragmentDialog().getBtnClear()) {
            configurationView.getAddFragmentDialog().clear();
            return;
        }
    }
}
