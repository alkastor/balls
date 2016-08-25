package com.alkastor.crowd.gui.settings;

import com.alkastor.crowd.gui.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SettingsAction extends AbstractAction {

    private static final long serialVersionUID = 1L;

    private MainFrame mainFrame;

    public SettingsAction(MainFrame mainFrame) {
        super("settings");
        this.mainFrame = mainFrame;
    }

    public void actionPerformed(ActionEvent e) {
        new SettingsDialog(mainFrame);
    }
}
