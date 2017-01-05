package dev.wolveringer.terminal.string;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import net.md_5.bungee.api.ChatColor;

public class ColoredString implements java.io.Serializable, Comparable<ColoredString>, CharSequence {
	private ColoredChar[] characters = new ColoredChar[0];
	private int index = 0;

	public ColoredString() {
	}

	public ColoredString(String message) {
		add(message);
	}

	public void add(String message) {
		List<ColoredChar> chars = new ArrayList<>();
		ColoredChar last = characters.length == 0 ? new ColoredChar('A') : characters[characters.length-1];
		char[] c = message.toCharArray();
		for(int index = 0; index < c.length;index++){
			if(c[index] == '§'){
				char colorcode = c[++index];
				ChatColor color = ChatColor.getByChar(colorcode);
				if(color == null){
					System.err.println("Cant find character code for "+colorcode);
					color = ChatColor.RESET;
				}
				last = last.clone();
				last.applayChatColor(color);
				continue;
			}
			chars.add(last = last.copyStyle(c[index]));
		}
		enschureSpace(chars.size());
		for (ColoredChar ch : chars)
			characters[index++] = ch;
	}
	
	public void add(ColoredString message) {
		enschureSpace(message.getSize());
		for(ColoredChar chars : message.characters)
			characters[index++] = chars;
	}

	public void add(ColoredChar character) {
		enschureSpace(1);
		characters[index++] = character;
	}

	public void set(int index, ColoredChar character) {
		characters[index] = character;
	}
	
	public ColoredChar getChar(int index){
		return characters[index];
	}
	
	public int getSize(){
		return characters.length;
	}

	private void enschureSpace(int size) {
		if (characters.length <= index + size) {
			ColoredChar[] oldCharacters = characters;
			characters = (ColoredChar[]) Array.newInstance(ColoredChar.class, index + size);
			System.arraycopy(oldCharacters, 0, characters, 0, oldCharacters.length);
		}
	}
	
	public ColoredString substring(int start,int end){
		ColoredChar[] chars = new ColoredChar[end-start];
		System.arraycopy(characters, start, chars, 0, chars.length);
		ColoredString string = new ColoredString();
		string.characters = chars;
		string.index = chars.length;
		return string;
	}
	
	@Override
	public String toString() {
		return toString(true);
	}
	
	public String toString(boolean colored) {
		StringBuilder out = new StringBuilder();
		ColoredChar last = null;
		for(ColoredChar character : this.characters){
			if(!colored)
				out.append(character.getCharacter());
			else
				out.append(character.toString(last));
			last = character;
		}
		return out.toString()/*+(colored ? "§r" : "")*/;
	}
	@Override
	public ColoredString clone(){
		ColoredString out = new ColoredString();
		out.characters = characters.clone();
		out.index = index;
		return out;
	}

	@Override
	public int length() {
		return characters.length;
	}

	@Override
	public char charAt(int index) {
		return characters[index].getCharacter();
	}

	@Override
	public CharSequence subSequence(int start, int end) {
		return null; //TODO
	}

	@Override
	public int compareTo(ColoredString o) {
		return 0;
	}
	
	public ColoredChar[] getCharacters(){
		return this.characters;
	}
}
