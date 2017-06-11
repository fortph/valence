package divers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FormaterDate {
	private String sortie;
	/**
	 * affiche date du jour au format yyyy-mm-dd  
	 * @return
	 */
	private String afficheJour(){
		Calendar cal=Calendar.getInstance();
		int an=cal.get(Calendar.YEAR);
		int mois=cal.get(Calendar.MONTH)+1;
		int jour=cal.get(Calendar.DAY_OF_MONTH);
		String mm=new String(),jj=new String();
		if(mois<10)
			mm="0"+mois+"-";
		else
			mm=mois+"-";
		if(jour<10)
			jj="0"+jour;
		else
			jj=jour+"";
		sortie=an+"-"+mm+jj;
		return sortie;
	}

	public String getSortie() {
		String s=this.afficheJour();
		return s;
	}
	/**
	 * convertir une date en string au format yyyy-mm-dd 
	 * @param d
	 * @return
	 */
	public String formateDate(Date d){
		Calendar jour=Calendar.getInstance();
		jour.setTime(d);
		//java.util.Date fin= jour.getTime();
		int aa=jour.get(Calendar.YEAR);
		int mm=jour.get(Calendar.MONTH)+1;
		int jj=jour.get(Calendar.DAY_OF_MONTH);
		String m1,j1;
		if(mm<10)
			m1="0"+mm+"-";
		else
			m1=mm+"-";
		if(jj<10)
			j1="0"+jj+"-";
		else
			j1=jj+"-";
		
		String retour=j1+m1+aa;
		return retour;
	}
	/** 
	 * formate une date au format francais
	 * @param d
	 * @return
	 */
	public Date formatFrancais(Date d){				
		DateFormat sdf=new SimpleDateFormat("dd-MM-yyyy",Locale.FRANCE);
		String dd=sdf.format(d);
		//System.out.println(dd);
		Date une=null;
		
		try {
			DateFormat sd=DateFormat.getDateInstance(DateFormat.SHORT, Locale.FRANCE);
				
			une= sd.parse(dd);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("date = "+une);
		return une;
	}
	/**
	 * recupere le nombre de jours, le mois et l'annee suivant le mois courant
	 * @return
	 */
	public List<Integer> afficheMoisSuivant(){		
			Calendar cal=Calendar.getInstance();	
			cal.setTime(new Date());
			//on rajoute 1 mois au mois en cours
			cal.add(Calendar.MONTH, 1);
			cal.getTime();
			int mois=cal.get(Calendar.MONTH);
			
			int an=cal.get(Calendar.YEAR);
						
			Calendar suivant=Calendar.getInstance();
			suivant.set(Calendar.MONTH, mois);
			suivant.set(Calendar.YEAR, an);
			int jourmoissuivant=suivant.getActualMaximum(Calendar.DAY_OF_MONTH);
			List<Integer> liste=new ArrayList<Integer>();
			liste.add(mois);
			liste.add(an);
			liste.add(jourmoissuivant);
			return liste;
			
					
	}
	
	/**
	 * recupere le mois correspondant a la chaine mois
	 * @param n
	 * @return
	 */
	public int moisCorrespondant(String n){		
			int num=0;
			if(n.equals("janvier"))
				num=1;
			else if(n.equals("février"))
				num=2;
			else if(n.equals("mars"))
				num=3;
			else if(n.equals("avril"))
				num=4;
			else if(n.equals("mai"))
				num=5;
			else if(n.equals("juin"))
				num=6;
			else if(n.equals("juillet"))
				num=7;
			else if(n.equals("août"))
				num=8;
			else if(n.equals("septembre"))
				num=9;
			else if(n.equals("octobre"))
				num=10;
			else if(n.equals("novembre"))
				num=11;
			else if(n.equals("décembre"))
				num=12;
			
			
			return num;
			
					
	}
	
	/**
	 * recupere le mois en toute lettre correspondant au numéro de mois 
	 * @param n
	 * @return
	 */
	public String moisEnLettre(int n){	
		
		String[] nomMois = { "Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet",
		        "Août", "Septembre", "Octobre", "Novembre", "Décembre" };
				String mois=nomMois[n];
			return mois;
			
					
	}
	
	
	
	/** 
	 * convertit chaine dd-mm-yyyy vers yyyy-mm-dd
	 */
	public java.sql.Date changeFormatChaineDate(String s){
	java.sql.Date date=null;
	if(!s.equals(null)|| !s.equals("")){
		s=s.trim();
		int j=s.lastIndexOf("-");		
		String annee=s.substring(j+1,s.length());
		
		int i=s.indexOf("-");
		String jour=s.substring(0,i);
		//System.out.println("longueur ="+jour.length());
		String mois=s.substring(i+1, j);
		//System.out.println("longueur ="+annee.length());
		if(annee.length()==4){
		String retour=annee+"-"+mois+"-"+jour;
	//	System.out.println("retour ="+retour);
		 date=java.sql.Date.valueOf(retour);
		}
		else{
			date=java.sql.Date.valueOf(s);
		}
	}
		return date;
		
	}
	
	public Date lendemain(Date d){
		Calendar cal=Calendar.getInstance();	
		cal.setTime(d);
		//on rajoute 1 jour à la date
		cal.add(Calendar.DAY_OF_MONTH, 1);
		
		Date une=cal.getTime();
		return une;
	}
	
	public Date debutMutuelle(){
		Calendar cal=Calendar.getInstance();
		cal.set(2017, 0, 1);
		Date une=cal.getTime();
		return une;
	}
	
	public Date plusUnAn(Date d){
		Calendar cal=Calendar.getInstance();	
		cal.setTime(d);
		//on rajoute 1 jour à la date
		cal.add(Calendar.YEAR, 1);
		
		Date une=cal.getTime();
		return une;
	}
	
	
}
