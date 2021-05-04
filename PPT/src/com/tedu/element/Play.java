package com.tedu.element;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;

import javax.swing.ImageIcon;

import com.tedu.element.props.BubbleProp;
import com.tedu.element.props.PowerDrug;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;

/**
 * 
 * @author ASUS
 * 1.继承父类
 * 2.重写各种方法，实现业务逻辑
 * 3.编写主线程的碰撞
 * 4.如果配置文件格式有改变，请重写GameLoad里面的加载方法
 */
public class Play extends ElementObj{
	//根据方向切换人物动作
	private int imgx = 0;
	private int imgy = 0;
	
	private long imgtime = 0;//用于控制图片变化速度
	
	private int id;	//玩家id
	private int speed = 3;//人物移动速度
	private int maxFileNum = 1;//玩家泡泡数量
	private int putFileNum = 0;//已放置的泡泡数量
	private int power = 1;//玩家泡泡威力
	
	private boolean down = false;
	private boolean up = false;
	private boolean left = false;
	private boolean right = false;
	
	private boolean pkType = false;	//状态：攻击
	private String fx = "down";	//方向

	@Override
	public void showElement(Graphics g) {
//		g.drawImage(this.getIcon().getImage(),
//				this.getX(), this.getY(), 
//				this.getW(), this.getH(), null);
		//图片分割
		g.drawImage(this.getIcon().getImage(), 
				this.getX(), this.getY(), 
				this.getX()+this.getW(), this.getY()+this.getH(), 
				24+(imgx*100), 36+(imgy*100),
				73+(imgx*100), 100+(imgy*100), 
				null);
	}
	
	@Override	//500,500,play,1
	public ElementObj createElement(String str) {
		String[] split = str.split(",");
		this.setX(Integer.parseInt(split[0]));
		this.setY(Integer.parseInt(split[1]));
		ImageIcon icon = GameLoad.imgMap.get(split[2]);
		this.setId(Integer.parseInt(split[3]));
		this.setIcon(icon);
		this.setW(icon.getIconWidth()/8);
		this.setH(icon.getIconHeight()/8);
		System.out.println("初始玩家"+this.id+"可放泡泡数量为："+this.maxFileNum);
		System.out.println("初始玩家"+this.id+"泡泡威力为："+this.power);
		return this;
	}
	
	@Override
	protected void updateImage(long gameTime) {
		if(gameTime-imgtime>10){
			imgtime = gameTime;
			imgx++;
			if(imgx>3){
				imgx=0;
			}
		}
		switch(fx) {
		case "up":imgy=3;break;
		case "down":imgy=0;break;
		case "left":imgy=1;break;
		case "right":imgy=2;break;
		}
	}
	
	@Override
	public void keyClick(boolean bl, int key) {
		switch(id){
		case 1:
			if(bl){	//按下
				switch(key){	//怎么优化
				case 37: 
					this.down=false;this.up=false;this.right=false;
					this.left=true;this.fx = "left";	
					break;
				case 38: 
					this.down=false;this.left=false;this.right=false;
					this.up=true;this.fx = "up";		
					break;
				case 39: 
					this.down=false;this.up=false;this.left=false;
					this.right=true;this.fx = "right";	
					break;
				case 40: 
					this.left=false;this.up=false;this.right=false;
					this.down=true;this.fx = "down";	
					break;
				case 32:
					this.pkType=true;break;//开启攻击状态
				}
			}else{
				switch(key){
				case 37: this.left=false;	break;
				case 38: this.up=false;		break;
				case 39: this.right=false;	break;
				case 40: this.down=false;	break;
				case 32: this.pkType=false;	break;
				}
			}
			break;
		case 2:
			if(bl){	//按下
				switch(key){	//怎么优化
				case 65: 
					this.down=false;this.up=false;this.right=false;
					this.left=true;this.fx = "left";	
					break;
				case 87: 
					this.down=false;this.left=false;this.right=false;
					this.up=true;this.fx = "up";		
					break;
				case 68: 
					this.down=false;this.up=false;this.left=false;
					this.right=true;this.fx = "right";	
					break;
				case 83: 
					this.left=false;this.up=false;this.right=false;
					this.down=true;this.fx = "down";	
					break;
				case 74:
					this.pkType=true;break;//开启攻击状态
				}
			}else{
				switch(key){
				case 65: this.left=false;	break;
				case 87: this.up=false;		break;
				case 68: this.right=false;	break;
				case 83: this.down=false;	break;
				case 74: this.pkType=false;	break;
				}
			}
			break;
		}
	}
	
//	@Override
//	protected void move() {
//		if(this.left && this.getX()>0){
//			this.setX(this.getX()-speed);
//		}
//		if(this.up && this.getY()>0){
//			this.setY(this.getY()-speed);
//		}
//		if(this.right && this.getX()<875-this.getW()){
//			this.setX(this.getX()+speed);
//		}
//		if(this.down && this.getY()<550-this.getH()){
//			this.setY(this.getY()+speed);
//		}
//	}
	
	@Override
	protected void move() {
//		判断上下左右的边界和碰撞情况
		if (this.left && this.getX()>0&&!collisionCheck(this.getX()-speed,this.getY())) {
			this.setX(this.getX() - speed);
		}
		if (this.up  && this.getY()>0&&!collisionCheck(this.getX(),this.getY()-speed)) {
			this.setY(this.getY() - speed);
		}
		if (this.right && this.getX()<875-this.getW() &&!collisionCheck(this.getX()+-speed,this.getY())) {  //坐标的跳转由大家来完成
			this.setX(this.getX() + speed);
		}
		if (this.down && this.getY()<550-this.getH() &&!collisionCheck(this.getX(),this.getY()+speed)) {
			this.setY(this.getY() + speed);
		}
	}
	
