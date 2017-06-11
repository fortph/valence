package beans.smic;

import java.util.Date;

public class Smic {
	private int id_smic;
	private float tsxmic;
	private Date date_smic;	
	private float anciensmic;
	public int getId_smic() {
		return id_smic;
	}
	public void setId_smic(int id_smic) {
		this.id_smic = id_smic;
	}
	public float getTsxmic() {
		return tsxmic;
	}
	public void setTsxmic(float tsxmic) {
		this.tsxmic = tsxmic;
	}
	public Date getDate_smic() {
		return date_smic;
	}
	public void setDate_smic(Date date_smic) {
		this.date_smic = date_smic;
	}
	public float getAnciensmic() {
		return anciensmic;
	}
	public void setAnciensmic(float anciensmic) {
		this.anciensmic = anciensmic;
	}
	public Smic(int id_smic, float tsxmic, Date date_smic, float anciensmic) {
		super();
		this.id_smic = id_smic;
		this.tsxmic = tsxmic;
		this.date_smic = date_smic;
		this.anciensmic = anciensmic;
	}
	public Smic() {
		super();
	}
	public Smic(float tsxmic, Date date_smic, float anciensmic) {
		super();
		this.tsxmic = tsxmic;
		this.date_smic = date_smic;
		this.anciensmic = anciensmic;
	}
	
	
	
	

}
