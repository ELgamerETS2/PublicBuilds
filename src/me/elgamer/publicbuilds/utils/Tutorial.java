package me.elgamer.publicbuilds.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.math.BlockVector2;
import com.sk89q.worldguard.protection.regions.ProtectedPolygonalRegion;

import me.elgamer.publicbuilds.Main;

public class Tutorial {

	public static void continueTutorial(User u) {

		switch (u.tutorialStage) {

		case 0:
			return;
			
		case 1:
			stage1(u);
			break;

		case 2:
			stage2(u);
			break;

		case 3:
			stage3(u);
			break;

		case 4:
			stage4(u);
			break;

		case 5:
			stage5(u);
			break;
			
		case 6:
			stage6(u);
			break;
			
		case 7:
			return;		

		}

	}

	public static void stage1(User u) {

		Player p = u.player;
		FileConfiguration config = Main.getInstance().getConfig();
		p.sendTitle(ChatColor.AQUA + "" + ChatColor.BOLD + "Tutorial Stage 1/5", "/ll", 10, 100, 50);
		p.teleport(new Location(Bukkit.getWorld(config.getString("worlds.tutorial")), config.getDouble("starting_position.x"), config.getDouble("starting_position.y"), config.getDouble("starting_position.z")));
		
		p.sendMessage(Utils.chat("&fThe first thing you'll be wondering, where am I?"));
		p.sendMessage(Utils.chat("&fFor this we have the command /ll"));
		p.sendMessage(Utils.chat("&fThis will return your current coordinates in chat with a link to google maps."));
		p.sendMessage(Utils.chat("&fTry it out and continue the tutorial."));

	}

	public static void stage2(User u) {

		Player p = u.player;
		p.sendTitle(ChatColor.AQUA + "" + ChatColor.BOLD + "Tutorial Stage 2/5", "/tpll", 10, 100, 50);
		p.sendMessage(Utils.chat("&fIn google maps you'll be able to see the area you are currently in."));
		p.sendMessage(Utils.chat("&fIf you right click on the map you can copy the the coordinates where you cursor is."));
		p.sendMessage(Utils.chat("&fTry copying the coordinates and then on the server do &7/tpll <coordinates> &f."));
		p.sendMessage(Utils.chat("&fIf you're having problems don't hesitate to ask in chat or on our discord."));

	}

	public static void stage3(User u) {
		
		Player p = u.player;
		p.sendTitle(ChatColor.AQUA + "" + ChatColor.BOLD + "Tutorial Stage 3/5", "Creating a plot", 10, 100, 50);
		p.sendMessage(Utils.chat("&fNow that you know where you are you can pick out a building to build."));
		p.sendMessage(Utils.chat("&fFor this tutorial you should make a plot for 134+136 Marlborough Gardens."));
		p.sendMessage(Utils.chat("&fTo create a plot you need to mark out the corners with the selection tool."));
		p.sendMessage(Utils.chat("&fThe selection tool can be found in the gui, it's the blaze rod."));
		p.sendMessage(Utils.chat("&fIt is recommended to make your plot larger than the house and garden need."));
		p.sendMessage(Utils.chat("&fWhen you have selected the corners open the gui and click the emerald."));

	}
	
	public static void stage4(User u) {
				
		Player p = u.player;
		p.sendTitle(ChatColor.AQUA + "" + ChatColor.BOLD + "Tutorial Stage 4/5", "Creating outlines", 10, 100, 50);
		p.sendMessage(Utils.chat("&fOnce you have created a plot you can start to build."));
		p.sendMessage(Utils.chat("&fThe first step is to create the outlines of the building."));
		p.sendMessage(Utils.chat("&fIn google maps right click on one of the corners of the building and copy the coordinates."));
		p.sendMessage(Utils.chat("&fKeep in mind that the roof often sticks out a bit so you may want to move inward a little."));
		p.sendMessage(Utils.chat("&fWith the coordinates again use /tpll to teleport to the corner of 134+136 Marlborough Gardens."));
	}
	
