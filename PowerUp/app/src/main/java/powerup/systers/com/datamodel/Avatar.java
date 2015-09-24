package powerup.systers.com.datamodel;

public class Avatar {
	private Integer id;
	private Integer face;
	private Integer eyes;
	private Integer hair;
	private Integer clothes;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer iD) {
		id = iD;
	}
	public Integer getFace() {
		return face;
	}
	public void setFace(Integer face) {
		this.face = face;
	}
	public Integer getEyes() {
		return eyes;
	}
	public void setEyes(Integer eyes) {
		this.eyes = eyes;
	}
	public Integer getHair() {
		return hair;
	}
	public void setHair(Integer hair) {
		this.hair = hair;
	}
	public Integer getClothes() {
		return clothes;
	}
	public void setClothes(Integer clothes) {
		this.clothes = clothes;
	}
}
