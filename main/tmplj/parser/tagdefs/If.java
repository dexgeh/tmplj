package tmplj.parser.tagdefs;

import java.util.Map;

import tmplj.parser.TagDef;

public class If extends TagDef {
	public If() {
		super("if", new String[] {"expr"});
	}
	@Override
	public void startTag(StringBuilder out, Map<String, String> attributes) {
		out.append("if ("+attributes.get("expr")+") {");
	}
	@Override
	public void endTag(StringBuilder out) {
		out.append("}");
	}
}
