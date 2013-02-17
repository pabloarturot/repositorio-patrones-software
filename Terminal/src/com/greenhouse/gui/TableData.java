package com.greenhouse.gui;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import com.greenhouse.base.Message;

public class TableData {

	public static TableModel buildTableData(final String[] titles,final String[][] dataSet) {
        
        TableModel model = new AbstractTableModel() {

			private static final long serialVersionUID = 1L;

			public int getColumnCount() {
                return titles.length;
            }

            public int getRowCount() {
                return dataSet.length;
            }

            public Object getValueAt(int row, int col) {
                return dataSet[row][col];
            }

            @Override
            public String getColumnName(int column) {
                return titles[column];
            }

            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }

            @SuppressWarnings("unused")
			public void setValueAt(String aValue, int row, int column) {
                dataSet[row][column] = aValue;
            }
        };

        return model;
    }

     public static TableModel builTableModel(List<Message> terminalsData){
    	 
    	SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MMM/yyyy hh:mm:ss");

        String[][] data = new String[terminalsData.size()][4];

        String[] titles = new String[3];
        titles[0] = "Terminal";
        titles[1] = "Temperature";
        titles[2] = "Date";


        for(int i=0;i<terminalsData.size();i++){

        	Message message=terminalsData.get(i);

            data[i][0]= message.getAlias();
            data[i][1]= new Double(message.getTemperature()).toString();
            data[i][2]= dateFormat.format(new Date(message.getTimestamp()));
        }

        return buildTableData(titles, data);
    }

}
