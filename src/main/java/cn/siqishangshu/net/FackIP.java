package cn.siqishangshu.net;

public class FackIP {
	
   
public static void main(String[] args) { 
//	FackIP.sendPost("www.baidu.com", headers, params, encoding, isProxy)
 

}


// public static Result sendPost(String url, Map<String, String> headers, Map<String, String> params, String encoding,boolean isProxy) throws ClientProtocolException, IOException {
//        // ʵ����һ��post����
//    HttpPost post = new HttpPost(url);
//    DefaultHttpClient client = new DefaultHttpClient();
//    if (isProxy) {//�Ƿ�������
//        HttpHost proxy = new HttpHost("221.181.192.30", 85);
//        client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
//    }
//    // ������Ҫ�ύ�Ĳ���
//    List<NameValuePair> list = new ArrayList<NameValuePair>();
//    if (params != null) {
//        for (String temp : params.keySet()) {
//            list.add(new BasicNameValuePair(temp, params.get(temp)));
//        }
//    }
//    post.setEntity(new UrlEncodedFormEntity(list, encoding));
//    // ����ͷ��
//    if (null != headers)
//        post.setHeaders(assemblyHeader(headers));
//    // ʵ�����󲢷���
//    HttpResponse response = client.execute(post);
//    HttpEntity entity = response.getEntity();
//    HttpEntity entityRsp = response.getEntity();
//    StringBuffer result1 = new StringBuffer();
//    BufferedReader rd = new BufferedReader(new InputStreamReader(entityRsp.getContent(), encoding));
//    String tempLine = rd.readLine();
//    // ��װ���صĲ���
//    Result result = new Result();
//    while (tempLine != null) {
//        // ���ػ�ȡ�����ַ
//        // if (tempLine.contains("encodeURI('")) {
//        // System.out.println("encode:" +
//        // tempLine.substring(tempLine.indexOf("encodeURI('") + 11,
//        // tempLine.indexOf("');")));
//        // }
//        result1.append(tempLine);
//        tempLine = rd.readLine();
//    } 
//    // ���÷���״̬����
//    result.setStatusCode(response.getStatusLine().getStatusCode());
//    // ���÷��ص�ͷ����Ϣ
//    result.setHeaders(response.getAllHeaders());
//    // ���÷��ص�cookie����
//    result.setCookie(assemblyCookie(client.getCookieStore().getCookies()));
//    // ���÷��ص���Ϣ
//    result.setHttpEntity(entity);
//    return result;
//}

} 
