package org.opendarts.core.ia.internal;

import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

import org.opendarts.core.ia.model.DartboardProperties;
import org.opendarts.core.ia.service.IComputerPlayerDartService;
import org.opendarts.core.ia.service.IDartboard;
import org.opendarts.core.model.dart.DartSector;
import org.opendarts.core.model.dart.DartZone;
import org.opendarts.core.model.dart.IDart;
import org.opendarts.core.model.player.IComputerPlayer;
import org.opendarts.core.service.dart.IDartService;
import org.opendarts.core.service.player.IPlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class ComputerPlayerDartService.
 */
public class ComputerPlayerDartService implements IComputerPlayerDartService {

	/** The logger. */
	private static final Logger LOG = LoggerFactory
			.getLogger(ComputerPlayerDartService.class);

	/** The dart service. */
	private final AtomicReference<IDartService> dartService;

	/** The player service. */
	private final AtomicReference<IPlayerService> playerService;

	/** The dartboard. */
	private final AtomicReference<IDartboard> dartboard;

	/** The rand. */
	private final Random rand;

	/**
	 * Instantiates a new computer player throw service.
	 */
	public ComputerPlayerDartService() {
		super();
		this.dartService = new AtomicReference<IDartService>();
		this.playerService = new AtomicReference<IPlayerService>();
		this.dartboard = new AtomicReference<IDartboard>();
		this.rand = new Random();
	}

	/* (non-Javadoc)
	 * @see org.opendarts.core.ia.service.IComputerPlayerDartService#getComputerDart(org.opendarts.core.model.player.IComputerPlayer, org.opendarts.core.model.dart.IDart)
	 */
	@Override
	public IDart getComputerDart(IComputerPlayer player, IDart wished) {
		LOG.trace("Wished: {}", wished);

		IDart dart;
		IDartboard db = this.dartboard.get();
		DartboardProperties prop = db.getDartboard();

		// unlucky factor
		int unlucky;
		if (DartSector.BULL.equals(wished.getSector())) {
			unlucky = prop.getUnLuckyBull();
		} else {
			unlucky = prop.getUnLuckyOthers();
		}

		// If unlucky
		if (this.rand.nextInt(unlucky) == 0) {
			dart = this.dartService.get().createDart(DartSector.UNLUCKY_DART,
					DartZone.NONE);
		} else {
			// Normal throw
			double wishedX = db.getX(wished);
			double wishedY = db.getY(wished);

			// Player factor;
			double factor = db.getPlayerFactor(player);

			// Gaussian
			double x = nextGaussian(factor, wishedX);
			double y = nextGaussian(factor, wishedY);

			dart = db.getDart(x, y);
		}
		LOG.trace("Wished: {}\tDone: {}", wished,dart);
		return dart;
	}

	/**
	 * Nex gaussian.
	 *
	 * @param factor the factor
	 * @param offset the offset
	 * @return the double
	 */
	private double nextGaussian(double factor, double offset) {
		double result = factor * this.rand.nextGaussian();
		result += offset;
		return result;
	}

	/**
	 * Sets the dart service.
	 *
	 * @param dartService the new dart service
	 */
	public void setDartService(IDartService dartService) {
		this.dartService.set(dartService);
	}

	/**
	 * Unset dart service.
	 *
	 * @param dartService the dart service
	 */
	public void unsetDartService(IDartService dartService) {
		this.dartService.compareAndSet(dartService, null);
	}

	/**
	 * Sets the player service.
	 *
	 * @param playerService the new player service
	 */
	public void setPlayerService(IPlayerService playerService) {
		this.playerService.set(playerService);
	}

	/**
	 * Unset player service.
	 *
	 * @param playerService the player service
	 */
	public void unsetPlayerService(IPlayerService playerService) {
		this.playerService.compareAndSet(playerService, null);
	}

	/**
	 * Sets the dartboard.
	 *
	 * @param dartboard the new dartboard
	 */
	public void setDartboard(IDartboard dartboard) {
		this.dartboard.set(dartboard);
	}

	/**
	 * Unset dartboard.
	 *
	 * @param dartboard the dartboard
	 */
	public void unsetDartboard(IDartboard dartboard) {
		this.dartboard.compareAndSet(dartboard, null);
	}
}
