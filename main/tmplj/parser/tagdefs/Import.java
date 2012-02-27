package tmplj.parser.tagdefs;

import java.util.Map;

import tmplj.parser.TagDef;

public class Import extends TagDef {
	
	public Import() {
		super("import", new String[] {"name"});
	}
	@Override
	public void startTag(StringBuilder out, Map<String, String> attributes) {
		out.insert(0, "import "+attributes.get("name")+";");
	}
	@Override
	public void endTag(StringBuilder out) {}
}
