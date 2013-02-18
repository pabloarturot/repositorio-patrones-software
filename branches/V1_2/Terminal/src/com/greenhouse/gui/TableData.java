package com.greenhouse.gui;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import com.greenhouse.base.Message;

public class TableData {

	public static TableModel buildTableData(final String[] titles,
			final String[][] dataSet) {

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
	
	public static String[] getColumns(){
		
		String[] titles = new String[4];
		titles[0] = "Terminal";
		titles[1] = "Temperature";
		titles[2] = "Date";
		titles[3] = "Frequency";

		return titles;
	}

	public static TableModel builTableModel(List<Message> terminalsData) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy hh:mm:ss");
		DecimalFormat decimalFormat = new DecimalFormat("#.#");

		String[][] data = new String[terminalsData.size()][4];
		
		for (int i = 0; i < terminalsData.size(); i++) {

			Message message = terminalsData.get(i);

			Math.round(message.getTemperature());

			data[i][0] = message.getAlias();
			data[i][1] = decimalFormat.format(message.getTemperature()) + "C°";
			data[i][2] = dateFormat.format(new Date(message.getTimestamp()));
			data[i][3] = decimalFormat.format(message.getFrequency());
		}

		return buildTableData(getColumns(), data);
	}

}
