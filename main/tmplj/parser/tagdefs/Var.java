package tmplj.parser.tagdefs;

import java.util.Map;

import tmplj.parser.TagDef;

public class Var extends TagDef {
	public Var() {
		super("var", new String[] {"type", "name", "expr"});
	}
	@Override
	public void endTag(StringBuilder out) {}
	@Override
	public void startTag(StringBuilder out, Map<String, String> attributes) {
		out.append(attributes.get("type")+" "+attributes.get("name")+" = "+attributes.get("expr") + ";");
	}
}
