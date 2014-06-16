package riskyken.utilities.common.tileentities;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import riskyken.utilities.common.InstrumentType;
import riskyken.utilities.common.lib.LibBlockNames;
import riskyken.utilities.common.lib.LibModInfo;

public class TileEntityMusicSequencer extends TileEntityUtilitiesBase {

	public Channel[] sequence;
	
	private int tickCount = 0;
	public int nodeCount = 0;
	public int bmt = 0;
	public InstrumentType instrumentType;

	public boolean[] getNodesData() {
		boolean[] data = new boolean[instrumentType.getChannelCount() * 16];
		
		for (int i = 0; i < instrumentType.getChannelCount() * 16; i++) {
			int thisChannel = 0;
			int thisNote = i;
			while (thisNote >= 16) {
				thisChannel += 1;
				thisNote -= 16;
			}
			
			data[i] = sequence[thisChannel].getNode(thisNote);
		}
		
		return data;
	}
	
	public void updateNodesData(boolean[] nodeData) {
		for (int i = 0; i < nodeData.length; i++) {
			int thisNode = i;
			int thisChannel = 0;
			while (thisNode >= 16) {
				thisChannel += 1;
				thisNode -= 16;
			}
			sequence[thisChannel].setNode(thisNode, nodeData[i]);
		}
	}
	
	public void updateNodeData(int nodeId, boolean nodeData) {
		int thisNode = nodeId;
		int thisChannel = 0;
		while (thisNode >= 16) {
			thisChannel += 1;
			thisNode -= 16;
		}
		sequence[thisChannel].setNode(thisNode, nodeData);
	}
	
	public void toggleNodeData(int nodeId, boolean nodeData) {
		int thisNode = nodeId;
		int thisChannel = 0;
		while (thisNode >= 16) {
			thisChannel += 1;
			thisNode -= 16;
		}
		sequence[thisChannel].toggleNode(thisNode);
	}
	
	public TileEntityMusicSequencer(){
		items = new ItemStack[1];
		instrumentType = InstrumentType.DRUM_KIT_DANCE_2;
		
		sequence = new Channel[instrumentType.getChannelCount()];
		
		for (int i = 0; i < instrumentType.getChannelCount(); i++) {
			sequence[i] = new Channel();
		}
	}
	
	@Override
	public void updateEntity() {
		if (worldObj.isRemote) { return; }
		
		tickCount++;
		
		if (tickCount > 4) {
			tickCount = 1;

			nodeCount++;
			
			if (nodeCount > 15) { nodeCount = 0; }
			
			for (int i = 0; i < instrumentType.getChannelCount(); i++) {
				sequence[i].play(nodeCount, instrumentType, i);
			}
		}
	}
	
	@Override
	public String getInventoryName() {
		return LibBlockNames.MUSIC_SEQUENCER;
	}

	public void receiveButtonEvent(short buttonId, EntityPlayer entityPlayer) {
		System.out.println("buttonId " + buttonId);
		toggleNodeData(buttonId, true);
		//beats[buttonId] = !beats[buttonId];
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		/*
		for (int i = 0; i < beats.length; i++) {
			compound.setBoolean("beat " + i, beats[i]);
		}
		*/
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		/*
		for (int i = 0; i < beats.length; i++) {
			beats[i] = compound.getBoolean("beat " + i);
		}
		*/
	}
	
	//16 = bar
	//4 = beat
	
	public class Channel {
		public Bar[] bars;
		public Channel() {
			bars = new Bar[1];
			for (int i = 0; i < bars.length; i++) {
				bars[i] = new Bar();
			}
		}
		
		public void toggleNode(int nodeId) {
			int noteNum = nodeId;
			for (int i = 0; i < bars.length; i++) {
				if (noteNum < 16 & noteNum >= 0) {
					bars[i].toggleNode(nodeId);
					break;
				}
				noteNum -= 16;
			}
		}

		public void setNode(int nodeId, boolean data) {
			int noteNum = nodeId;
			for (int i = 0; i < bars.length; i++) {
				if (noteNum < 16 & noteNum >= 0) {
					bars[i].setNode(nodeId, data);
					break;
				}
				noteNum -= 16;
			}
		}

		public boolean getNode(int nodeId) {
			
			int noteNum = nodeId;
			for (int i = 0; i < bars.length; i++) {
				if (noteNum < 16 & noteNum >= 0) {
					return bars[i].getNode(noteNum);
				}
				noteNum -= 16;
			}
			
			return false;
		}

		public void play(int nodeId, InstrumentType instrumentType, int soundId) {
			int noteNum = nodeId;
			for (int i = 0; i < bars.length; i++) {
				if (noteNum < 16 & noteNum >= 0) {
					bars[i].play(noteNum, instrumentType, soundId);
				}
				noteNum -= 16;
			}
		}
	}
	
	public class Bar {
		public boolean[] node;

	    public Bar() {
	    	node = new boolean[16];
			for (int i = 0; i < 16; i++) {
				Random ran = new Random();
				int pie = ran.nextInt(100);
				//node[i] = pie > 90;
			}
		}
	    
		public void toggleNode(int nodeId) {
			node[nodeId] = !node[nodeId];
		}

		public void setNode(int nodeId, boolean data) {
			node[nodeId] = data;
		}

		public boolean getNode(int nodeId) {
			return node[nodeId];
		}

		public void play(int nodeId, InstrumentType instrumentType, int soundId) {
			
			if (node[nodeId]) {
				worldObj.playSoundEffect(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, LibModInfo.ID.toLowerCase() + ":" + instrumentType.getSoundName(soundId), 1f, 1f);
			}
		}
	}
}
