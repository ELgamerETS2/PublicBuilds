package me.elgamer.publicbuilds.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

import me.elgamer.publicbuilds.Main;
import me.elgamer.publicbuilds.utils.Ranks;
import me.elgamer.publicbuilds.utils.Time;

public class PlayerData {


	public static boolean playerExists(String uuid) {

		Main instance = Main.getInstance();

		try {
			PreparedStatement statement = instance.getConnection().prepareStatement
					("SELECT * FROM " + instance.playerData + " WHERE ID=?");
			statement.setString(1, uuid);
			ResultSet results = statement.executeQuery();

			if (results.next()) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException sql) {
			sql.printStackTrace();
			return false;
		}


	}

	public static void updatePlayerName(String uuid, String name) {

		Main instance = Main.getInstance();

		try {
			PreparedStatement statement = instance.getConnection().prepareStatement
					("UPDATE " + instance.playerData + " SET NAME=? WHERE ID=?");
			statement.setString(1, name);
			statement.setString(2, uuid);
			statement.executeUpdate();

		} catch (SQLException sql) {
			sql.printStackTrace();
		}
	}

	public static void createPlayerInstance(String uuid, String name, String role) {

		Main instance = Main.getInstance();

		try {
			PreparedStatement statement = instance.getConnection().prepareStatement
					("INSERT INTO " + instance.playerData + " (ID,NAME,TUTORIAL_STAGE,BUILDING_POINTS,LAST_ONLINE,BUILDER_ROLE) VALUE (?,?,?,?,?,?)");
			statement.setString(1, uuid);
			statement.setString(2, name);
			statement.setInt(3, 0);
			statement.setInt(4, 0);
			statement.setLong(5, Time.currentTime());
			statement.setString(6, role);
			statement.executeUpdate();

		} catch (SQLException sql) {
			sql.printStackTrace();
		}
	}

	public static int getTutorialStage(String uuid) {

		Main instance = Main.getInstance();

		try {
			PreparedStatement statement = instance.getConnection().prepareStatement
					("SELECT * FROM " + instance.playerData + " WHERE ID=?");
			statement.setString(1, uuid);
			ResultSet results = statement.executeQuery();

			if (results.next()) {
				return (results.getInt("TUTORIAL_STAGE"));
			} else {
				return 0;
			}


		} catch (SQLException sql) {
			sql.printStackTrace();
			return 0;
		}
	}

	public static void setTutorialStage(String uuid, int stage) {

		Main instance = Main.getInstance();

		try {
			PreparedStatement statement = instance.getConnection().prepareStatement
					("UPDATE " + instance.playerData + " SET TUTORIAL_STAGE=? WHERE ID=?");
			statement.setInt(1, stage);
			statement.setString(2, uuid);
			statement.executeUpdate();


		} catch (SQLException sql) {
			sql.printStackTrace();
		}
	}

	public static void updateTime(String uuid) {

		Main instance = Main.getInstance();

		try {
			PreparedStatement statement = instance.getConnection().prepareStatement
					("UPDATE " + instance.playerData + " SET LAST_ONLINE=? WHERE ID=?");
			statement.setLong(1, Time.currentTime());
			statement.setString(2, uuid);
			statement.executeUpdate();

		} catch (SQLException sql) {
			sql.printStackTrace();
		}
	}

	public static void addPoints(String uuid, int points) {

		Main instance = Main.getInstance();

		try {
			PreparedStatement statement = instance.getConnection().prepareStatement
					("UPDATE " + instance.playerData + " SET BUILDING_POINTS=BUILDING_POINTS+" + points + " WHERE ID=?");
			statement.setString(1, uuid);
			statement.executeUpdate();

			Ranks.checkRankup(uuid);

		} catch (SQLException sql) {
			sql.printStackTrace();
		}
	}

	public static int getPoints(String uuid) {

		Main instance = Main.getInstance();

		try {
			PreparedStatement statement = instance.getConnection().prepareStatement
					("SELECT * FROM " + instance.playerData + " WHERE ID=?");
			statement.setString(1, uuid);
			ResultSet results = statement.executeQuery();

			if (results.next()) {
				return (results.getInt("BUILDING_POINTS"));
			} else {
				return 0;
			}

		} catch (SQLException sql) {
			sql.printStackTrace();
			return 0;
		}
	}

	public static String getRole(String uuid) {

		Main instance = Main.getInstance();

		try {
			PreparedStatement statement = instance.getConnection().prepareStatement
					("SELECT * FROM " + instance.playerData + " WHERE ID=?");
			statement.setString(1, uuid);
			ResultSet results = statement.executeQuery();

			if (results.next()) {
				return (results.getString("BUILDER_ROLE"));
			} else {
				return null;
			}

		} catch (SQLException sql) {
			sql.printStackTrace();
			return null;
		}
	}
	
	public static void promoteRole(String uuid, String role) {
		
		String newRole = null;
		
		if (role.equals("guest")) {
			newRole = "apprentice";
		} else if (role.equals("apprentice")) {
			newRole = "jrbuilder";
		} else if (role.equals("jrbuilder")) {
			newRole = "builder";
		} else {return;}
		
		Main instance = Main.getInstance();

		try {
			PreparedStatement statement = instance.getConnection().prepareStatement
					("UPDATE " + instance.playerData + " SET BUILDER_ROLE=? WHERE ID=?");
			
			statement.setString(1, newRole);
			statement.setString(2, uuid);
			statement.executeUpdate();

			Ranks.checkRankup(uuid);

		} catch (SQLException sql) {
			sql.printStackTrace();
		}
		
	}

	public static List<String> getInactivePlayers() {

		//Get plugin instance and config.
		Main instance = Main.getInstance();
		FileConfiguration config = instance.getConfig();

		//Calculate the time in milliseconds inactiveMax days ago, inactiveMax is the number of days player can be inactive before their plots are cancelled.
		int inactiveMax = config.getInt("plot_inactive_cancel");
		long time = inactiveMax*24*60*60*1000;
		long currentTime = Time.currentTime();
		long timeCheck = currentTime - time;

		//Create list of inactive players.
		List<String> players = new ArrayList<String>();

		//Find all players who have not been online since the timeCheck time.
		try {
			PreparedStatement statement = instance.getConnection().prepareStatement
					("SELECT * FROM " + instance.playerData + " WHERE LAST_ONLINE<?");
			statement.setLong(1, timeCheck);
			ResultSet results = statement.executeQuery();

			while (results.next()) {
				players.add(results.getString("ID"));
			}

			if (players.isEmpty()) {
				return null;
			} else {
				return players;
			}

		} catch (SQLException sql) {
			sql.printStackTrace();
			return null;
		}


	}

}
