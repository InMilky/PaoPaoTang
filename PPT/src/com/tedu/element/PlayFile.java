package com.tedu.element;

import java.awt.Graphics;
import java.util.List;

import javax.swing.ImageIcon;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;

public class PlayFile extends ElementObj {
	private int imgx=0;  //控制图片滑动  
	private long imgtime=0;//控制图片滑动速度
	private int livetime=0;   //生存时间
	private final static int LIMITTIME=150;//受限的生存时间，超过这个时间泡泡就会消失变成水柱
	private int power;//爆炸威力（默认为1，应该由玩家传过来，因为剪刀道具后，玩家的状态爆炸威力应该有改变）
	private int playId;

	@Override
	public void showElement(Graphics g) {
		g.drawImage(this.getIcon().getImage(), 
				this.getX(), this.getY(), //目标矩形第一个角的坐标
				this.getX()+32, this.getY()+32,//目标矩形第二个角的坐标 
				0+(imgx*42), 6, 
				42+(imgx*42), 47,
				null);
	}

	public ElementObj createElement(String str) {//自行定义字符串的规则
		System.out.println(str);
		String[] split = str.split(",");
		this.setX(Integer.parseInt(split[0])-16);
		this.setY(Integer.parseInt(split[1])-32);
		this.setPower(Integer.parseInt(split[2]));
		this.setPlayId(Integer.parseInt(split[3]));
		ImageIcon icon=GameLoad.imgMap.get(split[4]);
		this.setIcon(icon);
		this.setW(icon.getIconWidth());
		this.setH(icon.getIconHeight());
		return this;
	}
	
	@Override
	protected void updateImage(long time) {
		this.livetime++;       //生存时间计时
		if(livetime==LIMITTIME) this.setLive(false);
		
		if(time-imgtime>10) {   //
			imgtime=time;
			imgx++;
			if(imgx>3) {
				imgx=0;
			}
		}
	}
	
	@Override
	public void die() {  //泡泡消失后出现水柱
		ElementObj obj=GameLoad.getObj("water");
		ElementObj element = obj.createElement(this.toString());//会帮助返回对象的实体并初始化数据
		ElementManager.getManager().addElement(element, GameElement.WATERCOLUMN);
		
//		找到放置泡泡的对象，让他的已放泡泡数-1
		List<ElementObj> playList=ElementManager.getManager().getElementsByKey(GameElement.PLAY);
		for(int i=0;i<playList.size();i++) {
			Play player=(Play)playList.get(i);
			if(player.getId()==this.playId) {
				player.setPutFileNum(player.getPutFileNum()-1);
			}
		}
	}
	
	@Override
	public String toString() {
		int leftPower=getRealPower(this.getX()-32,this.getY(),"left");
		int rightPower=getRealPower(this.getX()-32,this.getY(),"right");
		int upPower=getRealPower(this.getX(),this.getY()-32,"up");
		int downPower=getRealPower(this.getX(),this.getY()+32,"down");
		
		return this.getX()+","+this.getY()+",water,"+upPower+","+downPower+","+leftPower+","+rightPower;
	}
	
	/**
	 * 获得四个方向水柱的长度
	 * @param waterGoX
	 * @param waterGoY
	 * @param direction
	 * @return
	 */
	private int getRealPower(int waterGoX,int waterGoY,String direction) {
		int realPower=0;
		List<ElementObj> maps=ElementManager.getManager().getElementsByKey(GameElement.MAPS);
		
		for(int i=0;i<power;i++) {
			for(int j=0;j<maps.size();j++) {
				int objX=maps.get(j).getX();
				int objY=maps.get(j).getY();
				
//				if(objX==waterGoX&&objY==waterGoY) {//判断是否为与泡泡相邻的元素
//					if(maps.get(j) instanceof Box) {   //判断地图里面的那些元素是不是可以炸掉的，如果可以炸掉则威力+1，代表着水柱长度+1
//						return realPower+1;						
//					}
//					else {
//						return realPower;//如果是不可以炸掉的元素，就返回0，说明水柱没有长度
//					}
//				}
			}
			realPower++;//如果相邻无元素，则长度可以+1
			switch(direction) {
			case "up": waterGoY-=32;break;
			case "down":waterGoY+=32;break;
			case "left":waterGoX-=32;break;
			case "right":waterGoX+=32;break;
			}
		}
		return realPower;
		
	}
	
	@Override
	public void pkResult(ElementObj enemy) {
		if(enemy instanceof WaterColumn){
			this.livetime=149;
		}
	}

	public void setPower(int power) {
		this.power = power;
	}

	public void setPlayId(int playId) {
		this.playId = playId;
	}
	
	
	
}
