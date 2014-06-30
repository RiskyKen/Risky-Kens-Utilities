package riskyken.utilities.client.gui;

import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;

public class GuiSilentButton extends GuiButton {

	public GuiSilentButton(int id, int left, int top, int width, int height, String text) {
		super(id, left, top, width, height, text);
	}

	@Override
	public void func_146113_a(SoundHandler soundHandler) {}
}
