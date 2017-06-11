package divers;

import java.text.DecimalFormat;

public class FormaterDouble {
	
	public static String formateDouble(int a, int b){
		if (a==0 && b==0)
			return "0";
		DecimalFormat formate=new DecimalFormat();
		formate.setMaximumFractionDigits(2);
		double taux=0;
		String retour="0";
		try{
			taux=(a*100.00)/b;
			retour= formate.format(taux
					);
		}catch(Exception e){
			e.printStackTrace();
			}
		return retour;
		
			
		}

}
