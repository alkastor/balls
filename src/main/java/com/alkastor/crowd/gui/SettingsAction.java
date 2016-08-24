package com.alkastor.crowd.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SettingsAction extends AbstractAction {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public MainFrame mainFrame;

    public SettingsAction(MainFrame mainFrame) {
        super("settings");
        this.mainFrame = mainFrame;
    }

    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        new SettingsDialog(mainFrame);
    }

}
