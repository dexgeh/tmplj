package tmplj.handler;

import java.util.LinkedList;
import java.util.Map;

import tmplj.parser.Handler;
import tmplj.parser.TagDef;
import tmplj.parser.tagdefs.Do;
import tmplj.parser.tagdefs.Else;
import tmplj.parser.tagdefs.Elseif;
import tmplj.parser.tagdefs.For;
import tmplj.parser.tagdefs.If;
import tmplj.parser.tagdefs.Import;
import tmplj.parser.tagdefs.Print;
import tmplj.parser.tagdefs.Set;
import tmplj.parser.tagdefs.Var;
import tmplj.util.Strings;

public class TmplHandler implements Handler {
	
	private static String head =
		"public class <TEMP> implements tmplj.Template {"+
		"public String render(Object locals) throws Exception {" +
		"StringBuilder out = new StringBuilder();";
	
	private static String tail = "return out.toString();}}";
	
	@SuppressWarnings("rawtypes")
	private static Class[] defaultSupported = new Class[] {
		Import.class,
		Print.class,
		Var.class,
		If.class,
		Elseif.class,
		Else.class,
		For.class,
		Set.class,
		Do.class
	};
	
	@SuppressWarnings("unchecked")
	public TmplHandler() {
		for (@SuppressWarnings("rawtypes") Class c : defaultSupported) {
			try {
				tagDefs.add((TagDef) c.getConstructor().newInstance());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	public LinkedList<TagDef> tagDefs = new LinkedList<>();
	public StringBuilder out = new StringBuilder();
	public boolean keepComments = false;
	
	@Override
	public void startDocument() {
		out.setLength(0);
		out.append(head);
	}
	@Override
	public void endDocument() {
		out.append(tail);
	}

	@Override
	public void characters(String chars) {
		out.append("out.append(\""+Strings.toJavaString(chars)+"\");\n");
	}

	@Override
	public void doctype(String doctype) {
		characters(doctype);
	}

	@Override
	public void comment(String comment) {
		if (keepComments)
			characters("<!-- " + comment + " -->");
	}

	@Override
	public boolean accept(String tagName) {
		for (TagDef tagDef: tagDefs) {
			if (tagDef.tagName.equals(tagName)) return true;
		}
		return false;
	}

	@Override
	public void startTag(String tagName, Map<String, String> attributes, boolean empty) {
		for (TagDef tagDef: tagDefs) {
			if (tagDef.tagName.equals(tagName)) {
				for (String attrName : tagDef.requiredAttributes) {
					if (!attributes.containsKey(attrName)) {
						throw new RuntimeException("Required attribute "+attrName+" for tag "+tagName+" not found");
					}
				}
				tagDef.startTag(out, attributes);
				out.append("\n");
				break;
			}
		}
	}

	@Override
	public void endTag(String tagName) {
		for (TagDef tagDef: tagDefs) {
			if (tagDef.tagName.equals(tagName)) {
				tagDef.endTag(out);
				break;
			}
		}
	}
}
