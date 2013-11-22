package com.xii.spreadem;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class MainWindow {
    private static Manager mgr;

    private JPanel MainPanel;
    private JList lstVariables;
    private JTextField txtId;
    private JTextField txtValue;
    private JTextField txtChgProd;
    private JTextField txtFunction;
    private JTextField txtName;
    private JTextField txtChange;
    private JTextField txtAuto;
    private JTextField txtValueNotify;
    private JButton updateButton;
    private JButton createButton;
    private JButton runButton;
    private JButton stopButton;
    private JTextArea txtMessages;
    private Timer UpdateTimer;


    private void UpdateInterface(Variable v) {
        txtId.setText(String.valueOf(lstVariables.getSelectedIndex()));
        txtName.setText(v.getName());
        txtFunction.setText(v.getFunction());
        txtValue.setText(String.valueOf(v.getValue()));
        txtValueNotify.setText(v.getValueNotify());
        txtValue.setText(String.valueOf(v.getValue()));
        txtChange.setText(String.valueOf(v.getChange()));
        txtChgProd.setText(String.valueOf(v.getChangeProduct()));
        txtAuto.setText(String.valueOf(v.getAuto()));

    }
    public MainWindow() {
        ActionListener taskPerformer = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mgr.Update();
                lstVariables.updateUI();
                UpdateInterface(mgr.Variables.get(lstVariables.getSelectedIndex()));
            }
        };
        UpdateTimer = new Timer(50, taskPerformer);
        UpdateTimer.setRepeats(true);


        lstVariables.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                UpdateInterface(mgr.Variables.get(lstVariables.getSelectedIndex()));
            }
        });

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(txtAuto.getText().equals(""))
                    txtAuto.setText("0");
                if(txtFunction.getText().equals(""))
                    txtFunction.setText("0");
                if(txtName.getText().equals(""))
                    txtName.setText("variable");

                mgr.Add(txtName.getText(),txtFunction.getText(),txtValueNotify.getText(), Integer.parseInt(txtAuto.getText()));
                mgr.Update(mgr.Variables.size() - 1);
                lstVariables.updateUI();
                lstVariables.setSelectedIndex(mgr.Variables.size() - 1);

                UpdateInterface(mgr.Variables.lastElement());


            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(txtAuto.getText().equals(""))
                    txtAuto.setText("0");
                if(txtFunction.getText().equals(""))
                    txtFunction.setText("0");
                if(txtName.getText().equals(""))
                    txtName.setText("variable");

                Variable selected = mgr.Variables.get(lstVariables.getSelectedIndex());
                selected.setName(txtName.getText());
                selected.setFunction(txtFunction.getText());
                selected.setValueNotify(txtValueNotify.getText());
                selected.setAuto(Integer.parseInt(txtAuto.getText()));
                mgr.Update(lstVariables.getSelectedIndex());
                UpdateInterface(selected);
                lstVariables.updateUI();
            }
        });
        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UpdateTimer.start();
            }
        });
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UpdateTimer.stop();
            }
        });
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {

        }
        mgr = new Manager();

        JFrame frame = new JFrame("spread 'em 2013");

        frame.setContentPane(new MainWindow().MainPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(800, 500);
        System.out.print(mgr);

    }


    private void createUIComponents() {
        lstVariables = new JList(mgr.Variables);

    }
}
