package com.tomcat.hosting.dao;
//http://minecraft.gamepedia.com/Server.properties
/**
 game mode
    0 - Survival
    1 - Creative
    2 - Adventure
    3 - Spectator 
    
 level type:
 
    1 - Ops can bypass spawn protection.
    2 - Ops can use /clear, /difficulty, /effect, /gamemode, /gamerule, /give, and /tp, and can edit command blocks.
    3 - Ops can use /ban, /deop, /kick, and /op.
    4 - Ops can use /stop. 
 */
public class MinecraftServerInfo implements Comparable<MinecraftServerInfo> {
	public static final String[] GAMEMODES = 
		{
			"Survival",
			"Creative",
			"Adventure",
			"Spectator"
		};
	
	public static final String[] DIFFICULTIES = 
		{
			"Peaceful",
			"Easy",
			"Normal",
			"Hard"
		};
	
	String id;
	String name;
	int active;
	String action;
	String memory;
	int gameMode;
	int difficulty;
	boolean spawnMonsters;
	int opsPermissionLevel;
	boolean queryEnabled;
	boolean forceGameMode;
	String levelType;
	int serverPort;
	boolean spawnAnimal;
	String descr;
	String gameModeDescr;
	String difficultyDescr;
	int maxPlayers;
	boolean whitelist;
	String motd;
	String mapName;
	int viewDistance;
	String resourcePack;
	String commandBlock;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	public String getMemory() {
		return memory;
	}
	public void setMemory(String memory) {
		this.memory = memory;
	}
	public int getGameMode() {
		return gameMode;
	}
	public void setGameMode(int gameMode) {
		this.gameMode = gameMode;
	}
	public int getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
	
	public int getOpsPermissionLevel() {
		return opsPermissionLevel;
	}
	public void setOpsPermissionLevel(int opsPermissionLevel) {
		this.opsPermissionLevel = opsPermissionLevel;
	}
	public boolean isQueryEnabled() {
		return queryEnabled;
	}
	public void setQueryEnabled(boolean queryEnabled) {
		this.queryEnabled = queryEnabled;
	}
	public boolean isForceGameMode() {
		return forceGameMode;
	}
	public void setForceGameMode(boolean forceGameMode) {
		this.forceGameMode = forceGameMode;
	}
	public String getLevelType() {
		return levelType;
	}
	public void setLevelType(String levelType) {
		this.levelType = levelType;
	}
	public int getServerPort() {
		return serverPort;
	}
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}
	public boolean getSpawnAnimal() {
		return spawnAnimal;
	}
	public void setSpawnAnimal(boolean spawnAnimal) {
		this.spawnAnimal = spawnAnimal;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MinecraftServerInfo other = (MinecraftServerInfo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public int compareTo(MinecraftServerInfo o) {
		return id.compareTo(o.id);
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getGameModeDescr() {
		return gameModeDescr;
	}
	public void setGameModeDescr(String gameModeDescr) {
		this.gameModeDescr = gameModeDescr;
	}
	public String getDifficultyDescr() {
		return difficultyDescr;
	}
	public void setDifficultyDescr(String difficultyDescr) {
		this.difficultyDescr = difficultyDescr;
	}
	public int getMaxPlayers() {
		return maxPlayers;
	}
	public void setMaxPlayers(int maxPlayers) {
		this.maxPlayers = maxPlayers;
	}
	public boolean isSpawnMonsters() {
		return spawnMonsters;
	}
	public void setSpawnMonsters(boolean spawnMonsters) {
		this.spawnMonsters = spawnMonsters;
	}
	public boolean getWhitelist() {
		return whitelist;
	}
	public void setWhitelist(boolean whitelist) {
		this.whitelist = whitelist;
	}
	public String getMotd() {
		return motd;
	}
	public void setMotd(String motd) {
		this.motd = motd;
	}
	public String getMapName() {
		return mapName;
	}
	public void setMapName(String mapName) {
		this.mapName = mapName;
	}
	public int getViewDistance() {
		return viewDistance;
	}
	public void setViewDistance(int viewDistance) {
		this.viewDistance = viewDistance;
	}
	public String getResourcePack() {
		return resourcePack;
	}
	public void setResourcePack(String resourcePack) {
		this.resourcePack = resourcePack;
	}
	public String getCommandBlock() {
		return commandBlock;
	}
	public void setCommandBlock(String commandBlock) {
		this.commandBlock = commandBlock;
	}
	
}
