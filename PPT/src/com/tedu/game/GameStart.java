package com.tedu.game;

import com.tedu.controller.GameListener;
import com.tedu.controller.GameThread;
import com.tedu.show.GameJFrame;
import com.tedu.show.GameMainJPanel;

public class GameStart {
	/**
	 * @˵�� �����Ψһ���
	 * @param args
	 */
	public static void main(String[] args) {
		GameJFrame gj = new GameJFrame();
		/*ʵ������壬ע�뵽jframe��*/
		GameMainJPanel jp = new GameMainJPanel();
		
		//ʵ��������
		GameListener listener = new GameListener();
		//ʵ�������߳�
		GameThread th = new GameThread();
		//ע��
		gj.setjPanel(jp);
		gj.setKeyListener(listener);
		gj.setThread(th);
		
		gj.start();//��ʾ����
		
	}
	
}


/**
 * 1.������Ϸ�������Ϸ�������ļ���ʽ���ļ���ȡ��ʽ��load��ʽ��
 * 2.�����Ϸ��ɫ��������Ϸ���󣨳�����ڻ���ļ̳У�
 * 3.����pojo�ࣨvo��...
 * 4.��Ҫ�ķ������ڸ�������д��������಻֧�֣����Բ����޸ĸ��ࣩ
 * 5.������ã���ɶ����load��add��manager
 * 6.��ײ��ϸ�ڴ���
 */
