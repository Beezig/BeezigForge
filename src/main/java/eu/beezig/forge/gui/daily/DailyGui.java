package eu.beezig.forge.gui.daily;

import com.google.gson.Gson;
import eu.beezig.forge.ForgeMessage;
import eu.beezig.forge.api.BeezigAPI;
import eu.beezig.forge.gui.welcome.GuiCheckBox;
import eu.beezig.forge.modules.pointstag.Games;
import eu.beezig.forge.utils.JSON;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.*;

public class DailyGui extends GuiScreen {
    private static final Gson GSON = new Gson();
    private DailyLeaderboard leaderboard;
    private GuiButton regPrev, regNext, modePrev, modeNext;
    private final List<Games> supportedGames = Games.dailies();
    private Games currentGame = Games.BED;
    private List<DailyRegion> supportedRegions;
    private DailyRegion currentRegion;
    private final Map<String, Map<Games, LbResponse>> lbCache = new HashMap<>();
    private final Object lbLock = new Object();
    private final Map<Games, DailyProfile> profileCache = new EnumMap<>(Games.class);
    private DailyProfile profile;
    private String regionToLoad;

    public DailyGui() {
        try {
            String game = BeezigAPI.getCurrentGame();
            if(game != null) currentGame = Games.valueOf(game.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException ignored) {}
        regionToLoad = BeezigAPI.getRegion();
    }

    @Override
    public void initGui() {
        buttonList.add(regPrev = new GuiButton(1, width / 2 - 80, 5, 20, 20, "<"));
        buttonList.add(regNext = new GuiButton(2, width / 2 + 80, 5, 20, 20, ">"));
        buttonList.add(modePrev = new GuiButton(3, width / 2 - 80, 30, 20, 20, "<"));
        buttonList.add(modeNext = new GuiButton(4, width / 2 + 80, 30, 20, 20, ">"));
        buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height - 29, I18n.format("gui.done")));
        String checkBoxText = ForgeMessage.translate("gui.daily.btn.opt");
        buttonList.add(new GuiCheckBox(1910, this.width - 30 - fontRendererObj.getStringWidth(checkBoxText), this.height - 28, checkBoxText, BeezigAPI.hasDailyScores()) {
            @Override
            public void setIsChecked(boolean isChecked) {
                if(BeezigAPI.hasDailyScores()) {
                    Minecraft.getMinecraft().displayGuiScreen(new GuiChat());
                    Minecraft.getMinecraft().thePlayer.sendChatMessage("/beezig daily hide");
                } else {
                    Minecraft.getMinecraft().thePlayer.sendChatMessage("/beezig daily show");
                    super.setIsChecked(isChecked);
                }
            }
        });
        regPrev.enabled = false;
        regNext.enabled = false;
        modePrev.enabled = false;
        modeNext.enabled = false;
        loadRegions();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 1) {
            currentRegion = supportedRegions.get(supportedRegions.indexOf(currentRegion) - 1);
        } else if (button.id == 2) {
            currentRegion = supportedRegions.get(supportedRegions.indexOf(currentRegion) + 1);
        } else if (button.id == 3) {
            currentGame = supportedGames.get(supportedGames.indexOf(currentGame) - 1);
        } else if (button.id == 4) {
            currentGame = supportedGames.get(supportedGames.indexOf(currentGame) + 1);
        } else if (button.id == 200) {
            Minecraft.getMinecraft().displayGuiScreen(null);
        } else return;
        refreshLeaderboard();
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        if (leaderboard != null) leaderboard.handleMouseInput();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        String loading = ForgeMessage.translate("gui.daily.loading");
        drawDefaultBackground();
        if (leaderboard != null) leaderboard.drawScreen(mouseX, mouseY, partialTicks);
        drawCenteredString(mc.fontRendererObj, currentRegion == null ? loading : currentRegion.getName(), width / 2, 10, 0xff_ff_ff_ff);
        drawCenteredString(mc.fontRendererObj, currentGame == null ? loading : currentGame.getCommonName(), width / 2, 35, 0xff_ff_ff_ff);
        if (leaderboard == null) {
            drawCenteredString(mc.fontRendererObj, loading, width / 2, height / 2, 0xff_ff_ff_ff);
        }
        else {
            String reset = leaderboard.getFormattedResetTime();
            if(reset != null)
                drawCenteredString(mc.fontRendererObj, ForgeMessage.translate("gui.daily.reset", reset), width / 2, 55, 0xff_ff_ff_ff);
        }
        if (profile != null) {
            drawCenteredString(mc.fontRendererObj, "§7" + ForgeMessage.translate("gui.daily.stats"), 40, height - 40, 0xff_ff_ff_ff);
            GlStateManager.pushMatrix();
            GlStateManager.translate(20, height - 20, 1);
            GlStateManager.scale(2f, 2f, 2f);
            mc.fontRendererObj.drawString(profile.place == -1 ? "?"  : "#" + profile.place, 0, 0, 0xff_ff_ff_ff);
            GlStateManager.popMatrix();
            mc.fontRendererObj.drawString(profile.points == -1 ? "?" : ForgeMessage.formatNumber(profile.points), 60, height - 20, 0xff_ff_ff_ff);
            mc.fontRendererObj.drawString(profile.most == -1 ? "?" : ForgeMessage.formatNumber(profile.most), 60, height - 10, 0xff_ff_ff_ff);
        } else drawCenteredString(mc.fontRendererObj, loading, 40, height - 20, 0xff_ff_ff_ff);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void updateScreen() {
        if(currentGame != null) {
            int modeIdx = supportedGames.indexOf(currentGame);
            modePrev.enabled = modeIdx > 0;
            modeNext.enabled = modeIdx < supportedGames.size() - 1;
        }
        if(currentRegion != null) {
            int regIdx = supportedRegions.indexOf(currentRegion);
            regPrev.enabled = regIdx > 0;
            regNext.enabled = regIdx < supportedRegions.size() - 1;
        }
    }

    public void show() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onTick(TickEvent.PlayerTickEvent event) {
        MinecraftForge.EVENT_BUS.unregister(this);
        Minecraft.getMinecraft().displayGuiScreen(this);
    }

    private void loadRegions() {
        new Thread(() -> {
            String url = "https://web.beezig.eu/v1/dailies/regions";
            JSONArray json = JSON.downloadJSONArray(url);
            supportedRegions = Arrays.asList(GSON.fromJson(json.toJSONString(), DailyRegion[].class));
            currentRegion = supportedRegions.get(0);
            if(regionToLoad != null) {
                for(DailyRegion region : supportedRegions) {
                    if(region.getId().equals(regionToLoad)) {
                        currentRegion = region;
                        break;
                    }
                }
            }
            updateScreen();
            refreshLeaderboard();
        }).start();
    }

    private void refreshProfile() {
        profile = null;
        if (profileCache.containsKey(currentGame)) {
            profile = profileCache.get(currentGame);
            return;
        }
        Games game = currentGame;
        new Thread(() -> {
            try {
                String url = String.format("https://web.beezig.eu/v1/dailies/profile/%s/%s", currentGame.name(),
                        Minecraft.getMinecraft().thePlayer.getGameProfile().getId().toString().replace("-", ""));
                JSONObject json = JSON.downloadJSON(url);
                DailyProfile res = GSON.fromJson(json.toJSONString(), DailyProfile.class);
                synchronized (lbLock) {
                    profileCache.put(game, res);
                    profile = res;
                }
            } catch (Exception e) {
                e.printStackTrace();
                profile = new DailyProfile();
                profile.place = -1;
                profile.points = -1;
                profile.most = -1;
            }
        }).start();
    }

    private void refreshLeaderboard() {
        refreshProfile();
        if (currentRegion == null)
            return;
        if (lbCache.containsKey(currentRegion.getId())) {
            Map<Games, LbResponse> gameCache = lbCache.get(currentRegion.getId());
            if (gameCache.containsKey(currentGame)) {
                LbResponse cached = gameCache.get(currentGame);
                leaderboard = new DailyLeaderboard(cached.leaderboard, Minecraft.getMinecraft(), width, height, 67, DailyGui.this.height - 44, 16);
                leaderboard.setResetTime(cached.reset_time);
                leaderboard.setExtensions(cached.extensions);
                return;
            }
        }
        Games game = currentGame;
        DailyRegion region = currentRegion;
        new Thread(() -> {
            String url = String.format("https://web.beezig.eu/v1/dailies/leaderboard/%s/%s/0/100?ext=true", currentGame.name(), currentRegion.getId());
            JSONObject json = JSON.downloadJSON(url);
            LbResponse res = GSON.fromJson(json.toJSONString(), LbResponse.class);
            synchronized (lbLock) {
                if (!lbCache.containsKey(region.getId())) lbCache.put(region.getId(), new EnumMap<>(Games.class));
                lbCache.get(region.getId()).put(game, res);
                leaderboard = new DailyLeaderboard(res.leaderboard, Minecraft.getMinecraft(), width, height, 67, DailyGui.this.height - 44, 16);
                leaderboard.setResetTime(res.reset_time);
                leaderboard.setExtensions(res.extensions);
            }
            updateScreen();
        }).start();
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        if(leaderboard != null) leaderboard.onGuiClose();
    }

    private static class LbResponse {
        private List<DailyLeaderboard.Profile> leaderboard;
        private long reset_time;
        private List<DailyLeaderboard.Extension> extensions;
    }

    private static class DailyProfile {
        public int place, points, most;
    }
}
