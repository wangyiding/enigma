package test.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.client.ClientProtocolException;
import org.eding.core.common.UserInfo;



public class HttpTest {
	public static void main(String[] args) throws ClientProtocolException, IOException {
	
//			RestClient client = com.xiaozhu.http.RestClient.getDefault();
//			Map header=new HashMap<String, Object>();
//			header.put("content-type", "application/x-www-form-urlencoded");
//			List param=new ArrayList();
//			Map m1=new HashMap();
//			m1.put("abcd", "123");
//			client.post("http://www.dianwandashi.com/testservlet/Test", header,m1);
			
			
			HttpClient client = new HttpClient(); 
	        PostMethod post = new PostMethod("http://www.dianwandashi.com/sso/authoBean.login.sso"); 
	        post.setRequestHeader("content-type", "application/x-www-form-urlencoded"); 
	        Map paramMap=new HashMap();
	        paramMap.put("id", ""+12);
	        NameValuePair[] pare = new NameValuePair[]{new NameValuePair("params",(JSONObject.fromObject(paramMap)).toString())}; 
	        post.setRequestBody(pare); 
	        client.executeMethod(post); 
	        System.out.println(post.getResponseBodyAsString());
	}
}
