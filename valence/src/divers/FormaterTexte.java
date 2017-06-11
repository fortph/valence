package divers;

public class FormaterTexte {

	public String supprimerTableMatiere(String st) {
		String reste = null;
		if (!st.equals("") || !st.equals(null)) {
			String c = st.substring(1, 2);
			String d = st.substring(0, 1);
			/* si on trouve '/' en deuxieme caractere on est en présence d'un sous titre*/
			if (c.equals("/"))
				/*on supprime donc tout ce qu'il y a avant*/
				reste = st.substring(3);
			else {
				try {
					/* si on peut convertir le premier caractere en entier on est sur la liste des propositions*/
					 Integer.parseInt(d);
					int deb = st.indexOf(".");
					reste = st.substring(deb + 2);
				} catch (Exception e) {
					/*sinon on ne modifie rien*/
					reste=st;
				}

			}
		}
		return reste;
	}
}
