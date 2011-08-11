package org.opendarts.core.ia.internal;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;

import org.opendarts.core.ia.service.IComputerPlayerDartService;
import org.opendarts.core.model.dart.IDart;
import org.opendarts.core.model.player.IComputerPlayer;
import org.opendarts.core.service.player.IPlayerService;
import org.opendarts.core.service.dart.IDartService;
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

	/** The gaussian props. */
	private final Properties boardProps;

	/** The gaussian stats. */
	private GaussianStats gaussianStats;

	/**
	 * Instantiates a new computer player throw service.
	 */
	public ComputerPlayerDartService() {
		super();
		this.boardProps = new Properties();
		this.dartService = new AtomicReference<IDartService>();
		this.playerService = new AtomicReference<IPlayerService>();
	}

	/**
	 * Startup.
	 */
	public void startup() {
		// load player stats
		URL resource;
		InputStream in = null;
		resource = this.getClass().getClassLoader()
				.getResource("DartBoard.properties");
		try {
			in = resource.openStream();
			this.boardProps.load(in);
		} catch (IOException e) {
			LOG.error("Fail to load computer level stats", e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				LOG.error("Fail to load computer level stats", e);
			}
		}
		
		this.gaussianStats = new GaussianStats(this.dartService.get(), this.boardProps,this.playerService.get().getAllComputerPlayers().size());
	}

	/**
	 * Shutdown.
	 */
	public void shutdown() {
		this.gaussianStats = null;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.core.ia.service.IComputerPlayerDartService#getComputerDart(org.opendarts.core.model.player.IComputerPlayer, org.opendarts.core.model.dart.IDart)
	 */
	@Override
	public IDart getComputerDart(IComputerPlayer player, IDart wished) {
		return this.gaussianStats.getDart(player, wished);
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
		this.playerService.compareAndSet(playerService,null);
	}

}
