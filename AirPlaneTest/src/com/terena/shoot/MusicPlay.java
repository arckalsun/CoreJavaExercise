package com.terena.shoot;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import sun.audio.*;

/**
 * ������������
 * 
 * @author С��
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
		// ������Ƶ������
		final AudioStream audioStream = new AudioStream(in);

		t = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				// ʹ����Ƶ��������������
				AudioPlayer.player.start(audioStream);
			}
		});

		/*Thread.sleep(500);
		// ֹͣ��������
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
		// ���������ļ�����������
		// String filename = System.getProperty("user.dir") + "\\" + "gun.wav";
		MusicPlay mp = new MusicPlay(System.getProperty("user.dir") + "\\" + "gun.wav");
		mp.play();
		System.out.println("haha");

	}
}