package linkgame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;







class paran 
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
	
	public static ImageIcon imgchess1 = new ImageIcon("src/linkgamepic/1.png");
	public static ImageIcon imgchess2 = new ImageIcon("src/linkgamepic/2.png");
	public static ImageIcon imgchess3 = new ImageIcon("src/linkgamepic/3.png");
	public static ImageIcon imgchess4 = new ImageIcon("src/linkgamepic/4.png");
	public static ImageIcon imgchess5 = new ImageIcon("src/linkgamepic/5.png");
	paran()
	{
		imgchess1.setImage(((ImageIcon) imgchess1).getImage().getScaledInstance(chang,kuan,Image.SCALE_DEFAULT));
		imgchess2.setImage(((ImageIcon) imgchess2).getImage().getScaledInstance(chang,kuan,Image.SCALE_DEFAULT));
		imgchess3.setImage(((ImageIcon) imgchess3).getImage().getScaledInstance(chang,kuan,Image.SCALE_DEFAULT));
		imgchess4.setImage(((ImageIcon) imgchess4).getImage().getScaledInstance(chang,kuan,Image.SCALE_DEFAULT));
		imgchess5.setImage(((ImageIcon) imgchess5).getImage().getScaledInstance(chang,kuan,Image.SCALE_DEFAULT));
	}
	
}






















 
public class canshu implements ActionListener 
{
	JFrame mainFrame; // �����
	Container thisContainer;
	JPanel centerPanel, southPanel, northPanel; // �����
	 static JButton diamondsButton[][] = new JButton[6][5];// ��Ϸ��ť����
	JButton exitButton, resetButton, newlyButton; // �˳������У����¿�ʼ��ť
	JLabel fractionLable = new JLabel("0"); // ������ǩ
	JButton firstButton, secondButton; // �ֱ��¼���α�ѡ�еİ�ť
	static int grid[][] = new int[8][7];// ������Ϸ��ťλ��
	static boolean pressInformation = false; // �ж��Ƿ��а�ť��ѡ��
	int x0 = 0, y0 = 0, x = 0, y = 0, firstMsg = 0, secondMsg = 0, validateLV; // ��Ϸ��ť��λ������
	int i, j, k, n;// ������������
	
	
	public static void main(String[] args) 
	{
		canshu llk = new canshu();
		llk.randomBuild();
		llk.init();
		llk.rename();
	}
	
	public void init() 
	{
		mainFrame = new JFrame("����ǰ��������");
		thisContainer = mainFrame.getContentPane();
		thisContainer.setLayout(new BorderLayout());
		centerPanel = new JPanel();
		southPanel = new JPanel();
		northPanel = new JPanel();
		thisContainer.add(centerPanel, "Center");
		thisContainer.add(southPanel, "South");
//		thisContainer.add(northPanel, "North");
		centerPanel.setLayout(new GridLayout(6, 5));
		for (int cols = 0; cols < 6; cols++) {
			for (int rows = 0; rows < 5; rows++) {
				diamondsButton[cols][rows] = new JButton(String
						.valueOf(grid[cols + 1][rows + 1]));
				diamondsButton[cols][rows].addActionListener(this);
				centerPanel.add(diamondsButton[cols][rows]);
			}
		}
		exitButton = new JButton("�˳�");
		exitButton.addActionListener(this);
		resetButton = new JButton("����");
		resetButton.addActionListener(this);
		newlyButton = new JButton("����һ��");
		newlyButton.addActionListener(this);
		southPanel.add(exitButton);
		southPanel.add(resetButton);
		southPanel.add(newlyButton);
		fractionLable.setText(String.valueOf(Integer.parseInt(fractionLable
				.getText())));
		northPanel.add(fractionLable);
		mainFrame.setBounds(280, 100, 500, 450);
		mainFrame.setVisible(true);
	}
 
