package tmplj.parser.tagdefs;

import java.util.Map;

import tmplj.parser.TagDef;

public class Elseif extends TagDef {
	public Elseif() {
		super("elseif", new String[] {"expr"});
	}
	@Override
	public void startTag(StringBuilder out, Map<String, String> attributes) {
		out.append("} else if (" + attributes.get("expr") + ") {");
	}
	@Override
	public void endTag(StringBuilder out) {
		out.append("}");
	}
}
