package linkgame;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class param 
{
	public static final int chessnum = 5;
	public static int row = 6;
	public static int col = 5;
	public static final int height = 600;
	public static final int width = 600;
	public static int chang = 160;
	public static int kuan = 90;
	public static JButton[][] btn = new JButton[param.row][param.col];
	public static int chess[][] = new int[row][col];//6,5
	public static int kuang[][] = new int[row+2][col+2] ;//8,7
	
	public static JLabel biaoqian = new JLabel("0");
	
	public static ImageIcon imgchess1 = new ImageIcon("src/linkgamepic/1.png");
	public static ImageIcon imgchess2 = new ImageIcon("src/linkgamepic/2.png");
	public static ImageIcon imgchess3 = new ImageIcon("src/linkgamepic/3.png");
	public static ImageIcon imgchess4 = new ImageIcon("src/linkgamepic/4.png");
	public static ImageIcon imgchess5 = new ImageIcon("src/linkgamepic/5.png");
	param()
	{
		imgchess1.setImage(((ImageIcon) imgchess1).getImage().getScaledInstance(chang,kuan,Image.SCALE_DEFAULT));
		imgchess2.setImage(((ImageIcon) imgchess2).getImage().getScaledInstance(chang,kuan,Image.SCALE_DEFAULT));
		imgchess3.setImage(((ImageIcon) imgchess3).getImage().getScaledInstance(chang,kuan,Image.SCALE_DEFAULT));
		imgchess4.setImage(((ImageIcon) imgchess4).getImage().getScaledInstance(chang,kuan,Image.SCALE_DEFAULT));
		imgchess5.setImage(((ImageIcon) imgchess5).getImage().getScaledInstance(chang,kuan,Image.SCALE_DEFAULT));
		for(int i=0;i<param.row;i++)
			for(int j=0;j<param.col;j++)
			{
				param.btn[i][j] = new JButton();
			}
		biaoqian.setText(String.valueOf(Integer.parseInt(biaoqian
				.getText())));
	}
	
}


class mainbutton
{
		
	int a=0,b=0;
	
	
	void initbutton()//给button添加在棋盘的位置
	{		
		for(int i=0;i<param.row;i++)
			for(int j=0;j<param.col;j++)
			{
				param.btn[i][j].setText(""+((i+1)*(param.col+2)+j+2));
			}
	}
	
	void getinpic()//给button添加图片
	{
		for(int i=0;i<param.row;i++)
			for(int j=0;j<param.col;j++)
			{
				param.btn[i][j].setVisible(false);
			}	
		for(int i=0;i<param.row;i++)
			for(int j=0;j<param.col;j++)
			{		
				switch(param.chess[i][j])//添加图片并设置可见度
				{
					case 0:	param.btn[i][j].setVisible(false);		break;
					case 1:	param.btn[i][j].setIcon(param.imgchess1);	param.btn[i][j].setVisible(true);	break;
					case 2:	param.btn[i][j].setIcon(param.imgchess2);	param.btn[i][j].setVisible(true);	break;
					case 3:	param.btn[i][j].setIcon(param.imgchess3);	param.btn[i][j].setVisible(true);	break;
					case 4:	param.btn[i][j].setIcon(param.imgchess4);	param.btn[i][j].setVisible(true);	break;
					case 5:	param.btn[i][j].setIcon(param.imgchess5);	param.btn[i][j].setVisible(true);	break;
				}
			}
	}
	
	void actionbutton()//给button注册监听
	{
		mainaction a = new mainaction();
		for(int i=0;i<param.row;i++)
			for(int j=0;j<param.col;j++)
			{
				param.btn[i][j].addActionListener(a);
			}
	}
	
	void randomnum()//产生随机数的函数
	{
		boolean success = true;
		for(int i=0;i<param.row;i++)
		{
			for(int j=0;j<param.col;j++)
			{
				if(param.chess[i][j]==0)
				{	
					int num = 0;
					while(num==0)
					{
						num = (int)(Math.random()*6);
					}
					param.chess[i][j] = num;
					param.kuang[i+1][j+1] = num;
					success=true;
					while(success)
					{
						a = (int)(Math.random()*6);//生成一对随机数，确保偶数
						b = (int)(Math.random()*5);
						if(param.chess[a][b]==0)
						{
							param.chess[a][b] = num;
							param.kuang[a+1][b+1] = num;
							success = false;
						}
					}
				}
			}
		}
	}
}




