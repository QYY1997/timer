package com.alipay.tps;

import java.sql.ResultSet;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import com.alipay.api.response.AlipayOpenAuthTokenAppQueryResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.alipay.config.Implements;
import com.alipay.model.Token;
import com.alipay.model.Agent;
import com.alipay.model.Pay;
import com.iceking.dynamicweb.DBConnection;
import com.iceking.dynamicweb.DWPConsole;
import com.iceking.dynamicweb.TaskProcessor;

public class Dao extends TaskProcessor{
	
public static boolean add_token(String token) throws Exception  {
		Token model = new Token();
		SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		AlipayOpenAuthTokenAppQueryResponse response = Implements.openauth_query(token);
		model.setApp_auth_token(token);
		model.setAuth_app_id(response.getAuthAppId());
		model.setAuth_end(time.format(response.getAuthEnd()));
		System.out.print(model.getAuth_end());
		model.setStatus(response.getStatus().equals("valid")?"ture":"false");
	    model.setUser_id(response.getUserId());
	    DBConnection connection = DWPConsole.getInstance().getOneConnection();
	    boolean result=connection.save(model)!=null;
	    connection.close();
	    return result;
}
public static boolean add_pay(String out_trade_no,String subject,String token) throws Exception  {
	Pay model = new Pay();
	SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	AlipayTradeQueryResponse response = Implements.query(out_trade_no,"",token);
	if (response.getSubMsg()!=null && response.getSubMsg().equals("系统繁忙")) {
	response = Implements.query(out_trade_no,"",token);
	}
	model.setOut_trade_no(out_trade_no);
	model.setTrade_no(response.getTradeNo());
	model.setSubject(subject);
	model.setTotal_amount(Double.parseDouble(response.getTotalAmount()));
	model.setBuyer_pay_amount(Double.parseDouble(response.getBuyerPayAmount()));
	
	model.setBuyer_user_id(response.getBuyerUserId());
	model.setSend_pay_date(time.format(response.getSendPayDate()));
	switch(response.getTradeStatus()) {
	case "WAIT_BUYER_PAY":
		model.setTrade_status("等待支付");
		break;
	case "TRADE_CLOSED":
		model.setTrade_status("交易关闭");
		break;
	case "TRADE_SUCCESS":
		model.setTrade_status("交易成功");
		break;
	case "TRADE_FINISHED":
		model.setTrade_status("交易结束");
		break;
	}
	Double receoptAmount=Double.parseDouble(response.getTotalAmount());
	
	model.setEffective_sum(receoptAmount);
	
	String uid=Implements.openauth_query(token).getUserId();
	model.setSeller_uid(uid);
	String superior1,superior2,superior3;
	try {
	superior1=(String)qurey("Agent","superior","agent_uid='"+uid+"'");
	
	}
	catch(Exception e){
		superior1="2088102180245964";
	}
	model.setSuperior1(superior1);
	try {
	superior2=(String)qurey("Agent","superior","agent_uid='"+superior1+"'");
	
	}catch(Exception e){
		superior2="2088102180245964";
	}
	model.setSuperior2(superior2);
	try {
	superior3=(String)qurey("Agent","superior","agent_uid='"+superior2+"'");
	}catch(Exception e) {
		superior3="2088102180245964";
	}
	model.setSuperior3(superior3);
	
	Double commission1=((Double)qurey("Role","rate", "level=3"))*receoptAmount/10000;
	model.setCommission1(commission1);
	Double commission2=((Double)qurey("Role","rate", "level=2"))*receoptAmount/10000;
	model.setCommission2(commission2);
	Double commission3=((Double)qurey("Role","rate", "level=1"))*receoptAmount/10000;
	model.setCommission3(commission3);
	NumberFormat nFormat=NumberFormat.getNumberInstance();
	nFormat.setMaximumFractionDigits(5);
	model.setReceipt_amount(Double.parseDouble(nFormat.format((receoptAmount-commission1-commission2-commission3))));
	
    DBConnection connection = DWPConsole.getInstance().getOneConnection();
    boolean result= connection.save(model)!=null;
    Double amount0=(Double)qurey("Agent","amount", "agent_uid='"+uid+"'")+receoptAmount;
    connection.update("update Agent set amount = "+amount0+" where agent_uid='"+uid+"'");
    
    Double amount1=(Double)qurey("Agent","amount", "agent_uid='"+superior1+"'")+receoptAmount;
    connection.update("update Agent set amount = "+amount1+" where agent_uid='"+superior1+"'");
    
    Double amount2=(Double)qurey("Agent","amount", "agent_uid='"+superior2+"'")+receoptAmount;
    connection.update("update Agent set amount = "+amount2+" where agent_uid='"+superior2+"'");
    
    Double amount3=(Double)qurey("Agent","amount", "agent_uid='"+superior3+"'")+receoptAmount;
    connection.update("update Agent set amount = "+amount3+" where agent_uid='"+superior3+"'");
    connection.update("update Agent set commission = rate*amount/10000;");
    
    connection.close();
    return result;
}
public static boolean add_refund(AlipayTradeRefundResponse response,String out_request_no,String reason,String token) throws Exception  {
    DBConnection connection = DWPConsole.getInstance().getOneConnection();
    boolean result=false;

	SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    if (response.getCode().equals("10000")) {
    Double amount=(Double)qurey("Pay","effective_sum"," out_trade_no ='"+response.getOutTradeNo()+"';");
    Double refundAmount=Double.valueOf(0);
   try {
    refundAmount=Double.parseDouble(response.getRefundChargeAmount());
   }
   catch(Exception exception) {
	   refundAmount=Double.parseDouble(response.getSendBackFee());
   }
   if(refundAmount!=0) {
    Double new_sum=amount-refundAmount;
    Double commission1=((Double)qurey("Role","rate", "level=3"))*new_sum/10000;
	Double commission2=((Double)qurey("Role","rate", "level=2"))*new_sum/10000;
	Double commission3=((Double)qurey("Role","rate", "level=1"))*new_sum/10000;
	
	NumberFormat nFormat=NumberFormat.getNumberInstance();
	nFormat.setMaximumFractionDigits(5);
	Double new_receipt=Double.parseDouble(nFormat.format((new_sum-commission1-commission2-commission3)));
	
	result = connection.update("update Pay set refund_amount = " +refundAmount
    + " , out_request_no = '" +out_request_no
    + "' , refund_reason = '" + reason
    + "' , gmt_refund_pay = '" + time.format(response.getGmtRefundPay())
    + "' , commission1 = "+commission1
    + " , commission2 = "+commission2
    + " , commission3 = "+commission3
    + " , receipt_amount = "+new_receipt
    + " , effective_sum= "+new_sum
    + " , trade_status = '"+(new_sum==0?"全额退款":"部分退款")
    + "' where out_trade_no = '"+response.getOutTradeNo()+"';" )>0;
    String superior0=(String)qurey("Pay","seller_uid", " out_trade_no ='"+response.getOutTradeNo()+"';");
    Double amount0=(Double)qurey("Agent","amount", "agent_uid='"+superior0+"'")-refundAmount;
    connection.update("update Agent set amount = "+amount0+" where agent_uid='"+superior0+"'");
 
    String superior1=(String)qurey("Pay","superior1", " out_trade_no ='"+response.getOutTradeNo()+"';");
    Double amount1=(Double)qurey("Agent","amount", "agent_uid='"+superior1+"'")-refundAmount;
    connection.update("update Agent set amount = "+amount1+" where agent_uid='"+superior1+"'");
    
    String superior2=(String)qurey("Pay","superior2", " out_trade_no ='"+response.getOutTradeNo()+"';");
    Double amount2=(Double)qurey("Agent","amount", "agent_uid='"+superior2+"'")-refundAmount;
    connection.update("update Agent set amount = "+amount2+" where agent_uid='"+superior2+"'");
 
    
    String superior3=(String)qurey("Pay","superior3", " out_trade_no ='"+response.getOutTradeNo()+"';");
    Double amount3=(Double)qurey("Agent","amount", "agent_uid='"+superior3+"'")-refundAmount;
    connection.update("update Agent set amount = "+amount3+" where agent_uid='"+superior3+"'");
    connection.update("update Agent set commission = rate*amount/10000;");
    }
    else {
    	System.out.print("改动金额为0！");
    }
    
    }
    connection.close();
    return result;
}
public static Object qurey(String cls,String field,String sql) throws Exception  {
    DBConnection connection = DWPConsole.getInstance().getOneConnection();   
    ResultSet set=connection.query("select "+field+" from "+cls+" where 1=1 and "+sql+" ;");
    Object result =set.getObject(1);  
    set.close();
    connection.close();
    return result;
}
public static List<Agent> qurey_agent(String sql) throws Exception  {
    DBConnection connection = DWPConsole.getInstance().getOneConnection();
    List<Agent> result= connection.search(Agent.class,sql, "id",0,0);
    connection.close();
    return result;
}
public static void update() throws Exception{
	DBConnection connection=DWPConsole.getInstance().getOneConnection();
	List<Agent> feeAgent3=qurey_agent("and level=3");
	for(Agent fee:feeAgent3) {
		fee.setAmount((Double)Dao.qurey("agent", "sum(amount)","superior='"+fee.getAgent_uid()+"'"));
		connection.update("update agent set amount = "+fee.getAmount()+" where agent_uid='"+fee.getAgent_uid()+"'");
	}
	List<Agent> feeAgent2=qurey_agent(" and level=2");
	for(Agent fee:feeAgent2) {
		fee.setAmount((Double)Dao.qurey("agent", "sum(amount)","superior='"+fee.getAgent_uid()+"'"));
		connection.update("update agent set amount = "+fee.getAmount()+" where agent_uid='"+fee.getAgent_uid()+"'");
	}
	List<Agent> feeAgent1=qurey_agent("and level=1");
	for(Agent fee:feeAgent1) {
		fee.setAmount((Double)Dao.qurey("agent", "sum(amount)","superior='"+fee.getAgent_uid()+"'"));
		connection.update("update agent set amount = "+fee.getAmount()+" where agent_uid='"+fee.getAgent_uid()+"'");
	}
	connection.update("update Agent set commission = rate*amount/10000;");
	connection.close();
}
}
