package org.opendarts.ui.x01.chart;

import java.awt.Color;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opendarts.core.model.player.IPlayer;

/**
 * The Class PlayerColor.
 */
public final class PlayerColor {

	/** The map. */
	private static Map<IPlayer, Color> map = new HashMap<IPlayer, Color>();

	/** The colors. */
	private static List<Color> colors = Arrays.asList(Color.cyan,
			Color.magenta, Color.orange, Color.blue, Color.red, Color.yellow,
			Color.pink, Color.yellow, Color.black, Color.lightGray);

	/**
	 * Instantiates a new player color.
	 */
	private PlayerColor() {
		super();
	}

	/**
	 * Gets the color.
	 *
	 * @param player the player
	 * @return the color
	 */
	public static Color getColor(IPlayer player) {
		Color result = map.get(player);
		if (result == null) {
			int mapSize = map.size();
			int maxSize = colors.size();

			int idx = mapSize % maxSize;
			int factor = mapSize / maxSize;

			result = colors.get(idx);
			if (factor == 0) {
				map.put(player, result);
			} else {
				for (int i = 0; i < factor; i++) {
					result = result.darker();
				}
				map.put(player, result);
			}
		}
		return result;
	}

}
