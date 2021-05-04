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
 * 1.�̳и���
 * 2.��д���ַ�����ʵ��ҵ���߼�
 * 3.��д���̵߳���ײ
 * 4.��������ļ���ʽ�иı䣬����дGameLoad����ļ��ط���
 */
public class BubbleProp extends ElementObj{
	private int imagx = 0;
	private int imagy = 0;
	private long imgtime = 0;//���ڿ���ͼƬ�仯�ٶ�
	private boolean pkType = false;

	@Override
	public void showElement(Graphics g) {
//		g.drawImage(this.getIcon().getImage(),
//				this.getX(), this.getY(), 
//				this.getW(), this.getH(), null);
		//ͼƬ�ָ�
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
