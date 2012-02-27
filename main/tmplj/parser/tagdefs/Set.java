package tmplj.parser.tagdefs;

import java.util.Map;

import tmplj.parser.TagDef;

public class Set extends TagDef {
	public Set() {
		super("set", new String[] {"name", "expr"});
	}
	@Override
	public void startTag(StringBuilder out, Map<String, String> attributes) {
		out.append(attributes.get("name")+" = ("+attributes.get("expr") + ");");
	}
	@Override
	public void endTag(StringBuilder out) {}
}
