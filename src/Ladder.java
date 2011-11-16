import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// 윤복아안녕?

class Draw extends JPanel {
	protected Image img = null;
	protected Graphics gImg = null;
	protected Dimension frameSize;

	Draw() {
	}

	public void init() {
		this.setBackground(Color.CYAN);
		this.frameSize = Ladder.frameSize;
		img = createImage(frameSize.width, frameSize.height);
		gImg = img.getGraphics();
	}

	public void changeSize() {

	}

	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, this);
	}
}

public class Ladder extends JFrame implements ActionListener {

	private JButton start_bt, player_bt[], goal_bt[];
	private JComboBox jcb;
	private JLabel jlb;
	private JPanel player_pl, goal_pl;
	private Container con;

	private int player = 6, state = 1;
	private int ladderWidth;
	private int ladderHeight = 10;
	public static int[][] ladderMap;

	private Draw draw;
	private Play play[];

	public static Dimension frameSize;

	Ladder(String title) {
		super(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(170, 130);

		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		frameSize = getSize();
		setLocation((int) (screen.getWidth() / 2 - frameSize.getWidth() / 2),
				(int) (screen.getHeight() / 2 - frameSize.getHeight() / 2));

		con = getContentPane();
	}

	private void initStart() {
		// setResizable(false);
		setLayout(null);

		jlb = new JLabel("참여 인원수를 선택하세요");
		jlb.setBounds(7, 7, 160, 20);

		jcb = new JComboBox();
		for (int i = 2; i <= 10; i++)
			jcb.addItem(i);
		jcb.setSelectedItem(6);
		jcb.setMaximumRowCount(9);
		jcb.setBounds(50, 34, 70, 20);

		start_bt = new JButton("Start");
		start_bt.setBounds(45, 62, 80, 30);
		con.add(jlb);
		con.add(start_bt);
		con.add(jcb);

		start_bt.addActionListener(this);
		jcb.addActionListener(this);

		setVisible(true);
	}

	private void init() {
		setLayout(new BorderLayout());
		setVisible(false);

		state = 2;

		con.removeAll();

		setSize(600, 450);

		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		frameSize = getSize();
		setLocation((int) (screen.getWidth() / 2 - frameSize.getWidth() / 2),
				(int) (screen.getHeight() / 2 - frameSize.getHeight() / 2));

		con = getContentPane();
		setVisible(true);

		ladderWidth = player;
		ladderMap = new int[ladderHeight][ladderWidth];

		player_pl = new JPanel();
		goal_pl = new JPanel();
		player_pl.setLayout(new GridLayout(1, 1, 5, 5));
		goal_pl.setLayout(new GridLayout(1, 1, 5, 5));

		player_bt = new JButton[player];
		goal_bt = new JButton[player];

		for (int i = 0; i < player; i++) {
			String s = String.valueOf(i + 1) + "번";
			player_bt[i] = new JButton(s);
			goal_bt[i] = new JButton("꽝");
			player_bt[i].setBounds(
					(int) (frameSize.getWidth() / player * i + frameSize
							.getWidth() / player * 0.1), 5,
					(int) (frameSize.getWidth() / player * 0.85), 25);
			goal_bt[i].setBounds(
					(int) (frameSize.getWidth() / player * i + frameSize
							.getWidth() / player * 0.05),
					(int) (frameSize.getHeight() - 50),
					(int) (frameSize.getWidth() / player * 0.9), 25);
			player_pl.add(player_bt[i]);
			goal_pl.add(goal_bt[i]);
			player_bt[i].addActionListener(this);
			goal_bt[i].addActionListener(this);
		}

		while (true) {
			int i = (int) (Math.random() * 10);
			if (i < player) {
				goal_bt[i].setText("당첨");
				break;
			}
		}

		con.add(player_pl, "North");
		con.add(goal_pl, "South");

		draw = new Draw();
		con.add(draw, "Center");
		draw.init();
		initLadder();
		validate();
		repaint();

		// play = new Play(this, draw, frameSize,1);
		// play.start();
		//
		// play2 = new Play(this, draw, frameSize,5);
		// play2.start();
		play = new Play[player];

		addComponentListener(new ComponentResizedHdl());

	}

	private void initLadder() {

		int width = (int) (frameSize.getWidth() / player);
		int height = (int) (frameSize.getHeight());

		for (int i = 0; i < ladderMap.length; i++)
			for (int j = 0; j < ladderMap[i].length - 1; j++) {
				if (Math.random() > 0.4 && ladderMap[i][j] != 2) {
					ladderMap[i][j] = 1;
					ladderMap[i][j + 1] = 2;
				}
			}

		drawLadder();

	}

	private void drawLadder() {
		int width = (int) (frameSize.getWidth() / player);
		int height = (int) (frameSize.getHeight());

		for (int i = 0; i < player; i++) {
			draw.gImg.drawLine(width * i + width / 2, 0, width * i + width / 2,
					(int) frameSize.getHeight() - 50);
		}
		for (int i = 0; i < ladderMap.length; i++)
			for (int j = 0; j < ladderMap[i].length - 1; j++) {
				if (ladderMap[i][j] == 1) {
					draw.gImg
							.drawLine(
									width * j + width / 2,
									(int) ((frameSize.getHeight() - 80) / 10
											* i + frameSize.getHeight() * 0.02),
									width * (j + 1) + width / 2,
									(int) ((frameSize.getHeight() - 80) / 10
											* i + frameSize.getHeight() * 0.02));
					// draw.gImg.drawLine((int)(frameSize.getWidth()/player*j+frameSize.getWidth()/player/2),
					// (int)((frameSize.getHeight()-80)/10*i+frameSize.getHeight()*0.02),
					// (int)(frameSize.getWidth()/player*(j+1)+frameSize.getWidth()/player/2),
					// (int)((frameSize.getHeight()-80)/10*i+frameSize.getHeight()*0.02));
				}
			}
		draw.repaint();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == start_bt) {
			init();
		} else if (e.getSource() == jcb) {
			player = (Integer) jcb.getSelectedItem();
		}

		if (state == 2) {
			for (int i = 0; i < player; i++) {
				if (e.getSource() == player_bt[i]) {
					play[i] = new Play(draw, i);
					play[i].start();
				}
			}
		}
	}

	class ComponentResizedHdl extends ComponentAdapter {
		public void componentResized(ComponentEvent e) {
			// draw.gImg.clearRect(0, 0, draw.frameSize.width,
			// draw.frameSize.height);
			frameSize = getSize();
			draw.frameSize = frameSize;
			draw.init();
			drawLadder();
		}
	}

	public static void main(String ar[]) {
		Ladder la = new Ladder("Ladder");
		// la.initStart();
		la.init();
	}
}