package com.tedu.show;


import java.awt.Graphics;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import com.tedu.element.ElementObj;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;

/**
 * @˵�� ��Ϸ����Ҫ���
 * @author ASUS
 * @����˵�� ��Ҫ����Ԫ�ص���ʾ��ͬʱ���н����ˢ��(���߳�)
 * 
 * @���⻰ java����ʵ��˼����Ӧ���ǣ����̳л����ǽӿ�ʵ��
 * 
 * @���߳�ˢ�� 1.����ʵ���߳̽ӿ�
 *           2.�����ж���һ���ڲ�����ʵ��
 */
public class GameMainJPanel extends JPanel implements Runnable{
	//����������
	private ElementManager em;
	
	public GameMainJPanel(){
		init();
	}
	
	
	public void init(){
		em = ElementManager.getManager();//�õ�Ԫ�ع�����
	}

	/**
	 * paint�����ǽ��л滭Ԫ�ء�
	 * �滭ʱ���й̶���˳���Ȼ滭��ͼƬ���ڵײ㣬��滭��ͼƬ�Ḳ���Ȼ滭��
	 * Լ����������ִֻ��һ�Σ���ʵʱˢ����Ҫʹ�ö��߳�
	 */
	@Override  //���ڻ滭��  Graphics ����
	public void paint(Graphics g) {
		
		super.paint(g);	//���ø����paint����
		//map key-value key�����򲻿��ظ��ġ�
		//set ��map��keyһ�� ���򲻿��ظ��ġ�
		Map<GameElement, List<ElementObj>> all = em.getGameElements();
		//GameElement.values();//���ط���  ����ֵ��һ�����飬�����˳����Ƕ���ö�ٵ�˳��
		for(GameElement ge:GameElement.values()){
			List<ElementObj> list = all.get(ge);
			for(int i=0; i<list.size(); i++){
				ElementObj obj = list.get(i);//��ȡΪ����
				obj.showElement(g);//����ÿ�����show��������Լ�����ʾ
			}
		}
		
		/*Set<GameElement> set = all.keySet();//�õ����е�key����
		for(GameElement ge:set){  //������
			List<ElementObj> list = all.get(ge);
			for(int i=0; i<list.size(); i++){
				ElementObj obj = list.get(i);//��ȡΪ����
				obj.showElement(g);//����ÿ�����show��������Լ�����ʾ
			}
		}*/
		
		/*g.setColor(new Color(255,0,0));
		g.setFont(new Font("΢���ź�",Font.BOLD,48));
		g.drawString("i l java", 200, 200);//һ��Ҫ�ڻ滭֮ǰ������ɫ������
		
		//���۾�
		g.setColor(new Color(0,0,0));
		g.fillOval(325, 240, 25, 25);
		g.fillOval(350, 240, 25, 25);
		//��ͷ
		g.setColor(new Color(255,255,255));
		g.fillOval(325, 225, 50, 100);
		//��β��
		g.fillOval(340, 480, 20, 70);
		//����֫
		g.fillOval(240, 325, 80, 40);
		g.fillOval(380, 325, 80, 40);
		g.fillOval(240, 425, 80, 40);
		g.fillOval(380, 425, 80, 40);
		//���۾�
		g.setColor(new Color(0,0,0));
		g.fillOval(325, 240, 25, 25);
		g.fillOval(350, 240, 25, 25);
		//������
		g.setColor(new Color(0,0,0));
		g.fillOval(300, 300, 100, 200);
		g.drawOval(325, 225, 50, 100);
		g.drawOval(240, 325, 80, 40);
		g.drawOval(380, 325, 80, 40);
		g.drawOval(240, 425, 80, 40);
		g.drawOval(380, 425, 80, 40);
		g.drawOval(340, 480, 20, 70);
		
		g.setColor(new Color(255,0,255));
		g.drawOval(200, 200, 100, 200);*/
	}

	@Override
	public void run() {	 //�ӿ�ʵ��
		while(true){
			this.repaint();
			//һ������£����̶߳���ʹ��һ�����ߣ������ٶ�
			try {
				Thread.sleep(10);	//����50����  1��ˢ��20��
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}
	}
	
}