	/**
	 * @说明 玩家和地图还有泡泡的碰撞检测，保证玩家不会从地图和泡泡上走过去（在move方法中调用）
	 * @param playGoX 玩家下一步会走的位置的x坐标
	 * @param playGoY 玩家下一步会走的位置的y坐标
	 * @return 如果发生碰撞就返回true
	 */
	private boolean collisionCheck(int playGoX,int playGoY) {
		
		List<ElementObj> mapList=ElementManager.getManager().getElementsByKey(GameElement.MAPS);
		List<ElementObj> boxList=ElementManager.getManager().getElementsByKey(GameElement.BOX);
		List<ElementObj> fileList=ElementManager.getManager().getElementsByKey(GameElement.PLAYFILE);
//		遍历所有的map元素，检测是否有元素与玩家下一步会走到的位置发生碰撞
		for(ElementObj map:mapList) {
			int x=map.getX();
			int y=map.getY();
			int w=map.getW();
			int h=map.getH();
			Rectangle r1=new Rectangle(x,y,w,h);
			Rectangle r2=new Rectangle(playGoX,playGoY,this.getW(),this.getH());
			if(r1.intersects(r2)) {
				return true;
			}
		}
		
		for(ElementObj box:boxList) {
			int x=box.getX();
			int y=box.getY();
			int w=box.getW();
			int h=box.getH();
			Rectangle r1=new Rectangle(x,y,w,h);
			Rectangle r2=new Rectangle(playGoX,playGoY,this.getW(),this.getH());
			if(r1.intersects(r2)) {
				return true;
			}
		}
		
//		遍历所有的泡泡，检测是否有元素与玩家下一步会走到的位置发生碰撞
		for(ElementObj file:fileList) {
			int x=file.getX();
			int y=file.getY();
			int w=file.getW();
			int h=file.getH();
			Rectangle r1=new Rectangle(x,y,w,h);
			Rectangle r2=new Rectangle(playGoX,playGoY,this.getW(),this.getH());
			Rectangle r3=new Rectangle(this.getX(),this.getY(),this.getW(),this.getH());
			if(r1.intersects(r2)&&!r1.intersects(r3)) {  //泡泡和玩家本身也要做碰撞检测，不然放了泡泡之后就会被泡泡困住
				return true;
			}
		}
		return false;
	}
	
	@Override
	protected void add(long gameTime) {
		if(!this.pkType||this.putFileNum>=this.maxFileNum) {//如果不在攻击状态或者已放泡泡数量达到最大放泡泡数量，就直接返回
			return;
		}
		this.pkType = false;//按一次发送一颗子弹，拼手速（也可以增加变量来控制）
		ElementObj obj = GameLoad.getObj("file");
		ElementObj element = obj.createElement(this.toString());//以后的框架学习中会碰到
//		//装入到集合中
		ElementManager.getManager().addElement(element, GameElement.PLAYFILE);
//		//如果要控制子弹速度等。。。还需要代码编写
		this.putFileNum++;	//放置泡泡后，已放置泡泡数量+1
	}
	
	//根据碰撞物体对象类型  对玩家做出相应变化
	@Override
	public void pkResult(ElementObj enemy) {
		if(enemy instanceof BubbleProp){
			this.maxFileNum++;
			GameLoad.musicMap.get("getMusic").play();
			System.out.println("玩家"+this.id+"可放泡泡数量为："+this.maxFileNum);	//测试数据
		}else if(enemy instanceof PowerDrug){
			this.power++;
			GameLoad.musicMap.get("getMusic").play();
			System.out.println("玩家"+this.id+"泡泡威力为："+this.power);	//测试数据
		}else if(enemy instanceof WaterColumn){
			this.setLive(false);
			die();
		}else{}
	}
	
	public void die()
	{
		ElementObj obj = GameLoad.getObj("die");
		String str = this.getX()+","+this.getY()+",die";
		ElementObj element = obj.createElement(str);
		ElementManager.getManager().addElement(element, GameElement.DIE);
	}
	
	@Override	//x:3,y:3,f:file
	public String toString() {	//这里是偷懒，直接使用toString：建议自己定义一个方法
		//{x:3,y:5,f:up} json格式
		int x = this.getX()+this.getW()/2;
		int y = this.getY()+this.getH();
//		switch(this.fx){	//子弹在发射的时候就已经给予固定的轨迹，可以加上目标，修改json格式
//		case "up": x+=12;break;	//一半不会写20等数值，图片大小就是显示大小，一半情况下可以使用图片大小参与运算。
//		case "left": y+=13;break;
//		case "right": x+=23;y+=12;break;
//		case "down": x+=11;y+=23;break;
//		}
		return x+","+y+","+this.power+","+this.id+","+"file";
	}
	
	public int getMaxFileNum() {
		return maxFileNum;
	}

	public void setMaxFileNum(int maxFileNum) {
		this.maxFileNum = maxFileNum;
	}

	public int getPutFileNum() {
		return putFileNum;
	}

	public void setPutFileNum(int putFileNum) {
		this.putFileNum = putFileNum;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	//玩家id 如play1、play2
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
