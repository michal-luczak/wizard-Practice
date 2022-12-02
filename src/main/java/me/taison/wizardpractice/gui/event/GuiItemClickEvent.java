package me.taison.wizardpractice.gui.event;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class GuiItemClickEvent {

    private final Player player;
    private final ClickType clickType;

    private final boolean isLeftClick;
    private final boolean isRightClick;
    private final boolean isShift;

    private boolean goBack = false;
    private boolean close = false;
    private boolean update = false;

    public GuiItemClickEvent(Player player, ClickType type, boolean isRightClick, boolean isLeftClick, boolean isShift) {
        this.player = player;
        this.clickType = type;

        this.isRightClick = isRightClick;
        this.isLeftClick = isLeftClick;
        this.isShift = isShift;
    }

    public boolean isLeftClick() {
        return isLeftClick;
    }

    public boolean isRightClick() {
        return isRightClick;
    }

    public boolean isShift() {
        return isShift;
    }

    public Player getPlayer() {
        return player;
    }

    public ClickType getClickType() {
        return clickType;
    }

    public boolean willGoBack() {
        return goBack;
    }

    public void setWillGoBack(boolean goBack) {
        this.goBack = goBack;
        if (goBack) {
            close = false;
            update = false;
        }
    }

    public boolean willClose() {
        return close;
    }

    public void setWillClose(boolean close) {
        this.close = close;
        if (close) {
            goBack = false;
            update = false;
        }
    }

    public boolean willUpdate() {
        return update;
    }

    public void setWillUpdate(boolean update) {
        this.update = update;
        if (update) {
            goBack = false;
            close = false;
        }
    }

}