class mainaction implements ActionListener//外部类做监听
{
	static int k1=0,weizhi1=0,x1=0,y1=0;
	static int k2=0,weizhi2=0,x2=0,y2=0,i=0;
	boolean key=false;
	JButton[] bt = new JButton[2];
	public void biaoqian() {
		param.biaoqian.setText(String.valueOf(Integer.parseInt(param.biaoqian
				.getText()) + 10));
	}
	
	public void actionPerformed(ActionEvent e)//监听按钮
	{
		JButton btt = (JButton)e.getSource();
		String a = ((AbstractButton) btt).getText();
		if(weizhi1 == 0)////////////////////////////读入第一个按钮信息
		{
			bt[0] = btt;
			weizhi1 = Integer.parseInt(a);
			y1 = (weizhi1%(param.col+2))-1;
			if(y1==-1)	y1 = param.col+2-1;
			x1 = (weizhi1-y1)/(param.col+2);
			k1 = param.kuang[x1][y1];
		}else if(weizhi1 != Integer.parseInt(a)){////读入第二个按钮信息
			bt[1] = btt;
			weizhi2 = Integer.parseInt(a);
			y2 = (weizhi2%(param.col+2))-1;
			if(y2==-1)	y2 = param.col+2-1;
			x2 = (weizhi2-y2)/(param.col+2);
			k2 = param.kuang[x2][y2];			
				
			if(k1==k2&&k1!=0&&k2!=0)//保证进入searching的都是一样的图案
			{
				/*插入search函数并返回判断结果*/
				search sr = new search(x1,y1,x2,y2);
				key=sr.searching(x1, y1, x2, y2);
				if(key)
				{
					System.out.println("路径可以消除");
					param.chess[x1-1][y1-1]=0;	param.chess[x2-1][y2-1]=0;
					param.kuang[x1][y1]=0;		param.kuang[x2][y2]=0;
					bt[0].setVisible(false);
					bt[1].setVisible(false);
					biaoqian();
					/*打印矩阵棋盘检查*/
					for(int i=0;i<param.row+2;i++)
					{
						for(int j=0;j<param.col+2;j++)
						{
							System.out.print(param.kuang[i][j]);
						}
						System.out.println();
					}
					//////////////////////////
				}
			}
			k1=0;	k2=0;	weizhi1=0;	weizhi2=0;
			System.out.println();
		}
	}
}





class search
{
	int x11,x22,y11,y22;
	static int aa=0,bb=0;
	boolean t=true,f=false;
	search(int x11,int y11,int x22,int y22)//导入kuang[][]的坐标
	{
		this.x11=x11;	this.x22=x22;	this.y11=y11;	this.y22=y22;	
		aa=x11;	bb=y11;
	}
	