	public void randomBuild() {
		int randoms, cols, rows;
		for (int twins = 1; twins <= 15; twins++) {
			randoms = (int) (Math.random() * 9+ 1);
			for (int alike = 1; alike <= 2; alike++) {
				cols = (int) (Math.random() * 6 + 1);
				rows = (int) (Math.random() * 5 + 1);
				while (grid[cols][rows] != 0) {
					cols = (int) (Math.random() * 6 + 1);
					rows = (int) (Math.random() * 5 + 1);
				}
				this.grid[cols][rows] = randoms;
			}
		}
	}
///////////////////////-ͼƬ����
public static void setIcon(String file,JButton iconButton)
{
ImageIcon icon=new ImageIcon(file);
Image temp=icon.getImage().getScaledInstance(140,100,icon.getImage().SCALE_DEFAULT );
icon=new ImageIcon(temp);
iconButton.setIcon(icon);
}  

//////////////////////////-����ͼƬ
public static void rename()
{
	for(int h=1;h<=6;h++)
	{
		for(int l=1;l<=5;l++)
		{
			if(grid[h][l]==1)setIcon("src/linkgamepic/1.png",diamondsButton[h-1][l-1]);
			if(grid[h][l]==2)setIcon("src/linkgamepic/2.png",diamondsButton[h-1][l-1]);
			if(grid[h][l]==3)setIcon("src/linkgamepic/3.png",diamondsButton[h-1][l-1]);
			if(grid[h][l]==4)setIcon("src/linkgamepic/4.png",diamondsButton[h-1][l-1]);
			if(grid[h][l]==5)setIcon("src/linkgamepic/5.png",diamondsButton[h-1][l-1]);
			if(grid[h][l]==6)setIcon("src/linkgamepic/6.png",diamondsButton[h-1][l-1]);
			if(grid[h][l]==7)setIcon("src/linkgamepic/7.png",diamondsButton[h-1][l-1]);
			if(grid[h][l]==8)setIcon("src/linkgamepic/8.png",diamondsButton[h-1][l-1]);
			if(grid[h][l]==9)setIcon("src/linkgamepic/9.png",diamondsButton[h-1][l-1]);
	
		}
	}
}
	public void fraction() {
		fractionLable.setText(String.valueOf(Integer.parseInt(fractionLable
				.getText()) + 10));
	}
 