	public static void stage5(User u) {
		
		Player p = u.player;
		p.sendTitle(ChatColor.AQUA + "" + ChatColor.BOLD + "Tutorial Stage 5/5", "Estimating the height of the building.", 10, 100, 50);
		p.sendMessage(Utils.chat("&fThe final step is to make the walls the right height."));
		p.sendMessage(Utils.chat("&fGoogle earth pro has elevations (elev) in the bottom right corner."));
		p.sendMessage(Utils.chat("&fThis can be used to get the height, or you can estimate it."));
		p.sendMessage(Utils.chat("&fIf you choose to guess the height a good reference is the door, which is usually around 2 metres."));
		p.sendMessage(Utils.chat("&fPlease type in chat your estimated height for the front wall of 134+136 Marlborough Gardens."));
	}
	
	public static void stage6(User u) {
				
		Player p = u.player;
		FileConfiguration config = Main.getInstance().getConfig();
		u.plots = new Plots();
		p.teleport(new Location(Bukkit.getWorld(config.getString("worlds.build")), config.getDouble("starting_position.x"), config.getDouble("starting_position.y"), config.getDouble("starting_position.z")));
		p.sendMessage(Utils.chat("&aYou have completed the tutorial!"));
		p.sendMessage(Utils.chat("&fHere is the building built by one of our builders."));
		p.sendMessage(Utils.chat("&fUse the gui or tpll to leave this area and go back to wherever you want to go."));
		u.tutorialStage = 7;
		
	}

	public static boolean containsCorners(User u) {

		//Checks whether the corners the player has set include all 4 corners of the minimum plot size.
		FileConfiguration config = Main.getInstance().getConfig();
		ProtectedPolygonalRegion region = new ProtectedPolygonalRegion("testregion", u.plots.vector, 1, 256);

		BlockVector2 pos1 = BlockVector2.at(config.getInt("plot_corners.1.x"), config.getInt("plot_corners.1.z"));
		BlockVector2 pos2 = BlockVector2.at(config.getInt("plot_corners.2.x"), config.getInt("plot_corners.2.z"));
		BlockVector2 pos3 = BlockVector2.at(config.getInt("plot_corners.3.x"), config.getInt("plot_corners.3.z"));
		BlockVector2 pos4 = BlockVector2.at(config.getInt("plot_corners.4.x"), config.getInt("plot_corners.4.z"));

		if (region.contains(pos1) && region.contains(pos2) && region.contains(pos3) && region.contains(pos4)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean nearCorners(double[] coords) {
		
		//Checks whether the player has teleported near the 4 corners of the building using /tpll
		FileConfiguration config = Main.getInstance().getConfig();
		
		if ((Math.abs(coords[0]-config.getDouble("tpll_points.1.x")) <= 0.5) && (Math.abs(coords[1]-config.getDouble("tpll_points.1.z")) <= 0.5)) {
			return true;
		} else if ((Math.abs(coords[0]-config.getDouble("tpll_points.2.x")) <= 0.5) && (Math.abs(coords[1]-config.getDouble("tpll_points.2.z")) <= 0.5)) {
			return true;
		} else if ((Math.abs(coords[0]-config.getDouble("tpll_points.3.x")) <= 0.5) && (Math.abs(coords[1]-config.getDouble("tpll_points.3.z")) <= 0.5)) {
			return true;
		} else if ((Math.abs(coords[0]-config.getDouble("tpll_points.4.x")) <= 0.5) && (Math.abs(coords[1]-config.getDouble("tpll_points.4.z")) <= 0.5)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean getHeight(Double h) {
		
		//Checks whether the player has given the correct height of the building.
		FileConfiguration config = Main.getInstance().getConfig();
		
		if (Math.floor(h) == config.getInt("building_height")) {
			return true;
		} else {
			return false;
		}
	}

}
