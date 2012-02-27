package tmplj.parser;
import java.util.Map;

public interface Handler {
	public void startDocument();
	public void endDocument();
	
	public void characters(String chars);
	
	public void doctype(String doctype);
	public void comment(String comment);
	
	public boolean accept(String tagName);
	public void startTag(String tagName, Map<String, String> attributes, boolean empty);
	public void endTag(String tagName);
}
