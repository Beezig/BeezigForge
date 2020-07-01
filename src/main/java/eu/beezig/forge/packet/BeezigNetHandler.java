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

package eu.beezig.forge.packet;

import eu.beezig.forge.API;
import eu.beezig.forge.api.BeezigAPIImpl;
import eu.beezig.forge.listener.games.timv.EnderchestsListener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.server.*;
import eu.beezig.forge.listener.games.cai.TitleListener;

import java.lang.reflect.Field;

public class BeezigNetHandler extends NetHandlerPlayClient {

    private NetHandlerPlayClient parent;
    private BeezigAPIImpl api;


    public BeezigNetHandler(NetHandlerPlayClient parent) {
        super(Minecraft.getMinecraft(), getGuiScreen(parent), parent.getNetworkManager(), Minecraft.getMinecraft().thePlayer.getGameProfile());
        this.parent = parent;
        this.api = API.inst;
    }

    private static GuiScreen getGuiScreen(final NetHandlerPlayClient parent) {
        for (final Field field : parent.getClass().getDeclaredFields()) {
            if (field.getType().equals(GuiScreen.class)) {
                field.setAccessible(true);
                try {
                    return (GuiScreen)field.get(parent);
                }
                catch (Exception e) {
                    return null;
                }
            }
        }
        return null;
    }
    
    @Override
    public void handleJoinGame(S01PacketJoinGame packetIn) {
        if(api.onPacketReceived(0x01, packetIn.toString()))
        parent.handleJoinGame(packetIn);
    }

    @Override
    public void handleSpawnObject(S0EPacketSpawnObject packetIn) {
        if(api.onPacketReceived(0x0E, packetIn.toString()))
        parent.handleSpawnObject(packetIn);
    }

    @Override
    public void handleSpawnExperienceOrb(S11PacketSpawnExperienceOrb packetIn) {
        if(api.onPacketReceived(0x11, packetIn.toString()))
        parent.handleSpawnExperienceOrb(packetIn);
    }

    @Override
    public void handleSpawnGlobalEntity(S2CPacketSpawnGlobalEntity packetIn) {
        if(api.onPacketReceived(0x2C, packetIn.toString()))
        parent.handleSpawnGlobalEntity(packetIn);
    }

    @Override
    public void handleSpawnPainting(S10PacketSpawnPainting packetIn) {
        if(api.onPacketReceived(0x10, packetIn.toString()))
        parent.handleSpawnPainting(packetIn);
    }

    @Override
    public void handleEntityVelocity(S12PacketEntityVelocity packetIn) {
        if(api.onPacketReceived(0x12, packetIn.toString()))
        parent.handleEntityVelocity(packetIn);
    }

    @Override
    public void handleEntityMetadata(S1CPacketEntityMetadata packetIn) {
        if(api.onPacketReceived(0x1C, packetIn.toString()))
        parent.handleEntityMetadata(packetIn);
    }

    @Override
    public void handleSpawnPlayer(S0CPacketSpawnPlayer packetIn) {
        if(api.onPacketReceived(0x0C, packetIn.toString()))
        parent.handleSpawnPlayer(packetIn);
    }

    @Override
    public void handleEntityTeleport(S18PacketEntityTeleport packetIn) {
        if(api.onPacketReceived(0x18, packetIn.toString()))
        parent.handleEntityTeleport(packetIn);
    }

    @Override
    public void handleHeldItemChange(S09PacketHeldItemChange packetIn) {
        if(api.onPacketReceived(0x09, packetIn.toString()))
        parent.handleHeldItemChange(packetIn);
    }

    @Override
    public void handleEntityMovement(S14PacketEntity packetIn) {
        if(api.onPacketReceived(0x14, packetIn.toString()))
        parent.handleEntityMovement(packetIn);
    }

    @Override
    public void handleEntityHeadLook(S19PacketEntityHeadLook packetIn) {
        if(api.onPacketReceived(0x19, packetIn.toString()))
        parent.handleEntityHeadLook(packetIn);
    }

    @Override
    public void handleDestroyEntities(S13PacketDestroyEntities packetIn) {
        if(api.onPacketReceived(0x13, packetIn.toString()))
        parent.handleDestroyEntities(packetIn);
    }

