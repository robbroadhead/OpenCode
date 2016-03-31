package com.rb.gmail.ui;

import javax.mail.*;
import javax.mail.search.AndTerm;
import javax.mail.search.BodyTerm;
import javax.mail.search.SearchTerm;
import javax.mail.search.SubjectTerm;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.swing.table.AbstractTableModel;

public class MailModel extends AbstractTableModel {

	private Folder folder;
	private Message[] messages;
	private HashMap threadmarks = new HashMap();

	private String[] columnNames = { "T", "Date", "From", "Subject", "ID" };
	private Class[] columnTypes = { String.class, String.class, String.class,
			String.class, String.class };

	public void setFolder(Folder what, String srch) throws MessagingException {
		if (what != null) {

			// opened if needed
			if (!what.isOpen()) {
				what.open(Folder.READ_WRITE);
			}

			// get the messages
			if (srch.equals("")) {
				messages = what.getMessages();
			} else {
				SearchTerm st = new AndTerm(new SubjectTerm(srch),
						new BodyTerm(srch));
				messages = what.search(st);
			}
			// Tweak messages
			messages = markThreads(messages);
			cached = new String[messages.length][];
		} else {
			messages = null;
			cached = null;
		}
		// close previous folder and switch to new folder
		if (folder != null)
			folder.close(true);
		folder = what;
		fireTableDataChanged();
	}

	public Message[] markThreads(Message[] src) {
		Message[] retVal = new Message[src.length];

		try {
			// Build a Map of Ids and replies
			HashMap ids = new HashMap();
			for (int i = 0; i < src.length; i++) {
				if (src[i].getHeader("In-Reply-To") != null) {
					String val[] = (String[]) src[i].getHeader("Message-ID");
					String val2[] = (String[]) src[i].getHeader("In-Reply-To");
					ids.put(val[0], val2[0]);
				} else {
					String val[] = (String[]) src[i].getHeader("Message-ID");
					ids.put(val[0], "");
				}
			}
			int j = 0;
			for (int i = 0; i < src.length; i++) {
				String val[] = (String[]) src[i].getHeader("Message-ID");
				if (ids.values().contains(val[0])) {
					retVal[j++] = getMessageData(src[i], true);
				} else {
					if (src[i].getHeader("In-Reply-To") != null) {
						String val2[] = (String[]) src[i]
								.getHeader("In-Reply-To");
						if (!ids.containsKey(val2[0])) {
							retVal[j++] = getMessageData(src[i], true);
						}
					} else {
						retVal[j++] = getMessageData(src[i], false);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return retVal;
	}

	public Message getMessage(int which) {
		return messages[which];
	}

	// ---------------------
	// Implementation of the TableModel methods
	// ---------------------

	public String getColumnName(int column) {
		return columnNames[column];
	}

	public Class getColumnClass(int column) {
		return columnTypes[column];
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		if (messages == null)
			return 0;

		return messages.length;
	}

	public Object getValueAt(int aRow, int aColumn) {
		switch (aColumn) {
		case 0: // date
		case 1: // From String[] what = getCachedData(aRow);
		case 2: // Subject
		case 3: // Subject
		case 4: // Subject
			String[] what = getCachedData(aRow);
			if (what != null) {
				return what[aColumn];
			} else {
				return "";
			}

		default:
			return "";
		}
	}

	protected static String[][] cached;

	protected Message getMessageData(Message m, boolean thread) {
		Message theData = m;
		try {
			if (thread) {
				String val[] = (String[]) m.getHeader("Message-ID");
				threadmarks.put(val[0], val[0]);
			}
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		return theData;
	}

	protected String[] getCachedData(int row) {
		if (cached[row] == null) {
			try {
				String[] theData = new String[5];
				Message m = messages[row];
				if (m != null) {
					String id = "";
					if (m.getHeader("Message-ID") != null) {
						String[] ids = m.getHeader("Message-ID");
						// ID
						id = ids[0];
						theData[4] = ids[0];
					} else {
						theData[4] = m.hashCode() + "";
						id = theData[4];
					}

					// Date
					if (threadmarks.keySet().contains(id)) {
						theData[0] = "*";
					} else {
						theData[0] = "";
					}

					// Date
					Date date = m.getSentDate();
					if (date == null) {
						theData[1] = "Unknown";
					} else {
						theData[1] = date.toString();
					}

					// From
					Address[] adds = m.getFrom();
					if (adds != null && adds.length != 0) {
						theData[2] = adds[0].toString();
					} else {
						theData[2] = "";
					}

					// Subject
					String subject = m.getSubject();
					if (subject != null) {
						theData[3] = subject;
					} else {
						theData[3] = "(No Subject)";
					}
				}
				cached[row] = theData;
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}

		return cached[row];
	}
}
