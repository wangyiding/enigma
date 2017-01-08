package org.eding.httpclient;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

public class ResponseHandlers {
	public static final ResponseHandler<Response> REST = new ResponseHandler<Response>() {
		@Override
		public Response handleResponse(HttpResponse response)
				throws ClientProtocolException, IOException {
			StatusLine status = response.getStatusLine();
			byte[] body = EntityUtils.toByteArray(response.getEntity());
			Response result = new Response(status.getStatusCode(), body);
			return result;
		}
	};
}