    @Override
    public void handlePlayerPosLook(S08PacketPlayerPosLook packetIn) {
        if(api.onPacketReceived(0x08, packetIn.toString()))
        parent.handlePlayerPosLook(packetIn);
    }

    @Override
    public void handleMultiBlockChange(S22PacketMultiBlockChange packetIn) {
        if(api.onPacketReceived(0x22, packetIn.toString()))
        parent.handleMultiBlockChange(packetIn);
    }

    @Override
    public void handleChunkData(S21PacketChunkData packetIn) {
        if(api.onPacketReceived(0x21, packetIn.toString()))
        parent.handleChunkData(packetIn);
    }

    @Override
    public void handleBlockChange(S23PacketBlockChange packetIn) {
        if(api.onPacketReceived(0x23, packetIn.toString()))
        parent.handleBlockChange(packetIn);
    }

    @Override
    public void handleDisconnect(S40PacketDisconnect packetIn) {
        if(api.onPacketReceived(0x40, packetIn.toString()))
        parent.handleDisconnect(packetIn);
    }


    @Override
    public void handleCollectItem(S0DPacketCollectItem packetIn) {
        if(api.onPacketReceived(0x0D, packetIn.toString()))
        parent.handleCollectItem(packetIn);
    }

    @Override
    public void handleChat(S02PacketChat packetIn) {
        if(api.onPacketReceived(0x02, packetIn.toString()))
        parent.handleChat(packetIn);
    }

    @Override
    public void handleAnimation(S0BPacketAnimation packetIn) {
        if(api.onPacketReceived(0x0B, packetIn.toString()))
        parent.handleAnimation(packetIn);
    }

    @Override
    public void handleUseBed(S0APacketUseBed packetIn) {
        if(api.onPacketReceived(0x0A, packetIn.toString()))
        parent.handleUseBed(packetIn);
    }

    @Override
    public void handleSpawnMob(S0FPacketSpawnMob packetIn) {
        if(api.onPacketReceived(0x0F, packetIn.toString()))
        parent.handleSpawnMob(packetIn);
    }

    @Override
    public void handleTimeUpdate(S03PacketTimeUpdate packetIn) {
        if(api.onPacketReceived(0x03, packetIn.toString()))
        parent.handleTimeUpdate(packetIn);
    }

    @Override
    public void handleSpawnPosition(S05PacketSpawnPosition packetIn) {
        if(api.onPacketReceived(0x05, packetIn.toString()))
        parent.handleSpawnPosition(packetIn);
    }

    @Override
    public void handleEntityAttach(S1BPacketEntityAttach packetIn) {
        if(api.onPacketReceived(0x1B, packetIn.toString()))
        parent.handleEntityAttach(packetIn);
    }

    @Override
    public void handleEntityStatus(S19PacketEntityStatus packetIn) {
        if(api.onPacketReceived(0x19, packetIn.toString()))
        parent.handleEntityStatus(packetIn);
    }

    @Override
    public void handleUpdateHealth(S06PacketUpdateHealth packetIn) {
        if(api.onPacketReceived(0x06, packetIn.toString()))
        parent.handleUpdateHealth(packetIn);
    }

    @Override
    public void handleSetExperience(S1FPacketSetExperience packetIn) {
        if(api.onPacketReceived(0x1F, packetIn.toString()))
        parent.handleSetExperience(packetIn);
    }

    @Override
    public void handleRespawn(S07PacketRespawn packetIn) {
        if(api.onPacketReceived(0x07, packetIn.toString()))
        parent.handleRespawn(packetIn);
    }

    @Override
    public void handleExplosion(S27PacketExplosion packetIn) {
        if(api.onPacketReceived(0x27, packetIn.toString()))
        parent.handleExplosion(packetIn);
    }

    @Override
    public void handleOpenWindow(S2DPacketOpenWindow packetIn) {
        if(api.onPacketReceived(0x2D, packetIn.toString()))
        parent.handleOpenWindow(packetIn);
    }

    @Override
    public void handleSetSlot(S2FPacketSetSlot packetIn) {
        if(api.onPacketReceived(0x2F, packetIn.toString()))
        parent.handleSetSlot(packetIn);
    }

