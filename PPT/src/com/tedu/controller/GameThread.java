package com.tedu.controller;

import java.util.List;

import java.util.Map;

import javax.swing.ImageIcon;

import com.tedu.element.ElementObj;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;
import com.tedu.show.GameMainJPanel;

public class GameThread extends Thread{
	private ElementManager em;
	
	public GameThread(){
		em = ElementManager.getManager();
	}
	@Override
	public void run() {	//��Ϸ��run����  ���߳�
		while(true){	//��չ�����Խ�true��Ϊһ���������ڿ��ƽ���
			//��Ϸ��ʼʱ	����������������Ϸ��Դ��������Դ��
			gameLoad();
			//��Ϸ����ʱ	��Ϸ������
			gameRun();
			//��Ϸ��������	��Ϸ��Դ���գ�������Դ��
			gameOver();
			
			try {
				sleep(50);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}
	}
	
	/*��Ϸ����*/
	private void gameLoad() {
		GameLoad.loadImg();	//����ͼƬ

		GameLoad.loadBgMusic();	//���ر�������
//		GameLoad.MapLoad(4);//���Ա�Ϊ������ÿһ�����¼���  ���ص�ͼ
		//��������
		GameLoad.loadPlay();//Ҳ���Դ���������������2��
		//���ص���NPC��
		GameLoad.loadBubble();//���ص���
		GameLoad.loadDrug();
		
		//ȫ��������ɣ���Ϸ����
	}
	
	/**
	 * @˵��  ��Ϸ����ʱ
	 * @����˵��  ��Ϸ��������Ҫ�������룺1.�Զ�����ҵ��ƶ�����ײ������
	 * 						  2.��Ԫ�ص����ӣ�NPC��������ֵ��ߣ�
	 * 						  3.��ͣ�ȵȡ�����
	 * ��ʵ�����ǵ��ƶ�
	 */
	private long gameTime = 0L;
	private void gameRun() {
		while(true){	//Ԥ����չtrue���Ա�Ϊ���������ڿ��ƹؿ�����
			Map<GameElement, List<ElementObj>> all = em.getGameElements();
			List<ElementObj> watercolumn = em.getElementsByKey(GameElement.WATERCOLUMN);
			List<ElementObj> maps = em.getElementsByKey(GameElement.MAPS);
			List<ElementObj> files = em.getElementsByKey(GameElement.PLAYFILE);
			List<ElementObj> props = em.getElementsByKey(GameElement.PROP);
			List<ElementObj> plays = em.getElementsByKey(GameElement.PLAY);
			
			moveAndUpdate(all,gameTime);	//��ϷԪ���Զ�������
			
			//ע�⣡����watercolumn��Ҫ����ǰ�棬��Ϊ��Ҫ����WaterColumn��pk��д����
			ElementPK(watercolumn,maps);	//ˮ�����ͼ��ײ
			ElementPK(watercolumn,plays);	//ˮ���������ײ
			ElementPK(watercolumn,props);	//ˮ���������ײ
			ElementPK(watercolumn,files);	//ˮ����ը����ײ:������ը����ͬʱ��ը
			ElementPK(plays, props);	//����������ײ
			
			gameTime++;	//Ψһ��ʱ�����
			try {
				sleep(10);	//Ĭ�����Ϊ1��ˢ��100��
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}
	}
	
	private void ElementPK(List<ElementObj> listA, List<ElementObj> listB) {
		//ʹ��ѭ��������һ��һ�ж������Ϊ�棬�������������������״̬
		for(int i=0; i<listA.size(); i++){
			ElementObj A = listA.get(i);
			for(int j=0; j<listB.size(); j++){
				ElementObj B = listB.get(j);
				if(A.pk(B)){
					//��setLive(false)���һ���ܹ��������������Դ�����һ������Ĺ�����
					//���ܹ���������ִ��ʱ�����Ѫ����Ϊ0�ڽ�����������Ϊfalse
					A.pkResult(B);
					B.pkResult(A);
					//break;
				}
			}
		}
	}
	
	//��ϷԪ���Զ�������
	public void moveAndUpdate(Map<GameElement, List<ElementObj>> all, long gameTime){
		//GameElement.values();//���ط���  ����ֵ��һ�����飬�����˳����Ƕ���ö�ٵ�˳��
		for(GameElement ge:GameElement.values()){
			List<ElementObj> list = all.get(ge);
//			��д����ֱ�Ӳ����������ݵĴ��뽨�鲻Ҫʹ�õ���������Ϊ�������������������ӻ�ɾ�����������ᱨ��
//			for(int i=0; i<list.size(); i++){
			for(int i=list.size()-1; i>=0; i--){//��ʽ��������ʹ�ã�
				ElementObj obj = list.get(i);//��ȡΪ����
				if(!obj.isLive()){//�������
//					list.remove(i--);//��ʽһ������ʹ�����ַ�ʽ����һλ
//					����һ�����������������п������������磺������������װ����
					obj.die();//��Ҫ����Լ�����
					list.remove(i);
					continue;
				}
				obj.model(gameTime);
			}
		}
	}
	
	/* ��Ϸ�л��ؿ�*/
	private void gameOver() {
	}
//	
//	public void load(){
//		//ͼƬ����
//		ImageIcon icon = new ImageIcon("image/tank/play1/player1_up.png");
//		ElementObj obj = new Play(100,100,35,35,icon);//ʵ��������
		//��������뵽Ԫ�ع�������
		//em.getElementsByKey(GameElement.PLAY).add(obj);
//		em.addElement(obj, GameElement.PLAY);
		
		//ͼƬ����
		//���һ�������࣬����������д��ע�⣺����Ҫʱ��  ���̼���
		//ʵ�ֵ��˵���ʾ����������ƶ�
//		ImageIcon icon2 = new ImageIcon("image/tank/bot/bot_up.png");
//		ElementObj obj2 = new Enemy(300,300,50,50,icon2);//ʵ��������
//		//��������뵽Ԫ�ع�������
//		//em.getElementsByKey(GameElement.PLAY).add(obj);
//		em.addElement(obj2, GameElement.ENEMY);		
		
		//ʵ���ӵ��ķ��䣬���ӵ��ƶ���Ԫ������
		//ע�⣺����ֻ���ӵ��ķ����Ԫ�����������ǣ����ߵĵ���  �Ƿ���ӵ��ķ��䷽ʽ���
		
		//�����������
//		for(int i=0; i<10; i++){
//			em.addElement(new Enemy().createElement(""), GameElement.ENEMY);
//		}
//	}
	

}
