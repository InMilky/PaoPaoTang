package com.tedu.element;

import java.awt.Graphics;

import javax.swing.ImageIcon;

import com.tedu.manager.GameLoad;

public class Dying extends ElementObj{
	private int imgx=0;
	//由方向控制
	private long imgtime=0;//用于控制图片变化速度
	private int t=0;
	
	@Override
	public void showElement(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(this.getIcon().getImage(), 
				this.getX(), this.getY(), 
				this.getX()+this.getW(), this.getY()+this.getH(), 
				19+(imgx*100), 22, 
				79+(imgx*100), 100, 
				null);	
	}
	
	@Override
	protected void updateImage(long time) {
		t++;
		if(t<80) {
			if(time-imgtime>10){
				imgtime = time;
				imgx++;
				if(imgx>3)
				{
					imgx=0;
				}
			}
		}
		else {
			this.setLive(false);
		}
	}
	
	@Override
	public ElementObj createElement(String str) {
		String []split = str.split(",");
		this.setX(Integer.parseInt(split[0]));
		this.setY(Integer.parseInt(split[1]));
		ImageIcon icon = GameLoad.imgMap.get(split[2]);
		this.setIcon(icon);
		this.setW(icon.getIconWidth()/8);
		this.setH(icon.getIconHeight()/8);
		return this;
	}
	
	

}
