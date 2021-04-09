package me.elgamer.publicbuilds.utils;

import java.util.List;

import org.bukkit.World;

import com.boydti.fawe.util.EditSessionBuilder;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.function.operation.ForwardExtentCopy;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector2;
import com.sk89q.worldedit.regions.Polygonal2DRegion;
import com.sk89q.worldedit.session.ClipboardHolder;

public class WorldEditor {

	public static boolean updateWorld(List<BlockVector2> vector, World copy, World paste) {

		//Get the worlds in worldEdit format
		com.sk89q.worldedit.world.World copyWorld = new BukkitWorld(copy);
		com.sk89q.worldedit.world.World pasteWorld = new BukkitWorld(paste);

		Polygonal2DRegion region = new Polygonal2DRegion(copyWorld, vector, 1, 256);
		BlockArrayClipboard clipboard = new BlockArrayClipboard(region);

		try (EditSession editSession = new EditSessionBuilder(copyWorld)
				.checkMemory(false).fastmode(true).limitUnlimited()
				.changeSetNull().autoQueue(false).build();) {
			ForwardExtentCopy forwardExtentCopy = new ForwardExtentCopy(
					editSession, region, clipboard, region.getMinimumPoint()
					);
			// configure here
			Operations.complete(forwardExtentCopy);
		} catch (WorldEditException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		try (EditSession editSession = new EditSessionBuilder(pasteWorld)
				.checkMemory(false).fastmode(true).limitUnlimited()
				.changeSetNull().autoQueue(false).build();) {
			Operation operation = new ClipboardHolder(clipboard)
					.createPaste(editSession)
					.to(region.getMinimumPoint())
					// configure here
					.build();
			Operations.complete(operation);
		} catch (WorldEditException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
}