	public void reload() {
		int save[] = new int[30];
		int n = 0, cols, rows;
		int grid[][] = new int[8][7];
		for (int i = 0; i <= 6; i++) {
			for (int j = 0; j <= 5; j++) {
				if (this.grid[i][j] != 0) {
					save[n] = this.grid[i][j];
					n++;
				}
			}
		}
		n = n - 1;
		this.grid = grid;
		while (n >= 0) {
			cols = (int) (Math.random() * 6 + 1);
			rows = (int) (Math.random() * 5 + 1);
			while (grid[cols][rows] != 0) {
				cols = (int) (Math.random() * 6 + 1);
				rows = (int) (Math.random() * 5 + 1);
			}
			this.grid[cols][rows] = save[n];
			n--;
		}
		mainFrame.setVisible(false);
		pressInformation = false; // ����һ��Ҫ����ť�����Ϣ��Ϊ��ʼ
		init();
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 5; j++) {
				if (grid[i + 1][j + 1] == 0)
					diamondsButton[i][j].setVisible(false);
			}
		}
	}
 
	public void estimateEven(int placeX, int placeY, JButton bz) {
		if (pressInformation == false) {
			x = placeX;
			y = placeY;
			secondMsg = grid[x][y];
			secondButton = bz;
			pressInformation = true;
		} else {
			x0 = x;
			y0 = y;
			firstMsg = secondMsg;
			firstButton = secondButton;
			x = placeX;
			y = placeY;
			secondMsg = grid[x][y];
			secondButton = bz;
			if (firstMsg == secondMsg && secondButton != firstButton) {
				xiao();
			}
		}
	}
 
	public void xiao() { // ��ͬ��������ܲ�����ȥ����ϸ��������һ����ע��
		if ((x0 == x && (y0 == y + 1 || y0 == y - 1))
				|| ((x0 == x + 1 || x0 == x - 1) && (y0 == y))) { // �ж��Ƿ�����
			remove();
		}   {
			for (j = 0; j < 7; j++) 
			{
				if (grid[x0][j] == 0) { // �жϵ�һ����ťͬ���ĸ���ťΪ��
					if (y > j) 
					{ // ����ڶ�����ť��Y������ڿհ�ť��Y����˵����һ��ť�ڵڶ���ť���
						for (i = y - 1; i >= j; i--)
						{ // �жϵڶ���ť���ֱ����һ��ť�м���û�а�ť
							if (grid[x][i] != 0) {
								k = 0;
								break;
							} else {
								k = 1;
							} // K=1˵��ͨ���˵�һ����֤
						}
						if (k == 1) {
							linePassOne();
						}
					}
					if (y < j) { // ����ڶ�����ť��Y����С�ڿհ�ť��Y����˵����һ��ť�ڵڶ���ť�ұ�
						for (i = y + 1; i <= j; i++) { // �жϵڶ���ť���ֱ����һ��ť�м���û�а�ť
							if (grid[x][i] != 0) {
								k = 0;
								break;
							} else {
								k = 1;
							}
						}
						if (k == 1) {
							linePassOne();
						}
					}
					if (y == j) {
						linePassOne();
					}
				}
				
				
				if (k == 2) {
					if (x0 == x) {
						remove();
					}
					if (x0 < x) {
						for (n = x0; n <= x - 1; n++) {
							if (grid[n][j] != 0) {
								k = 0;
								break;
							}
							if (grid[n][j] == 0 && n == x - 1) {
								remove();
							}
						}
					}
					if (x0 > x) {
						for (n = x0; n >= x + 1; n--) {
							if (grid[n][j] != 0) {
								k = 0;
								break;
							}
							if (grid[n][j] == 0 && n == x + 1) {
								remove();
							}
						}
					}
				}
			}
			for (i = 0; i < 8; i++) { // ��
				if (grid[i][y0] == 0) {
					if (x > i) {
						for (j = x - 1; j >= i; j--) {
							if (grid[j][y] != 0) {
								k = 0;
								break;
							} else {
								k = 1;
							}
						}
						if (k == 1) {
							rowPassOne();
						}
					}
					if (x < i) {
						for (j = x + 1; j <= i; j++) {
							if (grid[j][y] != 0) {
								k = 0;
								break;
							} else {
								k = 1;
							}
						}
						if (k == 1) {
							rowPassOne();
						}
					}
					if (x == i) {
						rowPassOne();
					}
				}
				if (k == 2) {
					if (y0 == y) {
						remove();
					}
					if (y0 < y) {
						for (n = y0; n <= y - 1; n++) {
							if (grid[i][n] != 0) {
								k = 0;
								break;
							}
							if (grid[i][n] == 0 && n == y - 1) {
								remove();
							}
						}
					}
					if (y0 > y) {
						for (n = y0; n >= y + 1; n--) {
							if (grid[i][n] != 0) {
								k = 0;
								break;
							}
							if (grid[i][n] == 0 && n == y + 1) {
								remove();
							}
						}
					}
				}
			}
		}
	}
 
	public void linePassOne() {
		if (y0 > j) { // ��һ��ťͬ�пհ�ť�����
			for (i = y0 - 1; i >= j; i--) { // �жϵ�һ��ťͬ���հ�ť֮����û��ť
				if (grid[x0][i] != 0) {
					k = 0;
					break;
				} else {
					k = 2;
				} // K=2˵��ͨ���˵ڶ�����֤
			}
		}
		if (y0 < j) { // ��һ��ťͬ�пհ�ť����ڶ���ť֮��
			for (i = y0 + 1; i <= j; i++) {
				if (grid[x0][i] != 0) {
					k = 0;
					break;
				} else {
					k = 2;
				}
			}
		}
	}
 
	public void rowPassOne() {
		if (x0 > i) {
			for (j = x0 - 1; j >= i; j--) {
				if (grid[j][y0] != 0) {
					k = 0;
					break;
				} else {
					k = 2;
				}
			}
		}
		if (x0 < i) {
			for (j = x0 + 1; j <= i; j++) {
				if (grid[j][y0] != 0) {
					k = 0;
					break;
				} else {
					k = 2;
				}
			}
		}
	}
 
	public void remove() {
		firstButton.setVisible(false);
		secondButton.setVisible(false);
		fraction();
		pressInformation = false;
		k = 0;
		grid[x0][y0] = 0;
		grid[x][y] = 0;
	}
 
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == newlyButton) {
			int grid[][] = new int[8][7];
			this.grid = grid;
			randomBuild();
			mainFrame.setVisible(false);
			pressInformation = false;
			init();
			rename();
		}
		if (e.getSource() == exitButton)
			System.exit(0);
		if (e.getSource() == resetButton)
		{
			reload();
			rename();
		}
			
		for (int cols = 0; cols < 6; cols++) {
			for (int rows = 0; rows < 5; rows++) {
				if (e.getSource() == diamondsButton[cols][rows])
					estimateEven(cols + 1, rows + 1, diamondsButton[cols][rows]);
			}
		}
	}
 
	
}



