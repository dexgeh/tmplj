package tmplj.parser.tagdefs;

import java.util.Map;

import tmplj.parser.TagDef;

public class Do extends TagDef {
	public Do() {
		super("do", new String[] {"expr"});
	}
	@Override
	public void startTag(StringBuilder out, Map<String, String> attributes) {
		out.append(attributes.get("expr") + ";");
	}
	@Override
	public void endTag(StringBuilder out) {}
}
