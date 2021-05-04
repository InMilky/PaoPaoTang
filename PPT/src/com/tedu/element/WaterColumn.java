package com.tedu.element;

import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import com.tedu.manager.GameLoad;

/**
 * @˵�� ���ݶ�ʱ��ը����ֵ�ˮ��
 * @author 16070
 *
 */
public class WaterColumn extends ElementObj {
	
//	�ĸ������ˮ����ʵ�ʳ���
	private int upLength;
	private int downLength;
	private int leftLength;
	private int rightLength;
	
	//����ͼƬ�ƶ��ٶ�
	private int imgx=-1;
	
	private long imgtime=0;

	@Override
	public void showElement(Graphics g) {
//		�����м��ͼ
		g.drawImage(this.getIcon().getImage(),
				this.getX(),this.getY(),
				this.getX()+32,this.getY()+32,
				28+(imgx*56),114,
				60+(imgx*56),153,null);
//		�����ĸ������ˮ��
		drawWaterColumn("up",upLength,g);
		drawWaterColumn("down",downLength,g);
		drawWaterColumn("left",leftLength,g);
		drawWaterColumn("right",rightLength,g);
	}

	/**
	 * �����ĸ������ˮ���ķ���
	 * @param direction ˮ������
	 * @param waterLength ˮ������
	 * @param g ����
	 * 
	 * @����˵�� 32Ϊһ����ͼԪ�صĳ��ȣ������б���Ҫ�޸�
	 */
	private void drawWaterColumn(String direction,int waterLength,Graphics g) {
		for(int i=1;i<=waterLength;i++) {
			switch(direction) {
			case "up":
				g.drawImage(this.getIcon().getImage(),
						this.getX(),this.getY()-32*i,
						this.getX()+32,(this.getY()-32*i)+32,
						28+(imgx*58),185,
						60+(imgx*58),224,null);
				break;
			case "down":
				g.drawImage(this.getIcon().getImage(),
						this.getX(),this.getY()+32*i,
						this.getX()+32,(this.getY()+32*i)+32,
						28+(imgx*58),260,
						60+(imgx*58),299,null);
				break;
			case "left":
				g.drawImage(this.getIcon().getImage(),
						this.getX()-32*i,this.getY(),
						(this.getX()-32*i)+32,this.getY()+32,
						273+(imgx*58),260,
						305+(imgx*58),299,null);
				break;
			case "right":
				g.drawImage(this.getIcon().getImage(),
						this.getX()+32*i,this.getY(),
						(this.getX()+32*i)+32,this.getY()+32,
						276+(imgx*58),185,
						308+(imgx*58),224,null);
				break;
			}
		}
	}

	public ElementObj createElement(String str) {
		String[] split = str.split(",");
		this.setX(Integer.parseInt(split[0]));
		this.setY(Integer.parseInt(split[1]));
		ImageIcon icon=GameLoad.imgMap.get(split[2]);
		this.setUpLengh(Integer.parseInt(split[3]));
		this.setDownLengh(Integer.parseInt(split[4]));
		this.setLeftLengh(Integer.parseInt(split[5]));
		this.setRightLengh(Integer.parseInt(split[6]));
		this.setIcon(icon);
		this.setW(icon.getIconWidth());
		this.setH(icon.getIconHeight());
		
		return this;
	}
	
	@Override
	protected void updateImage(long gameTime) {
		if(imgx>=3) {
			this.setLive(false);
		}
		if(gameTime-imgtime>3) {
			imgtime=gameTime;
			imgx++;
			
		}
	}
	
	/**
	 * ��д���෽������Ϊˮ�����ĸ����������൱�ں�������������������ײ
	 * 32Ϊ��ͼ����Ԫ�صĿ�ȣ��б�����Ҫ�޸�
	 */
	@Override
	public boolean pk(ElementObj obj) {
//		��ȡ������ľ���
		Rectangle r1=new Rectangle(this.getX(),this.getY()-upLength*32,32,(upLength+downLength+1)*32);
//		��ȡ������ľ���
		Rectangle r2=new Rectangle(this.getX()-leftLength*32,this.getY(),(leftLength+rightLength+1)*32,32);
		Rectangle r3=obj.getRectangle();
//		���������ľ��ηֱ���obj����ײ���
		if(r1.intersects(r3)||r2.intersects(r3)) {
			return true;
		}
		return false;
	}
	
	public void setUpLengh(int upLength) {
		this.upLength = upLength;
	}

	public void setDownLengh(int downLength) {
		this.downLength = downLength;
	}

	public void setLeftLengh(int leftLength) {
		this.leftLength = leftLength;
	}

	public void setRightLengh(int rightLength) {
		this.rightLength = rightLength;
	}
}
