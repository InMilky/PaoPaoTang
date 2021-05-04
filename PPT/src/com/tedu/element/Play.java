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
 * 1.�̳и���
 * 2.��д���ַ�����ʵ��ҵ���߼�
 * 3.��д���̵߳���ײ
 * 4.��������ļ���ʽ�иı䣬����дGameLoad����ļ��ط���
 */
public class Play extends ElementObj{
	//���ݷ����л����ﶯ��
	private int imgx = 0;
	private int imgy = 0;
	
	private long imgtime = 0;//���ڿ���ͼƬ�仯�ٶ�
	
	private int id;	//���id
	private int speed = 3;//�����ƶ��ٶ�
	private int maxFileNum = 1;//�����������
	private int putFileNum = 0;//�ѷ��õ���������
	private int power = 1;//�����������
	
	private boolean down = false;
	private boolean up = false;
	private boolean left = false;
	private boolean right = false;
	
	private boolean pkType = false;	//״̬������
	private String fx = "down";	//����

	@Override
	public void showElement(Graphics g) {
//		g.drawImage(this.getIcon().getImage(),
//				this.getX(), this.getY(), 
//				this.getW(), this.getH(), null);
		//ͼƬ�ָ�
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
		System.out.println("��ʼ���"+this.id+"�ɷ���������Ϊ��"+this.maxFileNum);
		System.out.println("��ʼ���"+this.id+"��������Ϊ��"+this.power);
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
			if(bl){	//����
				switch(key){	//��ô�Ż�
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
					this.pkType=true;break;//��������״̬
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
			if(bl){	//����
				switch(key){	//��ô�Ż�
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
					this.pkType=true;break;//��������״̬
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
//		�ж��������ҵı߽����ײ���
		if (this.left && this.getX()>0&&!collisionCheck(this.getX()-speed,this.getY())) {
			this.setX(this.getX() - speed);
		}
		if (this.up  && this.getY()>0&&!collisionCheck(this.getX(),this.getY()-speed)) {
			this.setY(this.getY() - speed);
		}
		if (this.right && this.getX()<875-this.getW() &&!collisionCheck(this.getX()+-speed,this.getY())) {  //�������ת�ɴ�������
			this.setX(this.getX() + speed);
		}
		if (this.down && this.getY()<550-this.getH() &&!collisionCheck(this.getX(),this.getY()+speed)) {
			this.setY(this.getY() + speed);
		}
	}
	
	/**
	 * @˵�� ��Һ͵�ͼ�������ݵ���ײ��⣬��֤��Ҳ���ӵ�ͼ���������߹�ȥ����move�����е��ã�
	 * @param playGoX �����һ�����ߵ�λ�õ�x����
	 * @param playGoY �����һ�����ߵ�λ�õ�y����
	 * @return ���������ײ�ͷ���true
	 */
	private boolean collisionCheck(int playGoX,int playGoY) {
		
		List<ElementObj> mapList=ElementManager.getManager().getElementsByKey(GameElement.MAPS);
		List<ElementObj> boxList=ElementManager.getManager().getElementsByKey(GameElement.BOX);
		List<ElementObj> fileList=ElementManager.getManager().getElementsByKey(GameElement.PLAYFILE);
//		�������е�mapԪ�أ�����Ƿ���Ԫ���������һ�����ߵ���λ�÷�����ײ
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
		
//		�������е����ݣ�����Ƿ���Ԫ���������һ�����ߵ���λ�÷�����ײ
		for(ElementObj file:fileList) {
			int x=file.getX();
			int y=file.getY();
			int w=file.getW();
			int h=file.getH();
			Rectangle r1=new Rectangle(x,y,w,h);
			Rectangle r2=new Rectangle(playGoX,playGoY,this.getW(),this.getH());
			Rectangle r3=new Rectangle(this.getX(),this.getY(),this.getW(),this.getH());
			if(r1.intersects(r2)&&!r1.intersects(r3)) {  //���ݺ���ұ���ҲҪ����ײ��⣬��Ȼ��������֮��ͻᱻ������ס
				return true;
			}
		}
		return false;
	}
	
	@Override
	protected void add(long gameTime) {
		if(!this.pkType||this.putFileNum>=this.maxFileNum) {//������ڹ���״̬�����ѷ����������ﵽ����������������ֱ�ӷ���
			return;
		}
		this.pkType = false;//��һ�η���һ���ӵ���ƴ���٣�Ҳ�������ӱ��������ƣ�
		ElementObj obj = GameLoad.getObj("file");
		ElementObj element = obj.createElement(this.toString());//�Ժ�Ŀ��ѧϰ�л�����
//		//װ�뵽������
		ElementManager.getManager().addElement(element, GameElement.PLAYFILE);
//		//���Ҫ�����ӵ��ٶȵȡ���������Ҫ�����д
		this.putFileNum++;	//�������ݺ��ѷ�����������+1
	}
	
	//������ײ�����������  �����������Ӧ�仯
	@Override
	public void pkResult(ElementObj enemy) {
		if(enemy instanceof BubbleProp){
			this.maxFileNum++;
			GameLoad.musicMap.get("getMusic").play();
			System.out.println("���"+this.id+"�ɷ���������Ϊ��"+this.maxFileNum);	//��������
		}else if(enemy instanceof PowerDrug){
			this.power++;
			GameLoad.musicMap.get("getMusic").play();
			System.out.println("���"+this.id+"��������Ϊ��"+this.power);	//��������
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
	public String toString() {	//������͵����ֱ��ʹ��toString�������Լ�����һ������
		//{x:3,y:5,f:up} json��ʽ
		int x = this.getX()+this.getW()/2;
		int y = this.getY()+this.getH();
//		switch(this.fx){	//�ӵ��ڷ����ʱ����Ѿ�����̶��Ĺ켣�����Լ���Ŀ�꣬�޸�json��ʽ
//		case "up": x+=12;break;	//һ�벻��д20����ֵ��ͼƬ��С������ʾ��С��һ������¿���ʹ��ͼƬ��С�������㡣
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

	//���id ��play1��play2
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
