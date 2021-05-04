package com.tedu.manager;

import java.applet.Applet;

import java.applet.AudioClip;
import java.io.File;
import java.io.IOException;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.swing.ImageIcon;

import com.sun.org.apache.bcel.internal.generic.LADD;
import com.tedu.element.ElementObj;

/**
 * @˵��  ������(���ߣ����ڶ�ȡ�����ļ��Ĺ�����)
 * @author ASUS
 *
 */
public class GameLoad {
	//�õ���Դ������
	private static ElementManager em = ElementManager.getManager();
	
	//ͼƬ����  ʹ��map�����д洢  ö����������ƶ�����չ��
	public static Map<String,ImageIcon> imgMap = new HashMap<>();
	public static Map<String,AudioClip> musicMap = new HashMap<>();
	
	
	//��ȡ�ļ�����
	private static Properties pro = new Properties();
	
	/**
	 * @˵��  �����ͼid �ɼ��ط��������ļ����������Զ����ɵ�ͼ�ļ����ƣ������ļ�
	 * @param mapID �ļ����  �ļ�id
	 */
	public static void MapLoad(int mapId) {
		//�õ����Ǵ洢���ļ�·��
		String mapName = "com/tedu/text/"+mapId+".map";
		//ʹ��io������ȡ�ļ�����,�õ��������
		ClassLoader classLoader = GameLoad.class.getClassLoader();
		InputStream maps = classLoader.getResourceAsStream(mapName);
		if(maps == null){
			System.out.println("�����ļ���ȡ�쳣");
			return;
		}
		
		try {
			//�Ժ��õĶ���xml��json�����ļ�
			pro.clear();
			pro.load(maps);
			//����ֱ�Ӷ�̬�Ļ�ȡ�����е�key����key�Ϳ��Ի�ȡvalue
			//javaѧϰ����õ���ʦ��java��API�ĵ�
			Enumeration<?> names = pro.propertyNames();
			//�����ĵ�������һ�����⣺һ�ε���һ��Ԫ��
			while(names.hasMoreElements()){//��ȡ�������
				String key = names.nextElement().toString();
				//���Ի�ȡ������  �Ϳ����Զ������������ǵĵ�ͼ
				String [] arrs = pro.getProperty(key).split(";");
				for(int i=0; i<arrs.length; i++){
					ElementObj obj = getObj("map");
					ElementObj element = obj.createElement(key+","+arrs[i]);
					em.addElement(element, GameElement.MAPS);
				}
			}

		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	/**
	 * @˵��  ����ͼƬ����
	 * ����ͼƬ  �����ͼƬ֮���һ��·������
	 */
	public static void loadImg(){	//���Դ���������Ϊ��ͬ�Ĺ���Ҫ��һ������Դ
		String texturl = "com/tedu/text/GameData.pro";
		ClassLoader classLoader = GameLoad.class.getClassLoader();
		InputStream texts = classLoader.getResourceAsStream(texturl);
		//imgMap���ڴ������
		pro.clear();
		try {
			pro.load(texts);
			Set<Object> set = pro.keySet();
			for(Object o:set){
				String url = pro.getProperty(o.toString());
				imgMap.put(o.toString(), new ImageIcon(url));
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	/**
	 * @˵��  �������ִ���
	 * ����ͼƬ  ���������֮���һ��·������
	 */
	public static void loadMusic(){	//���Դ���������Ϊ��ͬ�Ĺ���Ҫ��һ������Դ
		String texturl = "com/tedu/text/music.pro";
		ClassLoader classLoader = GameLoad.class.getClassLoader();
		InputStream texts = classLoader.getResourceAsStream(texturl);
		//imgMap���ڴ������
		pro.clear();
		try {
			pro.load(texts);
			Set<Object> set = pro.keySet();
			for(Object o:set){
				URI uri = new File(pro.getProperty(o.toString())).toURI();
				URL url = uri.toURL();
				musicMap.put(o.toString(), Applet.newAudioClip(url));
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	/**
	 * �������
	 */
	public static void loadPlay(){
		loadObj();
		String play1Str = "200,200,play1,1";	//û�зŵ������ļ���
		String play2Str = "200,400,play2,2";
		ElementObj obj1 = getObj("play");
		ElementObj obj2 = getObj("play");
		ElementObj play1 = obj1.createElement(play1Str);
		ElementObj play2 = obj2.createElement(play2Str);
//		ElementObj play = new Play().createElement(playStr);
		//������ʹ��������֮�����϶ȣ�����ֱ��ͨ���ӿڻ��߳�����Ϳ��Ի�ȡ��ʵ�����
		//ͨ�������ļ�����ϣ����ʹ������϶�
		em.addElement(play1, GameElement.PLAY);
		em.addElement(play2, GameElement.PLAY);
	}
	
	/**
	 * @˵��  ����ʹ�ô���
	 * ���ص���  ��Ӧ��д���⣬��Ϊ��������ǽ��ը��֮����ֵģ�֮����Ҫ�ģ�����Ϊ�˲��Է���
	 */
	public static void loadBubble(){
		loadObj();
		String propStr = "500,200,bubbleprop";	//û�зŵ������ļ���
		ElementObj obj = getObj("bubbleprop");
		ElementObj prop = obj.createElement(propStr);
		em.addElement(prop, GameElement.PROP);
	}
	
	public static void loadDrug(){
		loadObj();
		String drugStr = "500,400,powerdrug";	//û�зŵ������ļ���
		ElementObj obj = getObj("powerdrug");
		ElementObj prop = obj.createElement(drugStr);
		em.addElement(prop, GameElement.PROP);
	}
	
	public static ElementObj getObj(String str){
		try {
			Class<?> class1 = objMap.get(str);
			Object newInstance = class1.newInstance();
			if(newInstance instanceof ElementObj) {
				return (ElementObj)newInstance;   //�������ͺ� new Play()�ȼ�
//				�½�����һ����  GamePlay����
			}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * ��չ��ʹ�������ļ�����ʵ��������ͨ���̶���key���ַ�����ʵ������
	 */
	private static Map<String,Class<?>> objMap = new HashMap<>();
	public static void loadObj(){
		String texturl = "com/tedu/text/obj.pro";
		ClassLoader classLoader = GameLoad.class.getClassLoader();
		InputStream texts = classLoader.getResourceAsStream(texturl);
		pro.clear();
		try {
			pro.load(texts);
			Set<Object> set = pro.keySet();
			for(Object o:set){
				String classUrl = pro.getProperty(o.toString());
				//ʹ�÷���ķ�ʽֱ�ӽ��������ȡ
				Class<?> forName = Class.forName(classUrl);
				objMap.put(o.toString(), forName);
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}
	}
	
	public static void loadBgMusic(){
		loadMusic();	//���ü������ַ����������ּ��ؽ�musicMap��
		musicMap.get("bgMusic").loop();
	}
	
	//���ڲ���
//	public static void main(String[] args){
//		try {
//			//�������
//			//ͨ����·������  com.tedu.play
//			Class<?> forName = Class.forName("");
//			
//			//ͨ������ ����ֱ�ӷ��ʵ������
//			Class<?> forName1 = GameLoad.class;
//			
//			//ͨ��ʵ����󣬻�ȡ�������
//			GameLoad gameLoad = new GameLoad();
//			Class<? extends GameLoad> class1 = gameLoad.getClass();
//			
//		} catch (ClassNotFoundException e) {
//			
//			e.printStackTrace();
//		}
//	}
	
	
	
}
