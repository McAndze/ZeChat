package com.gmail.andreasmartinmorch.danandchat.chmarkup;

public enum ChatColour {
	BLACK("§0"),
	DARK_BLUE("§1"),
	DARK_GREEN("§2"),
	DARK_AQUA("§3"),
	DARK_RED("§4"),
	DARK_PURPLE("§5"),
	GOLD("§6"),
	GRAY("§7"),
	DARK_GRAY("§8"),
	BLUE("§9"),
	GREEN("§a"),
	AQUA("§b"),
	RED("§c"),
	LIGHT_PURPLE("§d"),
	YELLOW("§e"),
	WHITE("§f");
	
	private final String strPresentation;

	private ChatColour(String strPresentation) {
		this.strPresentation = strPresentation;
	}

	public String getStrPresentation() {
		return strPresentation;
	}
}
