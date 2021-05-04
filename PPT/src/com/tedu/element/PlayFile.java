package com.tedu.element;

import java.awt.Graphics;
import java.util.List;

import javax.swing.ImageIcon;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;

public class PlayFile extends ElementObj {
	private int imgx=0;  //����ͼƬ����  
	private long imgtime=0;//����ͼƬ�����ٶ�
	private int livetime=0;   //����ʱ��
	private final static int LIMITTIME=150;//���޵�����ʱ�䣬�������ʱ�����ݾͻ���ʧ���ˮ��
	private int power;//��ը������Ĭ��Ϊ1��Ӧ������Ҵ���������Ϊ�������ߺ���ҵ�״̬��ը����Ӧ���иı䣩
	private int playId;

	@Override
	public void showElement(Graphics g) {
		g.drawImage(this.getIcon().getImage(), 
				this.getX(), this.getY(), //Ŀ����ε�һ���ǵ�����
				this.getX()+32, this.getY()+32,//Ŀ����εڶ����ǵ����� 
				0+(imgx*42), 6, 
				42+(imgx*42), 47,
				null);
	}

	public ElementObj createElement(String str) {//���ж����ַ����Ĺ���
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
		this.livetime++;       //����ʱ���ʱ
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
	public void die() {  //������ʧ�����ˮ��
		ElementObj obj=GameLoad.getObj("water");
		ElementObj element = obj.createElement(this.toString());//��������ض����ʵ�岢��ʼ������
		ElementManager.getManager().addElement(element, GameElement.WATERCOLUMN);
		
//		�ҵ��������ݵĶ����������ѷ�������-1
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
	 * ����ĸ�����ˮ���ĳ���
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
				
//				if(objX==waterGoX&&objY==waterGoY) {//�ж��Ƿ�Ϊ���������ڵ�Ԫ��
//					if(maps.get(j) instanceof Box) {   //�жϵ�ͼ�������ЩԪ���ǲ��ǿ���ը���ģ��������ը��������+1��������ˮ������+1
//						return realPower+1;						
//					}
//					else {
//						return realPower;//����ǲ�����ը����Ԫ�أ��ͷ���0��˵��ˮ��û�г���
//					}
//				}
			}
			realPower++;//���������Ԫ�أ��򳤶ȿ���+1
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
