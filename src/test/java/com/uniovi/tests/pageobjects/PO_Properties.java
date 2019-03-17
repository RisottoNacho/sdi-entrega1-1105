package com.uniovi.tests.pageobjects;

import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class PO_Properties {
	public static int getSPANISH() {
		return SPANISH;
	}

	public static int getENGLISH() {
		return ENGLISH;
	}

	static private String Path;
	static int SPANISH = 0;
	static int ENGLISH = 1;	
	static Locale[] idioms = new Locale[] {new Locale("ES"), new Locale("EN")};
	static Properties p = new Properties();
	public PO_Properties(String Path)
	{
		//this.Path = Path;
		/*try {
			p.load(new FileReader(Path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		//p.getProperty();
	}
	//
	// locale is de index in idioms array.
	//
    public String getString(String prop, int locale) {
		
		ResourceBundle bundle = ResourceBundle.getBundle(Path, idioms[locale]);
		String value = bundle.getString(prop);
		@SuppressWarnings("unused")
		String result;
		try {
			result = new String(value.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}

	
}