    @Override
    public void handleConfirmTransaction(S32PacketConfirmTransaction packetIn) {
        if(api.onPacketReceived(0x32, packetIn.toString()))
        parent.handleConfirmTransaction(packetIn);
    }

    @Override
    public void handleWindowItems(S30PacketWindowItems packetIn) {
        if(api.onPacketReceived(0x30, packetIn.toString()))
        parent.handleWindowItems(packetIn);
    }

    @Override
    public void handleSignEditorOpen(S36PacketSignEditorOpen packetIn) {
        if(api.onPacketReceived(0x36, packetIn.toString()))
        parent.handleSignEditorOpen(packetIn);
    }

    @Override
    public void handleUpdateSign(S33PacketUpdateSign packetIn) {
        if(api.onPacketReceived(0x33, packetIn.toString()))
        parent.handleUpdateSign(packetIn);
    }

    @Override
    public void handleUpdateTileEntity(S35PacketUpdateTileEntity packetIn) {
        if(api.onPacketReceived(0x35, packetIn.toString()))
        parent.handleUpdateTileEntity(packetIn);
    }

    @Override
    public void handleWindowProperty(S31PacketWindowProperty packetIn) {
        if(api.onPacketReceived(0x31, packetIn.toString()))
        parent.handleWindowProperty(packetIn);
    }

    @Override
    public void handleEntityEquipment(S04PacketEntityEquipment packetIn) {
        if(api.onPacketReceived(0x04, packetIn.toString()))
        parent.handleEntityEquipment(packetIn);
    }

    @Override
    public void handleCloseWindow(S2EPacketCloseWindow packetIn) {
        if(api.onPacketReceived(0x2E, packetIn.toString()))
        parent.handleCloseWindow(packetIn);
    }

    @Override
    public void handleBlockAction(S24PacketBlockAction packetIn) {
        if(api.onPacketReceived(0x24, packetIn.toString()))
        parent.handleBlockAction(packetIn);
    }

    @Override
    public void handleBlockBreakAnim(S25PacketBlockBreakAnim packetIn) {
        if(api.onPacketReceived(0x25, packetIn.toString()))
        parent.handleBlockBreakAnim(packetIn);
    }

    @Override
    public void handleMapChunkBulk(S26PacketMapChunkBulk packetIn) {
        if(api.onPacketReceived(0x26, packetIn.toString()))
        parent.handleMapChunkBulk(packetIn);
    }

    @Override
    public void handleChangeGameState(S2BPacketChangeGameState packetIn) {
        if(api.onPacketReceived(0x2B, packetIn.toString()))
        parent.handleChangeGameState(packetIn);
    }

    @Override
    public void handleMaps(S34PacketMaps packetIn) {
        if(api.onPacketReceived(0x34, packetIn.toString()))
        parent.handleMaps(packetIn);
    }

    @Override
    public void handleEffect(S28PacketEffect packetIn) {
        if(api.onPacketReceived(0x28, packetIn.toString()))
        parent.handleEffect(packetIn);
    }

    @Override
    public void handleStatistics(S37PacketStatistics packetIn) {
        if(api.onPacketReceived(0x37, packetIn.toString()))
        parent.handleStatistics(packetIn);
    }

    @Override
    public void handleEntityEffect(S1DPacketEntityEffect packetIn) {
        if(api.onPacketReceived(0x1D, packetIn.toString()))
        parent.handleEntityEffect(packetIn);
    }

    @Override
    public void handleCombatEvent(S42PacketCombatEvent packetIn) {
        if(api.onPacketReceived(0x42, packetIn.toString()))
        parent.handleCombatEvent(packetIn);
    }

    @Override
    public void handleServerDifficulty(S41PacketServerDifficulty packetIn) {
        if(api.onPacketReceived(0x41, packetIn.toString()))
        parent.handleServerDifficulty(packetIn);
    }

    @Override
    public void handleCamera(S43PacketCamera packetIn) {
        if(api.onPacketReceived(0x43, packetIn.toString()))
        parent.handleCamera(packetIn);
    }

