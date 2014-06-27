package riskyken.utilities.common.items;

import java.util.List;

import riskyken.utilities.RiskyKensUtilities;
import riskyken.utilities.client.model.wings.ModelWings;
import riskyken.utilities.common.lib.LibItemNames;
import riskyken.utilities.common.lib.LibModInfo;
import riskyken.utilities.proxies.ClientProxy;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Chat;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemArmorWings extends ItemArmor {

	public ItemArmorWings(int renderIndex, int armorType) {
		super(ArmorMaterial.IRON, renderIndex, armorType);
		setCreativeTab(RiskyKensUtilities.tabRiskyKensUtilities);
		setUnlocalizedName(LibItemNames.WINGS);
		setHasSubtypes(false);
	}
	
	@Override
	public Item setUnlocalizedName(String name) {
		GameRegistry.registerItem(this, "item." + name);
		return super.setUnlocalizedName(name);
	}
	
	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List hoverText, boolean par4) {
		hoverText.add("§" + EnumChatFormatting.DARK_RED.getFormattingCode() + "TEST ITEM");
		hoverText.add("§" + EnumChatFormatting.DARK_RED.getFormattingCode() + "DO NOT USE");
		super.addInformation(itemStack, player, hoverText, par4);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register) {
		itemIcon = register.registerIcon(LibModInfo.ID + ":" + "wings");
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		return  LibModInfo.ID + ":" + "textures/armor/big-white-wings.png";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
		ModelBiped armorModel = null;
		if(itemStack != null) {
			if(itemStack.getItem() instanceof ItemArmorWings) {
				return ClientProxy.bigWings;
			}
		}
		return null;
	}

}
