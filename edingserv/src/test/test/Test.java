package test.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import net.sf.json.JSONObject;

import org.eding.core.ContextTool;
import org.springframework.util.ResourceUtils;

public class Test {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		JSONObject ob1 = JSONObject.fromObject("{sss:1}");
		System.out.println(ob1.get("sss"));
	}
}
