package cellTest;

public class CellPhone {

		private int id;
		final int mainPriority;
		final int subPriority;
		private double range;
		private double lat;
		private double lng;
		private long value;
		
		
		public CellPhone(double range, double lat, double lng, int mainPri, int subPri) {
			this.range = range;
			this.lat = lat;
			this.lng = lng;
			this.mainPriority = mainPri;
			this.subPriority = subPri;
			this.value = ((3 + 1 - mainPri)^2 * 1000 + 999 + 1 - subPri)*1000;
		}

		public long getValue() {
			return value;
		}

		public void setValue(long value) {
			this.value = value;
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

		public int getMainPriority() {
			return mainPriority;
		}

		public int getSubPriority() {
			return subPriority;
		}
		
		
	}

