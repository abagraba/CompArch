package Parsing;

import java.util.LinkedList;



public class LogEntry {

	private LinkedList<String>		log		= new LinkedList<String>();
	private LinkedList<LogEntry>	entries	= new LinkedList<LogEntry>();

	public LogEntry(String... logs) {
		for (String string : logs)
			log.add(string);
	}

	public static LogEntry log(String text) {
		LogEntry le = new LogEntry();
		le.addEntry(text);
		return le;
	}

	public void printLog(int indent) {
		for (String l : log) {
			String s = "";
			for (int i = 0; i < indent; i++)
				s += '\t';
			s += l;
			System.out.println(s);
		}
		for (LogEntry entry : entries)
			if (entry != null)
				entry.printLog(indent + 1);
	}

	public void addEntry(String text) {
		log.add(text);
	}

	public void addSubEntry(Object obj) {
		LogEntry entry = (LogEntry) obj;
		entries.add(entry);
	}

	public void addSubEntries(Object obj) {
		LogEntry entries = (LogEntry) obj;
		this.entries.addAll(entries.entries);
	}

}
