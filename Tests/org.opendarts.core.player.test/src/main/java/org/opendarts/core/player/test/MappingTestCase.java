package org.opendarts.core.player.test;

import junit.framework.Assert;

import org.junit.Test;
import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.service.player.IPlayerService;

/**
 * The Class MappingTestCase.
 */
public class MappingTestCase {

	/**
	 * Test.
	 */
	@Test
	public void testCreatePlayer() {
		// Get the player service
		IPlayerService playerService = PlayerTestBundle.getPlayerService();
		Assert.assertNotNull(playerService);

		// Test player
		String playerUuid;
		String name = "Toto";
		IPlayer player = playerService.createPlayer(name);
		Assert.assertNotNull(player);
		playerUuid = player.getUuid();
		Assert.assertNotNull(playerUuid);
		
		// Find player
		IPlayer p = playerService.getPlayer(playerUuid);
		Assert.assertNotNull(p);
		Assert.assertEquals(name, p.getName());
	}

}
