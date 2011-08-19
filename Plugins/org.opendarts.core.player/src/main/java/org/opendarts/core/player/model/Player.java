package org.opendarts.core.player.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.opendarts.core.model.player.IPlayer;

/**
 * The Class Player.
 */
@Entity
@NamedQueries({
		@NamedQuery(name = "Player.byUuid", query = "Select p From Player p Where p.uuid = :uuid"),
		@NamedQuery(name = "Player.byName", query = "Select p From Player p Where p.name = :name"),
		@NamedQuery(name = "Player.all", query = "Select p From Player p"), })
public class Player implements IPlayer {

	/** The id. */
	@Id
	@GeneratedValue
	private Long id;

	/** The uuid. */
	@Column(nullable = false, unique = true)
	private String uuid;

	/** The name. */
	@Column(nullable = false)
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

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	protected Long getId() {
		return this.id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	protected void setId(Long id) {
		this.id = id;
	}

}