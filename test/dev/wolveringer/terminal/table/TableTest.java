package dev.wolveringer.terminal.table;

import org.junit.Test;

import dev.wolveringer.terminal.string.ColoredChar;
import dev.wolveringer.terminal.table.TerminalTable.Align;
import dev.wolveringer.terminal.table.TerminalTable.TerminalRow;

public class TableTest {

	@Test
	public void test() {
		TerminalTable table = new TerminalTable(new TerminalTable.TerminalColumn[]{
				new TerminalTable.TerminalColumn("Name", Align.CENTER),
				new TerminalTable.TerminalColumn("Vorname", Align.CENTER),
				new TerminalTable.TerminalColumn("1.8", Align.CENTER),
				new TerminalTable.TerminalColumn("1.9", Align.CENTER)
		});
		table.addRow("Markus","Hadenfeldt","11","12");
		table.addRow("Markus","Hadenfeldt","12","11");
		TerminalRow row = new TerminalRow(4);
		row.setText(0, "Changes");
		row.setText(1, "Hand");
		row.setText(2, "2\n4");
		row.setText(3, "4\n1");
		table.addRow(row);
		
		
		table.setRowSeperator(new TerminalTable.RowSeperator() {
			@Override
			public ColoredChar getSeperator(TerminalRow row, int rowIndex, int columnFrom, int columnTo) {
				if(columnFrom < 0 || columnFrom < 2)
					return new ColoredChar('|');
				return Integer.parseInt(row.getColumns()[columnFrom].get(rowIndex)) > Integer.parseInt(row.getColumns()[columnTo].get(rowIndex)) ? new ColoredChar("§c>") : new ColoredChar("§a<");
			}
			
			@Override
			public ColoredChar getDefaultSeperator() {
				return new ColoredChar('|');
			}
		});
		
		
		table.printTable();
	}

}
