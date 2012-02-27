package tmplj.test;

import java.io.File;
import tmplj.Loader;
import tmplj.Template;


public class Main {
	public static void main(String[] args) throws Exception {
		System.out.println(new File(".").getCanonicalPath());
		Template template = Loader.fromFile("test/tmplj/test/test1.html", "UTF-8", null);
		ABean aBean = new ABean();
		aBean.setTitle("myTitle");
		System.out.println(template.render(aBean));
	}
}
