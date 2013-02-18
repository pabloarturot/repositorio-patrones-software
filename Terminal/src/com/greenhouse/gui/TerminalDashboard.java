package com.greenhouse.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableModel;

import com.greenhouse.controller.TemperatureController;

public class TerminalDashboard extends javax.swing.JFrame {

	private static final long serialVersionUID = 1L;

	private final JPanel leftPanel;
	private final JPanel rightPanel;

	private final JLabel labelTituloDatosRemotos;

	private final JButton updateFrequencyButton;
	private final JTextField updateFrequencyTextField;

	private final JTable terminalsInfoTable;
	
	TemperatureController temperatureController;

	public TerminalDashboard(TemperatureController temperatureController) {
		
		this.temperatureController = temperatureController;

		this.setLayout(null);
		this.setSize(810, 400);
		this.setResizable(false);

		leftPanel = new JPanel();
		leftPanel.setLayout(null);
		leftPanel.setBounds(0, 0, 410, 400);
	
		final GridLayout formLayout = new GridLayout(2, 2);
		final JPanel formJPanel = new JPanel(formLayout);
		formJPanel.setBounds(0, 0, 410, 60);

		updateFrequencyTextField = new JTextField("");
		updateFrequencyTextField.setVisible(true);
		formJPanel.add(updateFrequencyTextField);

		updateFrequencyButton = new javax.swing.JButton();
		updateFrequencyButton.setText("Change the Frequency");
		formJPanel.add(updateFrequencyButton);

		labelTituloDatosRemotos = new JLabel("Remote Terminals Lectures");
		labelTituloDatosRemotos.setSize(410, 20);
		formJPanel.add(labelTituloDatosRemotos);

		updateFrequencyButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(
							final java.awt.event.ActionEvent evt) {
						jButton1ActionPerformed(evt);
					}
				});

		leftPanel.add(formJPanel);

		rightPanel = new JPanel();
		rightPanel.setBounds(400, 0, 400, 400);
		rightPanel.setLayout(null);

		terminalsInfoTable = new JTable();
		terminalsInfoTable.setBounds(0, 70, 410, 300);
		terminalsInfoTable.getTableHeader().setReorderingAllowed(false);
		
		leftPanel.add(terminalsInfoTable);
		
		this.add(leftPanel, BorderLayout.WEST);
		this.add(rightPanel, BorderLayout.EAST);

		this.setVisible(true);

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(final WindowEvent e) {
				TerminalDashboard.this.windowClosed();
			}
		});

	}

	private void jButton1ActionPerformed(final java.awt.event.ActionEvent evt) {

		try {
			
			temperatureController.changeTerminalsFrequency(new Integer(updateFrequencyTextField.getText()));

		} catch (final NumberFormatException nfex) {
			JOptionPane.showMessageDialog(this,
					"Please check if the entered value is a numeric frequency",
					"Wrong Frequency Format", JOptionPane.WARNING_MESSAGE);
		}

	}

	protected void windowClosed() {
		temperatureController.closeTerminalConn();
		System.exit(0);
	}

	public static void main(final String args[]) {
    	
		final TemperatureController temperatureController = new TemperatureController();
    	final TerminalDashboard terminalDashboard=new TerminalDashboard(temperatureController);
    	double localTemperature ;

    	while (true) {
    		
    		localTemperature = temperatureController.getLocalTemperature();
    		
    		sleep(1000);
    		
    		final JPanel temperatureChart=ChartPanelHelper.getDialChart(localTemperature); 
    		terminalDashboard.rightPanel.add(temperatureChart);
    		temperatureChart.setBounds(55,45,300,300);
    		temperatureChart.repaint();
    		temperatureChart.setVisible(true);
    		
    		final int frecuenciaSegs=temperatureController.getFrequencyInterval();
    		
    		final TableModel model =TableData.builTableModel(temperatureController.getTerminalsTemperatures());
    		terminalDashboard.terminalsInfoTable.setModel(model);
    		
    		sleep(frecuenciaSegs*1000);
    	}
			
	}

	private static void sleep(final int tiempoEsperaMilis) {
		try {
			Thread.sleep(tiempoEsperaMilis);
		} catch (final InterruptedException e) {
			System.out.println("ERROR: " + e.getMessage());
		}
	}

}
