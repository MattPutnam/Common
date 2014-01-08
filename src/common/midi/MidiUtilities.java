package common.midi;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;

public class MidiUtilities {
	private static List<String> numList = Arrays.asList(new String[] {"C",
			"C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"});
	
	private static final Map<String, Integer> _baseMap = new HashMap<>();
	static {
		_baseMap.put("C", 0);
		_baseMap.put("D", 2);
		_baseMap.put("E", 4);
		_baseMap.put("F", 5);
		_baseMap.put("G", 7);
		_baseMap.put("A", 9);
		_baseMap.put("B", 11);
	}
	
	private static final Pattern NOTE_FORMAT = Pattern.compile("([A-G])([#b]?)(-?\\d{1,2})");
	
	/**
	 * Converts a note name in scientific pitch notation into its MIDI number
	 * @param noteName - the name of the note in scientific pitch notation
	 * (e.g., A4, C#3, Gb5)
	 * @return the MIDI number of the given note
	 */
	public static int noteNameToNumber(String noteName) {
		try {
			return Integer.parseInt(noteName);
		} catch (NumberFormatException nfe) {
		}
		
		final Matcher matcher = NOTE_FORMAT.matcher(noteName);
		if (!matcher.matches())
			throw new IllegalArgumentException("Given note name didn't match pattern");
		
		final MatchResult result = matcher.toMatchResult();
		final String pitchClass = result.group(1);
		final String sharpFlat = result.group(2);
		final int octave = Integer.parseInt(result.group(3));
		
		final int accidental;
		if (sharpFlat.isEmpty())
			accidental = 0;
		else if (sharpFlat.charAt(0) == 'b')
			accidental = -1;
		else
			accidental = 1;
		
		return _baseMap.get(pitchClass) + accidental + (12*(octave+1));
	}
	
	/**
	 * Converts a MIDI number into a note name in scientific pitch notation
	 * @param number - the MIDI number of the note
	 * @return the note name in scientific pitch notation (e.g., A4, C#3, F#5)
	 */
	public static String noteNumberToName(int number) {
		int octave = (number / 12) - 1;
		int noteIdx = number % 12;
		return (numList.get(noteIdx)).toUpperCase() + octave;
	}
	
	/**
	 * Creates a human-readable string for a MIDI ShortMessage
	 * @param sm - the ShortMessage to use
	 * @return a String representation of the ShortMessage
	 */
	public static String toString(MidiMessage mm) {
		if (mm instanceof ShortMessage) {
			final ShortMessage sm = (ShortMessage) mm;
			if (isNoteOn(sm)) {
				return "Note On ch=" + (sm.getChannel()+1) + " note=" + sm.getData1()
						+ " (" + noteNumberToName(sm.getData1()) + ") vel="
						+ sm.getData2();
			} else if (isNoteOff(sm)) {
				return "Note Off ch=" + (sm.getChannel()+1) + " note="
						+ sm.getData1() + " ("
						+ noteNumberToName(sm.getData1()) + ")";
			} else if (isControlChange(sm)) {
				return "Control Change ch=" + (sm.getChannel()+1) + " control="
						+ sm.getData1() + " value=" + sm.getData2();
			}
			
			return "ShortMessage[ch=" + (sm.getChannel()+1) +
					" cmd=" + sm.getCommand() +
					" d1=" + sm.getData1() +
					" d2=" + sm.getData2() + "]";
		} else {
			return mm.getClass().getSimpleName() + " " + Arrays.toString(mm.getMessage());
		}
	}
	
	/**
	 * Determines if the given ShortMessage is a MIDI NOTE ON message.  Note
	 * that the MIDI specification says that a NOTE ON with velocity=0 is
	 * equivalent to a NOTE OFF.
	 * @param sm - the ShortMessage to check
	 * @return <tt>true</tt> if the ShortMessage is a MIDI NOTE ON message
	 */
	public static boolean isNoteOn(ShortMessage sm) {
		return sm.getCommand() == ShortMessage.NOTE_ON && sm.getData2() > 0;
	}
	
	/**
	 * Determines if the given ShortMessage is a MIDI NOTE OFF message.  Note
	 * that the MIDI specification says that a NOTE ON with velocity=0 is
	 * equivalent to a NOTE OFF.
	 * @param sm - the ShortMessage to check
	 * @return <tt>true</tt> if the ShortMessage is a MIDI NOTE OFF message
	 */
	public static boolean isNoteOff(ShortMessage sm) {
		final int command = sm.getCommand();
		return command == ShortMessage.NOTE_OFF ||
			(command == ShortMessage.NOTE_ON && sm.getData2() == 0);
	}
	
	/**
	 * Determines if the given ShortMessage is a MIDI Control Change message
	 * @param sm - the ShortMessage to cehck
	 * @return <tt>true</tt> if the ShortMessage is a MIDI Control Change message
	 */
	public static boolean isControlChange(ShortMessage sm) {
		return sm.getCommand() == ShortMessage.CONTROL_CHANGE;
	}
	
	/**
	 * Clamps the given value to be between 0 and 127 (inclusive)
	 * @param val the value
	 * @return 0 if <tt>val</tt> < 0, 127 if <tt>val</tt> > 127, otherwise <tt>val</tt>
	 */
	public static int clamp(int val) {
		if (val < 0) return 0;
		if (val > 127) return 127;
		return val;
	}
	
}
