package riskyken.utilities.common.hair;

public enum HairStyleType {

	NONE(0, "None"),
	LONG(1, "Long"),
	PONYTAIL(2, "Ponytail"),
	TWIN_TAILS(3, "Twin Tails"),
	ODANGO(4, "Odango"),
	BUN(5,"Bun"),
	BUNS(6, "Buns"),
	UPDO(7, "Updo"),
	MOHICAN(8, "Mohican"),
	AFRO(9, "Afro");
	//PLEAT(10, "Pleat");
	
	int id;
	String name;
	
	HairStyleType(int id, String name) {
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
