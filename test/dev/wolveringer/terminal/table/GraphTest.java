package dev.wolveringer.terminal.table;

import org.junit.Test;

import dev.wolveringer.terminal.graph.TerminalGraph;
import dev.wolveringer.terminal.graph.TerminalGraph.Graph;
import dev.wolveringer.terminal.graph.TerminalGraph.Point;
import dev.wolveringer.terminal.string.ColoredString;

public class GraphTest {
	@Test
	public void printGraph(){
		TerminalGraph screen = new TerminalGraph();
		/*
		screen.setStartX(0);
		screen.setStartY(0);
		screen.setEndX(10);
		screen.setEndY(10);
		*/
		
		screen.setStepX(2);
		
		Graph g = new Graph();
		g.addPoint(new Point(0, 0));
		g.addPoint(new Point(10, 10));
		screen.addGraph(g);
		
		for(ColoredString s : screen.buildLines(100, 100, false))
			System.out.println(s.toString(false));
	}
}
