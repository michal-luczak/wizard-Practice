package me.taison.wizardpractice.gui.party;

import me.taison.wizardpractice.gui.EmptySlotFillerGuiItem;
import me.taison.wizardpractice.gui.GuiMenu;
import me.taison.wizardpractice.gui.gametypeselector.GameMapType;
import me.taison.wizardpractice.gui.gametypeselector.GameSelectorGuiItem;
import me.taison.wizardpractice.utilities.chat.StringUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.stream.IntStream;

public class PartyGui extends GuiMenu {

    private final EmptySlotFillerGuiItem BLACK_GLASS = new EmptySlotFillerGuiItem(" ", new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1));
    private final EmptySlotFillerGuiItem GRAY_GLASS = new EmptySlotFillerGuiItem(" ", new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1));


    public PartyGui() {
        super(StringUtils.color("&aZarzadzanie druzyna."), GuiMenu.Size.SIX_LINE);

        IntStream.range(0, 54).forEach(blackGrayColor -> {
            if(blackGrayColor % 2 == 0){
                setItem(blackGrayColor, this.BLACK_GLASS);
                return;
            }
            setItem(blackGrayColor, this.GRAY_GLASS);
        });
        Arrays.stream(PartyItems.values()).forEach(partyItem ->
                setItem(partyItem.getSlotId(), new PartyGuiItem(partyItem, this)));
    }

}
