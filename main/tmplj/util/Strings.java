package tmplj.util;

public class Strings {
	public static String toJavaString(String input) {
		return
			input
			.replace("\\", "\\\\")	
			.replace("\n", "\\n")
			.replace("\f", "\\f")
			.replace("\r", "\\r")
			.replace("\t", "\\t")
			.replace("\b", "\\b")
			.replace("\"", "\\\"")
			.replace("\'", "\\'");
	}
	private static String[] escape_replacements =
			new String [] {"&lt;","&gt;","&apos;", "&quot;", "&amp;"};
	public static String escapeBasic(String input) {
		StringBuilder out = new StringBuilder();
		int len = input.length();
		for (int i=0;i<len;i++) {
			char c = input.charAt(i);
			switch (c) {
			case '<': out.append("&lt;");break;
			case '>': out.append("&gt;");break;
			case '"': out.append("&quot;");break;
			case '\'': out.append("&apos;");break;
			case '&': out.append("&amp;");break;
			default: out.append(c); break;
			}
		}
		return out.toString();
	}
}
