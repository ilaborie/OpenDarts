package org.opendarts.core.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.opendarts.core.model.dart.DartSector;
import org.opendarts.core.model.dart.DartZone;
import org.opendarts.core.model.dart.IDart;
import org.opendarts.core.service.dart.IDartService;

/**
 * The Class DartServiceTestCase.
 */
public class DartServiceTestCase {

	/** The dart service. */
	private IDartService dartService;

	/**
	 * Inits the service.
	 */
	@Before
	public void init() {
		dartService = CoreTestBundle.getDartService();
	}

	/**
	 * Test create.
	 */
	@Test
	public void testCreate() {
		IDart dart;
		for (DartZone zone : DartZone.values()) {
			for (DartSector sector : DartSector.values()) {
				dart = dartService.createDart(sector, zone);
				Assert.assertNotNull(dart);
				Assert.assertEquals(zone, dart.getZone());
				Assert.assertEquals(sector, dart.getSector());
				dart.toString();
			}
		}
	}
	

	/**
	 * Test get dart.
	 */
	@Test
	public void testGetDart() {
		IDart dart;
		
		// Classics
		dart = this.dartService.getDart("T20");
		Assert.assertNotNull(dart);
		Assert.assertEquals(DartZone.TRIPLE, dart.getZone());
		Assert.assertEquals(DartSector.TWENTY, dart.getSector());
		
		
		dart = this.dartService.getDart("D16");
		Assert.assertNotNull(dart);
		Assert.assertEquals(DartZone.DOUBLE, dart.getZone());
		Assert.assertEquals(DartSector.SIXTEEN, dart.getSector());
		
		dart = this.dartService.getDart("5");
		Assert.assertNotNull(dart);
		Assert.assertEquals(DartZone.SINGLE, dart.getZone());
		Assert.assertEquals(DartSector.FIVE, dart.getSector());

		// Bull
		dart = this.dartService.getDart("25");
		Assert.assertNotNull(dart);
		Assert.assertEquals(DartZone.SINGLE, dart.getZone());
		Assert.assertEquals(DartSector.BULL, dart.getSector());

		dart = this.dartService.getDart("50");
		Assert.assertNotNull(dart);
		Assert.assertEquals(DartZone.DOUBLE, dart.getZone());
		Assert.assertEquals(DartSector.BULL, dart.getSector());
		
		// LowerCase
		dart = this.dartService.getDart("t3");
		Assert.assertNotNull(dart);
		Assert.assertEquals(DartZone.TRIPLE, dart.getZone());
		Assert.assertEquals(DartSector.THREE, dart.getSector());
		
		dart = this.dartService.getDart("d12");
		Assert.assertNotNull(dart);
		Assert.assertEquals(DartZone.DOUBLE, dart.getZone());
		Assert.assertEquals(DartSector.TWELVE, dart.getSector());
		
		// Errors
		dart = this.dartService.getDart("d 12");
		Assert.assertNotNull(dart);
		Assert.assertEquals(DartZone.NONE, dart.getZone());
		Assert.assertEquals(DartSector.NONE, dart.getSector());

		dart = this.dartService.getDart("21");
		Assert.assertNotNull(dart);
		Assert.assertEquals(DartZone.NONE, dart.getZone());
		Assert.assertEquals(DartSector.NONE, dart.getSector());

		dart = this.dartService.getDart("d 12");
		Assert.assertNotNull(dart);
		Assert.assertEquals(DartZone.NONE, dart.getZone());
		Assert.assertEquals(DartSector.NONE, dart.getSector());

		dart = this.dartService.getDart(null);
		Assert.assertNotNull(dart);
		Assert.assertEquals(DartZone.NONE, dart.getZone());
		Assert.assertEquals(DartSector.NONE, dart.getSector());

		dart = this.dartService.getDart("Plop");
		Assert.assertNotNull(dart);
		Assert.assertEquals(DartZone.NONE, dart.getZone());
		Assert.assertEquals(DartSector.NONE, dart.getSector());
		
		dart = this.dartService.getDart("T25");
		Assert.assertNotNull(dart);
		Assert.assertEquals(DartZone.NONE, dart.getZone());
		Assert.assertEquals(DartSector.NONE, dart.getSector());
	}
}
