package com.sns.Apps.ui;

import java.io.*;
import java.awt.*;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;

import javax.swing.tree.*;
import java.util.Iterator;
import java.util.ArrayList;
import com.sns.Util.Tracer;
import com.sns.Util.DirectoryTool;

/**
 * The main program for the Directory application.
 * 
 * @version 1.0 12-Jun-2006
 * @author Rob Broadhead
 */
public class DirectoryFrame extends JFrame {

	/* Properties of the Frame */
	public static DirectoryFrame thisFrame;

	public DirectoryTool dirUtil;
	public JTree tree;
	public JTextArea info;

	public String rootPath;

	/* End properties definition */

	static {
		/*
		 * try { // Start the GUI with a Windows look and feel
		 * UIManager.setLookAndFeel("javax.swing.plaf.metal."); }catch (Exception e){}
		 */
		Tracer.setFile("AppTrace.log");
		Tracer.setActive(true);
	}

	/**
	 * A constructor for the class. This takes no parameters.
	 * 
	 */
	public DirectoryFrame() {
	} /* End of the constructor */

	/**
	 * A constructor for the class. It takes a parameter that is used to build the info about the
	 * path.
	 * 
	 * @param String parm The path we'll be working with.
	 */
	public DirectoryFrame(String parm) {
		/* Give the frame a title */
		super("Directory Tool");

		thisFrame = this;
		rootPath = parm;
		dirUtil = new DirectoryTool();
		dirUtil.setRootPath(rootPath);

		/* Set up default values */
		createPanel();

		/* Add a listener in case user clicks the x to close the frame */
		this.addWindowListener(new windowHandler());
	} /* End of the constructor */

	private DefaultMutableTreeNode generatePathNodes(File curFile) {
		DefaultMutableTreeNode curNode = new DefaultMutableTreeNode(curFile);

		ArrayList curDirs = DirectoryTool.getSubDirs(curFile);
		Iterator it = curDirs.iterator();

		DefaultMutableTreeNode subNode;
		while (it.hasNext()) {
			// Grab the subdirectories for each.
			subNode = generatePathNodes((File) it.next());
			curNode.add(subNode);
		}

		return curNode;
	}

	/**
	 * Performs all of the data and graphical object creation and initialization for the frame.
	 */
	public void createPanel() {
		/* Set up and display the frame */
		getContentPane().setLayout(new BorderLayout());
		setSize(800, 600);
		treeHandler th = new treeHandler();

		/* Setup the info area */
		info = new JTextArea("Details will be shown here when a node is selected...");

		/* Build the tree */
		DefaultMutableTreeNode top = new DefaultMutableTreeNode(new File(rootPath));
		tree = new JTree(top);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.addTreeSelectionListener(th);

		ArrayList curDirs = dirUtil.getSubDirs();
		Iterator it = curDirs.iterator();

		DefaultMutableTreeNode curNode;
		while (it.hasNext()) {
			// Grab the subdirectories for each.
			curNode = generatePathNodes((File) it.next());
			top.add(curNode);
		}

		JScrollPane treeView = new JScrollPane(tree);

		getContentPane().add(treeView, "Center");
		getContentPane().add(info, "South");
	} /* End of createPanel() */

	/**
	 * Handles tree selection events
	 */
	class treeHandler implements TreeSelectionListener {

		public void valueChanged(TreeSelectionEvent e) {
	        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

	        if (node == null) return;

	       File curFile = (File) node.getUserObject();
	        
	        info.setText("Size of Files (bytes):" + DirectoryTool.sizeOfFiles(curFile.getAbsolutePath()) + "\n" + "Total number of directories:" + DirectoryTool.countSubDirsRecurse(curFile) + "\n");
	    }
	} /* End of treeHandler class definition */

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
