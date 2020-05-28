package net.gudenau.minecraft.largeenchants.mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin{
    @Redirect(
        method = "addEnchantment",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/nbt/CompoundTag;putShort(Ljava/lang/String;S)V"
        )
    )
    private void addEnchantment$CompoundTag$putShort(CompoundTag tag, String key, short shortLevel, Enchantment enchantment, int level){
        tag.putInt(key, level);
    }
}
