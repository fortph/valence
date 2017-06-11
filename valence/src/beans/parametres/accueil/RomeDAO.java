package beans.parametres.accueil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.DAOConstants;
import dao.DBConnexion;
import dao.exception.DAOException;
import dao.interfaces.DAO;
import divers.FormaterTexte;

public class RomeDAO implements DAO<Rome> {
	
	private Connection connect = null;

	public RomeDAO() {
		connect = DBConnexion.getConnexion();

	}

	@Override
	public Rome findByID(int id) throws DAOException {
		String query = "select * from " + DAOConstants.t_rome
				+ " where  idrome=" + id;
		Rome rome = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				rome = new Rome((int) id, res.getString(2), res.getString(3),
						res.getShort(4), res.getBoolean(5));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			try {
				res.close();
				st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return rome;
	}
	/**  
	 * on recupere le metier recherche, chaque métier étant unique 
	 * en fonction de la longueur du code métier (1,2 ou 4)  
	 * on a affaire a un titre, un sous titre ou un metier   
	 * 
	 * @param s
	 * @return
	 * @throws DAOException
	 */
	
	
	public List<Rome> rechercheParCodeNRome(String s) throws DAOException {
		String query = "select idrome from " + DAOConstants.t_rome
				+ " where  idrome in (select idrome from " 
				+ DAOConstants.t_rome
				+" where nrome like '"+s+"%')";
		List<Rome>liste=new ArrayList<Rome>();
		Rome rome = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			while(res.next()) {
				rome = this.findByID(res.getInt(1));
				liste.add(rome);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			try {
				res.close();
				st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return liste;
	}

	/** 
	 * recherche tous les champs de lemploi recherche dont le nom est indiqué
	 * @param nom
	 * @return
	 * @throws DAOException
	 */
	 
	public Rome findByName(String nom) throws DAOException {
		nom=new FormaterTexte().supprimerTableMatiere(nom);
		//nom = new FormaterChaine().supprimerApostrophe(nom);
		Rome rom = null;
		Statement st = null;
		ResultSet res = null;
		String req = "select * from " + DAOConstants.t_rome
				+ "  where intitule like \"" + nom + "\"";
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			if (res.first()) {

				rom = new Rome(res.getInt(1), res.getString(2),
						res.getString(3), res.getShort(4), res.getBoolean(5));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			try {
				res.close();
				st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return rom;

	}

	@Override
	public List<Rome> findAll() throws DAOException {
		List<Rome> liste = null;
		Statement stat = null;
		Rome rome=null;
		ResultSet rs = null;
		
		String req = "select idrome,nrome,intitule from " + DAOConstants.t_rome
				+ " where  actif=1 and idrome!=299 order by nrome asc";
		

		try {
			stat = connect.createStatement();
			rs = stat.executeQuery(req);
			liste = new ArrayList<Rome>();

			/* Pour chaque catégorie de rang 0 */
			while (rs.next()) {
				rome=new Rome(rs.getInt("idrome"),rs.getString("nrome"),rs.getString("intitule"));
				
				liste.add(rome);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		
		return liste;
	}

	@Override
	public List<Rome> findByCriteria(Rome obj) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int create(Rome obj) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Rome update(Rome obj) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int delete(Rome obj) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(long id) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<String> afficherRome() throws DAOException {
		List<String> liste = null;
		Statement stat = null;
		Statement stat1 = null;
		Statement stat2 = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		String req = "select intitule,nrome from " + DAOConstants.t_rome
				+ " where rang=0 and actif=1 order by nrome asc";

		try {

			stat = connect.createStatement();
			rs = stat.executeQuery(req);
			liste = new ArrayList<String>();

			/* Pour chaque catégorie de rang 0 */
			while (rs.next()) {
				// int idrome=rs.getInt("idrome");
				String niveau = rs.getString("intitule");

				// niveau=niveau+"("+idrome+")";
				String rom = rs.getString("nrome");
				liste.add("");
				liste.add(niveau);

				String req1 = "select intitule, nrome from "
						+ DAOConstants.t_rome
						+ " where actif=1 and rang=1 and nrome like'" + rom
						+ "%' order by nrome asc";
				stat1 = connect.createStatement();
				rs1 = stat1.executeQuery(req1);
				/*
				 * on regarde si la catégorie comporte des sous catégories de
				 * rang 1
				 */
				/* si on en trouve , on les rajoute à la liste */
				if (rs1.first()) {
					// on se positionne avant le premier résultat
					rs1.beforeFirst();
					char c = 'A';
					while (rs1.next()) {
						// int idrome1=rs1.getInt("idrome");
						String niveau1 = rs1.getString("intitule");
						niveau1 = c + "/ " + niveau1;// +"("+idrome1+")";
						String rom1 = rs1.getString("nrome");

						liste.add(niveau1);
						c++;
						// liste.add(rs1.getString("intitule"));
						String req2 = "select intitule from "
								+ DAOConstants.t_rome
								+ " where actif=1 and rang=3 and nrome like'"
								+ rom1 + "%' order by nrome asc";
						stat2 = connect.createStatement();
						rs2 = stat2.executeQuery(req2);
						while (rs2.next()) {
							// int idrome3=rs2.getInt("idrome");
							String niveau2 = rs2.getString("intitule");
							liste.add(niveau2);

						}
					}

				}
				/* sinon on affiche les differents elements de rang 3 */
				else {
					String req2 = "select intitule from " + DAOConstants.t_rome
							+ " where actif=1 and rang=3 and nrome like'" + rom
							+ "%' order by nrome asc";
					stat2 = connect.createStatement();
					rs2 = stat2.executeQuery(req2);
					while (rs2.next()) {
						// int idrome3=rs2.getInt("idrome");
						String niveau2 = rs2.getString("intitule");
						// niveau2=niveau2+"("+idrome3+")";
						liste.add(niveau2);

					}

				}

			}
		} catch (SQLException ex) {
			System.out.print("Probl�me " + ex);
		} finally {
			try {
				stat.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return liste;
	}

}
