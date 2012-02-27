package tmplj;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLClassLoader;

import tmplj.handler.TmplHandler;
import tmplj.parser.Parser;

public class Loader {
	public static Template fromFile(String path, String charset, String destdir) throws Exception {
		FileInputStream fis = new FileInputStream(path);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[4096];
		int len;
		while ((len = fis.read(buffer)) != -1) {
			baos.write(buffer, 0, len);
		}
		fis.close();
		String template = baos.toString(charset);
		return fromString(template, destdir);
	}
	
	public static String toJavaSource(String template) {
		TmplHandler handler = new TmplHandler();
		Parser parser = new Parser(template);
		parser.parse(handler);
		return handler.out.toString();
	}
	
	public static Template fromString(String template, String destdir) throws Exception {
		String javaSource = toJavaSource(template);
		String outdir = destdir;
		if (destdir == null) {
			outdir = System.getProperty("java.io.tmpdir");
		}
		outdir = new File(outdir).getCanonicalPath();
		File file = File.createTempFile("tmpl", ".java", new File(outdir));
		String className = file.getCanonicalPath()
								.substring(
										outdir.length() + File.separator.length()
										, file.getCanonicalPath().length() - 5);
		javaSource = javaSource.replaceFirst("<TEMP>", className);
		FileWriter writer = new FileWriter(file);
		writer.write(javaSource);
		writer.close();
		PrintWriter pw = new PrintWriter(System.out);
		int res = com.sun.tools.javac.Main.compile(new String[] { file.getCanonicalPath() }, pw);
		if (res != 0) {
			System.err.println("SOURCE\n"+javaSource+"\n");
			throw new Exception("Compilation failed.");
		}
		URL fileUrl = new URL("file://"+ outdir + File.separator);
		return (Template)
				URLClassLoader
				.newInstance(new URL[] {fileUrl})
				.loadClass(className)
				.newInstance();
	}
}
