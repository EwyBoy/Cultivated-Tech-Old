package com.ewyboy.cultivatedtech.common.items;

import com.google.common.collect.Multimap;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemSword;

/**
 * Created by EwyBoy
 */
public class ItemTestSword extends ItemAxe {

    private final float attackDamage;
    private final ToolMaterial material;

    public ItemTestSword(ToolMaterial material) {
        super(material);
        this.attackDamage = 1.0F + material.getDamageVsEntity();
        this.material = material;
    }

    @Override
    public float getDamageVsEntity() {
        return super.getDamageVsEntity();
    }

    @Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

        if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double)this.attackDamage, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -15D, 10));
        }

        return multimap;
    }
}
