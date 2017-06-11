package divers;

public class FormaterChaine {
		
	/**insére une apostrophe avant une apostrophe dans une chaine*/
	public String supprimerApostrophe(String s){
		for(int i=0;i<s.length();i++){
			if(s.charAt(i)==0x27){
				s=s.substring(0, i)+"'"+s.substring(i++);
			}
		}
		return s;
	}
	

}
