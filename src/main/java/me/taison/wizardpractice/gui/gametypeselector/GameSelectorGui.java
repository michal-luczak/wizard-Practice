package me.taison.wizardpractice.gui.gametypeselector;

import me.taison.wizardpractice.gui.EmptySlotFillerGuiItem;
import me.taison.wizardpractice.gui.GuiMenu;
import me.taison.wizardpractice.utilities.chat.StringUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.stream.IntStream;

public class GameSelectorGui extends GuiMenu {

    private final EmptySlotFillerGuiItem BLACK_GLASS = new EmptySlotFillerGuiItem(" ", new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1));
    private final EmptySlotFillerGuiItem GRAY_GLASS = new EmptySlotFillerGuiItem(" ", new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1));


    public GameSelectorGui() {
        super(StringUtils.color("&aWybierz rodzaj pojedynku"), Size.SIX_LINE);

        IntStream.range(0, 54).forEach(blackGrayColor -> {
            if(blackGrayColor % 2 == 0){
                setItem(blackGrayColor, this.BLACK_GLASS);
                return;
            }
            setItem(blackGrayColor, this.GRAY_GLASS);
        });
        Arrays.stream(GameMapType.values()).forEach(gameMapType ->
                setItem(gameMapType.getSlotId(), new GameSelectorGuiItem(gameMapType, this)));
    }
}
