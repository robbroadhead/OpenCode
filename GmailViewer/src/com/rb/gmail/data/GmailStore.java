package com.rb.gmail.data;

import java.util.*;
import javax.mail.*;
import javax.mail.search.*;
import javax.mail.internet.*;

public class GmailStore {
	private Store store;
	private Message[] msgs;
	public String[] GetEmails(String name,String pwd) {
		String[] retVal = null;
		Properties props = System.getProperties();
		props.setProperty("mail.store.protocol", "imaps");
		try {
		  Session session = Session.getDefaultInstance(props, null);
		  store = session.getStore("imaps");
		  store.connect("imap.gmail.com", name + "@gmail.com", pwd);
		  Folder folder = store.getDefaultFolder();
		  System.out.println("Retrieved Mail Store:" + folder.getURLName());
		  System.out.println("Retrieved Mail Store:" + folder.getType());
		  System.out.println("Retrieved Mail Store:" + folder.list().length);
		  Folder subs[] = folder.list();
		  retVal = new String[subs.length];
		  for (int i=0;i < subs.length;i++) {
			  if (subs[i].getType() != 2) {
				  retVal[i] = subs[i].getFullName();
			  }
		  }
		} catch (NoSuchProviderException e) {
		  e.printStackTrace();
		  System.exit(1);
		} catch (MessagingException e) {
		  e.printStackTrace();
		  System.exit(2);
		}
		
		return retVal;
	}
	
	public Folder getFolder(String name) {
		try {
			return store.getDefaultFolder().getFolder(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String getEmail(Message msg) {
		String retVal = "Invalid Message";
		
		try {
			MimeMultipart m = (MimeMultipart) msg.getContent();
			retVal = "";
			for (int i = 0;i < m.getCount();i++) {
				System.out.println(m.getBodyPart(i).getContentType().toString());
				// Only use the plain/text part of a message.
				if (m.getBodyPart(i).getContentType().contains("TEXT/PLAIN")) {
					retVal += m.getBodyPart(i).getContent().toString();
				} else if (m.getBodyPart(i).getContentType().contains("TEXT/HTML")) {
					retVal += m.getBodyPart(i).getContent().toString();					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();			
		}
		return retVal;
	}
	
	public String[] GetMsgs(String name) {
		String[] retVal = null;
		try {
		  Folder folder = store.getDefaultFolder();
		  Folder current = folder.getFolder(name);
		  current.open(Folder.READ_WRITE);
		  msgs = current.getMessages();
		  HashMap titles = new HashMap();
		  HashMap ids = new HashMap();
		  for (int i=0;i < msgs.length;i++) {
			  if (msgs[i].getHeader("In-Reply-To") != null) {
				  String val[] = (String[]) msgs[i].getHeader("Message-ID");
				  String val2[] = (String[]) msgs[i].getHeader("In-Reply-To");
				  ids.put(val[0],val2[0]);
			  } else {
				  String val[] = (String[]) msgs[i].getHeader("Message-ID");
				  ids.put(val[0], "");				  
			  }
		  }
		  for (int i=0;i < msgs.length;i++) {
			  String val[] = (String[]) msgs[i].getHeader("Message-ID");
			  if (ids.values().contains(val[0])) {
				  titles.put(msgs[i].getSubject(),"1");
			  } else {
				  if (msgs[i].getHeader("In-Reply-To") != null) {
					  String val2[] = (String[]) msgs[i].getHeader("In-Reply-To");
					  if (!ids.containsKey(val2[0])) {
						  titles.put(msgs[i].getSubject(),"0");
					  }
				  }
			  }
		  }
		  
		  Set keys = titles.keySet();
		  Iterator it = titles.keySet().iterator();
		  retVal = new String[keys.size()];
		  int idx = 0;
		  while (it.hasNext()) {
			  String curTitle = it.next().toString();
			  if (titles.get(curTitle).equals("1")) {
				  retVal[idx++] = "[Thread] " + curTitle;				  
			  } else {
				  retVal[idx++] = curTitle;
			  }
		  }
		} catch (NoSuchProviderException e) {
		  e.printStackTrace();
		  System.exit(1);
		} catch (MessagingException e) {
		  e.printStackTrace();
		  System.exit(2);
		}
		
		return retVal;
	}

	public String[] GetMsgs(String name,String srchString) {
		String[] retVal = null;
		try {
		  Folder folder = store.getDefaultFolder();
		  Folder current = folder.getFolder(name);
		  current.open(Folder.READ_WRITE);
		  SearchTerm st = new AndTerm(
				   new SubjectTerm(srchString), 
				   new BodyTerm(srchString));
		  msgs = current.search(st);
		  retVal = new String[msgs.length];
		  for (int i=0;i < msgs.length;i++) {
			  retVal[i] = msgs[i].getSubject();
		  }
		} catch (NoSuchProviderException e) {
		  e.printStackTrace();
		  System.exit(1);
		} catch (MessagingException e) {
		  e.printStackTrace();
		  System.exit(2);
		}
		
		return retVal;
	}
}