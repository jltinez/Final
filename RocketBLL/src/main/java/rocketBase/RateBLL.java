package rocketBase;

import org.apache.poi.ss.formula.functions.*;

public class RateBLL {

	private static RateDAL _RateDAL = new RateDAL();
	
	static double getRate(int GivenCreditScore) throws RatException
	{
		double myrate= 0;
		for(int j= 0; j<RDMList.size(); j++){
			if(GivenCreditScore>= RDMList.get(j).getiMinCreditScore()){
				myRate= 0.01*RDMList.get(j).getdInterestRate();
			}
		}
		if(myrate== 0){
			RateException noRate= new RateException(RDMList.get(0));
			return 0;
		}
		return myrate;
	}
	
	
	//TODO - RocketBLL RateBLL.getPayment 
	//		how to use:
	//		https://poi.apache.org/apidocs/org/apache/poi/ss/formula/functions/FinanceLib.html
	
	public static double getPayment(double r, double n, double p, double f, boolean t)
	{		
		double pmt= -1*(FinanceLib.pmt(r/12.0, 12.0*n, p, f, t));
		return pmt;
	}
	public static double calculateP(double expenses, double income){
		double monthlyP= 0;
		if(expenses==0){
			monthlyP= income*0.28;
		}
		else{
			monthlyP= (income*0.36)-expenses;
		}
		return monthlyP;
	}
	//message part for after calculation
	public static String fullPaymentToString(double rate, double pmt, double monthlyP){
		String rateAsPercentage= ((100*rate))+"%";
		String resultMessage;
		if(rate==0){
			resultMessage= "Credit score invalid";
		}
		else if(monthlyP>=pmt){
			String payment= String.format("%.2f", pmt);
			resultMessage= "Payment is: $"+payment+" at "+rateAsPercentage+" interest";
		}
		else{
			resultMessage= "Mortgage not affordable";
		}
		return resultMessage;
	}
}
