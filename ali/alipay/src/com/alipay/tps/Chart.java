package com.alipay.tps;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import com.alipay.model.Agent;
import com.alipay.model.ChartModel;
import com.alipay.model.Charts;
import com.alipay.model.Pay;
import com.iceking.dynamicweb.DBConnection;
import com.iceking.dynamicweb.DWPConsole;
import com.iceking.dynamicweb.TaskProcessor;

public class Chart extends TaskProcessor {

	/**
	 * 数据的查询方法
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("null")
	public void search() throws Exception {
		// 初始化model对象
		ChartModel model = new ChartModel();
		// 把网页参数装载到model中
		loadParamsToObj(model);
		// 从数据库连接池中获得一条链接
		DBConnection connection = DWPConsole.getInstance().getOneConnection();
		int k=0,s=0;
		List<Pay> pays=connection.search(Pay.class,"","send_pay_date",0,0);
		s=pays.size();
		List<Integer> y=new ArrayList<Integer>();
		y.add(0,Integer.parseInt(pays.get(0).getSend_pay_date().substring(0,4)));
		k=Integer.parseInt((pays.get(s-1).getSend_pay_date().substring(0,4)));
		for(int i = 1;i<k-y.get(0)+1;i++) {
		y.add(i,y.get(0)+i);
		System.out.print(y.get(i)+"\t");
		}
		System.out.print("\n");
		model.setYears(y);
		String name="";
		if (model.getLevel()==0) {
			model.setType(1);
		}
		if (model.getYear()==0) {
			model.setMonth(0);
		}
		switch (model.getLevel()) {
		case 4:
			name=" and seller_uid = '";
			break;
		case 3:
			name=" and superior1 = '";
			break;
		case 2:
			name=" and superior2 = '";
			break;
		case 1:
			name=" and superior3 = '";
			break;
		}
		


		BigDecimal rate4=new BigDecimal(((Double)Dao.qurey("Role","rate","level=4")).toString());
		
		BigDecimal rate3=new BigDecimal(((Double)Dao.qurey("Role","rate","level=3")).toString());

		BigDecimal rate2=new BigDecimal(((Double)Dao.qurey("Role","rate","level=2")).toString());

		BigDecimal rate1=new BigDecimal(((Double)Dao.qurey("Role","rate","level=1")).toString());
		
		if(model.getLevel()==0) {//商户等级
			
			int times=0;
			
			List<BigDecimal> data1=new ArrayList<>();

			List<BigDecimal> data2=new ArrayList<>();

			List<BigDecimal> data3=new ArrayList<>();

			List<BigDecimal> data4=new ArrayList<>();

			List<BigDecimal> data_all=new ArrayList<>();
			if(model.getYear()==0) {
				times=y.size();
					for(int i=0;i<times;i++) {
						try {
						data_all.add(i,new BigDecimal(((Double)Dao.qurey("Pay","sum(effective_sum)", " send_pay_date >'"+y.get(i)+"-00-00' and send_pay_date <'"+(y.get(i)+1)+"-00-00'")).toString()).setScale(4,BigDecimal.ROUND_HALF_UP));
						}
						catch (Exception e) {
							// TODO: handle exception
							data_all.add(i,new BigDecimal(Double.valueOf(0)));
						}
						System.out.print(data_all.get(i)+"\t");
				}
			}
			else if(model.getYear()!=0 && model.getMonth()==0) {
				times=12;
				for(int i=0;i<times;i++) {
					try {
					data_all.add(i,new BigDecimal(((Double)Dao.qurey("Pay","sum(effective_sum)", " send_pay_date >'"+model.getYear()+"-"+(i<9?"0":"")+(i+1)+"-00' and send_pay_date <'"+model.getYear()+"-"+(i<8?"0":"")+(i+2)+"-00'")).toString()).setScale(4,BigDecimal.ROUND_HALF_UP));
				}
				catch (Exception e) {
					// TODO: handle exception
					data_all.add(i,new BigDecimal(Double.valueOf(0)));
				}
				System.out.print(data_all.get(i)+"\t");
				}
			}
			else {
				times=31;
				for(int i=0;i<times;i++) {
					try {
					data_all.add(i,new BigDecimal(((Double)Dao.qurey("Pay","sum(effective_sum)", " send_pay_date >'"+model.getYear()+"-"+(model.getMonth()<10?"0":"")+model.getMonth()+"-"+(i<9?"0":"")+(i+1)+"' and send_pay_date <'"+model.getYear()+"-"+(model.getMonth()<10?"0":"")+model.getMonth()+"-"+(i<8?"0":"")+(i+2)+"'")).toString()).setScale(4,BigDecimal.ROUND_HALF_UP));
				}
				catch (Exception e) {
					// TODO: handle exception
					data_all.add(i,new BigDecimal(Double.valueOf(0)));
				}
				System.out.print(data_all.get(i)+"\t");
				}	
		    }
			System.out.print("\n");
			for(int i=0;i<times;i++) {
				BigDecimal daBigDecimal=data_all.get(i);
				data1.add(i,(rate1.multiply(daBigDecimal).divide(new BigDecimal("10000"),4,BigDecimal.ROUND_HALF_UP)));
				data2.add(i,(rate2.multiply(daBigDecimal).divide(new BigDecimal("10000"),4,BigDecimal.ROUND_HALF_UP)));
				data3.add(i,(rate3.multiply(daBigDecimal).divide(new BigDecimal("10000"),4,BigDecimal.ROUND_HALF_UP)));
				data4.add(i,(rate4.multiply(daBigDecimal).divide(new BigDecimal("10000"),4,BigDecimal.ROUND_HALF_UP)));
				System.out.print(data1.get(i)+"\t");
				System.out.print(data2.get(i)+"\t");
				System.out.print(data3.get(i)+"\t");
				System.out.print(data4.get(i)+"\t");
			}
			System.out.print("\n");
			model.setAgent1(data1);
			model.setAgent2(data2);
			model.setAgent3(data3);
			model.setAgent4(data4);
			model.setAgent_all(data_all);
		}
		else {
			List<Charts> mCharts =new ArrayList<>();	
			BigDecimal rate;
			switch (model.getLevel()) {
			case 1:
				rate=rate1;
				break;
			case 2:
				rate=rate2;
				break;
			case 3:
				rate=rate3;
				break;
			case 4:
				rate=rate4;
				break;
				default:
					rate=new BigDecimal("0");
					break;
			}
			List<Agent> agents=connection.search(Agent.class,"and level="+model.getLevel(),"id",0,0);
			int s2=agents.size();
			for(int u=0;u<s2;u++) {
				Charts charts=new Charts();
				charts.setAgent_uid(agents.get(u).getAgent_uid());
				System.out.print(agents.get(u).getAgent_uid()+"\t");
				List<BigDecimal> money = new ArrayList<>();
				List<BigDecimal> moneyall = new ArrayList<>();
		if(model.getYear()==0) {
				for(int i=0;i<y.size();i++) {
					String where= " send_pay_date >'"+y.get(i)+"-00-00' and send_pay_date <'"+(y.get(i)+1)+"-00-00'"+name+charts.getAgent_uid()+"'";
					System.out.print(where);
					try {
					moneyall.add(i,new BigDecimal(((Double)Dao.qurey("Pay","sum(effective_sum)",where)).toString()).setScale(4,BigDecimal.ROUND_HALF_UP));
				}
				catch (Exception e) {
					// TODO: handle exception
					moneyall.add(i,new BigDecimal(Double.valueOf(0)));
				}
				money.add(i,(rate.multiply(moneyall.get(i)).divide(new BigDecimal("10000"),4,BigDecimal.ROUND_HALF_UP)));
					System.out.print(moneyall.get(i)+"\t");
					System.out.print(money.get(i)+"\t");
				}
		}
		else if(model.getYear()!=0 && model.getMonth()==0) {
			for(int i=0;i<12;i++) {
				try{
				moneyall.add(i,new BigDecimal(((Double)Dao.qurey("Pay","sum(effective_sum)", " send_pay_date >'"+model.getYear()+"-"+(i<9?"0":"")+(i+1)+"-00' and send_pay_date <'"+model.getYear()+"-"+(i<8?"0":"")+(i+2)+"-00'"+name+charts.getAgent_uid()+"'")).toString()).setScale(4,BigDecimal.ROUND_HALF_UP));
			}
			catch (Exception e) {
				// TODO: handle exception
				moneyall.add(i,new BigDecimal(Double.valueOf(0)));
			}
			money.add(i, (rate.multiply(moneyall.get(i)).divide(new BigDecimal("10000"),4,BigDecimal.ROUND_HALF_UP)));
				System.out.print(moneyall.get(i)+"\t");
				System.out.print(money.get(i)+"\t");
			}
		
		}
		else {
			for(int i=0;i<31;i++) {
				try {
				moneyall.add(i,new BigDecimal(((Double)Dao.qurey("Pay","sum(effective_sum)", " send_pay_date >'"+model.getYear()+"-"+(model.getMonth()<10?"0":"")+model.getMonth()+"-"+(i<9?"0":"")+(i+1)+"' and send_pay_date <'"+model.getYear()+"-"+(model.getMonth()<10?"0":"")+model.getMonth()+"-"+(i<8?"0":"")+(i+2)+"'"+name+charts.getAgent_uid()+"'")).toString()).setScale(4,BigDecimal.ROUND_HALF_UP));
				}
				catch (Exception e) {
					// TODO: handle exception
					moneyall.add(i,new BigDecimal(Double.valueOf(0)));
				}
				money.add(i,(rate.multiply(moneyall.get(i)).divide(new BigDecimal("10000"),4,BigDecimal.ROUND_HALF_UP)));
				System.out.print(moneyall.get(i)+"\t");
				System.out.print(money.get(i)+"\t");
			
			}
			}
		System.out.print("\n");
		charts.setAmount(model.getType()==2?moneyall:money);
		mCharts.add(u,charts);
		}
			model.setList(mCharts);
		}
		

		connection.close();
		setAttribute("model", model);
		toJsp("/chart.jsp");
	}
}
