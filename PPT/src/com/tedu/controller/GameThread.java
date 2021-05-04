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
	public void run() {	//游戏的run方法  主线程
		while(true){	//扩展，可以将true变为一个变量用于控制结束
			//游戏开始时	读进度条，加载游戏资源（场景资源）
			gameLoad();
			//游戏进行时	游戏过程中
			gameRun();
			//游戏场景结束	游戏资源回收（场景资源）
			gameOver();
			
			try {
				sleep(50);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}
	}
	
	/*游戏加载*/
	private void gameLoad() {
		GameLoad.loadImg();	//加载图片

		GameLoad.loadBgMusic();	//加载背景音乐
//		GameLoad.MapLoad(4);//可以变为变量，每一关重新加载  加载地图
		//加载主角
		GameLoad.loadPlay();//也可以带参数，单机还是2人
		//加载敌人NPC等
		GameLoad.loadBubble();//加载道具
		GameLoad.loadDrug();
		
		//全部加载完成，游戏启动
	}
	
	/**
	 * @说明  游戏进行时
	 * @任务说明  游戏过程中需要做的事请：1.自动化玩家的移动，碰撞，死亡
	 * 						  2.新元素的增加（NPC死亡后出现道具）
	 * 						  3.暂停等等。。。
	 * 先实现主角的移动
	 */
	private long gameTime = 0L;
	private void gameRun() {
		while(true){	//预留扩展true可以变为变量，用于控制关卡结束
			Map<GameElement, List<ElementObj>> all = em.getGameElements();
			List<ElementObj> watercolumn = em.getElementsByKey(GameElement.WATERCOLUMN);
			List<ElementObj> maps = em.getElementsByKey(GameElement.MAPS);
			List<ElementObj> files = em.getElementsByKey(GameElement.PLAYFILE);
			List<ElementObj> props = em.getElementsByKey(GameElement.PROP);
			List<ElementObj> plays = em.getElementsByKey(GameElement.PLAY);
			
			moveAndUpdate(all,gameTime);	//游戏元素自动化方法
			
			//注意！！！watercolumn需要放在前面，因为需要调用WaterColumn的pk重写方法
			ElementPK(watercolumn,maps);	//水柱与地图碰撞
			ElementPK(watercolumn,plays);	//水柱与玩家碰撞
			ElementPK(watercolumn,props);	//水柱与道具碰撞
			ElementPK(watercolumn,files);	//水柱与炸弹碰撞:相连的炸弹会同时爆炸
			ElementPK(plays, props);	//玩家与道具碰撞
			
			gameTime++;	//唯一的时间控制
			try {
				sleep(10);	//默认理解为1秒刷新100次
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}
	}
	
	private void ElementPK(List<ElementObj> listA, List<ElementObj> listB) {
		//使用循环，进行一对一判定，如果为真，就设置两个对象的死亡状态
		for(int i=0; i<listA.size(); i++){
			ElementObj A = listA.get(i);
			for(int j=0; j<listB.size(); j++){
				ElementObj B = listB.get(j);
				if(A.pk(B)){
					//将setLive(false)变成一个受攻击方法，还可以传入另一个对象的攻击力
					//当受攻击方法里执行时，如果血量减为0在进行设置生存为false
					A.pkResult(B);
					B.pkResult(A);
					//break;
				}
			}
		}
	}
	
	//游戏元素自动化方法
	public void moveAndUpdate(Map<GameElement, List<ElementObj>> all, long gameTime){
		//GameElement.values();//隐藏方法  返回值是一个数组，数组的顺序就是定义枚举的顺序
		for(GameElement ge:GameElement.values()){
			List<ElementObj> list = all.get(ge);
//			编写这样直接操作集合数据的代码建议不要使用迭代器，因为当迭代器中数据有增加或删除，编译器会报错
//			for(int i=0; i<list.size(); i++){
			for(int i=list.size()-1; i>=0; i--){//方式二（建议使用）
				ElementObj obj = list.get(i);//读取为基类
				if(!obj.isLive()){//如果死亡
//					list.remove(i--);//方式一：可以使用这种方式回退一位
//					启动一个死亡方法（方法中可以做事情例如：死亡动画、掉装备）
					obj.die();//需要大家自己补充
					list.remove(i);
					continue;
				}
				obj.model(gameTime);
			}
		}
	}
	
	/* 游戏切换关卡*/
	private void gameOver() {
	}
//	
//	public void load(){
//		//图片导入
//		ImageIcon icon = new ImageIcon("image/tank/play1/player1_up.png");
//		ElementObj obj = new Play(100,100,35,35,icon);//实例化对象
		//将对象放入到元素管理器中
		//em.getElementsByKey(GameElement.PLAY).add(obj);
//		em.addElement(obj, GameElement.PLAY);
		
		//图片导入
		//添加一个敌人类，仿照玩家类编写，注意：不需要时间  键盘监听
		//实现敌人的显示，最基本的移动
//		ImageIcon icon2 = new ImageIcon("image/tank/bot/bot_up.png");
//		ElementObj obj2 = new Enemy(300,300,50,50,icon2);//实例化对象
//		//将对象放入到元素管理器中
//		//em.getElementsByKey(GameElement.PLAY).add(obj);
//		em.addElement(obj2, GameElement.ENEMY);		
		
		//实现子弹的发射，和子弹移动，元素死亡
		//注意：内容只有子弹的发射和元素死亡，考虑：道具的掉落  是否和子弹的发射方式相近
		
		//创建放入敌人
//		for(int i=0; i<10; i++){
//			em.addElement(new Enemy().createElement(""), GameElement.ENEMY);
//		}
//	}
	

}
