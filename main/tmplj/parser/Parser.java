package tmplj.parser;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
	private String input;
	private int p, last;
	public Parser(String input) {
		this.input = input;
	}
	
	private void parseCharacters(Handler handler) {
		if (p > last) {
			String characters = input.substring(last, p);
			handler.characters(characters);
			last = p;
		}
	}
	
	private void parseDoctype(String tag, int end, Handler handler) {
		parseCharacters(handler);
		String doctype = tag;
		handler.doctype(doctype);
		last = end;
		p = last - 1;
	}
	
	private void parseComment(String tag, int end, Handler handler) {
		end = input.indexOf(END_COMMENT, p);
		if (end == -1) {
			parseCharacters(handler);
			String comment = tag.substring(p + COMMENT.length(), input.length());
			handler.comment(comment);
			last = input.length();
			p = last;
			return;
		}
		parseCharacters(handler);
		String comment = input.substring(p + COMMENT.length(), end);
		handler.comment(comment);
		last = end + END_COMMENT.length();
		p = last;
	}
	
	private static String ws = "[\\n\\r\\t\\s]+";
	private static String id = "[a-zA-Z]([a-zA-Z0-9:]*[a-zA-Z0-9])?";
	private static Pattern id_p = Pattern.compile(id);
	private static String attrv = "(\"[^\"]*\")|('[^']*')";
	private static Pattern attr_pair_p = Pattern.compile("("+id+")=("+attrv+")");
	private static Pattern tag_p = Pattern.compile("<(" + id + ")("+ws+"("+id+")=("+attrv+"))*/?>", Pattern.DOTALL);
	
	
	private boolean verifyTag(String tag) {
		return tag_p.matcher(tag).matches();
	}
	
	private void parseTag(String tag, int end, Handler handler) {
		Matcher tagName_m = id_p.matcher(tag);
		tagName_m.find();
		String tagName = tagName_m.group();
		
		if (!handler.accept(tagName)) {
			return;
		}
		
		Map<String, String> attributes = new HashMap<>();
		Matcher attr_pair_m = attr_pair_p.matcher(tag);
		while (attr_pair_m.find()) {
			String attrName = attr_pair_m.group(1);
			String attrValue = attr_pair_m.group(3);
			attrValue = attrValue.substring(1, attrValue.length() - 1);
			attributes.put(attrName, attrValue);
		}
		parseCharacters(handler);
		boolean empty = tag.charAt(tag.length()-2) == '/';
		handler.startTag(tagName, attributes, empty);
		last = end;
		p = last - 1;
	}
	
	private void parseEndTag(String tag, int end, Handler handler) {
		String tagName = tag.substring(2, tag.length() - 1);
		if (id_p.matcher(tagName).matches()) {
			parseCharacters(handler);
			if (!handler.accept(tagName)) {
				return;
			}
			handler.endTag(tagName);
			last = end;
			p = last;
		}
	}
	
	public void parse(Handler handler) {
		handler.startDocument();
		p = -1;
		last = 0;
		while (++p < input.length()) {
			if (input.charAt(p) == '<') {
				int end = input.indexOf('>', p);
				if (end == -1) {
					break;
				}
				end++;
				String tag = input.substring(p, end);
				if (tag.startsWith(DOCTYPE)) {
					parseDoctype(tag, end, handler);
				} else if (tag.startsWith(COMMENT)) {
					parseComment(tag, end, handler);
				} else if (tag.startsWith(END_TAG)) {
					parseEndTag(tag, end, handler);
				} else {
					boolean verified = verifyTag(tag);
					if (verified) {
						parseTag(tag, end, handler);
					}
				}
			}
		}
		if (last != input.length()) {
			String characters = input.substring(last);
			handler.characters(characters);
		}
		handler.endDocument();
	}
	
	private static final String DOCTYPE = "<!DOCTYPE ";
	private static final String COMMENT = "<!--";
	private static final String END_COMMENT = "-->";
	private static final String END_TAG = "</";
}
