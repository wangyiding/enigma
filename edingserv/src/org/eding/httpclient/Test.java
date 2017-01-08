package org.eding.httpclient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

public class Test {
	public static void main(String[] args) throws ClientProtocolException, IOException {
		RestClient rc=new RestClient(new HTTP(new HTTPConfig()));
		Map params=new HashMap();
		params.put("name", "马云");
		Response res = rc.post("http://127.0.0.1:8080/eds/edingbean.test.c", params);
		System.out.println(res.getBody());
	}
}
