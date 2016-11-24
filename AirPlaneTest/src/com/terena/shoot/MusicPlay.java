package com.terena.shoot;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import sun.audio.*;

/**
 * 测试声音播放
 * 
 * @author 小明
 *
 */
public class MusicPlay
{

	public Thread t;

	public MusicPlay(String filename) 
	{
		try
		{
		
		InputStream in = new FileInputStream(filename);
		// 创建音频流对象
		final AudioStream audioStream = new AudioStream(in);

		t = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				// 使用音频播放器播放声音
				AudioPlayer.player.start(audioStream);
			}
		});

		/*Thread.sleep(500);
		// 停止声音播放
		AudioPlayer.player.stop(audioStream);*/
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void play()
	{
		t.start();
	}

	public static void main(String[] args) 
	{
		// 创建音乐文件输入流对象
		// String filename = System.getProperty("user.dir") + "\\" + "gun.wav";
		MusicPlay mp = new MusicPlay(System.getProperty("user.dir") + "\\" + "gun.wav");
		mp.play();
		System.out.println("haha");

	}
}