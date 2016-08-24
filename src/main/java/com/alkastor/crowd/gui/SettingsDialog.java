package com.alkastor.crowd.gui;

import com.alkastor.crowd.model.Direct;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SettingsDialog extends JDialog {

    private static final long serialVersionUID = 1L;

    private JTextField txN = new JTextField();
    private JTextField txNx = new JTextField();
    private JTextField txNy = new JTextField();
    private JTextField txT = new JTextField();

    private JButton btUpdate = new JButton("Update var");
    private JButton btRun = new JButton("GO");
    private JButton btInc = new JButton("inc");
    private JButton btDec = new JButton("dec");

    private JCheckBox is_graf_direct = new JCheckBox("is_graf_direct");
    private JCheckBox is_int_speed = new JCheckBox("is_int_speed");
    private JCheckBox is_grid = new JCheckBox("is_grid");

    public SettingsDialog(final MainFrame mainFrame) {
        setModal(true);
        setLocationByPlatform(isEnabled());
//		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(280, 200);
        setTitle("Settings");
        setLayout(new GridLayout(8, 1));
        add(new JLabel("         N"));
        add(txN);
        txN.setText(mainFrame.model.N + "");
        add(new JLabel("         nx"));
        add(txNx);
        txNx.setText(mainFrame.model.nx + "");
        add(new JLabel("         ny"));
        add(txNy);
        txNy.setText(mainFrame.model.ny + "");
        add(is_grid);
        is_grid.setSelected(Direct.is_grid);
        is_grid.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Direct.is_grid = is_grid.isSelected();
                mainFrame.comp.update(mainFrame.comp.getGraphics());
            }
        });
        add(is_graf_direct);
        is_graf_direct.setSelected(Direct.is_gdirect);
        is_graf_direct.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Direct.is_gdirect = is_graf_direct.isSelected();
                mainFrame.comp.update(mainFrame.comp.getGraphics());
            }
        });
        add(is_int_speed);
        is_int_speed.setSelected(Direct.is_speed);
        is_int_speed.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Direct.is_speed = is_int_speed.isSelected();
            }
        });
        add(new JCheckBox());
        add(new JLabel("    delta T event"));
        add(txT);
        txT.setText(mainFrame.timer.getDelay() + "");
        add(btInc);
        btInc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainFrame.timer.setDelay(mainFrame.timer.getDelay() * 2);
                txT.setText(mainFrame.timer.getDelay() + "");
            }
        });
        add(btDec);
        btDec.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainFrame.timer.setDelay(mainFrame.timer.getDelay() / 2);
                if (mainFrame.timer.getDelay() == 0)
                    mainFrame.timer.setDelay(1);
                txT.setText(mainFrame.timer.getDelay() + "");
            }
        });
        add(btRun);
        btRun.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (mainFrame.timer.isRunning()) {
                    mainFrame.timer.stop();
                    btRun.setText("GO");
                } else {
                    mainFrame.timer.start();
                    btRun.setText("STOP");
                }
            }
        });
        add(btUpdate);
        btUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainFrame.model.N = Integer.parseInt(txN.getText());
                mainFrame.model.nx = Integer.parseInt(txNx.getText());
                mainFrame.model.ny = Integer.parseInt(txNy.getText());
                mainFrame.model.initialize();
                mainFrame.comp.update(mainFrame.comp.getGraphics());
            }
        });
        setVisible(true);
    }
}
