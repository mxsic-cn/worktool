package cn.mysic.bugs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Web
public class Bug implements Runnable {

	/*
	 * disallowListCacherobotURL RobotWebrobots.txt,
	 *  ,robots.txt: # robots.txt for
	 * http://somehost.com/ User-agent: * Disallow: /cgi-bin/ Disallow:
	 * /registration # /Disallow robots on registration page Disallow: /login
	 */

	private HashMap<String, ArrayList<String>> disallowListCache = new HashMap<String, ArrayList<String>>();
	ArrayList<String> errorList = new ArrayList<String>();// 
	ArrayList<String> result = new ArrayList<String>(); // 
	ArrayList<String> checklist = new ArrayList<String>();
	String startUrl;// 
	int maxUrl;// url
	String searchString;// ()
	boolean caseSensitive = false;// 
	boolean limitHost = false;// 
	int totcount = 0;

	private static boolean can_be_stopped = false;

	public Bug(String startUrl, int maxUrl, String searchString) {
		this.startUrl = startUrl;
		this.maxUrl = maxUrl;
		this.searchString = searchString;
	}

	public ArrayList<String> getResult() {
		return result;
	}

	public void run() {// 

		try {
			crawl(startUrl, maxUrl, searchString, limitHost, caseSensitive);
		} catch (Exception e) {
			//   Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("over");
	}

	// URL
	private URL verifyUrl(String url) {
		// HTTP URLs.
		if (!url.toLowerCase().startsWith("http://"))
			return null;

		URL verifiedUrl = null;
		if (url.matches(".*//.*//.*"))
			return null;
		try {
			verifiedUrl = new URL(url);
		} catch (Exception e) {
			return null;
		}

		return verifiedUrl;
	}

	// robotURL.
	private boolean isRobotAllowed(URL urlToCheck) {
		String host = urlToCheck.getHost().toLowerCase();// RUL
		// System.out.println("="+host);

		// URL
		ArrayList<String> disallowList = disallowListCache.get(host);

		// ,
		if (disallowList == null) {
			disallowList = new ArrayList<String>();
			try {
				URL robotsFileUrl = new URL("http://" + host + "/robots.txt");
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(robotsFileUrl.openStream()));

				// robot
				String line;
				while ((line = reader.readLine()) != null) {
					if (line.indexOf("Disallow:") == 0) {// "Disallow:"
						String disallowPath = line.substring("Disallow:"
								.length());// 

						// 
						int commentIndex = disallowPath.indexOf("#");
						if (commentIndex != -1) {
							disallowPath = disallowPath.substring(0,
									commentIndex);// 
						}

						disallowPath = disallowPath.trim();
						disallowList.add(disallowPath);
					}
				}

				// 
				disallowListCache.put(host, disallowList);
			} catch (Exception e) {
				return true; // webrobots.txt,
			}
		}

		String file = urlToCheck.getFile();
		// System.out.println("getFile()="+file);
		for (int i = 0; i < disallowList.size(); i++) {
			String disallow = disallowList.get(i);
			if (file.startsWith(disallow)) {
				return false;
			}
		}

		return true;
	}

	private String downloadPage(URL pageUrl) {
		try {
			// Open connection to URL for reading.
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					pageUrl.openStream()));

			// Read page into buffer.
			String line;
			StringBuffer pageBuffer = new StringBuffer();

			// int count=1;
			while ((line = reader.readLine()) != null) {

				pageBuffer.append(line);
			}

