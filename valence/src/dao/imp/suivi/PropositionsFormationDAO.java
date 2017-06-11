package dao.imp.suivi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.suivi.AccompagnementFormation;
import beans.suivi.PropositionsFormation;
import dao.DAOConstants;
import dao.DAOUtil;
import dao.DBConnexion;
import dao.exception.DAOException;
import dao.interfaces.DAO;

public class PropositionsFormationDAO implements DAO<PropositionsFormation> {

	final private String query_insert = "INSERT INTO "
			+ DAOConstants.t_propositionsFormation
			+ " (id_accomp,nomProposition,commentaire ) VALUES(?,?,?)";
	
	final private String query_delete = "DELETE FROM "
			+ DAOConstants.t_propositionsFormation + " WHERE id_proposition= ";

	private Connection connect = null;

	public PropositionsFormationDAO() {
		connect = DBConnexion.getConnexion();
	}

	@Override
	public PropositionsFormation findByID(int id) throws DAOException {
		String query = "select  `id_proposition`,  `id_accomp`,`nomProposition`,commentaire  from "
	+ DAOConstants.t_propositionsFormation
				+ " where id_proposition=" + id;
		PropositionsFormation pl = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.next()) {
				pl = new PropositionsFormation(id, new AccompagnementFormationDAO().findByID(res.getInt(2)),
						res.getString(3),res.getString(4));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			
				// Fermeture du resultset
				DAOUtil.closeResultSet(res);

				// Fermeture du statement
				DAOUtil.closeStatement(st);
		}

		return pl;

	}

	@Override
	public List<PropositionsFormation> findAll() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PropositionsFormation> findByCriteria(PropositionsFormation obj)
			throws DAOException {
		List<PropositionsFormation> liste = new ArrayList<PropositionsFormation>();
		Statement st = null;
		ResultSet rs = null;
		PropositionsFormation prof = null;
		String req= " select  `id_proposition`,  `id_accomp`, `nomProposition`,commentaire from "
	+ DAOConstants.t_propositionsFormation
				+ " where 1=1";
		if(obj.getId_accomp()!=null)
			req+=" and id_accomp ="+obj.getId_accomp();
		
		if (obj.getNomProposition() != null)
			req += " AND nomProposition LIKE '"
					+ obj.getNomProposition().toUpperCase() + "'";
		try {
			st=connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs=st.executeQuery(req);
			while(rs.next()){
				prof=new PropositionsFormation(rs.getInt(1),new AccompagnementFormationDAO().findByID(rs.getInt(2)),
						rs.getString(3),rs.getString(4));
				liste.add(prof);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(rs);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}
		return liste;
	}

	@Override
	public int create(PropositionsFormation obj) throws DAOException {
		PreparedStatement pst = null;
		int cle=0;
		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query_insert,Statement.RETURN_GENERATED_KEYS);
			pst.setInt(1,obj.getId_accomp().getId_accomp());
			pst.setString(2, obj.getNomProposition());
			pst.setString(3, obj.getCommentaire());
			pst.executeUpdate();
			ResultSet rs = pst.getGeneratedKeys();
			if (rs != null && rs.next()) 
			cle = rs.getInt(1); 
			connect.commit();

		} catch (SQLException e) {
			try {
				connect.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
						// Fermeture du statement
						DAOUtil.closeStatement(pst);
		}
		return cle;
	}

	@Override
	public PropositionsFormation update(PropositionsFormation obj) throws DAOException {
		PreparedStatement pst = null;
		PreparedStatement pst1 = null;
		String query = query_delete + obj.getId_proposition();
		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query);
			pst.executeUpdate();
			pst1=connect.prepareStatement(query_insert);
			pst.setInt(1,obj.getId_accomp().getId_accomp());
			pst.setString(2, obj.getNomProposition());
			pst.setString(3, obj.getCommentaire());
			pst1.executeUpdate();
			connect.commit();


		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			

						// Fermeture du statement
			DAOUtil.closeStatement(pst1);
						DAOUtil.closeStatement(pst);
	}
		return obj;
	}

	@Override
	public int delete(PropositionsFormation obj) throws DAOException {
		int num=obj.getId_proposition();
		return delete(num);
	}
	public void deleteByIdAccomp(AccompagnementFormation obj) throws DAOException {
		String query = "delete from "
				+ DAOConstants.t_propositionsFormation
							+ " where id_accomp=" + obj.getId_accomp();
					
					Statement st = null;
					
					try {
						st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
								ResultSet.CONCUR_READ_ONLY);
						 st.executeUpdate(query);
					

					} catch (SQLException e) {

						e.printStackTrace();

					} finally {
						
						
							// Fermeture du statement
							DAOUtil.closeStatement(st);
					}

					
	}

	@Override
	public int delete(long id) throws DAOException {
		String query = query_delete + id;
		int retour = 0;
		Statement st=null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			retour = st.executeUpdate(query);
			} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
					// Fermeture du statement
						DAOUtil.closeStatement(st);
		}

		return retour;
	
	}

	public List<String> afficherPropositions() throws DAOException {
		List<String> liste = null;
		Statement stat = null;
		ResultSet rs = null;
		String req = null;
		try {

			req = "select nomProposition from " + DAOConstants.t_propositionsFormation+" where 1=1";
			stat = connect.createStatement();
			rs = stat.executeQuery(req);
			liste= new ArrayList<String>();
			while (rs.next())
				liste.add(rs.getString("nomProposition"));

		} catch (SQLException ex) {
			System.out.print("Problème " + ex);
		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(rs);

			// Fermeture du statement
			DAOUtil.closeStatement(stat);
		}
		return liste;
	}
	
	
	/**
	 * retourne la liste des propositions de formation pour un numero d'accompagnement formattion
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	public List<PropositionsFormation> findByIDAccompagnement(AccompagnementFormation accomp) throws DAOException {
		String query = "select  `id_proposition`,  `id_accomp`,`nomProposition`, `commentaire`  from "
	+ DAOConstants.t_propositionsFormation
				+ " where id_accomp=" + accomp.getId_accomp()+" order by id_proposition asc"; 
		PropositionsFormation pl = null;
		List<PropositionsFormation> liste=null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			liste=new ArrayList<PropositionsFormation>();
			while (res.next()) {
			
				pl = new PropositionsFormation(res.getInt(1), accomp,
						res.getString(3),res.getString(4));
				liste.add(pl);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			// Fermeture du resultset
						DAOUtil.closeResultSet(res);

						// Fermeture du statement
						DAOUtil.closeStatement(st);
		}

		return liste;

	}
	
	
	/**
	 * renvoie la proposition correspondant a u suivi de la personne et au nom de proposition
	 * @param accomp
	 * @param nom
	 * @return
	 * @throws DAOException
	 */
	public PropositionsFormation findByIDAccompagnementNomPropos
	(AccompagnementFormation accomp, String nom) throws DAOException {
		String query = "select  `id_proposition`,  `id_accomp`,`nomProposition`, `commentaire`  from "
	+ DAOConstants.t_propositionsFormation
				+ " where id_accomp=" + accomp.getId_accomp()+" and nomProposition='"+nom+"'"; 
		PropositionsFormation pl = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			
			if (res.first()) {
			
				pl = new PropositionsFormation(res.getInt(1), accomp,
						nom,res.getString(4));
				
			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			// Fermeture du resultset
						DAOUtil.closeResultSet(res);

						// Fermeture du statement
						DAOUtil.closeStatement(st);
		}

		return pl;

	}

}