    @Override
    public void handleWorldBorder(S44PacketWorldBorder packetIn) {
        if(api.onPacketReceived(0x44, packetIn.toString()))
        parent.handleWorldBorder(packetIn);
    }

    @Override
    public void handleTitle(S45PacketTitle packetIn) {
        if(!TitleListener.inst.onTitle(packetIn)) return;
        if(api.onPacketReceived(0x45, packetIn.toString()))
        parent.handleTitle(packetIn);
    }

    @Override
    public void handleSetCompressionLevel(S46PacketSetCompressionLevel packetIn) {
        if(api.onPacketReceived(0x46, packetIn.toString()))
        parent.handleSetCompressionLevel(packetIn);
    }

    @Override
    public void handlePlayerListHeaderFooter(S47PacketPlayerListHeaderFooter packetIn) {
        if(api.onPacketReceived(0x47, packetIn.toString()))
        parent.handlePlayerListHeaderFooter(packetIn);
    }

    @Override
    public void handleRemoveEntityEffect(S1EPacketRemoveEntityEffect packetIn) {
        if(api.onPacketReceived(0x1E, packetIn.toString()))
        parent.handleRemoveEntityEffect(packetIn);
    }

    @Override
    public void handlePlayerListItem(S38PacketPlayerListItem packetIn) {
        if(api.onPacketReceived(0x38, packetIn.toString()))
        parent.handlePlayerListItem(packetIn);
    }

    @Override
    public void handleKeepAlive(S00PacketKeepAlive packetIn) {
        if(api.onPacketReceived(0x00, packetIn.toString()))
        parent.handleKeepAlive(packetIn);
    }

    @Override
    public void handlePlayerAbilities(S39PacketPlayerAbilities packetIn) {
        if(api.onPacketReceived(0x39, packetIn.toString()))
        parent.handlePlayerAbilities(packetIn);
    }

    @Override
    public void handleTabComplete(S3APacketTabComplete packetIn) {
        if(api.onPacketReceived(0x3A, packetIn.toString()))
            parent.handleTabComplete(packetIn);
    }

    @Override
    public void handleSoundEffect(S29PacketSoundEffect packetIn) {
        if(api.onPacketReceived(0x29, packetIn.toString()))
        parent.handleSoundEffect(packetIn);
    }

    @Override
    public void handleResourcePack(S48PacketResourcePackSend packetIn) {
        if(api.onPacketReceived(0x48, packetIn.toString()))
        parent.handleResourcePack(packetIn);
    }

    @Override
    public void handleEntityNBT(S49PacketUpdateEntityNBT packetIn) {
        if(api.onPacketReceived(0x49, packetIn.toString()))
        parent.handleEntityNBT(packetIn);
    }

    @Override
    public void handleCustomPayload(S3FPacketCustomPayload packetIn) {
        if(api.onPacketReceived(0x3F, packetIn.toString()))
        parent.handleCustomPayload(packetIn);
    }

    @Override
    public void handleScoreboardObjective(S3BPacketScoreboardObjective packetIn) {
        if(api.onPacketReceived(0x3B, packetIn.toString()))
        parent.handleScoreboardObjective(packetIn);
    }

    @Override
    public void handleUpdateScore(S3CPacketUpdateScore packetIn) {
        if(api.onPacketReceived(0x3C, packetIn.toString()))
        parent.handleUpdateScore(packetIn);
    }

    @Override
    public void handleDisplayScoreboard(S3DPacketDisplayScoreboard packetIn) {
        if(api.onPacketReceived(0x3D, packetIn.toString()))
        parent.handleDisplayScoreboard(packetIn);
    }

    @Override
    public void handleTeams(S3EPacketTeams packetIn) {
        if(api.onPacketReceived(0x3E, packetIn.toString()))
        parent.handleTeams(packetIn);
    }

    @Override
    public void handleParticles(S2APacketParticles packetIn) {
        if(api.onPacketReceived(0x2A, packetIn.toString()))
        parent.handleParticles(packetIn);
    }

    @Override
    public void handleEntityProperties(S20PacketEntityProperties packetIn) {
        if(api.onPacketReceived(0x20, packetIn.toString()))
        parent.handleEntityProperties(packetIn);
    }
}
