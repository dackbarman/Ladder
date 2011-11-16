import java.awt.Color;
import java.awt.Dimension;

class Play extends Thread{
	private int[][] ladderMap;
	private int player, ladderWidth, ladderHeight;
	private Dimension frameSize;
	private int x,y;
	int curPlayer;
	

	
	private Draw draw;
	
	Play(Draw draw, int curPlayer){
		this.draw = draw;
		this.ladderMap = Ladder.ladderMap;
		this.ladderWidth = ladderMap[0].length;
		this.ladderHeight = ladderMap.length;
		this.player = ladderMap[0].length;
		this.frameSize = Ladder.frameSize;

		
		this.curPlayer = curPlayer;
	}
	
	
	
	public void run(){
		draw.gImg.setColor(Color.BLUE);
		int width = (int)(frameSize.getWidth()/player);
		
		int j=curPlayer,temp=0;
		x = width * (curPlayer) + width/2 ; 
		y = -2;
		
		for(int i=0; i<ladderMap.length; i++){
			int height = getHeight(i);
			while(y<=height-2){
				y+=2;
				draw.gImg.setColor(Color.BLUE);
				draw.gImg.fillRect(x, y, 3, 3);
				draw.repaint();
				try{
					Thread.sleep(15);
				}catch(InterruptedException e){}
			}
			y = height;
		
			
			width = getWidth(i,j);

			
			while(ladderMap[i][j] == 1 || ladderMap[i][j] == 2){
				if(x>width){
					x-=2;
					temp = 1;
				}
				else{
					x+=2;
					temp = 2;
				}
				if(x == width+1)
					x--;
				
				draw.gImg.fillRect(x, y, 3, 3);
				draw.repaint();
				try{
					Thread.sleep(15);
				}catch(InterruptedException e){}
				
				if(x == width)
					break;
			}
			if(temp == 1){
				j--;
			}
			else if(temp == 2){
				j++;
			}
			temp = 0;
		}
		
		while(y <= (int)(frameSize.getHeight()-50)){
			y+=2;
			draw.gImg.fillRect(x, y, 3, 3);
			draw.repaint();
			try{
				Thread.sleep(15);
			}catch(InterruptedException e){}
		}
		
	}
	
	int getHeight(int i){
		return (int)((frameSize.getHeight()-80)/10*i+frameSize.getHeight()*0.02);
	}
	
	int getWidth(int i, int j){
		int width = (int)(frameSize.getWidth()/player);
		if(ladderMap[i][j] == 1)
			return width*(j+1)+width/2;
		else if(ladderMap[i][j] == 2)
			return width*(j-1)+width/2;
		else
			return width*j+width/2;
	}
}