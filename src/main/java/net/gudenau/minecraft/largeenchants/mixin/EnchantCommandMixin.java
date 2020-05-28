package net.gudenau.minecraft.largeenchants.mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.server.command.EnchantCommand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EnchantCommand.class)
public abstract class EnchantCommandMixin{
    @Redirect(
        method = "execute",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/enchantment/Enchantment;getMaximumLevel()I"
        )
    )
    private static int execute$Enchantment$getMaximumLevel(Enchantment enchantment){
        return Integer.MAX_VALUE;
    }
}
