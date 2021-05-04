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
 * @说明  加载器(工具：用于读取配置文件的工具类)
 * @author ASUS
 *
 */
public class GameLoad {
	//得到资源管理器
	private static ElementManager em = ElementManager.getManager();
	
	//图片集合  使用map来进行存储  枚举类型配合移动（扩展）
	public static Map<String,ImageIcon> imgMap = new HashMap<>();
	public static Map<String,AudioClip> musicMap = new HashMap<>();
	
	
	//读取文件的类
	private static Properties pro = new Properties();
	
	/**
	 * @说明  传入地图id 由加载方法依据文件命名规则自动生成地图文件名称，加载文件
	 * @param mapID 文件编号  文件id
	 */
	public static void MapLoad(int mapId) {
		//得到我们存储的文件路径
		String mapName = "com/tedu/text/"+mapId+".map";
		//使用io流来获取文件对象,得到类加载器
		ClassLoader classLoader = GameLoad.class.getClassLoader();
		InputStream maps = classLoader.getResourceAsStream(mapName);
		if(maps == null){
			System.out.println("配置文件读取异常");
			return;
		}
		
		try {
			//以后用的都是xml和json配置文件
			pro.clear();
			pro.load(maps);
			//可以直接动态的获取到所有的key，有key就可以获取value
			//java学习中最好的老师是java的API文档
			Enumeration<?> names = pro.propertyNames();
			//这样的迭代都有一个问题：一次迭代一个元素
			while(names.hasMoreElements()){//获取是无序的
				String key = names.nextElement().toString();
				//可以获取到数据  就可以自动加载生成我们的地图
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
	 * @说明  加载图片代码
	 * 加载图片  代码和图片之间差一个路径问题
	 */
	public static void loadImg(){	//可以带参数，因为不同的关需要不一样的资源
		String texturl = "com/tedu/text/GameData.pro";
		ClassLoader classLoader = GameLoad.class.getClassLoader();
		InputStream texts = classLoader.getResourceAsStream(texturl);
		//imgMap用于存放数据
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
	 * @说明  加载音乐代码
	 * 加载图片  代码和音乐之间差一个路径问题
	 */
	public static void loadMusic(){	//可以带参数，因为不同的关需要不一样的资源
		String texturl = "com/tedu/text/music.pro";
		ClassLoader classLoader = GameLoad.class.getClassLoader();
		InputStream texts = classLoader.getResourceAsStream(texturl);
		//imgMap用于存放数据
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
	 * 加载玩家
	 */
	public static void loadPlay(){
		loadObj();
		String play1Str = "200,200,play1,1";	//没有放到配置文件中
		String play2Str = "200,400,play2,2";
		ElementObj obj1 = getObj("play");
		ElementObj obj2 = getObj("play");
		ElementObj play1 = obj1.createElement(play1Str);
		ElementObj play2 = obj2.createElement(play2Str);
//		ElementObj play = new Play().createElement(playStr);
		//解耦，降低代码与代码之间的耦合度，可以直接通过接口或者抽象父类就可以获取到实体对象
		//通过配置文件的耦合，降低代码的耦合度
		em.addElement(play1, GameElement.PLAY);
		em.addElement(play2, GameElement.PLAY);
	}
	
	/**
	 * @说明  测试使用代码
	 * 加载道具  不应该写在这，因为道具是在墙被炸了之后出现的，之后需要改，现在为了测试方便
	 */
	public static void loadBubble(){
		loadObj();
		String propStr = "500,200,bubbleprop";	//没有放到配置文件中
		ElementObj obj = getObj("bubbleprop");
		ElementObj prop = obj.createElement(propStr);
		em.addElement(prop, GameElement.PROP);
	}
	
	public static void loadDrug(){
		loadObj();
		String drugStr = "500,400,powerdrug";	//没有放到配置文件中
		ElementObj obj = getObj("powerdrug");
		ElementObj prop = obj.createElement(drugStr);
		em.addElement(prop, GameElement.PROP);
	}
	
	public static ElementObj getObj(String str){
		try {
			Class<?> class1 = objMap.get(str);
			Object newInstance = class1.newInstance();
			if(newInstance instanceof ElementObj) {
				return (ElementObj)newInstance;   //这个对象就和 new Play()等价
//				新建立啦一个叫  GamePlay的类
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
	 * 扩展：使用配置文件，来实例化对象，通过固定的key（字符串来实例化）
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
				//使用反射的方式直接将类进行提取
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
		loadMusic();	//调用加载音乐方法，把音乐加载进musicMap中
		musicMap.get("bgMusic").loop();
	}
	
	//用于测试
//	public static void main(String[] args){
//		try {
//			//反射理解
//			//通过类路径名称  com.tedu.play
//			Class<?> forName = Class.forName("");
//			
//			//通过类名 可以直接访问到这个类
//			Class<?> forName1 = GameLoad.class;
//			
//			//通过实体对象，获取反射对象
//			GameLoad gameLoad = new GameLoad();
//			Class<? extends GameLoad> class1 = gameLoad.getClass();
//			
//		} catch (ClassNotFoundException e) {
//			
//			e.printStackTrace();
//		}
//	}
	
	
	
}
