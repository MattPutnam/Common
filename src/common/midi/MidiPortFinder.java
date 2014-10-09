package common.midi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;


public final class MidiPortFinder {
  private MidiPortFinder() {}
  
  static {
//    com.sun.media.sound.JDK13Services.setCachingPeriod(1);
  }
  
  public static List<MidiDevice.Info> getAllMidiDeviceInfos() {
    return Arrays.asList(MidiSystem.getMidiDeviceInfo());
  }
  
  public static List<MidiDevice> getInputMidiDevices() {
    final List<MidiDevice> result = new ArrayList<>();
    for (final MidiDevice.Info info : MidiSystem.getMidiDeviceInfo()) {
      MidiDevice device = null;
      try {
        device = MidiSystem.getMidiDevice(info);
        if (device.getMaxTransmitters() != 0)
          result.add(device);
      } catch (MidiUnavailableException mue) {
        // shouldn't happen
        mue.printStackTrace();
      }
    }
    return result;
  }
  
  public static List<MidiDevice.Info> getInputMidiDeviceInfos() {
    final List<MidiDevice.Info> result = new ArrayList<>();
    for (final MidiDevice device : getInputMidiDevices()) {
      result.add(device.getDeviceInfo());
    }
    return result;
  }
  
  public static List<MidiDevice> getOutputMidiDevices() {
    final List<MidiDevice> result = new ArrayList<>();
    for (final MidiDevice.Info info : MidiSystem.getMidiDeviceInfo()) {
      MidiDevice device = null;
      try {
        device = MidiSystem.getMidiDevice(info);
        if (device.getMaxReceivers() != 0)
          result.add(device);
      } catch (MidiUnavailableException mue) {
        // shouldn't happen
        mue.printStackTrace();
      }
    }
    return result;
  }
  
  public static List<MidiDevice.Info> getOutputMidiDeviceInfos() {
    final List<MidiDevice.Info> result = new ArrayList<>();
    for (final MidiDevice device : getOutputMidiDevices()) {
      result.add(device.getDeviceInfo());
    }
    return result;
  }
}
