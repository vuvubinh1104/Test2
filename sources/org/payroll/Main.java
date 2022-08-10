package org.payroll;

import javax.swing.JFrame;
import javax.swing.UIManager;
import java.awt.Insets;
import java.awt.Color;

// Loader Class
public class Main {
	public static DatabaseManager dbManager;	// shared database manager
	public static JFrame mainFrame;
	public static String userName;
	
	public static void main(String[] args) {
		SetUpUI();
		dbManager = new DatabaseManager("mydb.sqlite");
		// If "the path to database file" is empty, a temporary in-memory database is opened.
		
		(new LoginFrame()).setVisible(true);
	}

	static void SetUpUI() {
		try {
			com.formdev.flatlaf.intellijthemes.FlatSolarizedDarkIJTheme.setup();
			UIManager.put( "Button.arc", 999 );
			UIManager.put( "Component.arc", 999 );
			UIManager.put( "TextComponent.arc", 999 );
			UIManager.put( "Component.arrowType", "triangle" );
			UIManager.put( "ScrollBar.trackArc", 999 );
			UIManager.put( "ScrollBar.thumbArc", 999 );
			UIManager.put( "ScrollBar.trackInsets", new Insets( 2, 4, 2, 4 ) );
			UIManager.put( "ScrollBar.thumbInsets", new Insets( 2, 2, 2, 2 ) );
			UIManager.put( "ScrollBar.track", new Color( 0xe0e0e0 ) );
			UIManager.put( "Component.focusWidth", 2 );
			UIManager.put( "TabbedPane.selectedBackground", Color.white );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
