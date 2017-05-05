package motherteresa;
/*
 * Copyright (c) 1999 -2001 by Matthias Pfisterer
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * - Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 * - Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

//import gnu.getopt.Getopt;


    /**	&lt;titleabbrev&gt;OscillatorPlayer&lt;/titleabbrev&gt;
	&lt;title&gt;Playing waveforms&lt;/title&gt;

	&lt;formalpara&gt;&lt;title&gt;Purpose&lt;/title&gt;
	&lt;para&gt;
	Plays waveforms (sine, square, ...).
	&lt;/para&gt;&lt;/formalpara&gt;

	&lt;formalpara&gt;&lt;title&gt;Usage&lt;/title&gt;
	&lt;para&gt;
	&lt;cmdsynopsis&gt;
	&lt;command&gt;java OscillatorPlayer&lt;/command&gt;
	&lt;arg&gt;&lt;option&gt;-t &lt;replaceable&gt;waveformtype&lt;/replaceable&gt;&lt;/option&gt;&lt;/arg&gt;
	&lt;arg&gt;&lt;option&gt;-f &lt;replaceable&gt;signalfrequency&lt;/replaceable&gt;&lt;/option&gt;&lt;/arg&gt;
	&lt;arg&gt;&lt;option&gt;-r &lt;replaceable&gt;samplerate&lt;/replaceable&gt;&lt;/option&gt;&lt;/arg&gt;
	&lt;arg&gt;&lt;option&gt;-a &lt;replaceable&gt;amplitude&lt;/replaceable&gt;&lt;/option&gt;&lt;/arg&gt;
	&lt;/cmdsynopsis&gt;
	&lt;/para&gt;
	&lt;/formalpara&gt;

	&lt;formalpara&gt;&lt;title&gt;Parameters&lt;/title&gt;
	&lt;variablelist&gt;
	&lt;varlistentry&gt;
	&lt;term&gt;&lt;option&gt;-t &lt;replaceable&gt;waveformtype&lt;/replaceable&gt;&lt;/option&gt;&lt;/term&gt;
	&lt;listitem&gt;&lt;para&gt;the waveform to play. One of sine, sqaure, triangle and sawtooth. Default: sine.&lt;/para&gt;&lt;/listitem&gt;
	&lt;/varlistentry&gt;
	&lt;varlistentry&gt;
	&lt;term&gt;&lt;option&gt;-f &lt;replaceable&gt;signalfrequency&lt;/replaceable&gt;&lt;/option&gt;&lt;/term&gt;
	&lt;listitem&gt;&lt;para&gt;the frequency of the signal to create. Default: 1000 Hz.&lt;/para&gt;&lt;/listitem&gt;
	&lt;/varlistentry&gt;
	&lt;varlistentry&gt;
	&lt;term&gt;&lt;option&gt;-r &lt;replaceable&gt;samplerate&lt;/replaceable&gt;&lt;/option&gt;&lt;/term&gt;
	&lt;listitem&gt;&lt;para&gt;the sample rate to use. Default: 44.1 kHz.&lt;/para&gt;&lt;/listitem&gt;
	&lt;/varlistentry&gt;
	&lt;varlistentry&gt;
	&lt;term&gt;&lt;option&gt;-a &lt;replaceable&gt;amplitude&lt;/replaceable&gt;&lt;/option&gt;&lt;/term&gt;
	&lt;listitem&gt;&lt;para&gt;the amplitude of the generated signal. May range from 0.0 to 1.0. 1.0 means a full-scale wave. Default: 0.7.&lt;/para&gt;&lt;/listitem&gt;
	&lt;/varlistentry&gt;
	&lt;/variablelist&gt;
	&lt;/formalpara&gt;

	&lt;formalpara&gt;&lt;title&gt;Bugs, limitations&lt;/title&gt;
	&lt;para&gt;
	Full-scale waves can lead to clipping. It is currently not known
	which component is responsible for this.
	&lt;/para&gt;&lt;/formalpara&gt;

	&lt;formalpara&gt;&lt;title&gt;Source code&lt;/title&gt;
	&lt;para&gt;
	&lt;ulink url="OscillatorPlayer.java.html"&gt;OscillatorPlayer.java&lt;/ulink&gt;,
	&lt;ulink url="Oscillator.java.html"&gt;Oscillator.java&lt;/ulink&gt;,
	&lt;ulink url="http://www.urbanophile.com/arenn/hacking/download.html"&gt;gnu.getopt.Getopt&lt;/ulink&gt;
	&lt;/para&gt;&lt;/formalpara&gt;

*/
public class OscillatorPlayer
{
	private static final int	BUFFER_SIZE = 128000;
	private static boolean		DEBUG = false;



