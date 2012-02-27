package tmplj.parser;

import java.util.Map;

public abstract class TagDef {
	public String tagName;
	public String[] requiredAttributes;
	public TagDef(String tagName, String[] requiredAttributes) {
		this.tagName = tagName;
		this.requiredAttributes = requiredAttributes;
	}
	public abstract void startTag(StringBuilder out, Map<String, String> attributes);
	public abstract void endTag(StringBuilder out);
}
