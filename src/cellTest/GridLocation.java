package cellTest;

import java.util.List;

public class GridLocation {
	public double lat;
	public double lng;
	public boolean hasTower;
	public CellTower towerHere;
	public long score;
	public List<CellPhone> phonesHere;
	
	public GridLocation(double lat, double lng, long score) {
		this.lat = lat;
		this.lng = lng;
		this.score = score;
	}
	
	public List<CellPhone> getPhonesHere() {
		return phonesHere;
	}

	public void setPhonesHere(List<CellPhone> phonesHere) {
		this.phonesHere = phonesHere;
	}

	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	public boolean isHasTower() {
		return hasTower;
	}
	public void setHasTower(boolean hasTower) {
		this.hasTower = hasTower;
	}
	public CellTower getTowerHere() {
		return towerHere;
	}
	public void setTowerHere(CellTower towerHere) {
		this.towerHere = towerHere;
	}

	public long getScore() {
		return score;
	}

	public void setScore(long score) {
		this.score = score;
	}
	
	
	
}
