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
		StringBuilder out = new StringBuilder(input);
		int[] I = new int[5];
		do {
			I[0] = I[0] == -1 ? I[0] : input.indexOf('<');
			I[1] = I[1] == -1 ? I[1] : input.indexOf('>');
			I[2] = I[2] == -1 ? I[2] : input.indexOf('\'');
			I[3] = I[3] == -1 ? I[3] : input.indexOf('\"');
			I[4] = I[4] == -1 ? I[4] : input.indexOf('&');
			for (int i=0;i<I.length;i++) {
				if (I[i] != -1) {
					out.replace(I[i], I[i]+1, escape_replacements[i]);
				}
			}
		} while (
				   I[0] != -1
				|| I[1] != -1
				|| I[2] != -1
				|| I[3] != -1
				|| I[4] != -1);
		return out.toString();
	}
}
