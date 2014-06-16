package riskyken.utilities.common;

public enum InstrumentType {
	DRUM_KIT_ELECTRO(0, 9, "Drum Kit - Electro",
			new String[] {"BD", "c Hat", "clap", "crash", "o_hat", "ride", "SD", "tom1", "tom2"},
			new String[] {"electro_BD", "electro_cHats", "electro_clap", "electro_crash", "electro_oHats", "electro_Ride", "electro_SD", "electro_Tom1", "electro_Tom2"}),
	
	DRUM_KIT_DANCE_1(1, 9, "Drum Kit - Dance 1",
			new String[] {"BD", "c Hat", "clap", "crash", "o_hat", "ride", "SD", "tom1", "tom2"},
			new String[] {"dance1_BD", "dance1_cHat", "dance1_clap", "dance1_crash", "dance1_oHat", "dance1_Ride", "dance1_SD", "dance1_Tom1", "dance1_Tom2"}),
	
	DRUM_KIT_DANCE_2(2, 9, "Drum Kit - Dance 2",
			new String[] {"BD", "c Hat", "clap", "crash", "o_hat", "ride", "SD", "tom1", "tom2"},
			new String[] {"dance2_BD", "dance2_cHat", "dance2_clap", "dance2_crash", "dance2_oHat", "dance2_Ride", "dance2_SD", "dance2_Tom1", "dance2_Tom2"});
	
	int channelCount;
	String[] channelNames;
	String[] soundNames;
	
	InstrumentType(int id, int channelCount, String name, String[] channelNames, String[] soundNames) {
		this.channelCount = channelCount;
		this.channelNames = channelNames;
		this.soundNames = soundNames;
	}
	
	public String getChannelName(int id) {
		return channelNames[id];
	}
	
	public String getSoundName(int id) {
		return soundNames[id];
	}
	
	public int getChannelCount() {
		return channelCount;
	}
}
