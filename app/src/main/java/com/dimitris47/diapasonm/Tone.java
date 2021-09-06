package com.dimitris47.diapasonm;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

public class Tone {

    final static int duration = 1;
    final static int sampleRate = 44100;
    final static int numSamples = duration * sampleRate;
    final static double[] samples = new double[numSamples];
    final static short[] buffer = new short[numSamples];

    static AudioTrack audioTrack;

    public Tone(double freq) {
        for (int i = 0; i < numSamples; ++i) {
            samples[i] = Math.sin(2 * Math.PI * i / (sampleRate / freq));
            buffer[i] = (short) (samples[i] * Short.MAX_VALUE);
        }
        if (audioTrack != null) {
            audioTrack.flush();
            audioTrack.stop();
            audioTrack.release();
        }
        audioTrack = new AudioTrack(
                AudioManager.STREAM_MUSIC,
                sampleRate,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                buffer.length * 2 / 3,
                AudioTrack.MODE_STATIC);
        audioTrack.write(buffer, 0, buffer.length);
        audioTrack.play();
    }
}
