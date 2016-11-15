package cellTest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;





public class TowerLocations {
	private List<CellTower> towerList;
	private List<CellPhone> phoneList;
	private TowerGrid towerGrid;
	private int Score;
	private List<CellTower> placedTowers = new ArrayList<CellTower>();
	
	public int getScore() {
		return Score;
	}

	public void setScore(int score) {
		Score = score;
	}

	public TowerGrid getTowerGrid() {
		return towerGrid;
	}

	public void setTowerGrid(TowerGrid towerGrid) {
		this.towerGrid = towerGrid;
	}

	public void setTowerList(List<CellTower> towerList) {
		this.towerList = towerList;
	}

	public List<CellPhone> getPhoneList() {
		return phoneList;
	}

	public void setPhoneList(List<CellPhone> phoneList) {
		this.phoneList = phoneList;
	}


	 public List<CellTower> getTowerList() {
        return towerList;
    }
	 
	 

		public List<CellTower> getPlacedTowers() {
		return placedTowers;
	}

	public void setPlacedTowers(List<CellTower> placedTowers) {
		this.placedTowers = placedTowers;
	}

		public void makeInitialGuess() {
			int numTowers = this.towerList.size();
			int count = 0;
			while (count < numTowers) {
			Collections.sort(this.towerGrid.getGrid(), new Comparator<GridLocation>() {
				public int compare(GridLocation g1, GridLocation g2) {
					Long score1 = ((GridLocation) g1).getScore();
					Long score2 = ((GridLocation) g2).getScore();

					 return score2.compareTo(score1);
				
				}
			});
			
			List<CellPhone> phonesServiced = this.towerGrid.getGrid().get(0).getPhonesHere(); 		
			this.towerGrid.getGrid().get(0).setHasTower(true);
			CellTower placedTower = this.getTowerList().get(0);
			placedTower.setLat(this.towerGrid.getGrid().get(0).getLat());
			placedTower.setLng(this.towerGrid.getGrid().get(0).getLng());
			placedTower.setScore(this.towerGrid.getGrid().get(0).getScore());
			placedTower.setPhonesServiced(phonesServiced);
			this.placedTowers.add(placedTower);
			this.getTowerList().remove(0);			
			towerGrid.setGrid(towerGrid.recalcScores(this.towerGrid.getGrid(), phonesServiced));
			count++;
		}
	}
}
