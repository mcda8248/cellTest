package cellTest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.json.*;

public class TestApp {

	private static final File outputDir = new File("data/celltowers/unsolved/");

	protected TowerLocations solution;

	public static void main(String[] args) {
		new TestApp().generate();
	}

	public void generate() {
		solution = new TowerLocations();
		writeTowerLocations(10, 2);
	}

	private void writeTowerLocations(int phones, int towers) {
		// String outputFileName = towers + "towerLocations.xml";
		// File outputFile = new File(outputDir, outputFileName);
		TowerLocations locations = createTowerLocations(phones, towers);
		// solution.writeSolution(locations, outputFile);
		locations.makeInitialGuess();
		List<CellTower> initialGuess = locations.getPlacedTowers();
		JSONObject featureCollection = new JSONObject();
		try {
			featureCollection.put("type", "FeatureCollection");
			JSONArray featureList = new JSONArray();
			List<CellPhone> phoneList = locations.getPhoneList();
			List<List<GridLocation>> latLines = locations.getTowerGrid().getLatLines();
			int count = 0;
			for (CellPhone phone : phoneList) {

				JSONObject point = new JSONObject();
				point.put("type", "Point");

				JSONArray coord = new JSONArray("[" + phone.getLng() + "," + phone.getLat() + "]");
				point.put("coordinates", coord);

				JSONObject feature = new JSONObject();
				feature.put("geometry", point);
				JSONObject properties = new JSONObject();
				properties.put("id", phone.getId());
				feature.put("type", "Feature");
				feature.put("properties", properties);
				featureList.put(feature);
			}
			for (List<GridLocation> latLine : latLines) {
				JSONObject lineString = new JSONObject();
				lineString.put("type", "LineString");

				double[][] gridArray = new double[latLine.size()][2];
				for (int i = 0; i < latLine.size(); i++) {
					gridArray[i][0] = latLine.get(i).getLng();
					gridArray[i][1] = latLine.get(i).getLat();
				}
				
				JSONArray coord = new JSONArray(gridArray);
				

				
				lineString.put("coordinates", coord);
				JSONObject feature = new JSONObject();
				feature.put("geometry", lineString);
				JSONObject properties = new JSONObject();
				feature.put("type", "Feature");
				feature.put("properties", properties);
				// System.out.println(feature.toString());
				featureList.put(feature);
			}
			for (CellTower placedTower : initialGuess) {
				JSONObject point = new JSONObject();
				point.put("type", "Point");

				JSONArray coord = new JSONArray("[" + placedTower.getLng() + "," + placedTower.getLat() + "]");
				point.put("coordinates", coord);

				JSONObject feature = new JSONObject();
				feature.put("geometry", point);
				JSONObject properties = new JSONObject();
				properties.put("marker-color", "#32CD32");
				feature.put("type", "Feature");
				feature.put("properties", properties);
				featureList.put(feature);
			}
			featureCollection.put("features", featureList);
		} catch (JSONException e) {
			System.out.println("error");
		}
		
		System.out.println(featureCollection.toString());
		
		for (CellTower placed : initialGuess) {
			System.out.println("This tower is at " + placed.getLat() + " lat and " + placed.getLng()
					+ " lng. This tower has a total score of " + placed.getScore());
			for (CellPhone phone : placed.getphonesServiced()) {
				System.out.println("This tower services cell phone id " + phone.getId());
			}
		}
	}

	public TowerLocations createTowerLocations(int phones, int towers) {
		TowerLocations locations = new TowerLocations();
		locations.setPhoneList(createPhoneList(phones));
		locations.setTowerList(createTowerList(towers));
		locations.setTowerGrid(createTowerGrid(4000.00, 36.00, -106.00, 35.00, -105.00, locations.getPhoneList()));
		return locations;
	}

	private TowerGrid createTowerGrid(double range, double topLeftCornerLat, double topLeftCornerLng,
			double bottomRightCornerLat, double topRightCornerLng, List<CellPhone> phones) {
		TowerGrid newGrid = new TowerGrid(range, topLeftCornerLat, topLeftCornerLng, bottomRightCornerLat,
				topRightCornerLng, phones);
		return newGrid;
	}

	private List<CellPhone> createPhoneList(int numPhones) {
		int n = numPhones;
		List<CellPhone> phoneList = new ArrayList<CellPhone>(n);
		for (int i = 0; i < n; i++) {
			CellPhone phone = new CellPhone(4000.00, (35 + Math.random()), (-106 + Math.random()),
					(ThreadLocalRandom.current().nextInt(0, 3 + 1)), (ThreadLocalRandom.current().nextInt(0, 999 + 1)));
			phone.setId(i);
			phoneList.add(phone);
			System.out.println("We Created a Cell Phone. Its range is: " + phone.getRange() + " its latitude is: "
					+ phone.getLat() + " its longitude is: " + phone.getLng() + " and its score is: " + phone.getValue()
					+ " and also its id is " + phone.getId());
		}
		return phoneList;
	}

	private List<CellTower> createTowerList(int numTowers) {
		int n = numTowers;
		List<CellTower> towerList = new ArrayList<CellTower>(n);
		for (int i = 0; i < n; i++) {
			CellTower tower = new CellTower(12000.00, 0.0, 0.0);
			tower.setId(i);
			towerList.add(tower);
		}
		return towerList;
	}

}
