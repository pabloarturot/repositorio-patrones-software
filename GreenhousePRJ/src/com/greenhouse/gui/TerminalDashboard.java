package com.greenhouse.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableModel;

import com.greenhouse.base.Message;
import com.greenhouse.base.MessageDTO;

public class TerminalDashboard extends javax.swing.JFrame{


	private static final long serialVersionUID = 1L;
   
	private final JPanel leftPanel;
    private final JPanel rightPanel;

    private final JLabel labelTituloDatosRemotos;

    private final JButton updateFrequencyButton;
    private final JTextField updateFrequencyTextField;

    private final JTable terminalsInfoTable;



    public TerminalDashboard(){
    	
    	this.setLayout(null);
    	this.setSize(810,400);
    	this.setResizable(false);

    	leftPanel=new JPanel();
    	leftPanel.setLayout(null);
    	leftPanel.setBounds(0,0,410,400);
        leftPanel.setLayout(new BoxLayout(leftPanel,BoxLayout.Y_AXIS));
        
        final GridLayout formLayout = new GridLayout(2,2);
        final JPanel formJPanel = new JPanel(formLayout);
        
        updateFrequencyTextField=new JTextField("");
        updateFrequencyTextField.setVisible(true);
        formJPanel.add(updateFrequencyTextField);
        
        updateFrequencyButton = new javax.swing.JButton();
        updateFrequencyButton.setText("Change the Frequency");
        formJPanel.add(updateFrequencyButton);


        labelTituloDatosRemotos=new JLabel("Remote Terminals Lectures");
        labelTituloDatosRemotos.setSize(410,20);
        formJPanel.add(labelTituloDatosRemotos);
       
       
        updateFrequencyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        
        leftPanel.add(formJPanel);
        
        rightPanel=new JPanel();
        rightPanel.setBounds(400,0,400,400);
        rightPanel.setLayout(null);
        
        final JPanel tableJPanel = new JPanel();
        tableJPanel.setLayout(new GridLayout(2,0));

        terminalsInfoTable = new JTable();
        terminalsInfoTable.getTableHeader().setReorderingAllowed(false);
        tableJPanel.add(terminalsInfoTable);
        
        leftPanel.add(tableJPanel);
           
        this.add(leftPanel,BorderLayout.WEST);
    	this.add(rightPanel,BorderLayout.EAST);
    	
    	this.setVisible(true);

        this.addWindowListener
        (
            new WindowAdapter() {
            @Override
                public void windowClosing(final WindowEvent e) {
                   TerminalDashboard.this.windowClosed();
                }
            }
        );

    }

    private void jButton1ActionPerformed(final java.awt.event.ActionEvent evt) {
    	
        try{
          new Integer(updateFrequencyTextField.getText());
          
        }catch(final NumberFormatException nfex){
            JOptionPane.showMessageDialog(this,"Please check if the entered value is a numeric frequency","Wrong Frequency Format",JOptionPane.WARNING_MESSAGE);
        }

    }

    protected void windowClosed()
    {
        System.exit(0);
    }

   
    public static void main(final String args[]) {

        final TerminalDashboard terminalDashboard=new TerminalDashboard();
        
        final JPanel temperatureChart=ChartPanelHelper.getDialChart(40d); terminalDashboard.rightPanel.add(temperatureChart);
        temperatureChart.setBounds(55,45,300,300);
        temperatureChart.repaint();
        temperatureChart.setVisible(true);
        
        Message message = null;
        final List<Message> result = new ArrayList<Message>();
        for (int i = 0; i < 50; i++) {
        	message = new MessageDTO();
        	message.setAlias("Terminal"+i);
        	message.setHostIp("192.168.1."+i);
        	message.setTimestamp(new Date().getTime());
        	message.setFrequency(10+i);
        	result.add(message);
		}
        
        final TableModel model =TableData.builTableModel(result);
        terminalDashboard.terminalsInfoTable.setModel(model);
    }


}
