package com.alipay.tps;

import java.util.List;

import com.alipay.model.PayModel;
import com.alipay.model.Pay;
import com.iceking.dynamicweb.DBConnection;
import com.iceking.dynamicweb.DWPConsole;
import com.iceking.dynamicweb.TaskProcessor;

public class Paysql extends TaskProcessor {

	/**
	 * 数据的查询方法
	 * 
	 * @throws Exception
	 */
	public void search() throws Exception {
		// 初始化model对象
		PayModel model = new PayModel();
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
		if (model.getUid()!=null && !model.getUid().equals("") && !model.getUid().equals("0") ) {
			where += " and seller_uid = '" + model.getUid() + "' ";
		}
		else {
			model.setUid("");
		}

		if (!model.getKeyword().equals("")) {
			where += " and ( subject like '%"+model.getKeyword()+"%' or superior1 like '%"+model.getKeyword()
			+ "%'or superior2 like '%"+ model.getKeyword()+ "%'or superior3 like '%" + model.getKeyword()+ "%')";
		}
		// 执行查询语句，获得结果列表

		// 从类别表中获得全部对象
		List<Pay> Pays = connection.all(Pay.class);
		model.setPay_list(Pays);

		// 查询数据库中满足条件的数量
		int total=connection.total(Pay.class, where);

			int totalpage = total / model.getPagesize();
			if (total % model.getPagesize() != 0) {
				totalpage++;
			}
			model.setTotal(total);
			model.setTotalpage(totalpage);
			

			// 查询满足条件的数据列表
			List<Pay> pay= connection.search(Pay.class, where, " id",model.getPage() + 1, model.getPagesize());
			model.setPay_list(pay);
		
		connection.close();
		setAttribute("model", model);
		toJsp("/pay.jsp");
	}
}