	int direction(int x11,int y11,int x22,int y22)//判断两棋子相对位置
	{
		/*	1 2 3
		 * 	8 a 4
		 *  7 6 5	 */
		if(x11>x22&&y11>y22){
			return 1;
		}else if(x11>x22&&y11==y22){
			return 2;
		}else if(x11>x22&&y11<y22) {
			return 3;
		}else if(x11==x22&&y11<y22) {
			return 4;
		}else if(x11<x22&&y11<y22) {
			return 5;
		}else if(x11<x22&&y11==y22) {
			return 6;
		}else if(x11<x22&&y11>y22) {
			return 7;
		}else if(x11==x22&&y11>y22) {
			return 8;
		}
		return 0;
	}
	/*shang,xia,zuo,you四个基本方法*/
	boolean shang(int x1,int y1,int x2,int y2,boolean f)//  f=true找目标、找尽头    f=false找尽头
	{
//		System.out.println("上");
		x1--;
		if(f)
		{
			while(x1>=0){
//				System.out.println("kuang值:"+param.kuang[x1][y1]);
//				System.out.println("x1:"+x1+",y1:"+y1);
				if((param.kuang[x1][y1]==param.kuang[x2][y2])&&(x1==x2)&&(y1==y2))	return true;
				if(param.kuang[x1][y1]!=0)	{aa=x1+1;	bb=y1;	return false;}
				x1--;
			}
			aa=0;	bb=y1;
			return false;
		}
		while(x1>=0){
//			System.out.println("kuang值:"+param.kuang[x1][y1]);
//			System.out.println("x1:"+x1+",y1:"+y1);
			if((param.kuang[x2][y2]==0)&&(x1==x2)&&(y1==y2)) {aa=x1;	bb=y1;	return false;}
			if(param.kuang[x1][y1]!=0)	{aa=x1+1;	bb=y1;	return false;}
			x1--;
		}
		aa=0;	bb=y1;
		return false;
	}
	boolean xia(int x1,int y1,int x2,int y2,boolean f)//
	{
//		System.out.println("下");
		x1++;
		if(f)
		{
			while(x1<=7){
//				System.out.println("kuang值:"+param.kuang[x1][y1]);
//				System.out.println("x1:"+(x1)+",y1:"+(y1));
				if((param.kuang[x1][y1]==param.kuang[x2][y2])&&(x1==x2)&&(y1==y2))	return true;
				if(param.kuang[x1][y1]!=0)	{aa=x1-1;	bb=y1;	return false;}
				x1++;
			}
			aa=7;	bb=y1;
			return false;
		}
		while(x1<=7){
//			System.out.println("kuang值:"+param.kuang[x1][y1]);
//			System.out.println("x1:"+(x1)+",y1:"+(y1));
			if((param.kuang[x2][y2]==0)&&(x1==x2)&&(y1==y2)) {aa=x1;	bb=y1;	return false;}
			if(param.kuang[x1][y1]!=0)	{aa=x1-1;	bb=y1;	return false;}
			x1++;
		}
		aa=7;	bb=y1;
		return false;
	}
	boolean zuo(int x1,int y1,int x2,int y2,boolean f)
	{
//		System.out.println("左");
		y1--;
		if(f)
		{
			while(y1>=0){
//				System.out.println("kuang值:"+param.kuang[x1][y1]);
//				System.out.println("x1:"+(x1)+",y1:"+(y1));
				if((param.kuang[x1][y1]==param.kuang[x2][y2])&&(x1==x2)&&(y1==y2))	return true;
				if(param.kuang[x1][y1]!=0)	{aa=x1;	bb=y1+1;	return false;}
				y1--;
			}
			aa=x1;	bb=0;
			return false;
		}
		while(y1>=0){
//			System.out.println("kuang值:"+param.kuang[x1][y1]);
//			System.out.println("x1:"+(x1)+",y1:"+(y1));
			if((param.kuang[x2][y2]==0)&&(x1==x2)&&(y1==y2)) {aa=x1;	bb=y1;	return false;}
			if(param.kuang[x1][y1]!=0)	{aa=x1;	bb=y1+1;	return false;}
			y1--;
		}
		aa=x1;	bb=0;
		return false;
	}
	boolean you(int x1,int y1,int x2,int y2,boolean f)
	{
//		System.out.println("右");
		y1++;
		if(f)
		{
			while(y1<=6){
//				System.out.println("kuang值:"+param.kuang[x1][y1]);
//				System.out.println("x1:"+(x1)+",y1:"+(y1));
				if((param.kuang[x1][y1]==param.kuang[x2][y2])&&(x1==x2)&&(y1==y2)) 	return true;
				if(param.kuang[x1][y1]!=0)	{aa=x1;	bb=y1-1; 	return false;}
				y1++;
			}
			aa=x1;	bb=6;
			return false;
		}
		while(y1<=6){
//			System.out.println("kuang值:"+param.kuang[x1][y1]);
//			System.out.println("x1:"+(x1)+",y1:"+(y1));
			if((param.kuang[x2][y2]==0)&&(x1==x2)&&(y1==y2)) {aa=x1;	bb=y1;	return false;}
			if(param.kuang[x1][y1]!=0)	{aa=x1;	bb=y1-1; 	return false;}
			y1++;
		}
		aa=x1;	bb=6;
		return false;
	}	
	/*有一个拐点的方法*/
	boolean onePoint(int x1,int y1,int x2,int y2)
	{
		int d=0;
		boolean key=false;
		d = direction(x1,y1,x2,y2);
		if(d==1)
		{	
//			System.out.println("onepoint d:"+d);
			key=zuo(x1,y1,x1,y2,f);
			key=shang(aa,bb,x2,y2,t);
			if(key&&(x1!=x2)&&(y1!=y2))	return true;
			else {
				key=shang(x1,y1,x2,y1,f);
				key=zuo(aa,bb,x2,y2,t);
				if(key&&(x1!=x2)&&(y1!=y2))	return true;
			}
		}
		
		if(d==3)
		{
//			System.out.println("onepoint d:"+d);
			key=shang(x1,y1,x2,y1,f);
			key=you(aa,bb,x2,y2,t);
			if(key&&(x1!=x2)&&(y1!=y2))	return true;
			else {
				key=you(x1,y1,x1,y2,f);
				key=shang(aa,bb,x2,y2,t);
				if(key&&(x1!=x2)&&(y1!=y2))	return true;
			}
		}
		if(d==5)
		{
//			System.out.println("onepoint d:"+d);
			key=you(x1,y1,x1,y2,f);
			key=xia(aa,bb,x2,y2,t);
			if(key&&(x1!=x2)&&(y1!=y2))	return true;
			else {
				key=xia(x1,y1,x2,y1,f);
				key=you(aa,bb,x2,y2,t);
				if(key&&(x1!=x2)&&(y1!=y2))	return true;
			}
		}
		if(d==7)
		{
//			System.out.println("onepoint d:"+d);
			key=xia(x1,y1,x2,y1,f);
			key=zuo(aa,bb,x2,y2,t);
			if(key&&(x1!=x2)&&(y1!=y2))	return true;
			else {
				key=zuo(x1,y1,x1,y2,f);
				key=xia(aa,bb,x2,y2,t);
				if(key&&(x1!=x2)&&(y1!=y2))	return true;
			}
		}
		return false;
	}
	
