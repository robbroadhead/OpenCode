package com.rb.gmail.ui;

import java.io.*;


import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;

import java.util.Iterator;
import java.util.ArrayList;
import com.rb.gmail.data.*;

/**
 * The main Frame for the Gmail viewer application.
 * 
 * @version 1.0 03-Feb-2010
 * @author Rob Broadhead
 */
public class ViewerFrame extends JFrame {

	/* Properties of the Frame */
	public static ViewerFrame thisFrame;
	public MailModel mailmodel = new MailModel();

	public JTextField txtSearch;
	public JList lstFolder;
	public JTable lstMail;
	public JTextArea txtcontent;
	
	private GmailStore myMail = new GmailStore();
	/* End properties definition */

	/*
	 * static { Tracer.setFile("AppTrace.log"); Tracer.setActive(true); }
	 */

	/**
	 * A constructor for the class. This takes no parameters.
	 * 
	 */
	public ViewerFrame() {
		/* Give the frame a title */
		super("Gmail Viewer Demo");

		thisFrame = this;

		/* Set up default values */
		createPanel();

		/* Add a listener in case user clicks the x to close the frame */
		this.addWindowListener(new windowHandler());
		
		lstFolder.setListData(myMail.GetEmails("Robert.Broadhead", "2muchjoy."));
	} /* End of the constructor */

	public void populateMessages(String folder) {
		String srchString = this.txtSearch.getText();
		
		if (srchString.equals("Enter search string here...")) {
			srchString = "";
		}
		
		try {
			mailmodel.setFolder(myMail.getFolder(folder), srchString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		lstMail.setModel(mailmodel);
	}
	
	public void populateMail(int idx) {
		txtcontent.setText(myMail.getEmail(mailmodel.getMessage(idx)));
	}
	
	/**
	 * Performs all of the data and graphical object creation and initialization
	 * for the frame.
	 */
	public void createPanel() {
		/* Set up and display the frame */
		getContentPane().setLayout(new BorderLayout());
		setSize(800, 600);
		folderHandler lh = new folderHandler();
		mailHandler mh = new mailHandler();

		/* Setup the info area */
		txtcontent = new JTextArea(
				"Details will be shown here when an email is selected...");

		/* Build the list */
		lstFolder = new JList();
		lstFolder.addListSelectionListener(lh);
		lstFolder.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		lstMail = new JTable(mailmodel);
		lstMail.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		ListSelectionModel lsm = lstMail.getSelectionModel();
		lsm.addListSelectionListener(mh);

		JScrollPane folderView = new JScrollPane(lstFolder);
		JScrollPane mailView = new JScrollPane(lstMail);
		JScrollPane contentView = new JScrollPane(txtcontent);
		JPanel browser = new JPanel();
		txtSearch = new JTextField();
		txtSearch.setText("Enter search string here...");
		browser.setLayout(new BorderLayout());
		browser.add(txtSearch,"North");
		browser.add(folderView,"West");
		browser.add(mailView,"Center");
		
		getContentPane().add(browser, "North");
		getContentPane().add(contentView, "Center");
	} /* End of createPanel() */

	/**
	 * Handles list selection events
	 */
	class folderHandler implements ListSelectionListener {
		private String lastSelection = "";

		public void valueChanged(ListSelectionEvent e) {
			JList jl = (JList) e.getSource();
			String curSelection = jl.getSelectedValue().toString();
			if (!curSelection.equals(lastSelection)) {
				System.out.println("Selected:" + curSelection);
				ViewerFrame.thisFrame.populateMessages(curSelection);
				lastSelection = curSelection;
			}
		}
	} /* End of listHandler class definition */
	
	class mailHandler implements ListSelectionListener {
		private String lastSelection = "";

		public void valueChanged(ListSelectionEvent e) {
			DefaultListSelectionModel jl = (DefaultListSelectionModel) e.getSource();
			if (jl.getMinSelectionIndex() == -1) {
				return;
			}
			int idx = jl.getMinSelectionIndex();
			String[] curSelection = ViewerFrame.thisFrame.mailmodel.getCachedData(idx);
			if (!curSelection[4].equals(lastSelection)) {
				System.out.println("Selected:" + curSelection[3]);
				ViewerFrame.thisFrame.populateMail(idx);
				lastSelection = curSelection[4];
			}
		}
	} /* End of listHandler class definition */

	/**
	 * Handles Frame events such as closing the Frame.
	 */
	class windowHandler extends WindowAdapter {

		public void windowClosing(WindowEvent event) {
			System.exit(0);
		}

		public void windowActivated(WindowEvent e) {
			/* Update look and feel */
			SwingUtilities.updateComponentTreeUI(thisFrame);
		} /* End of actionPerformed */
	} /* End of windowHandler class definition */

} /* End of DirectoryFrame class definition */
