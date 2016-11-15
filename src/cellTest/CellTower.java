package cellTest;

import java.util.List;

public class CellTower {
	private List<CellPhone> phonesServiced;
	private int id;
	private double range;
	private double lat;
	private long score;
	private double lng;
	private GridLocation towerLocation;
	
		public CellTower(double range, double lat, double lng) {
		this.range = range;
		this.lat = lat;
		this.lng = lng;
	}
	
	public double metersBetween(double otherLat, double otherLng) {
		final double R = 6372800; //Earth Radius in Kilometers
        double dLat = Math.toRadians(this.lat - otherLat);
        double dLon = Math.toRadians(this.lng - otherLng);
        double latRad = Math.toRadians(this.lat);
        double otherLatRad= Math.toRadians(otherLat);
 
        double a = Math.pow(Math.sin(dLat / 2),2) + Math.pow(Math.sin(dLon / 2),2) * Math.cos(latRad) * Math.cos(otherLatRad);
        double c = 2 * Math.asin(Math.sqrt(a));
        return R * c;
    }
	
	public boolean isOverlapping(CellPhone phone) {
		if (metersBetween(phone.getLat(), phone.getLng()) <= (phone.getRange()+this.range)) {
			return true;
		} else {
			return false;
		}
	}

	public long getScore() {
		return score;
	}

	public void setScore(long score) {
		this.score = score;
	}

	public List<CellPhone> getPhonesServiced() {
		return phonesServiced;
	}
	
	public List<CellPhone> getphonesServiced() {
		return phonesServiced;
	}

	public void setPhonesServiced(List<CellPhone> phonesServiced) {
		this.phonesServiced = phonesServiced;
	}
	
	public GridLocation getTowerLocation() {
		return towerLocation;
	}

	public void setTowerLocation(GridLocation towerLocation) {
		this.towerLocation = towerLocation;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getRange() {
		return range;
	}

	public void setRange(double range) {
		this.range = range;
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
	
	
}
