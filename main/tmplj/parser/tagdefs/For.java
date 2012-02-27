package tmplj.parser.tagdefs;

import java.util.Map;

import tmplj.parser.TagDef;

public class For extends TagDef {
	public For() {
		super("for", new String[] {"expr"});
	}
	@Override
	public void startTag(StringBuilder out, Map<String, String> attributes) {
		out.append("for ("+attributes.get("expr")+") {");
	}
	@Override
	public void endTag(StringBuilder out) {
		out.append("}");
	}
}