	/*有两个拐点的方法*/
	boolean twoPoint(int x1,int y1,int x2,int y2)
	{
		int d = direction(x1,y1,x2,y2);
		boolean key=false;
		if(d==1||d==3||d==8||d==4||d==7||d==5)
		{
//			System.out.println("twopoint d:"+d);
			key=shang(x1,y1,x2,y2,f);
			d=direction(aa,bb,x2,y2);
			key=onePoint(aa,bb,x2,y2);
			if(key)	return true;
		}
		if(d==1||d==3||d==8||d==4||d==7||d==5)
		{
//			System.out.println("twopoint d:"+d);
			key=xia(x1,y1,x2,y2,f);
			d=direction(aa,bb,x2,y2);
			key=onePoint(aa,bb,x2,y2);
			if(key)	return true;
		}
		if(d==1||d==3||d==2||d==6||d==7||d==5)
		{
//			System.out.println("twopoint d:"+d);
			key=zuo(x1,y1,x2,y2,f);
			d=direction(aa,bb,x2,y2);
			key=onePoint(aa,bb,x2,y2);
			if(key)	return true;
		}
		if(d==1||d==3||d==2||d==6||d==7||d==5)
		{
//			System.out.println("twopoint d:"+d);
			key=you(x1,y1,x2,y2,f);
			d=direction(aa,bb,x2,y2);
			key=onePoint(aa,bb,x2,y2);
			if(key)	return true;
		}
		return false;	
	}
	
	/*综合搜索算法*/
	boolean searching(int x1,int y1,int x2,int y2)
	{
		int d = direction(x1,y1,x2,y2);
		boolean key=false;
		if(d==2)
		{
			key=shang(x1,y1,x2,y2,t);
			if(key)	return true;
			key=twoPoint(x1,y1,x2,y2);
			if(key)	return true;
			return false;
		}
		if(d==4)
		{
			key=you(x1,y1,x2,y2,t);
			if(key)	return true;
			key=twoPoint(x1,y1,x2,y2);
			if(key)	return true;
			return false;
		}
		if(d==6)
		{
			key=xia(x1,y1,x2,y2,t);
			if(key)	return true;
			key=twoPoint(x1,y1,x2,y2);
			if(key)	return true;
			return false;
		}
		if(d==8)
		{
			key=zuo(x1,y1,x2,y2,t);
			if(key)	return true;
			key=twoPoint(x1,y1,x2,y2);
			if(key)	return true;
			return false;
		}
		if(d==1||d==3||d==5||d==7)
		{
			key=onePoint(x1,y1,x2,y2);
			if(key)	return true;
			key=twoPoint(x1,y1,x2,y2);
			if(key)	return true;
			return false;
		}
		return false;	
	}
}