	public static void main(String[] args)		throws	IOException
	{
		byte[]		abData;
		AudioFormat	audioFormat;
		int	nWaveformType = Oscillator.WAVEFORM_SINE;
		float	fSampleRate = 44100.0F;
		float	fSignalFrequency = 8000.0F;
		float	fAmplitude = 0.7F;

		
		 //*	Parsing of command-line options takes place...
		Getopt	g = new Getopt("AudioPlayer",args, "ht:r:f:a:D");
		int	c;
		while ((c = g.getopt()) != -1)
		{
			switch (c)
			{
			case 'h':
				printUsageAndExit();

			case 't':
				nWaveformType = 
                                Integer.parseInt(g.getOptarg());
				break;

			case 'r':
				fSampleRate = Float.parseFloat(g.getOptarg());
				break;

			case 'f':
				fSignalFrequency = Float.parseFloat(g.getOptarg());
				break;

			case 'a':
				fAmplitude = Float.parseFloat(g.getOptarg());
				break;

			case 'D':
				DEBUG = true;
				break;

			case '?':
				printUsageAndExit();

			default:
				if (DEBUG) { out("getopt() returned " + c); }
				break;
			}
		}

		audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
					   fSampleRate, 16, 2, 4, fSampleRate, false);
		AudioInputStream	oscillator = new Oscillator(
			nWaveformType,
			fSignalFrequency,
			fAmplitude,
			audioFormat,
			AudioSystem.NOT_SPECIFIED);
		SourceDataLine	line = null;
		DataLine.Info	info = new DataLine.Info(
			SourceDataLine.class,
			audioFormat);
		try
		{
			line = (SourceDataLine) AudioSystem.getLine(info);
			line.open(audioFormat);
		}
		catch (LineUnavailableException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		line.start();
		
                


		abData = new byte[BUFFER_SIZE];
		while (true)
		{
                if (DEBUG) { out("OscillatorPlayer.main(): trying to read (bytes): " + abData.length); }
			int	nRead = oscillator.read(abData);
			if (DEBUG) { out("OscillatorPlayer.main(): in loop, read (bytes):"  + nRead); }
			int	nWritten = line.write(abData, 0, nRead);
			if (DEBUG) { out("OscillatorPlayer.main(): written: " + nWritten); }
		}
	}


	private static int getWaveformType(String strWaveformType)
        {
                int	nWaveformType = Oscillator.WAVEFORM_SINE;
		strWaveformType = strWaveformType.trim().toLowerCase();
		if (strWaveformType.equals("sine"))
		{
			nWaveformType = Oscillator.WAVEFORM_SINE;
		}
		else if (strWaveformType.equals("square"))
		{
			nWaveformType = Oscillator.WAVEFORM_SQUARE;
		}
		else if (strWaveformType.equals("triangle"))
		{
			nWaveformType = Oscillator.WAVEFORM_TRIANGLE;
		}
		else if (strWaveformType.equals("sawtooth"))
		{
			nWaveformType = Oscillator.WAVEFORM_SAWTOOTH;
		}
		return nWaveformType;
	}



        private static void printUsageAndExit()
	{
		out("OscillatorPlayer: usage:");
		out("\tjava OscillatorPlayer [-t <waveformtype>] [-f <signalfrequency>] [-r <samplerate>]");
		System.exit(1);
	}


        private static void out(String strMessage)
	{
		System.out.println(strMessage);
	}
}



