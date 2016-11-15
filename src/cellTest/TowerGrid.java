package cellTest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TowerGrid {

	private double gridSize;
	private ArrayList<GridLocation> grid = new ArrayList<GridLocation>();
	private int gridOffSet = 0;
	private List<List<GridLocation>> latLines = new ArrayList<List<GridLocation>>();

	public TowerGrid(double range, double topLeftCornerLat, double topLeftCornerLng, double botLeftCornerLat,
			double topRightCornerLng, List<CellPhone> phones) {
		double areaOfSearch = metersBetweenTwoPoints(topLeftCornerLat, topLeftCornerLng, topLeftCornerLat,
				topRightCornerLng);
		gridSize = Math.sqrt((range * range) / 2);
		double currentLng = topLeftCornerLng;
		double currentLat = topLeftCornerLat;
		int iteration = 0;
		List<GridLocation> currentLine = new ArrayList<GridLocation>();
		System.out.println("We Created a grid. Its Grid Size is: " + gridSize + " and the length of one side is: "
				+ areaOfSearch + " therefore we will place " + (int) ((areaOfSearch / gridSize) + 1)
				+ " points down before moving down for a total of "
				+ (int) ((areaOfSearch / gridSize) + 1) * ((areaOfSearch / gridSize) + 1));
		while (currentLat > botLeftCornerLat) {
			List<CellPhone> phonesHere = new ArrayList<CellPhone>();
			if (currentLng > topRightCornerLng) {
				this.latLines.add(currentLine);

				currentLine = new ArrayList<GridLocation>();

				// System.out.println("We say "+currentLng+"is larger than
				// "+topRightCornerLng+" so we're moving down to "+currentLat+".
				// move down #"+iteration);
				currentLng = topLeftCornerLng;
				currentLat = distFromPointWithDistance(currentLat, currentLng, gridSize, 180.0)[0];
				iteration++;
			}
			long gridLocationScore = 0;
			for (CellPhone phone : phones) {
				if (metersBetweenTwoPoints(currentLat, currentLng, phone.getLat(), phone.getLng()) <= 12000.00
						+ phone.getRange()) {
					gridLocationScore = gridLocationScore + phone.getValue();
					phonesHere.add(phone);
				}
			}
			GridLocation thisLocation = new GridLocation(currentLat, currentLng, gridLocationScore);
			thisLocation.setPhonesHere(phonesHere);
			grid.add(thisLocation);
			currentLine.add(thisLocation);
			currentLng = distFromPointWithDistance(currentLat, currentLng, gridSize, 90.0)[1];
		}
	}

	public List<List<GridLocation>> getLatLines() {
		return latLines;
	}

	public void setLatLines(List<List<GridLocation>> latLines) {
		this.latLines = latLines;
	}

	public ArrayList<GridLocation> recalcScores(ArrayList<GridLocation> grid, List<CellPhone> phones) {
		for (GridLocation gridLocation : grid) {
			for(Iterator<CellPhone> itr = phones.iterator(); itr.hasNext();){ 
				CellPhone phone = itr.next();
				if (gridLocation.getPhonesHere().contains(phone)) {
					List<CellPhone> newPhones = gridLocation.getPhonesHere();
					itr.remove();
					gridLocation.setPhonesHere(newPhones);
					gridLocation.setScore(gridLocation.getScore()-phone.getValue());
				}
			}
		}
		return grid;
	}

	public double metersBetweenTwoPoints(double thisLat, double thisLng, double thatLat, double thatLng) {
		final double R = 6372800; // Earth Radius in Meters
		double dLat = Math.toRadians(thisLat - thatLat);
		double dLon = Math.toRadians(thisLng - thatLng);
		double latRad = Math.toRadians(thisLat);
		double thatLatRad = Math.toRadians(thatLat);

		double a = Math.pow(Math.sin(dLat / 2), 2)
				+ Math.pow(Math.sin(dLon / 2), 2) * Math.cos(latRad) * Math.cos(thatLatRad);
		double c = 2 * Math.asin(Math.sqrt(a));
		return R * c;
	}

	public double[] distFromPointWithDistance(double lat1Deg, double lng1Deg, double d, double brngDeg) {
		double lat1 = Math.toRadians(lat1Deg);
		double lng1 = Math.toRadians(lng1Deg);
		double brng = Math.toRadians(brngDeg);
		double dRad = d / (6372797.6);

		double lat2 = Math.asin(Math.sin(lat1) * Math.cos(dRad) + Math.cos(lat1) * Math.sin(dRad) * Math.cos(brng));
		double lng2 = lng1 + Math.atan2(Math.sin(brng) * Math.sin(dRad) * Math.cos(lat1),
				Math.cos(dRad) - Math.sin(lat1) * Math.sin(lat2));

		double[] result = new double[2];
		result[0] = Math.toDegrees(lat2);
		result[1] = Math.toDegrees(lng2);
		return result;
	}

	public double getGridSize() {
		return gridSize;
	}

	public void setGridSize(double gridSize) {
		this.gridSize = gridSize;
	}

	public ArrayList<GridLocation> getGrid() {
		return grid;
	}

	public void setGrid(ArrayList<GridLocation> grid) {
		this.grid = grid;
	}

	public int getGridOffSet() {
		return gridOffSet;
	}

	public void setGridOffSet(int gridOffSet) {
		this.gridOffSet = gridOffSet;
	}

}
