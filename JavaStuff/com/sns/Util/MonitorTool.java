package com.sns.Util;

import java.util.Properties;
import java.net.InetSocketAddress;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Iterator;
import java.net.*;
import java.io.*;

public class MonitorTool {
	private Properties sitesList;

	private ArrayList siteValues;

	public ArrayList getSites() {
		return siteValues;
	}

	public void loadSites() {
		try {
			sitesList = new Properties();
			sitesList.load(new FileInputStream("Monitor.sites"));

			// Load the site names
			siteValues = new ArrayList();
			StringTokenizer st = new StringTokenizer(sitesList
					.getProperty("sitevalues"), ",");
			while (st.hasMoreTokens()) {
				siteValues.add(st.nextToken());
			}
		} catch (Exception e) {
			System.out.println("Error loading Monitor.sites file: "
					+ e.getMessage());
		}
	}

	public String getSiteValue(String siteName) {
		return sitesList.getProperty(siteName);
	}

	public String getSiteAddress(String siteName) {
		return sitesList.getProperty(siteName + "addr");
	}

	public String getSiteName(String siteName) {
		return sitesList.getProperty(siteName + "name");
	}

	public String getSiteType(String siteName) {
		return sitesList.getProperty(siteName + "type");
	}

	public boolean getSiteStatus(String siteName) {
		boolean retVal = false;

		String siteAddress = getSiteValue(siteName);
		InetSocketAddress address;
		try {
			String siteType = getSiteType(siteName);
			if (siteType.equals("A")) {
				address = new InetSocketAddress(InetAddress
						.getByName(siteAddress), 25);
				address.getHostName();
				retVal = true;
			}

			if (siteType.equals("W")) {
			    try {
			        URL url = new URL(getSiteValue(siteName));
			    
			        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			        // If we can read a line it is there.
			        if (in.readLine() != null) {
						retVal = true;
			        }
			        in.close();
			    } catch (MalformedURLException e) {
					System.out.println("Error in URL: " + siteName + " -->"
							+ e.getMessage());
					retVal = false;
			    } catch (IOException e) {
					System.out.println("Error in reading page: " + siteName + " -->"
							+ e.getMessage());
					retVal = false;
			    }
			}
		} catch (Exception e) {
			System.out.println("Error pinging site: " + siteName + " -->"
					+ e.getMessage());
		}

		return retVal;
	}

	public boolean verifySiteAddress(String siteName) {
		boolean retVal = false;

		try {
			String siteType = getSiteType(siteName);
			if (siteType.equals("A")) {
				retVal = InetAddress.getByName(getSiteValue(siteName))
						.getHostAddress().equals(getSiteAddress(siteName));
			}
			if (siteType.equals("W")) {
				StringTokenizer st = new StringTokenizer(
						getSiteAddress(siteName), ":");

				// Handle http:// in address
				if (st.countTokens() == 3) {
					st.nextToken();
				}
				String mainAddr = st.nextToken();
				retVal = InetAddress.getByName(getSiteValue(mainAddr))
				.getHostAddress().equals(getSiteAddress(mainAddr));
			}
		} catch (Exception e) {
			System.out.println("Error verifying site: " + siteName + " -->"
					+ e.getMessage());
		}
		return retVal;
	}

	public static void main(String args[]) {
		/* Create a file */
		MonitorTool util = new MonitorTool();
		util.loadSites();

		Iterator it = util.getSites().iterator();

		// Simple ping of the site
		while (it.hasNext()) {
			String curItem = (String) it.next();
			try {
				if (util.getSiteStatus(curItem)) {
					System.out.println(util.getSiteValue(curItem)
							+ " is up at "
							+ InetAddress.getByName(util.getSiteValue(curItem))
									.getHostAddress());
				} else {
					System.out.println(util.getSiteName(curItem) + " is down");
				}
			} catch (Exception e) {
				System.out.println("Error grabbing site" + e.getMessage());
			}

			// Make sure the address matches the one we think it does.
			if (util.verifySiteAddress(curItem)) {
				System.out.println(util.getSiteName(curItem)
						+ " is using a valid address");
			} else {
				System.out.println(util.getSiteName(curItem)
						+ " is using an invalid address");
			}
		}

	}
}
