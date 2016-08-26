package com.alkastor.crowd.gui;

import com.alkastor.crowd.calculation.Direct;
import com.alkastor.crowd.gui.settings.SettingsAction;
import com.alkastor.crowd.impl.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainFrame extends JFrame {
    public Model model;
    public Timer timer;
    public Compp comp;

    private static final long serialVersionUID = 1L;
    private JMenuBar mainMenu = new JMenuBar();
    private JMenu menu = new JMenu("menu");
    private Action showSettings = new SettingsAction(MainFrame.this);

    private JMenuItem settings = new JMenuItem(showSettings);

    public MainFrame() {
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setLocationByPlatform(true);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainMenu.add(menu);
        menu.add(settings);
        model = new Model.Builder(50, 50, 500).build();
        comp = new Compp(model);
        timer = new Timer(50, new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                for (int i = 0; i < model.n; i++) {
                    model.simulate();
                }
                Direct.sum_v_direct(model.cells, model.balls, model.nx, model.ny);
                comp.update(comp.getGraphics());
            }
        });
        add(comp);
        setJMenuBar(mainMenu);
    }
}
