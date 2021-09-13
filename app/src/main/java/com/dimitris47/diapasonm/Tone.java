package com.dimitris47.diapasonm;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

public class Tone {

    final static int sampleRate = 44100;
    static AudioTrack audioTrack;

    public Tone(double freq, int duration) {
        final int numSamples = duration * sampleRate;
        final double[] samples = new double[numSamples];
        final short[] buffer = new short[numSamples];
        for (int i = 0; i < numSamples; ++i) {
            samples[i] = Math.sin(2 * Math.PI * i / (sampleRate / freq));
            buffer[i] = (short) (samples[i] * Short.MAX_VALUE);
        }
        stopTone();
        audioTrack = new AudioTrack(
                AudioManager.STREAM_MUSIC,
                sampleRate,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                buffer.length * 2,
                AudioTrack.MODE_STATIC);
        audioTrack.write(buffer, 0, buffer.length);
        audioTrack.play();
    }

    public static void stopTone() {
        if (audioTrack != null) {
            audioTrack.flush();
            audioTrack.stop();
            audioTrack.release();
            audioTrack = null;
        }
    }
}
