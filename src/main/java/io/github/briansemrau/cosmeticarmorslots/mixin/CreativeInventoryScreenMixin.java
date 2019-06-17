package io.github.briansemrau.cosmeticarmorslots.mixin;

import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.container.Slot;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * This mixin removes the bug where extra slots mixed into PlayerContainer show up on the hotbar.
 */
@Mixin(CreativeInventoryScreen.class)
public abstract class CreativeInventoryScreenMixin extends AbstractInventoryScreen<CreativeInventoryScreen.CreativeContainer> {

    public CreativeInventoryScreenMixin(CreativeInventoryScreen.CreativeContainer container_1, PlayerInventory playerInventory_1, Component component_1) {
        super(container_1, playerInventory_1, component_1);
    }

    @Inject(method = "setSelectedTab",
            at = @At(value = "FIELD",
                    target = "Lnet/minecraft/client/gui/screen/ingame/CreativeInventoryScreen;deleteItemSlot:Lnet/minecraft/container/Slot;",
                    opcode = Opcodes.PUTFIELD,
                    shift = At.Shift.BEFORE))
    private void onSetDeleteItemSlot(CallbackInfo ci) {
        for (int i = 0; i < this.minecraft.player.playerContainer.slotList.size(); ++i) {
            if (i > 45) {
                Slot slot = this.container.slotList.get(i);
                slot.xPosition = -10000;
                slot.yPosition = -10000;
            }
        }
    }

}
