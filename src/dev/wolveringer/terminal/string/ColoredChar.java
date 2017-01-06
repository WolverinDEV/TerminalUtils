package dev.wolveringer.terminal.string;

import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ChatColor;

public class ColoredChar {
	private static final ChatColor[] MODIFIERS = {ChatColor.BOLD,ChatColor.ITALIC,ChatColor.UNDERLINE,ChatColor.MAGIC};
	@Getter
	@Setter
	private ChatColor color;
	private boolean[] modifiers = new boolean[MODIFIERS.length];
	@Getter
	@Setter
	private char character;
	
	public ColoredChar(char character) {
		this.character = character;
	}
	
	public ColoredChar(String character) {
		if(ChatColor.stripColor(character).length() != 1 || character.charAt(character.length()-1) != ChatColor.stripColor(character).charAt(0))
			throw new IllegalArgumentException();
		this.character = ChatColor.stripColor(character).charAt(0);
		setColors(character.substring(0, character.indexOf(this.character)));
	}
	
	public void setModifier(ChatColor color,boolean flag){
		switch (color) {
		case BOLD:
		case ITALIC:
		case UNDERLINE:
		case MAGIC:
			modifiers[getModifierIndex(color)] = flag;
			break;
		default:
			throw new IllegalArgumentException("Colorcode "+color+" isnt a modifier!");
		}
	}
	
	public void applayChatColor(ChatColor color){
		switch (color) {
		case BOLD:
		case ITALIC:
		case UNDERLINE:
		case MAGIC:
			modifiers[getModifierIndex(color)] = true;
			break;
		case RESET:
			resetColor();
			resetModifiers();
			break;
		default:
			resetModifiers();
			this.color = color;
		}
	}
	
	public void resetColor(){
		color = null;
	}
	
	public void resetModifiers(){
		modifiers = new boolean[MODIFIERS.length];
	}
	
	public void setColors(String colors){
		int index = 0;
		while (index < colors.length()) {
			if(colors.charAt(index) != ChatColor.COLOR_CHAR)
				throw new IllegalArgumentException("Unexpected character '"+colors.charAt(index)+"' at index "+index+" in string '"+colors+"'");
			char charcode = colors.charAt(++index);
			ChatColor color = ChatColor.getByChar(charcode);
			switch (color) {
			case RESET:
				resetColor();
				resetModifiers();
				break;
			case BOLD:
			case ITALIC:
			case UNDERLINE:
			case MAGIC:
				modifiers[getModifierIndex(color)] = true;
				break;
			default:
				setColor(color);
				resetModifiers();
				break;
			}
			index++;
		}
	}
	
	private int getModifierIndex(ChatColor color){
		for (int i = 0; i < MODIFIERS.length; i++) {
			if(color == MODIFIERS[i])
				return i;
		}
		return -1;
	}
	
	private ChatColor getModifier(int index){
		return MODIFIERS[index];
	}
	
	@Override
	public String toString() {
		return toString(true);
	}
	
	public String toString(boolean printColor) {
		return toString(printColor, false);
	}
	
	public String toString(boolean printColor,boolean reset) {
		if(printColor)
			return (color != null ? color.toString() : "")+buildModifiers()+Character.toString(character)+(reset ? ChatColor.COLOR_CHAR+"r" : "");
		return Character.toString(character);
	}
	
	public String toString(ColoredChar parent){
		if(parent == null)
			return toString(true);
		String out = "";
		boolean resetted = false;
		
		block:
		do {
			if(parent.color != color){
				resetted = true;
				break block;
			}
			for(int i = 0;i<MODIFIERS.length;i++)
				if(modifiers[i]){
					if(!parent.modifiers[i]){
						out += getModifier(i);
					}
				}
				else if(parent.modifiers[i]){
					resetted = true;
					break block;
				}
		}while(false);
		if(resetted){
			out = "";
			out += color;
			for(int i = 0;i<MODIFIERS.length;i++)
				if(modifiers[i])
					out += getModifier(i);
		}
		return out+Character.toString(this.character);
	}
	
	private String buildModifiers(){
		String out = "";
		for(int i = 0;i<MODIFIERS.length;i++)
			if(modifiers[i])
				out += getModifier(i);
		return out;
	}
	
	@Override
	public ColoredChar clone() {
		ColoredChar out = new ColoredChar(character);
		out.color = color;
		System.arraycopy(modifiers, 0, out.modifiers, 0, modifiers.length);
		return out;
	}
	
	protected ColoredChar copyStyle(char newChar){
		ColoredChar clone = clone();
		clone.character = newChar;
		return clone;
	}
}
