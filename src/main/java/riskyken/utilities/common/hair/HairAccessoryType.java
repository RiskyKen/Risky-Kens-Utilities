package riskyken.utilities.common.hair;

public enum HairAccessoryType {
	//NONE(0, "None"),
	BOW(1, "Bow"),
	BAND(2, "Band"),
	FLOWER_CROWN(3, "Flower Crown"),
	FLOWER(4, "Flower"),
	BUNNY_EARS(5, "Bunny Ears"),
	CAT_EARS(6, "Cat Ears"),
	TIARA(7, "Tiara");
	
	int id;
	String name;
	
	private HairAccessoryType(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public int getFlagId() {
		if (id == 0) { return 0; }
		int flag = 1;
		for (int i = 1; i < id; i++) {
			flag *= 2;
		}

		return flag;
	}
	
	public String getName() {
		return name;
	}
}
