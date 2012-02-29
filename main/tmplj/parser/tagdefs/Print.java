package tmplj.parser.tagdefs;

import java.util.Map;

import tmplj.parser.TagDef;

public class Print extends TagDef {
	public Print() {
		super("print", new String[] {"expr"});
	}
	@Override
	public void startTag(StringBuilder out, Map<String, String> attributes) {
		String escape = attributes.get("escape");
		if (escape == null || "basic-xml".equalsIgnoreCase(escape)) {
			out.append("out.append(tmplj.util.Strings.escapeBasic(\"\" + "+attributes.get("expr")+"));");
		} else {
			out.append("out.append("+attributes.get("expr")+");");
		}
	}
	@Override
	public void endTag(StringBuilder out) {}
}
