package riskyken.utilities.common.hair;

public enum DyeType {

	NONE(0, 0),
	WHITE(1, 16777215),
	BLACK(2, 1644825);
	
	int flagId;
	int colour;
	
	DyeType(int flagId, int colour) {
		this.flagId = flagId;
		this.colour = colour;
	}
}
