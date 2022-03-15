package com.alipay.config;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;


public class AlipayConfig {

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id ="2016091300503049";
	
	public static String sys_service_provider_id="2088102175471198";
	@SuppressWarnings("deprecation")
	public static String Url ="https://openauth.alipaydev.com/oauth2/appToAppAuth.htm?app_id="+app_id+"&redirect_uri="+URLEncoder.encode("http://localhost:8080/QYY/index.jsp");
	public static String app_auth_code="202003BBa83f69e09c2a418e9f8360e5c7c25A96";
	//202003BBaef3e221471c47b08bf27221847ecB19
	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCNGgkvlNFx2vmURVRbkj0bK1qwMcg4ob3BYgDYQlOVZOQ+YgFCv0QCXxcs6/fQCWx9bIyqtufDpmw9HXMohmOuoTiX6/YxbjNtkoNjVE+XjYejVTXUbBL3X8NLVxFLCAuX0FGRT2I+tMngrJ3Q5V2K3cpKqiiZMHfHh3NUOzhXK6Ph+4iVlpdHBjOtfMFaJ6j8m9A8uU0WPPhHQ7GknVugiWmL18c1eXVIVoFFisbGt2F0Ha4IxCCKZvUSbMt6OM/wVjcarv0wafYgwyRBzXM0yPyLkY+Eatvby3j1zi5CgYwEzR1XuLN4OsW2OnF6hiJmPO2f9NeXoFl/scRLEuYHAgMBAAECggEAU2hUjb/WnwrmBXOGDis/29Og5A5JYNCbLrkT7sImTmU5Vnbwb9hIB27WcEN8VYl8jBeifzdngQvbZ7UE4auvOQcsQQHGoimWVOsKp6iq89dBmpdAHrVT8M62zs5E9qlm0PLfhJn8ZTzRKPJgx58N0IySi9hR71vrMhMrZVssTrokSWAu1anHlw7I9GKs3F5BOZNGkl0SPBeF6Vf2u313QL5GVJ5+nsi/V5H+fapeCct/dvyZ9yiaE/B3oQ9IpY3W1Bw9otqCcdbQTaSF4UD/2hJQ7CsAhOJye0HdVyEbXdgtucld7bxJdHW1TTQpPtWRVr7oa8F+bH6WhoDFZgSTQQKBgQDm7Bxujd1rFwGFfAl4/I0stHNKLQ87QUXIBkZWFKRCfQ0on+7US0Q+ge615GH/golLwBgzXJWWGMt6SAN40QofIhvda8/1D8ZyucUnGbiQIHPCmSUqb4JJ0o3oWOXL6pl4IKRNhkrh3udZ915rYbuXgBvh6K2OVtCZCjZJdZCqOQKBgQCcbNCxy0tWNb0MRboCFm0d1tabpVQHCimwIRK2+FPnD4TFYfqjKMBrqiPgGt6H9YhlkPHi5eTJHKmY93W1YZYedi/fGDRLebwbn/LxdivFN3lXCGp18knfhnnakJm0Q9jhvE4Va86sWbVDSM07VrsNSf1riwSAv6DrbmBp2IkSPwKBgQDQYPILJUNP9rOtw888eRHf7Ho4zk53VyBkLp1b4kaTpQqxxRicQ8syaMeDJbL1AdNpIs/z0TE+/+Ll1oQjiucKTxo8BaTj7DdUoaRx8zjj/hTX3qB2GafFfBYIO+gpyepxR2tcdbmu9xlnytxOJCRGwxjmE7RBxL95yohQY6GGqQKBgFa190XnhJ6IcYBhEohfuCMHWbW0Aw1X893/p2kn5RK7xoTIGHDjgS7thK+RFXRWhkKPA5cciyN0sQrreiOOsfG24DiSKUMBe2Xw13JEaGUNqES3lJRHN+4hlGC1MY784TfY84vqVAm0PMfMjn/rRwtJzaIU0O0xzNlVOTrTXsBvAoGAWQj6lEUpOGMLbqLZV79sWMcqVMunj87D6alDKiyvqYsvtu3CEYfwGKwMtz79QCR3vgBaC2nOj2omrN/9t6XjDHrsELQmxNGeR08IrkJGIyZdLTUZO6HaaeMCxsQHj/JSWwD8zmlk7uUs+9HGw5nBwAOUVkrZepBCLC00eQuJaeg=";
    
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwPWkomjRZVmGThFsWR7IInbTFYvAijWu1RJREin7VP4sbF6rqVnfRiGJll6uWYoYjdch8MUyEVGgdUC4f59OIkJiCydMnS60oBIby/872OXmO5eOKMhjF8XIuH2ZIDc6dbowL6oU0Z0YcD3scdqZUXyYnBDtmVAocJo63mD2sXkXJxPfTMRNlZ35t/muKDNJ567ZsqxsqcbYmGUe/hzlvYZI4r9w9J6t6cOUyWPba0XbeO83o1fsS+aFycbxei6270MQ46JZQNWevYaEwhpXx9d0VguVe6c0QcBxTXGfuFvUwOky6uoHIR1N4soGK995F6B6NnklQ7wgBQIgKZhoNQIDAQAB";
    
	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://localhost:8080/QYY/notify_url.jsp";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://localhost:8080/QYY/return_url.jsp";

	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";
	@SuppressWarnings("deprecation")
	public static String getCode() {
		String DATE = "";
		Date date=new Date();
		DATE+=date.getYear()+1900;
		if(date.getMonth()<9){
			DATE+="0";
		}DATE+=date.getMonth()+1;
		if(date.getDate()<10) {
			DATE+="0";
		}DATE+=date.getDate();
		if(date.getHours()<10) {
			DATE+="0";
		}DATE+=date.getHours();
		if(date.getMinutes()<10) {
			DATE+="0";
		}DATE+=date.getMinutes();
		if(date.getSeconds()<10) {
			DATE+="0";
		}DATE+=date.getSeconds();
		String Time=Long.toString(System.currentTimeMillis());
		DATE+=Time.substring(Time.length()-3);
				return DATE;
	}
	// 支付宝网关
	public static String gatewayUrl ="https://openapi.alipaydev.com/gateway.do";
	
	// 支付宝网关
	public static String log_path = "D:/Q/ali/alipay/log/";

//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /** 
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord,String type,String out_trade_no) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path+type+"/"+out_trade_no+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

