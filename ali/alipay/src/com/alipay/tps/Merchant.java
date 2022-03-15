package com.alipay.tps;

import java.util.List;

import com.alipay.model.Agent;
import com.alipay.model.AgentModel;
import com.iceking.dynamicweb.DBConnection;
import com.iceking.dynamicweb.DWPConsole;
import com.iceking.dynamicweb.TaskProcessor;
import com.sun.glass.events.WheelEvent;
import com.sun.glass.ui.Size;

import net.sf.json.JSONObject;

public class Merchant extends TaskProcessor {

	/**
	 * 数据的查询方法
	 * 
	 * @throws Exception
	 */
	public void search() throws Exception {
		// 初始化model对象
		AgentModel model = new AgentModel();
		// 把网页参数装载到model中
		loadParamsToObj(model);
		// 从数据库连接池中获得一条链接
		DBConnection connection = DWPConsole.getInstance().getOneConnection();

		String where = "";

		if (model.getLevel() > 0) {
			where += " and level = " + model.getLevel() + " ";
		}

		if (!model.getKeyword().equals("")) {
			where += " and ( agent_name like '%" + model.getKeyword() + "%' or agent_uid like '%" + model.getKeyword()
					+ "%')";
		}
		// 执行查询语句，获得结果列表

		// 从类别表中获得全部对象
		List<Agent> agents = connection.all(Agent.class);
		model.setAgent_list(agents);

		// 查询数据库中满足条件的数量
		int total=connection.total(Agent.class, where);

			int totalpage = total / model.getPagesize();
			if (total % model.getPagesize() != 0) {
				totalpage++;
			}
			model.setTotal(total);
			model.setTotalpage(totalpage);
			

			// 查询满足条件的数据列表
			List<Agent> agent = connection.search(Agent.class, where, " level asc , id desc ",model.getPage() + 1, model.getPagesize());
			model.setAgent_list(agent);
		
		connection.close();
		setAttribute("model", model);
		toJsp("/agent.jsp");
	}
	/**
	 * 根据id删除某个商户
	 * @throws Exception
	 */
	public void del() throws Exception {
		//获得页面参数id
		int id=getParamInt("id");
		if(id<=0) {
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("succ",false);
			jsonObject.put("stmt", "缺少关键参数id");
			toJson(jsonObject.toString());
			return;
			}
		
		//根据id删除数据记录
		DBConnection connection=DWPConsole.getInstance().getOneConnection();
		Agent de=connection.findByPK(Agent.class, id);
		if(de==null) {
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("succ",false);
			jsonObject.put("stmt", "根据id查找，商户不存在,"+id);
			toJson(jsonObject.toString());
			connection.close();
			return;
		}
		int count=connection.total(Agent.class," and superior='"+de.getAgent_uid()+"'");
		if (count>0) {
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("succ",false);
			jsonObject.put("stmt", "该商户存在下级，不可删除！请修改下级信息后再删除该商户！");
			toJson(jsonObject.toString());
			connection.close();
			return;
		}
		connection.delete(de);
		connection.close();
		Dao.update();
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("succ",true);
		toJson(jsonObject.toString());
	}
	
	/**
	 * 新增或者修改之前的操作
	 * @throws Exception
	 */
	public void presave() throws Exception {
		//获得页面参数id
		int id=getParamInt("id");
		if(id<=0) {
			//新增
			Agent de=new Agent();
			de.setId(0);
			de.setAgent_name("");
			de.setAgent_uid("");
			de.setLevel(0);
			de.setSuperior("");
			de.setAmount(Double.valueOf(0));
//			de.setRate(0);	
//			de.setCommission(0);
			
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("succ",true);
			jsonObject.put("de",de);
			toJson(jsonObject.toString());
		}else {
			//修改
			DBConnection connection=DWPConsole.getInstance().getOneConnection();
			Agent de=connection.findByPK(Agent.class, id);
			if(de==null) {
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("succ",false);
				jsonObject.put("stmt", "根据id查找，商户不存在，"+id);
				toJson(jsonObject.toString());
				connection.close();
				return;
			}
			connection.close();
			//保存数据，可以修改
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("succ",true);
			jsonObject.put("de",de);
			toJson(jsonObject.toString());
		}
	
	}
	
	/**
	 * 数据库保存
	 * @throws Exception
	 */
	public void save() throws Exception {
		Agent model=new Agent();
		Boolean flag=false;
		//用jquery提交的data完成后放入model
		loadParamsToObj(model);
		model.setRate((Double)Dao.qurey("Role","rate","level="+model.getLevel()));
		
		DBConnection connection=DWPConsole.getInstance().getOneConnection();
		try {
			model = connection.save(model);
		flag=connection.update("update Agent set commission = rate*amount/10000;")>0;
		} catch (Exception e) {
			// TODO: handle exception
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("succ",false);
			jsonObject.put("stmt", "数据保存失败");
			toJson(jsonObject.toString());
			connection.close();
			return;
		}
		if(model==null || !flag) {
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("succ",false);
			jsonObject.put("stmt", "数据保存失败");
			toJson(jsonObject.toString());
			connection.close();
			return;
		}

		Dao.update();
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("succ",true);
		toJson(jsonObject.toString());
	}
	/**
	 * 费率修改
	 * @throws Exception
	 */
	public void rate() throws Exception {
		Double rate1=getParamDouble("rate1");
		Double rate2=getParamDouble("rate2");
		Double rate3=getParamDouble("rate3");
		Double rate4=getParamDouble("rate4");
		
		DBConnection connection=DWPConsole.getInstance().getOneConnection();
		int change=0;
		change+=connection.update("update Role set rate = "+rate1+" where level = 1 ");
		change+=connection.update("update Role set rate = "+rate2+" where level = 2 ");
		change+=connection.update("update Role set rate = "+rate3+" where level = 3 ");
		change+=connection.update("update Role set rate = "+rate4+" where level = 4 ");
		change+=connection.update("update Agent set rate = "+rate1+" where level = 1 ");
		change+=connection.update("update Agent set rate = "+rate2+" where level = 2 ");
		change+=connection.update("update Agent set rate = "+rate3+" where level = 3 ");
		change+=connection.update("update Agent set rate = "+rate4+" where level = 4 ");
		change+=connection.update("update Agent set commission = rate*amount/10000;");
		if(change==0) {
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("succ",false);
			jsonObject.put("stmt", "费率修改失败");
			toJson(jsonObject.toString());
			connection.close();
			return;
		}
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("succ",true);
		toJson(jsonObject.toString());
	}
}
