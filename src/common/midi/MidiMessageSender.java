package common.midi;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.SysexMessage;

/**
 * Utility class for sending common MIDI messages.
 * 
 * @author Matt Putnam
 */
public class MidiMessageSender {
	private Receiver _receiver;
	private boolean _valid = false;
	
	public MidiMessageSender(Receiver receiver) {
		setReceiver(receiver);
	}
	
	public Receiver getReceiver() {
		return _receiver;
	}
	
	public void setReceiver(Receiver receiver) {
		_receiver = receiver;
		_valid = _receiver != null;
	}
	
	public boolean isValid() {
		return _valid;
	}
	
	/**
	 * Sends a NOTE ON message with the given information.
	 * @param timestamp - the timestamp for the MIDI message (unused for
	 * real-time MIDI)
	 * @param channel - the MIDI channel on which to send the message
	 * @param midinumber - the MIDI number of the note to send
	 * @param velocity - the velocity of the note
	 * @throws InvalidMidiDataException if an error occurs setting the message
	 * data
	 */
	public void sendNoteOn(long timestamp, int channel, int midinumber,
			int velocity) throws InvalidMidiDataException {
		if (!isValid())
			return;
		
		final ShortMessage message = new ShortMessage();
		message.setMessage(ShortMessage.NOTE_ON, channel, midinumber,
				velocity);
		getReceiver().send(message, timestamp);
	}
	
	/**
	 * Sends a NOTE OFF message with the given information.  This method uses
	 * the NOTE OFF command (some devices may send a NOTE ON with velocity=0)
	 * @param timestamp - the timestamp for the MIDI message (unused for
	 * real-time MIDI)
	 * @param channel - the MIDI channel on which to send the message
	 * @param midinumber - the MIDI number of the note to send
	 * @throws InvalidMidiDataException if an error occurs setting the message
	 * data
	 */
	public void sendNoteOff(long timestamp, int channel, int midinumber)
			throws InvalidMidiDataException {
		if (!isValid())
			return;
		
		final ShortMessage message = new ShortMessage();
		message.setMessage(ShortMessage.NOTE_OFF, channel, midinumber, 0);
		getReceiver().send(message, timestamp);
	}
	
	/**
	 * Sends an ALL NOTES OFF message to the given channel.
	 * @param timestamp timestamp - the timestamp for the MIDI message (unused for
	 * real-time MIDI)
	 * @param channel - the MIDI channel on which to send the message
	 * @throws InvalidMidiDataException if an error occurs setting the message
	 * data
	 */
	public void sendAllNotesOff(long timestamp, int channel) throws InvalidMidiDataException {
		if (!isValid())
			return;
		
		final ShortMessage message = new ShortMessage();
		message.setMessage(ShortMessage.CONTROL_CHANGE, channel, 120, 0);
		getReceiver().send(message, timestamp);
	}
	
	/**
	 * Sends an ALL NOTES OFF message to all MIDI channels.
	 * @param timestamp - the timestamp for the MIDI message (unused for
	 * real-time MIDI)
	 * @throws InvalidMidiDataException if an error occurs setting the message
	 * data
	 */
	public void sendAllNotesOff(long timestamp) throws InvalidMidiDataException {
		if (!isValid())
			return;
		
		for (int channel = 0; channel < 16; ++channel)
			sendAllNotesOff(timestamp, channel);
	}
	
	/**
	 * Sends a Sysex message with the given data.
	 * @param timestamp - the timestamp for the MIDI message (unused for
	 * real-time MIDI)
	 * @param data - the series of bytes in the Sysex message
	 * @throws InvalidMidiDataException if an error occurs setting the message
	 * data
	 */
	public void sendSysexMessage(long timestamp, byte... data) throws InvalidMidiDataException {
		if (!isValid())
			return;
		
		final SysexMessage message = new SysexMessage();
		message.setMessage(data, data.length);
		getReceiver().send(message, timestamp);
	}
	
	/**
	 * Sends a Sysex message with the given data.
	 * @param timestamp - the timestamp for the MIDI message (unused for
	 * real-time MIDI)
	 * @param data - the series of bytes in the Sysex message.  These values
	 * should be byte-sized, the use of the int data type is only for
	 * convenience from the caller's end.
	 * @throws InvalidMidiDataException if an error occurs setting the message
	 * data
	 */
	public void sendSysexMessage(long timestamp, int... data) throws InvalidMidiDataException {
		if (!isValid())
			return;
		
		final byte[] bdata = new byte[data.length];
		for (int i = 0; i < data.length; ++i)
			bdata[i] = (byte) data[i];
		
		sendSysexMessage(timestamp, bdata);
	}
}
