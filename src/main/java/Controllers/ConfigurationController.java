package Controllers;

import Models.*;
import Views.ConfigurationView;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ConfigurationController implements ActionListener {
    private final ConfigurationView configurationView;
    public ConfigurationController(ConfigurationView configurationView){
        this.configurationView= configurationView;
        setListeners();
    }

    public void setVisible(boolean visible){
        configurationView.fillTable(SingletonDatabaseConfiguration.getInstance().selectFragments());
        configurationView.setVisible(visible);
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
                configurationView.showMessage("Please enter an ID");
                return;
            }
            if (!SingletonDatabaseConfiguration.getInstance().deleteFragment(Integer.parseInt(answer))) {
                configurationView.showMessage("An error has occurred deleting the fragment");
                return;
            }
            configurationView.showMessage("Fragment deleted successfully, restart the application to see the changes");

            configurationView.fillTable(SingletonDatabaseConfiguration.getInstance().selectFragments());

            configurationView.refresh();
            return;
        }

        if (e.getSource() == configurationView.getAddFragmentDialog().getBtnAdd()) {
            String[] values = configurationView.getAddFragmentDialog().getValues();
            for (String s : values)
                if (s.isBlank()) {
                    configurationView.showMessage("Please enter all the values");
                    return;
                }
            if (!SingletonDatabaseConfiguration.getInstance().addFragment(values)) {
                configurationView.showMessage("An error has occurred adding the fragment");
                return;
            }
            configurationView.showMessage("Fragment added successfully, restart the application to see the changes");
            configurationView.fillTable(SingletonDatabaseConfiguration.getInstance().selectFragments());
            configurationView.refresh();
            return;
        }

        if (e.getSource() == configurationView.getAddFragmentDialog().getBtnClear()) {
            configurationView.getAddFragmentDialog().clear();
            return;
        }

    }
}
