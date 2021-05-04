package com.tedu.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tedu.element.ElementObj;

/**
 * @˵�� ������Ԫ�ع�������ר�Ŵ洢���е�Ԫ�أ�ͬʱ���ṩ����
 *     ������ͼ�Ϳ��ƻ�ȡ����
 * @����һ���洢���е�Ԫ�����ݣ���ô��ţ� list map set 3�󼯺�
 * @�����������������ͼ�Ϳ���Ҫ���ʣ��������ͱ���ֻ��һ��������ģʽ
 * @author ASUS
 *
 */
public class ElementManager {

	/**
	 * String ��Ϊkeyƥ�����е�Ԫ��play ->List<Object> listPlay
	 *                      enemy->List<Object> listEnemy
	 * ö�����ͣ�����map��key�������ֲ�һ������Դ�����ڻ�ȡ����
	 * List��Ԫ�صķ���Ӧ����Ԫ�ػ���
	 * ���е�Ԫ�ض����Դ����map�����У���ʾģ��ֻ��Ҫ��ȡ�����map�Ϳ���
	 * ��ʾ���еĽ�����Ҫ��ʾ��Ԫ��(����Ԫ�ػ����showElement())
	 */
	private Map<GameElement,List<ElementObj>> gameElements;
	
	public Map<GameElement, List<ElementObj>> getGameElements() {
		return gameElements;
	}
	//���Ԫ��(����ɼ���������)
	public void addElement(ElementObj obj, GameElement ge){
	   	//List<ElementObj> list = gameElements.get(ge);
		//list.add(obj);
		gameElements.get(ge).add(obj);//��Ӷ��󵽼����У�����keyֵ���д洢
	}
	//����key����list���ϣ�ȡ��ĳһԪ��
	public List<ElementObj> getElementsByKey(GameElement ge){
		return gameElements.get(ge);
	}
	
	/**
	 * ����ģʽ���ڴ�������ֻ��һ��ʵ����
	 * ����ģʽ-���������Զ�����ʵ��
	 * ����ģʽ-����Ҫʹ�õ�ʱ��ż���ʵ��
	 * 
	 * ��д��ʽ��
	 * 1.��Ҫһ����̬������(����һ������) ����������
	 * 2.�ṩһ����̬�ķ���(�������ʵ��) return����������
	 * 3.һ��Ϊ��ֹ�������Լ�ʹ��(���ǿ���ʵ����)
	 *   ElementManager em=new ElementManager();
	 */
	private static ElementManager EM=null;//����
	
	//synchronized�߳���->��֤������ִ����ֻ��һ���߳�
	//����ģʽ
	public static synchronized ElementManager getManager(){
		if(EM==null){//�����ж�
			EM=new ElementManager();
		}
		return EM;
	}
	
	//����ʵ��������  ��̬���������౻���ص�ʱ��ֱ��ִ��
	/*static{
		EM=new ElementManager();//ֻ��ִ��һ��
	}*/
	
	private ElementManager(){//˽�л����췽��
		init();//�����У�����Ϊר�ŵ�ʵ��������
	}
	
	/**
	 * ��������Ϊ�������ܳ��ֵĹ�����չ����дinit����׼����
	 * ��Ϊ���췽���޷�����д
	 */
	public void init(){
		//hashMap hashɢ��
		gameElements=new HashMap<GameElement,List<ElementObj>>();
		//ÿ��Ԫ�ؼ��϶�����map��
//		gameElements.put(GameElement.PLAY, new ArrayList<ElementObj>());
//		gameElements.put(GameElement.MAPS, new ArrayList<ElementObj>());
//		gameElements.put(GameElement.ENEMY, new ArrayList<ElementObj>());
//		gameElements.put(GameElement.BOSS, new ArrayList<ElementObj>());
		for(GameElement ge:GameElement.values()){
			gameElements.put(ge, new ArrayList<ElementObj>());
		}
		//
	}
}
