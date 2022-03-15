package com.alipay.tps;

import java.util.List;

import com.alipay.model.TokenModel;
import com.alipay.model.Token;
import com.iceking.dynamicweb.DBConnection;
import com.iceking.dynamicweb.DWPConsole;
import com.iceking.dynamicweb.TaskProcessor;

public class Tokensql extends TaskProcessor {

	/**
	 * 数据的查询方法
	 * 
	 * @throws Exception
	 */
	public void search() throws Exception {
		// 初始化model对象
		TokenModel model = new TokenModel();
		// 把网页参数装载到model中
		loadParamsToObj(model);
		try{
			if (model.getUid()==null || model.getUid().equals("")) {	
			String u="";
			u=getParam("UID");
			model.setUid(u);
			}
		
		}
		catch(Exception exception) {
		}
		// 从数据库连接池中获得一条链接
		DBConnection connection = DWPConsole.getInstance().getOneConnection();

		String where = "";
		if (model.getUid()!=null && !model.getUid().equals("") && !model.getUid().equals("0")) {
			where += " and user_id = '" + model.getUid() + "' ";
		}
		else {
			model.setUid("");
		}

		if (!model.getKeyword().equals("")) {
			where += " and ( user_id like '%" + model.getKeyword() + "%' or auth_app_id like '%" + model.getKeyword() + "%' or app_auth_token like '%" + model.getKeyword()
					+ "%')";
		}
		// 执行查询语句，获得结果列表

		// 从类别表中获得全部对象
		List<Token> Tokens = connection.all(Token.class);
		model.setToken_list(Tokens);

		// 查询数据库中满足条件的数量
		int total=connection.total(Token.class, where);

			int totalpage = total / model.getPagesize();
			if (total % model.getPagesize() != 0) {
				totalpage++;
			}
			model.setTotal(total);
			model.setTotalpage(totalpage);
			

			// 查询满足条件的数据列表
			List<Token> Token = connection.search(Token.class, where, " id ",model.getPage() + 1, model.getPagesize());
			model.setToken_list(Token);
		
		connection.close();
		setAttribute("model", model);
		toJsp("/token.jsp");
	}
}
