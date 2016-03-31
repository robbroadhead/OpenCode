package eclipseMail;

import java.io.File;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Iterator;
import javax.mail.*;

public class MailTool {

	private Store theStore;

	public void initStore(String h, String u, String p) {
		Properties props = new Properties();
		props.put("mail.smtp.host", h);
		Session session = Session.getInstance(props);

		try {
			theStore = session.getStore("pop3");
			theStore.connect(h, u, p);
		} catch (Exception e) {
			System.out.println("Exception on connection:");
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public int getMessageCount() {
		int numMsgs = 0;

		try {
			Folder temp = theStore.getFolder("INBOX");
			temp.open(Folder.READ_WRITE);
			numMsgs = temp.getMessageCount();
			temp.close(true);
		} catch (Exception e) {
			// System.out.println("Exception on connection:");
			e.printStackTrace();
			System.exit(-1);
		}

		return numMsgs;
	}

	public ArrayList getMessageSubjects() {
		int numMsgs = getMessageCount();
		ArrayList retVal = new ArrayList();

		try {
			Folder temp = theStore.getFolder("INBOX");
			temp.open(Folder.READ_WRITE);
			for (int i = 1; i <= numMsgs; i++) {
				retVal.add(temp.getMessage(i).getSubject());
			}
			temp.close(true);
		} catch (Exception e) {
			// System.out.println("Exception on connection:");
			e.printStackTrace();
			System.exit(-1);
		}

		return retVal;
	}

	public String getMessageForSubject(String subject) {
		int numMsgs = getMessageCount();
		String retVal = "Not Found";

		try {
			Folder temp = theStore.getFolder("INBOX");
			temp.open(Folder.READ_WRITE);
			for (int i = 1; i <= numMsgs; i++) {
				if (temp.getMessage(i).getSubject().equals(subject)) {
					retVal = (String) temp.getMessage(i).getContent();
				}
			}
			temp.close(true);
		} catch (Exception e) {
			// System.out.println("Exception on connection:");
			e.printStackTrace();
			System.exit(-1);
		}

		return retVal;
	}

	public static void main(String args[]) {
		/* Create a file */
		MailTool util = new MailTool();

		/* Connect to server */
		util.initStore("pop.mindspring.com", "robbroadhead", "matso");

		/* Count the incoming messages */
		System.out.println("Unread Messages:" + util.getMessageCount());
		
		/* List the subjects */
		Iterator it =util.getMessageSubjects().iterator();
		while (it.hasNext()) {
			System.out.println("Subject:" + (String) it.next());
		}

		System.out.println("Program Completed Successfully.");
	}
}
