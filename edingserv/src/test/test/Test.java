package test.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Properties;

import net.sf.json.JSONObject;

import org.eding.core.ContextTool;
import org.eding.core.CoreCipher;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
@Controller
public class Test {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		String s1="12345678";
		String s1enc=CoreCipher.encryptBasedDes(s1);
		System.out.println(s1enc);
		System.out.println(CoreCipher.decryptBasedDes(s1enc));
	}
}
