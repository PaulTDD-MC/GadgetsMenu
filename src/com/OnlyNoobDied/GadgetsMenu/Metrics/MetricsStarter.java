package com.OnlyNoobDied.GadgetsMenu.Metrics;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import com.OnlyNoobDied.GadgetsMenu.GadgetsMenu;
import com.OnlyNoobDied.GadgetsMenu.API.*;
import com.OnlyNoobDied.GadgetsMenu.Metrics.Metrics.Plotter;
import com.OnlyNoobDied.GadgetsMenu.Utils.VersionManager;

public class MetricsStarter implements Runnable {

	private final Plugin plugin;
	private transient Boolean start;

	public MetricsStarter(final GadgetsMenu plugin) {
		this.plugin = plugin;
		try {

			final Metrics metrics = new Metrics(this.plugin);

			if (!metrics.isOptOut()) {
				start = true;
				return;
			}
		} catch (Exception ex) {
			Bukkit.getLogger().log(Level.INFO, "[Metrics] " + ex.getMessage());
		}
	}

	@Override
	public void run() {
		try {
			Metrics metrics = new Metrics(this.plugin);

			final Metrics.Graph enabledGraph = metrics.createGraph("Enabled_Features");
			enabledGraph.addPlotter(new SimplePlotter("Total"));
			if (HatAPI.isHatsEnabled()) {
				enabledGraph.addPlotter(new SimplePlotter("Hats"));
			}
			if (ParticleAPI.isParticlesEnabled()) {
				enabledGraph.addPlotter(new SimplePlotter("Particles"));
			}
			if (GadgetAPI.isGadgetsEnabled()) {
				enabledGraph.addPlotter(new SimplePlotter("Gadgets"));
			}
			if (PetAPI.isPetsEnabled()) {
				enabledGraph.addPlotter(new SimplePlotter("Pets"));
			}
			if (MorphAPI.isMorphsEnabled() && MorphAPI.isLibDisguisesHooked() && VersionManager.is1_9OrAbove()) {
				enabledGraph.addPlotter(new SimplePlotter("Morphs"));
			}
			if (BannerAPI.isBannersEnabled()) {
				enabledGraph.addPlotter(new SimplePlotter("Banners"));
			}
			if (EmoteAPI.isEmotesEnabled()) {
				enabledGraph.addPlotter(new SimplePlotter("Emotes"));
			}
			if (CloakAPI.isCloaksEnabled()) {
				enabledGraph.addPlotter(new SimplePlotter("Cloaks"));
			}
			final Metrics.Graph database = metrics.createGraph("Database");
			database.addPlotter(new SimplePlotter("Total"));
			if (MainAPI.isDatabaseEnabled()) {
				database.addPlotter(new SimplePlotter("Enabled"));
			} else {
				database.addPlotter(new SimplePlotter("Disabled"));
			}
			database.addPlotter(new SimplePlotter("Test"));
			final Metrics.Graph version = metrics.createGraph("EnabledFeatures");
			version.addPlotter(new SimplePlotter("Total"));
			/*if (PlaceHolders.getPluginVersion().startsWith("3.5")) {
				version.addPlotter(new SimplePlotter("3.5.0+"));
			} else if (PlaceHolders.getPluginVersion().startsWith("3.6")) {
				version.addPlotter(new SimplePlotter("3.6.0+"));
			} else {
				version.addPlotter(new SimplePlotter("Unknown"));
			}*/
			metrics.start();
		} catch (Exception e) {
			Bukkit.getLogger().log(Level.INFO, "[Metrics] " + e.getMessage());
		}
	}

	public Boolean getStart() {
		return start;
	}

	private class SimplePlotter extends Plotter {
		public SimplePlotter(final String name) {
			super(name);
		}

		@Override
		public int getValue() {
			return 1;
		}
	}
}