			return pageBuffer.toString();
		} catch (Exception e) {
		}

		return null;
	}

	private String DownloadPage(URL pageUrl) {
		// String page=null;
		final URL thePage = pageUrl;
		final ExecutorService exec = Executors.newFixedThreadPool(1);
		Callable<String> call = new Callable<String>() {
			public String call() throws Exception {

				return downloadPage(thePage);
			}
		};

		try {
			Future<String> future = exec.submit(call);
			// set db connection timeout to 10 seconds
			String obj = future.get(1000 * 30, TimeUnit.MILLISECONDS);
			return obj;

		} catch (TimeoutException ex) {
			System.out
					.println("====================task time out===============");
			ex.printStackTrace();

		} catch (Exception e) {
			System.out.println("failed to handle.");
			e.printStackTrace();

		}
		// close thread pool
		exec.shutdown();

		return null;

	}

	// URL"www"
	private String removeWwwFromUrl(String url) {
		int index = url.indexOf("://www.");
		if (index != -1) {
			return url.substring(0, index + 3) + url.substring(index + 7);
		}

		return (url);
	}

	// g
	private ArrayList<String> retrieveLinks(URL pageUrl, String pageContents,
			HashSet crawledList, boolean limitHost, HashSet toCrawlList)
			throws Exception {
		// 
		Pattern p = Pattern.compile("]", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(pageContents);

		ArrayList<String> linkList = new ArrayList<String>();

		while (m.find()) {
//			if(m.group())
			String link = m.group(0).trim();

			if (link.length() < 1) {
				continue;
			}

			// 
			if (link.charAt(0) == '#') {
				continue;
			}

			if (link.indexOf("mailto:") != -1) {
				continue;
			}

			if (link.toLowerCase().indexOf("javascript") != -1) {
				continue;
			}

			if (link.indexOf("://") == -1) {
				continue;
				/*
				 * if (link.charAt(0) == '/') {// link = "http://" +
				 * pageUrl.getHost()+":"+pageUrl.getPort()+ link; } else {
				 * String file = pageUrl.getFile(); if (file.indexOf('/') == -1)
				 * {// link = "http://" +
				 * pageUrl.getHost()+":"+pageUrl.getPort() + "/" + link; } else
				 * { String path =file.subString(0, file.lastIndexOf('/') + 1);
				 * link = "http://" + pageUrl.getHost() +":"+pageUrl.getPort()+
				 * path + link; } }
				 */
				// link=link+"-----------";
			}

			int index = link.indexOf('#');
			if (index != -1) {
				link = link.substring(0, index);
			}

			link = removeWwwFromUrl(link);

			URL verifiedLink = verifyUrl(link);
			if (verifiedLink == null) {
				continue;
			}

			/* URL */
			if (limitHost
					&& !pageUrl.getHost().toLowerCase()
							.equals(verifiedLink.getHost().toLowerCase())) {
				continue;
			}

			// 
			link = SimpleUrl(link);

			// .
			if (crawledList.contains(link)) {
				continue;
			}

			if (toCrawlList.contains(link)) {
				continue;
			}

			linkList.add(link);
		}

		return (linkList);
	}

	// Web

	private boolean searchStringMatches(String pageContents,
			String searchString, boolean caseSensitive) {
		return true;
		/*
		 * String searchContents = pageContents; if (!caseSensitive) {//
		 * searchContents = pageContents.toLowerCase(); }
		 * 
		 * 
		 * Pattern p = Pattern.compile("[\\s]+"); String[] terms =
		 * p.split(searchString); for (int i = 0; i < terms.length; i++) { if
		 * (caseSensitive) { if (searchContents.indexOf(terms[i]) == -1) {
		 * return false; } } else { if
		 * (searchContents.indexOf(terms[i].toLowerCase()) == -1) { return
		 * false; } } }
		 * 
		 * return true;
		 */
	}

	// 
	public ArrayList<String> crawl(String startUrl, int maxUrls,
			String searchString, boolean limithost, boolean caseSensitive)
			throws Exception {

		System.out.println("searchString=" + searchString);
		HashSet<String> crawledList = new HashSet<String>();
		LinkedHashSet<String> toCrawlList = new LinkedHashSet<String>();

		if (maxUrls < 1) {
			errorList.add("Invalid Max URLs value.");
			System.out.println("Invalid Max URLs value.");
		}

		if (searchString.length() < 1) {
			errorList.add("Missing Search String.");
			System.out.println("Missing search String");
		}

		if (errorList.size() > 0) {
			System.out.println("err!!!");
			return errorList;
		}

		// URLwww
		startUrl = removeWwwFromUrl(startUrl);

		toCrawlList.add(startUrl);
		while (toCrawlList.size() > 0) {

			if (maxUrls != -1) {
				if (crawledList.size() == maxUrls) {
					break;
				}
			}

			// Get URL at bottom of the list.
			String url = toCrawlList.iterator().next();

			// Remove URL from the to crawl list.
			toCrawlList.remove(url);

			// Convert String url to URL object.
			URL verifiedUrl = verifyUrl(url);

			// Skip URL if robots are not allowed to access it.
			if (!isRobotAllowed(verifiedUrl)) {
				continue;
			}

			// URLcrawledList
			crawledList.add(url);

			if (!can_be_stopped) {

				System.out.println("--downloadPage..");
				String pageContents = DownloadPage(verifiedUrl);

				if (pageContents != null && pageContents.length() > 0) {
					// 
					System.out.println("--retrieveLinks..");
					ArrayList<String> links = retrieveLinks(verifiedUrl,
							pageContents, crawledList, limitHost, toCrawlList);

					// toCrawlList
					// if(toCrawlList.size()<100000)

					if (!links.isEmpty())
						toCrawlList.addAll(links);

					// toCrawlListurl
					if (toCrawlList.size() > 10000)
						can_be_stopped = true;
				}
			}

			System.out.println("toCrawList:" + toCrawlList.size());
			// if (searchStringMatches(pageContents, searchString,
			// caseSensitive)) {
			result.add(url);
			System.out.println("crawledList:" + crawledList.size());
			System.out.println(url);

			File file = new File("E:/load_from_net/url3.txt");
			BufferedWriter bw = null;
			try {
				bw = new BufferedWriter(new FileWriter(file, true));
				bw.write(url + "\r\n");
				bw.close();
			} catch (IOException e) {
				// Auto-generated catch block
				e.printStackTrace();
			}
			// }

		}
		return result;
	}

	String SimpleUrl(String url) throws Exception {

		URL uri = new URL(url);

		String result = "http://" + uri.getHost() + uri.getPath();

		return result;

	}

	// 
	public static void main(String[] args) {

		can_be_stopped = false;

		if (args.length != 3) {
			System.out
					.println("Usage:java SearchCrawler startUrl maxUrl searchString");

		}
		String[] paramers = new String[3];
		paramers[0] = "https://www.baidu.com";
		paramers[1] = "10000";
		paramers[2] = "java";
		int max = Integer.parseInt(paramers[1]);
		Bug crawler = new Bug(paramers[0], max, paramers[2]);
		Thread search = new Thread(crawler);
		System.out.println("Start searching...");
		System.out.println("result:");

		search.start();

	}
}
