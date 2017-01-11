package test.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.springframework.util.ResourceUtils;

public class Test {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		Properties properties=new Properties();
		properties.load(new FileReader(ResourceUtils.getFile("classpath:altconfig.properties")));
		System.setProperty("suffix", properties.getProperty("suffix"));
	}
}
