package net.gudenau.minecraft.largeenchants.mixin;

import net.minecraft.util.Language;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Map;

@Mixin(Language.class)
public abstract class LanguageMixin{
    @Shadow @Final private Map<String, String> translations;

    @Inject(
        method = "getTranslation",
        at = @At(
            value = "RETURN",
            shift = At.Shift.BEFORE
        ),
        locals = LocalCapture.CAPTURE_FAILHARD,
        cancellable = true
    )
    private void getTranslation$Map$get(String key, CallbackInfoReturnable<String> callbackInfo, String translated){
        if(translated == null && key.startsWith("enchantment.level.")){
            int level = Integer.parseInt(key.substring(18));
            String value = generateRomanNumeral(level);
            //translations.put(key, value);
            callbackInfo.setReturnValue(value);
        }
    }

    private static final int[] DECIMAL = new int[]{
        1000000, 900000, 500000, 400000, 100000, 90000, 50000, 40000, 10000, 9000, 5000, 4000,
        1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1
    };
    private static final String[] ROMAN = new String[]{
        "m", "cm", "d", "cd", "c", "xc", "l", "xl", "x", "ix", "v", "iv",
        "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"
    };
    private String generateRomanNumeral(int number){
        if(number > 3888888){
            return "" + number;
        }
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < DECIMAL.length; i++){
            int decimal = DECIMAL[i];
            String roman = ROMAN[i];
            while(number > decimal){
                builder.append(roman);
                number -= decimal;
            }
        }
        return builder.toString();
    }
}
