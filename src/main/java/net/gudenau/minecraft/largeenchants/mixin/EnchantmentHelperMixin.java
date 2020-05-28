package net.gudenau.minecraft.largeenchants.mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;
import java.util.Map;

@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin{
    @Redirect(
        method = "getLevel",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/util/math/MathHelper;clamp(III)I"
        )
    )
    private static int getLevel$MathHelper$clamp(int value, int min, int max){
        return value;
    }

    @Redirect(
        method = "set",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/nbt/CompoundTag;putShort(Ljava/lang/String;S)V"
        )
    )
    private static void set$CompoundTag$putShort(CompoundTag tag, String key, short shortLevel){}

    @Inject(
        method = "set",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/nbt/CompoundTag;putShort(Ljava/lang/String;S)V"
        ),
        locals = LocalCapture.CAPTURE_FAILHARD
    )
    private static void set$CompoundTag$putShort(
        Map<Enchantment, Integer> enchantments, ItemStack stack, CallbackInfo callbackInfo,
        ListTag listTag, Iterator<Map.Entry<Enchantment, Integer>> iterator, Map.Entry<Enchantment, Integer> entry, Enchantment enchantment, int level, CompoundTag tag, String dunno1, int dunno2
    ){
        tag.putInt("lvl", level);
    }
}
