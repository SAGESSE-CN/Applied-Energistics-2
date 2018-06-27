/*
 * This file is part of Applied Energistics 2.
 * Copyright (c) 2013 - 2018, AlgorithmX2, All rights reserved.
 *
 * Applied Energistics 2 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Applied Energistics 2 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Applied Energistics 2.  If not, see <http://www.gnu.org/licenses/lgpl>.
 */

package appeng.fluids.client.gui;


import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;

import appeng.client.gui.AEBaseGui;
import appeng.client.gui.widgets.GuiFluidTank;
import appeng.client.gui.widgets.GuiTabButton;
import appeng.core.localization.GuiText;
import appeng.core.sync.GuiBridge;
import appeng.core.sync.network.NetworkHandler;
import appeng.core.sync.packets.PacketSwitchGuis;
import appeng.fluids.container.ContainerFluidInterface;
import appeng.fluids.helper.DualityFluidInterface;
import appeng.fluids.helper.IFluidInterfaceHost;


public class GuiFluidInterface extends AEBaseGui
{
	public final static int ID_BUTTON_TANK = 222;

	private final IFluidInterfaceHost host;
	private GuiTabButton priority;

	public GuiFluidInterface( final InventoryPlayer ip, final IFluidInterfaceHost te )
	{
		super( new ContainerFluidInterface( ip, te ) );
		this.ySize = 231;
		this.host = te;
	}

	@Override
	public void initGui()
	{
		super.initGui();

		for( int i = 0; i < DualityFluidInterface.NUMBER_OF_TANKS; ++i )
		{
			this.buttonList.add( new GuiFluidTank( ID_BUTTON_TANK + i, this.host.getDualityFluidInterface()
					.getTank( i ), this.getGuiLeft() + 7 + 18 * i, this.getGuiTop() + 16 + 8, 16, 80 ) );
		}

		this.priority = new GuiTabButton( this.getGuiLeft() + 154, this.getGuiTop(), 2 + 4 * 16, GuiText.Priority.getLocal(), this.itemRender );
		this.buttonList.add( this.priority );
	}

	@Override
	public void drawFG( int offsetX, int offsetY, int mouseX, int mouseY )
	{
		this.fontRenderer.drawString( this.getGuiDisplayName( GuiText.FluidInterface.getLocal() ), 8, 6, 4210752 );
		this.fontRenderer.drawString( GuiText.inventory.getLocal(), 8, this.ySize - 96 + 3, 4210752 );
	}

	@Override
	public void drawBG( int offsetX, int offsetY, int mouseX, int mouseY )
	{
		this.bindTexture( "guis/interfacefluid.png" );
		this.drawTexturedModalRect( offsetX, offsetY, 0, 0, this.xSize, this.ySize );
	}

	@Override
	protected void actionPerformed( final GuiButton btn ) throws IOException
	{
		super.actionPerformed( btn );

		if( btn == this.priority )
		{
			NetworkHandler.instance().sendToServer( new PacketSwitchGuis( GuiBridge.GUI_PRIORITY ) );
		}
	}
}
