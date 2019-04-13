/*
 * This file is part of BeezigForge.
 *
 * BeezigForge is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * BeezigForge is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with BeezigForge.  If not, see <http://www.gnu.org/licenses/>.
 */

package eu.beezig.forge.settings;

import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.GuiConfigEntries;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.client.config.IConfigElement;

public class BeezigEntry extends GuiConfigEntries.ButtonEntry {

    protected final boolean beforeValue;
    protected boolean       currentValue;

    public BeezigEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement configElement)
    {
        super(owningScreen, owningEntryList, configElement);
        this.beforeValue = Boolean.valueOf(configElement.get().toString());
        this.currentValue = beforeValue;
        this.btnValue.enabled = enabled();
        updateValueButtonText();
    }

    @Override
    public void updateValueButtonText()
    {
        this.btnValue.displayString = currentValue ? "Enabled" : "Disabled";
        btnValue.packedFGColour = currentValue ? GuiUtils.getColorCode('a', true) : GuiUtils.getColorCode('c', true);
    }

    @Override
    public void valueButtonPressed(int slotIndex)
    {
        if (enabled())
            currentValue = !currentValue;
    }

    @Override
    public boolean isDefault()
    {
        return currentValue == Boolean.valueOf(configElement.getDefault().toString());
    }

    @Override
    public void setToDefault()
    {
        if (enabled())
        {
            currentValue = Boolean.valueOf(configElement.getDefault().toString());
            updateValueButtonText();
        }
    }

    @Override
    public boolean isChanged()
    {
        return currentValue != beforeValue;
    }

    @Override
    public void undoChanges()
    {
        if (enabled())
        {
            currentValue = beforeValue;
            updateValueButtonText();
        }
    }

    @Override
    public boolean saveConfigElement()
    {
        if (enabled() && isChanged())
        {
            configElement.set(currentValue);
            return configElement.requiresMcRestart();
        }
        return false;
    }

    @Override
    public Boolean getCurrentValue()
    {
        return currentValue;
    }

    @Override
    public Boolean[] getCurrentValues() {
        return new Boolean[]{getCurrentValue()};
    }
}
