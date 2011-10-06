/**
 * 
 */
package com.jeffreyricker.osgi.builder.console;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author jricker
 *
 */
public class BuilderServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3737678529520270428L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp) {
		try {
			Writer out = resp.getWriter();
			out.write("<html><body><h1>Builder</h1>");
			out.write("<p>This is a placeholder for the builder console</p>");
			out.write("</body></html");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