class mainFrame extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	
	JFrame win = new JFrame("砥砺前行连连看");
	JPanel P1 = new JPanel();
	JPanel P2 = new JPanel();
	JPanel P3 = new JPanel();
	JPanel P4 = new JPanel();
	JPanel P5 = new JPanel();
	param p = new param();
	mainbutton mb = new mainbutton();
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	public static int cs[][] = new int[param.row][param.col];
	public static int kong[][] = new int[param.row][param.col];
	
	mainFrame()
	{
		P1.setLayout(new GridLayout(param.row,param.col));//给面板P1设置
		P1.setBackground(Color.blue);
		mb.randomnum();
		mb.initbutton();
		mb.getinpic();
		mb.actionbutton();
		for(int i=0;i<param.row;i++)
			for(int j=0;j<param.col;j++)
			{
				P1.add(param.btn[i][j]);
			}
		
	
		P2.setLayout(new FlowLayout());	//给面板P2设置
		JButton ex = new JButton("退出");
		ex.addActionListener(new ActionListener(){ //匿名类做监听
            public void actionPerformed(ActionEvent e){  
                	System.exit(0);
               }  
           });
		P2.add(ex);
		
		
		P3.setLayout(new FlowLayout());	//给面板P3设置
		JButton rn = new JButton("新的一局");
		rn.addActionListener(this);
		P3.add(rn);
		
		
		P4.setLayout(new FlowLayout());//给面板P4设置
		JButton up1 = new JButton("重列");
		up1.setBounds(0, 0, 20, 20);
		chonglie u = new chonglie();
		up1.addActionListener(u);	
		P4.add(up1);
		
			
		P5.setLayout(new FlowLayout());//给面板P5设置
		P5.add(param.biaoqian);
		
		
		validate();
		setVisible(true);
	}
	void start()
	{
		/*对窗体设置*/
		win.setSize(param.height,param.width);
		win.setLocation((screenSize.width-param.width)/2, (screenSize.height-param.height)/2);
		win.setVisible(true);
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		win.add(P1,BorderLayout.CENTER);
		win.add(P2,BorderLayout.SOUTH);
		win.add(P3,BorderLayout.EAST);
		win.add(P4,BorderLayout.WEST);
		win.add(P5,BorderLayout.NORTH);
		
		win.validate();
	}
	@Override
	
	public void actionPerformed(ActionEvent e) {//自身做监听     重新开始
		
		P1.setVisible(false);
		win.setVisible(false);		
		int chess[][] = new int[param.row][param.col];
		param.chess=chess;		               //     清空chess
		mb.randomnum();
		mb.initbutton();
		mb.getinpic();	
		param.biaoqian.setText(""+0);
		P1.setVisible(true);
		win.setVisible(true);
			
	}
	
	
	class chonglie implements ActionListener//内部类做监听    重列
	{
		public void actionPerformed(ActionEvent e)
		{
			int save[] = new int[30];
			int r=0,c=0,n=0;
			P1.setVisible(false);
			win.setVisible(false);
			
			for(int i=0;i<param.row;i++)//把剩下的给存到一个数组
					for(int j=0;j<param.col;j++)
					{
						if(param.chess[i][j]!=0)
						{
							save[n] =param.chess[i][j];
							n++;
						}
					}
			n=n-1;
					
			param.chess=kong;
					
			while (n >= 0) //重新分配位置
			{
				r = (int)(Math.random()*param.row);
				c = (int)(Math.random()*param.col);
				while (cs[r][c]!=0)
				{
					r = (int)(Math.random()*param.row);
					c = (int)(Math.random()*param.col);
				}
				cs[r][c] = save[n];
				n--;
			}			
			param.chess=cs;
			cs=kong;
			
			for(int i=0;i<param.row;i++)
				for(int j=0;j<param.col;j++)
				{
					param.kuang[i+1][j+1]=param.chess[i][j];
				}
			mb.initbutton();
			mb.getinpic();
			P1.setVisible(true);
			win.setVisible(true);
			

		}
	}
	

}





public class windows{
	
	public static void main(String[] args) {
		mainFrame mf = new mainFrame();
		mf.start();
		
		
		for(int i=0;i<param.row+2;i++)
		{
			for(int j=0;j<param.col+2;j++)
			{
				System.out.print(param.kuang[i][j]);
			}
			System.out.println();
		}
		
		
	}
}





