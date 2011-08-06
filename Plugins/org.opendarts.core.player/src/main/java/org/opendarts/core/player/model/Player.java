package org.opendarts.core.player.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.opendarts.core.model.player.IPlayer;

/**
 * The Class Player.
 */
@Entity
public class Player implements IPlayer {
	
	/** The uuid. */
	@Id
	private String uuid;

	/** The name. */
	@Column(nullable=false)
	private String name;

	/**
	 * Instantiates a new player.
	 */
	public Player() {
		super();
	}

	/**
	 * Instantiates a new player.
	 */
	public Player(String name) {
		super();
		this.name = name;
	}
	
	/**
	 * Gets the uuid.
	 *
	 * @return the uuid
	 */
	@Override
	public String getUuid() {
		return this.uuid;
	}

	/**
	 * Sets the uuid.
	 *
	 * @param uuid the new uuid
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.model.IPlayer#getName()
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.getName();
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.model.player.IPlayer#isComputer()
	 */
	@Override
	public boolean isComputer() {
		return false;
	}
	

}
