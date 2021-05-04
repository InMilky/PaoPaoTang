package com.tedu.element.props;

import java.awt.Graphics;

import javax.swing.ImageIcon;

import com.tedu.element.ElementObj;
import com.tedu.element.Play;
import com.tedu.element.WaterColumn;
import com.tedu.manager.GameLoad;

/**
 * �������ݱ�ը������ҩˮ������
 * @author kt0302
 * @˵�� Ҫʵ�ֵĹ���:
 * 		1.updateImg() ҩˮ���ֺ��ԭ������
 * 		2.die() ��������������ҩˮ�������ײ��,������ݱ�ը����+1,ҩˮ��ʧ
 */
public class PowerDrug extends ElementObj{

	private int imgx = 0;
	private long imagetime = 0;  //���ڿ���ͼƬ�仯�ٶ�
	@Override
	public void showElement(Graphics g) {
//		��ͼƬ�ķָ�
		g.drawImage(this.getIcon().getImage(), 
				 this.getX(),this.getY(), 
				 this.getX()+this.getW(), 
				 this.getY()+this.getH(), 
				 (imgx*32), 0, 
				 32+(imgx*32),50,
				 null);		
	}
//	ҩˮͼƬת�� Ʈ��
	@Override
	protected void updateImage(long time){
		
		if(time - imagetime > 30){
			imagetime=time;
			imgx++;
			if(imgx > 3){
				imgx=0;
			}
		}
	}
	
//	��������ҩˮ
	@Override
	public ElementObj createElement(String str){
		String[] split = str.split(",");
		this.setX(Integer.parseInt(split[0]));
		this.setY(Integer.parseInt(split[1]));
		ImageIcon icon = GameLoad.imgMap.get(split[2]);
		this.setIcon(icon);
		this.setW(icon.getIconWidth()/4);
		this.setH(icon.getIconHeight()/4);
		return this;
		
	}
//	ҩˮ�������ײ��,���ݱ�ը����+1

	@Override
	public void pkResult(ElementObj obj){
		if(obj instanceof Play){
			this.setLive(false);
		}else if(obj instanceof WaterColumn){
			this.setLive(false);
		}
		
	}
	

}
