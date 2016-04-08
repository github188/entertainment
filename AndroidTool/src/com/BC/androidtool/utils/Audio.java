package com.BC.androidtool.utils;

import java.io.FileInputStream;
import java.io.InputStream;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;

public class Audio {
	public static AudioTrack palyAudio(String file) {
		// TODO Auto-generated method stub
		byte[] buffer = null;
		AudioTrack at = null;
		int pcmlen = 0;
		try {
			FileInputStream fis = new FileInputStream(file);
			buffer = new byte[1024 * 1024 * 2];// 2M
			int len = fis.read(buffer);
			Log.i("shuzhi", "fis len=" + len);
			// Log.i("shuzhi", "0:" + (char) buffer[0]);
			pcmlen = 0;
			pcmlen += buffer[0x2b];
			pcmlen = pcmlen * 256 + buffer[0x2a];
			pcmlen = pcmlen * 256 + buffer[0x29];
			pcmlen = pcmlen * 256 + buffer[0x28];

			int channel = buffer[0x17];
			channel = channel * 256 + buffer[0x16];

			int bits = buffer[0x23];
			bits = bits * 256 + buffer[0x22];
			// Log.i("shuzhi", "pcmlen=" + pcmlen + ",channel=" + channel +
			// ",bits="
			// + bits);
			at = new AudioTrack(AudioManager.STREAM_MUSIC, 44100, channel,
					AudioFormat.ENCODING_PCM_16BIT, pcmlen,
					AudioTrack.MODE_STATIC);
			at.write(buffer, 0x2C, pcmlen);
			// Log.i("shuzhi", "write 1...");
			at.play();
			// Log.i("shuzhi", "play 1...");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return at;
	}
	public static AudioTrack palyAudio(Context context,int file) {
		// TODO Auto-generated method stub
		byte[] buffer = null;
		AudioTrack at = null;
		int pcmlen = 0;
		try {
			InputStream in = context.getResources().openRawResource(file);
			// 获取文件的字节数
			int lenght = in.available();
			// 创建byte数组
			buffer = new byte[lenght];
			// 将文件中的数据读到byte数组中
			in.read(buffer);
			// Log.i("shuzhi", "fis len=" + len);
			// Log.i("shuzhi", "0:" + (char) buffer[0]);
			pcmlen = 0;
			pcmlen += buffer[0x2b];
			pcmlen = pcmlen * 256 + buffer[0x2a];
			pcmlen = pcmlen * 256 + buffer[0x29];
			pcmlen = pcmlen * 256 + buffer[0x28];

			int channel = buffer[0x17];
			channel = channel * 256 + buffer[0x16];

			int bits = buffer[0x23];
			bits = bits * 256 + buffer[0x22];
			// Log.i("shuzhi", "pcmlen=" + pcmlen + ",channel=" + channel +
			// ",bits="
			// + bits);
			at = new AudioTrack(AudioManager.STREAM_MUSIC, 44100, channel,
					AudioFormat.ENCODING_PCM_16BIT, pcmlen,
					AudioTrack.MODE_STATIC);
			at.write(buffer, 0x2C, pcmlen);
			// Log.i("shuzhi", "write 1...");
			at.play();
			// Log.i("shuzhi", "play 1...");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return at;
	}
}
