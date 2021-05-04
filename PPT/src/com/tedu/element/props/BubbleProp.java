package com.tedu.element.props;

import java.awt.Graphics;


import javax.swing.ImageIcon;

import com.tedu.element.ElementObj;
import com.tedu.element.Play;
import com.tedu.element.WaterColumn;
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
public class BubbleProp extends ElementObj{
	private int imagx = 0;
	private int imagy = 0;
	private long imgtime = 0;//用于控制图片变化速度
	private boolean pkType = false;

	@Override
	public void showElement(Graphics g) {
//		g.drawImage(this.getIcon().getImage(),
//				this.getX(), this.getY(), 
//				this.getW(), this.getH(), null);
		//图片分割
		g.drawImage(this.getIcon().getImage(), 
				this.getX(), this.getY(), 
				this.getX()+this.getW(), this.getY()+this.getH(), 
				0+(imagx*32), 0,
				32+(imagx*32), 48, 
				null);
	}
	
	@Override	//500,200,bubbleprop
	public ElementObj createElement(String str) {
		String[] split = str.split(",");
		this.setX(Integer.parseInt(split[0]));
		this.setY(Integer.parseInt(split[1]));
		ImageIcon icon = GameLoad.imgMap.get(split[2]);
		this.setIcon(icon);
		this.setW(icon.getIconWidth()/4);
		this.setH(icon.getIconHeight()/4);
		return this;
	}
	
	@Override
	protected void updateImage(long gameTime) {
		if(gameTime-imgtime>10){
			imgtime = gameTime;
			imagx++;
			if(imagx>3){
				imagx=0;
			}
		}
		super.updateImage(gameTime);
	}
	
	@Override
	public void keyClick(boolean bl, int key) {
		
	}
	
	@Override
	public void pkResult(ElementObj enemy) {
		if(enemy instanceof Play){
			this.setLive(false);
		}else if(enemy instanceof WaterColumn){
			this.setLive(false);
		}
	}

}
