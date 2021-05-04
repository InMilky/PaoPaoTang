package com.tedu.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tedu.element.ElementObj;

/**
 * @说明 本类是元素管理器，专门存储所有的元素，同时，提供方法
 *     给予视图和控制获取数据
 * @问题一：存储所有的元素数据，怎么存放？ list map set 3大集合
 * @问题二：管理器是视图和控制要访问，管理器就必须只有一个，单例模式
 * @author ASUS
 *
 */
public class ElementManager {

	/**
	 * String 作为key匹配所有的元素play ->List<Object> listPlay
	 *                      enemy->List<Object> listEnemy
	 * 枚举类型，当作map的key用来区分不一样的资源，用于获取数据
	 * List中元素的泛型应该是元素基类
	 * 所有的元素都可以存放在map集合中，显示模块只需要获取到这个map就可以
	 * 显示是有的界面需要显示的元素(调用元素基类的showElement())
	 */
	private Map<GameElement,List<ElementObj>> gameElements;
	
	public Map<GameElement, List<ElementObj>> getGameElements() {
		return gameElements;
	}
	//添加元素(多半由加载器调用)
	public void addElement(ElementObj obj, GameElement ge){
	   	//List<ElementObj> list = gameElements.get(ge);
		//list.add(obj);
		gameElements.get(ge).add(obj);//添加对象到集合中，按照key值进行存储
	}
	//依据key返回list集合，取出某一元素
	public List<ElementObj> getElementsByKey(GameElement ge){
		return gameElements.get(ge);
	}
	
	/**
	 * 单例模式：内存中有且只有一个实例。
	 * 饿汉模式-是启动就自动加载实例
	 * 饱汉模式-是需要使用的时候才加载实例
	 * 
	 * 编写方式：
	 * 1.需要一个静态的属性(定义一个常量) 单例的引用
	 * 2.提供一个静态的方法(返回这个实例) return单例的引用
	 * 3.一般为防止其他人自己使用(类是可以实例化)
	 *   ElementManager em=new ElementManager();
	 */
	private static ElementManager EM=null;//引用
	
	//synchronized线程锁->保证本方法执行中只有一个线程
	//饱汉模式
	public static synchronized ElementManager getManager(){
		if(EM==null){//控制判定
			EM=new ElementManager();
		}
		return EM;
	}
	
	//饿汉实例化对象  静态语句块是在类被加载的时候直接执行
	/*static{
		EM=new ElementManager();//只会执行一次
	}*/
	
	private ElementManager(){//私有化构造方法
		init();//开发中，被认为专门的实例化方法
	}
	
	/**
	 * 本方法是为将来可能出现的功能扩展，重写init方法准备的
	 * 因为构造方法无法被重写
	 */
	public void init(){
		//hashMap hash散列
		gameElements=new HashMap<GameElement,List<ElementObj>>();
		//每种元素集合都放在map中
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
