package com.tedu.element.props;

import java.awt.Graphics;

import javax.swing.ImageIcon;

import com.tedu.element.ElementObj;
import com.tedu.element.Play;
import com.tedu.element.WaterColumn;
import com.tedu.manager.GameLoad;

/**
 * 增加泡泡爆炸威力的药水道具类
 * @author kt0302
 * @说明 要实现的功能:
 * 		1.updateImg() 药水出现后的原地跳动
 * 		2.die() 死亡方法——若药水与玩家碰撞后,玩家泡泡爆炸长度+1,药水消失
 */
public class PowerDrug extends ElementObj{

	private int imgx = 0;
	private long imagetime = 0;  //用于控制图片变化速度
	@Override
	public void showElement(Graphics g) {
//		做图片的分割
		g.drawImage(this.getIcon().getImage(), 
				 this.getX(),this.getY(), 
				 this.getX()+this.getW(), 
				 this.getY()+this.getH(), 
				 (imgx*32), 0, 
				 32+(imgx*32),50,
				 null);		
	}
//	药水图片转换 飘动
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
	
//	创建威力药水
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
//	药水与玩家碰撞后,泡泡爆炸长度+1

	@Override
	public void pkResult(ElementObj obj){
		if(obj instanceof Play){
			this.setLive(false);
		}else if(obj instanceof WaterColumn){
			this.setLive(false);
		}
		
	}
	

}
