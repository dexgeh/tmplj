package tmplj.parser.tagdefs;

import java.util.Map;

import tmplj.parser.TagDef;

public class Else extends TagDef {
	public Else() {
		super("else", new String[] {});
	}
	@Override
	public void startTag(StringBuilder out, Map<String, String> attributes) {
		out.append("} else {");
	}
	@Override
	public void endTag(StringBuilder out) {}
}
