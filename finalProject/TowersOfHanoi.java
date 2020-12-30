package finalProject;

import javax.vecmath.*;
import java.awt.*;
import java.awt.image.*;
import java.net.URL;
import java.awt.event.*;
import javax.imageio.ImageIO;
import javax.media.j3d.*;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.geometry.Cylinder;
import java.applet.Applet;

public class TowersOfHanoi extends Applet implements ActionListener, KeyListener {

	private static final long serialVersionUID = 1L;

	private Button red = new Button("red");
	private Button green = new Button("green");
	private Button yellow = new Button("yellow");
	private Button purp = new Button("purp");
	private Button cyan = new Button("cyan");

	private TransformGroup gameBoard;
	private TransformGroup redTorus;
	private TransformGroup greenTorus;
	private TransformGroup yellowTorus;
	private TransformGroup purpleTorus;
	private TransformGroup cyantorus;
	
	private Transform3D tfred = new Transform3D();
	private Transform3D tfyellow = new Transform3D();
	private Transform3D tfgreen = new Transform3D();
	private Transform3D tfpurp = new Transform3D();
	private Transform3D tfcyan = new Transform3D();

	private float xloc = 0.0f;
	private float yloc = 0.0f;

	private float grxloc = 0.0f;
	private float gryloc = 0.0f;

	private float yexloc = 0.0f;
	private float yeyloc = 0.0f;

	private float pxloc = 0.0f;
	private float pyloc = 0.0f;
	
	private float cyloc = 0.0f;
	private float cxloc = 0.0f;
	//private Torus torus;

	private SimpleUniverse su = null;

	public static void main(String[] args) {
		new MainFrame(new TowersOfHanoi(), 600, 400);
	}

	public void init() {
		// create canvas
		GraphicsConfiguration gc = SimpleUniverse.getPreferredConfiguration();
		Canvas3D cv = new Canvas3D(gc);
		setLayout(new BorderLayout());
		add(cv, BorderLayout.CENTER);
		BranchGroup bg = createSceneGraph(cv);
		bg.compile();
		su = new SimpleUniverse(cv);
		su.getViewingPlatform().setNominalViewingTransform();
		su.getViewer().getView().setBackClipDistance(100.0);


		Panel p = new Panel();
		p.add(red);
		p.add(cyan);
		p.add(yellow);
		p.add(purp);
		p.add(green);
		add("North", p);
		red.addActionListener(this);
		red.addKeyListener(this);
		cyan.addActionListener(this);
		cyan.addKeyListener(this);
		yellow.addActionListener(this);
		yellow.addKeyListener(this);
		purp.addActionListener(this);
		purp.addKeyListener(this);
		green.addActionListener(this);
		green.addKeyListener(this);	

		orbitControls(cv);
		
		su.addBranchGraph(bg);
	}
	private void orbitControls(Canvas3D canvas) {
        BoundingSphere bounds = new BoundingSphere(new Point3d(0, 0, 0), 100);

        OrbitBehavior orbit = new OrbitBehavior(canvas,
                OrbitBehavior.REVERSE_ALL);
        orbit.setSchedulingBounds(bounds);

        ViewingPlatform vp = su.getViewingPlatform();
        vp.setViewPlatformBehavior(orbit);
    }
	private BranchGroup createSceneGraph(Canvas3D cv) {
		BranchGroup root = new BranchGroup();
		Appearance ap = new Appearance();
		ap.setMaterial(new Material());

		// transform group for the board components
		gameBoard = new TransformGroup();
		gameBoard.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		int countTrans = 0;
		gameBoard.addChild(createPegs(countTrans));
		countTrans++;
		gameBoard.addChild(createPegs(countTrans));
		countTrans++;
		gameBoard.addChild(createPegs(countTrans));
		gameBoard.addChild(createBoard());
		root.addChild(gameBoard);

		// testing redTorus
		redTorus = new TransformGroup();
		redTorus.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		redTorus.addChild(createTorus(0.2, 0.5));
		root.addChild(redTorus);

		// testing yellowTorus
		yellowTorus = new TransformGroup();
		yellowTorus.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		yellowTorus.addChild(createTorus(0.3, 0.9));
		root.addChild(yellowTorus);

		// testing greenTorus
		greenTorus = new TransformGroup();
		greenTorus.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		greenTorus.addChild(createTorus(0.4, 1.3));
		root.addChild(greenTorus);
		
		purpleTorus = new TransformGroup();
		purpleTorus.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		purpleTorus.addChild(createTorus(0.31, 1.1));
		root.addChild(purpleTorus);
		
		cyantorus = new TransformGroup();
		cyantorus.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		cyantorus.addChild(createTorus(0.25, 0.6));
		root.addChild(cyantorus);

		BoundingSphere bounds = new BoundingSphere();
		
		//light
		AmbientLight light = new AmbientLight(true, new Color3f(Color.blue));
		light.setInfluencingBounds(bounds);
		root.addChild(light);
		PointLight ptlight = new PointLight(new Color3f(Color.white), new Point3f(0f, 0f, 2f),
				new Point3f(1f, 0.3f, 0f));
		ptlight.setInfluencingBounds(bounds);
		root.addChild(ptlight);
		
		// background
		URL url = getClass().getClassLoader().getResource("images/398165.jpg");
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(url);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		ImageComponent2D image = new ImageComponent2D(ImageComponent2D.FORMAT_RGB, bi);
		Background background = new Background(image);
		background.setApplicationBounds(bounds);
		root.addChild(background);
		return root;
	}
	

	public void keyPressed(KeyEvent e) {
		// Invoked when a key has been pressed.
		// WASD FUNCTION
		if (e.getSource() == red) {

			if (e.getKeyChar() == 'w') {
				yloc = yloc + .05f;
				tfred.setTranslation(new Vector3f(xloc, yloc, 0.0f));
				redTorus.setTransform(tfred);
			}
			if (e.getKeyChar() == 'a') {
				xloc = xloc - .1f;
				tfred.setTranslation(new Vector3f(xloc, yloc, 0.0f));
				redTorus.setTransform(tfred);
			}
			if (e.getKeyChar() == 's') {
				yloc = yloc - .05f;
				tfred.setTranslation(new Vector3f(xloc, yloc, 0.0f));
				redTorus.setTransform(tfred);
			}
			if (e.getKeyChar() == 'd') {
				xloc = xloc + .1f;
				tfred.setTranslation(new Vector3f(xloc, yloc, 0.0f));
				redTorus.setTransform(tfred);
			}

		}

		if (e.getSource() == green) {

			if (e.getKeyChar() == 'w') {
				gryloc = gryloc + .05f;
				tfgreen.setTranslation(new Vector3f(grxloc, gryloc, 0.0f));
				greenTorus.setTransform(tfgreen);
			}
			if (e.getKeyChar() == 'a') {
				grxloc = grxloc - .1f;
				tfgreen.setTranslation(new Vector3f(grxloc, gryloc, 0.0f));
				greenTorus.setTransform(tfgreen);
			}
			if (e.getKeyChar() == 's') {
				gryloc = gryloc - .05f;
				tfgreen.setTranslation(new Vector3f(grxloc, gryloc, 0.0f));
				greenTorus.setTransform(tfgreen);
			}
			if (e.getKeyChar() == 'd') {
				grxloc = grxloc + .1f;
				tfgreen.setTranslation(new Vector3f(grxloc, gryloc, 0.0f));
				greenTorus.setTransform(tfgreen);
			}
		}

		if (e.getSource() == yellow) {

			if (e.getKeyChar() == 'w') {
				yeyloc = yeyloc + .05f;
				tfyellow.setTranslation(new Vector3f(yexloc, yeyloc, 0.0f));
				yellowTorus.setTransform(tfyellow);
			}
			if (e.getKeyChar() == 'a') {
				yexloc = yexloc - .1f;
				tfyellow.setTranslation(new Vector3f(yexloc, yeyloc, 0.0f));
				yellowTorus.setTransform(tfyellow);
			}
			if (e.getKeyChar() == 's') {
				yeyloc = yeyloc - .05f;
				tfyellow.setTranslation(new Vector3f(yexloc, yeyloc, 0.0f));
				yellowTorus.setTransform(tfyellow);
			}
			if (e.getKeyChar() == 'd') {
				yexloc = yexloc + .1f;
				tfyellow.setTranslation(new Vector3f(yexloc, yeyloc, 0.0f));
				yellowTorus.setTransform(tfyellow);
			}

		}
		if (e.getSource() == purp) {

			if (e.getKeyChar() == 'w') {
				pyloc = pyloc + .05f;
				tfpurp.setTranslation(new Vector3f(pxloc, pyloc, 0.0f));
				purpleTorus.setTransform(tfpurp);
			}
			if (e.getKeyChar() == 'a') {
				pxloc = pxloc - .1f;
				tfpurp.setTranslation(new Vector3f(pxloc, pyloc, 0.0f));
				purpleTorus.setTransform(tfpurp);
			}
			if (e.getKeyChar() == 's') {
				pyloc = pyloc - .05f;
				tfpurp.setTranslation(new Vector3f(pxloc, pyloc, 0.0f));
				purpleTorus.setTransform(tfpurp);
			}
			if (e.getKeyChar() == 'd') {
				pxloc = pxloc + .1f;
				tfpurp.setTranslation(new Vector3f(pxloc, pyloc, 0.0f));
				purpleTorus.setTransform(tfpurp);
			}
		}
		if (e.getSource() == cyan) {

			if (e.getKeyChar() == 'w') {
				cyloc = cyloc + .05f;
				tfcyan.setTranslation(new Vector3f(cxloc, cyloc, 0.0f));
				cyantorus.setTransform(tfcyan);
			}
			if (e.getKeyChar() == 'a') {
				cxloc = cxloc - .1f;
				tfcyan.setTranslation(new Vector3f(cxloc, cyloc, 0.0f));
				cyantorus.setTransform(tfcyan);
			}
			if (e.getKeyChar() == 's') {
				cyloc = cyloc - .05f;
				tfcyan.setTranslation(new Vector3f(cxloc, cyloc, 0.0f));
				cyantorus.setTransform(tfcyan);
			}
			if (e.getKeyChar() == 'd') {
				cxloc = cxloc + .1f;
				tfcyan.setTranslation(new Vector3f(cxloc, cyloc, 0.0f));
				cyantorus.setTransform(tfcyan);
			}
		}
		if (e.getKeyChar() == ' ') {
			xloc=xloc-xloc;
			yloc=yloc-yloc;
			yexloc=yexloc-yexloc;
			yeyloc=yeyloc-yeyloc;
			grxloc=grxloc-grxloc;
			gryloc=gryloc-gryloc;
			cxloc=cxloc-cxloc;
			cyloc=cyloc-cyloc;
			pyloc=pyloc-pyloc;
			pxloc=pxloc-pxloc;
			
			tfred.setTranslation(new Vector3f(xloc, yloc, 0.0f));
			redTorus.setTransform(tfred);
			tfyellow.setTranslation(new Vector3f(yexloc, yeyloc, 0.0f));
			yellowTorus.setTransform(tfyellow);
			tfgreen.setTranslation(new Vector3f(grxloc, gryloc, 0.0f));
			greenTorus.setTransform(tfgreen);
			tfcyan.setTranslation(new Vector3f(cxloc, cyloc, 0.0f));
			cyantorus.setTransform(tfcyan);
			tfpurp.setTranslation(new Vector3f(pxloc, pyloc, 0.0f));
			purpleTorus.setTransform(tfpurp);
		}
	}
	public float setXLoc() {
		return xloc = 0;
	}

	public float setYLoc() {
		return yloc = 0;
	}

	public void keyReleased(KeyEvent e) {
		// Invoked when a key has been released.
	}

	public void keyTyped(KeyEvent e) {
		// Invoked when a key has been typed.
	}

	private TransformGroup createTorus(double x, double y) {

		Transform3D trans = new Transform3D();
		if ((float) x == 0.2f)
			trans.setTranslation(new Vector3d(-0.6, 0.16, 0));// ap.setMaterial(matred);
		if ((float) x == 0.3f)
			trans.setTranslation(new Vector3d(-0.6, 0.03, 0));// ap.setMaterial(matyellow);
		if ((float) x == 0.4f)
			trans.setTranslation(new Vector3d(-0.6, -0.14, 0));// ap.setMaterial(matgreen);
		if ((float) x == 0.31f)
			trans.setTranslation(new Vector3d(-0.6, -0.05, 0));
		if ((float) x == 0.25f)
			trans.setTranslation(new Vector3d(-0.6, 0.1, 0));
		
		trans.setScale(0.1);
		TransformGroup rings = new TransformGroup(trans);
		rings.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		rings.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		rings.setCapability(TransformGroup.ENABLE_PICK_REPORTING);

		Appearance ap = new Appearance();

		Color3f red = new Color3f(Color.red);
		Color3f yellow = new Color3f(Color.yellow);
		Color3f green = new Color3f(Color.green);
		Color3f purp = new Color3f(Color.MAGENTA);
		Color3f cyan = new Color3f(Color.CYAN);

		Color3f black = new Color3f(0.0f, 0.0f, 0.0f);
		Color3f white = new Color3f(1.0f, 1.0f, 1.0f);
		Material matred = new Material(red, black, red, white, 70f);
		Material matyellow = new Material(yellow, black, yellow, white, 70f);
		Material matgreen = new Material(green, black, green, white, 70f);
		Material matpurp = new Material(purp, black, purp, white, 70f);
		Material matcyan = new Material(cyan, black, cyan, white, 70f);

		if ((float) x == 0.2f)
			ap.setMaterial(matred);
		if ((float) x == 0.3f)
			ap.setMaterial(matyellow);
		if ((float) x == 0.4f)
			ap.setMaterial(matgreen);
		if ((float) x == 0.31f)
			ap.setMaterial(matpurp);
		if ((float) x == 0.25f)
			ap.setMaterial(matcyan);
		
		Torus torus = new Torus(x, y);
		torus.setAppearance(ap);
		// PickTool.setCapabilities(shape, PickTool.INTERSECT_FULL);
		rings.addChild(torus);
		return rings;
	}
	
	private TransformGroup createBoard() {
		Transform3D trans = new Transform3D();
		trans.setTranslation(new Vector3d(0, -.2f, 0));
		trans.setScale(0.2);
		TransformGroup boardTrans = new TransformGroup(trans);
		boardTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		boardTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		boardTrans.setCapability(TransformGroup.ENABLE_PICK_REPORTING);
		Appearance ap = new Appearance();
		Color3f gray = new Color3f(Color.gray);
		Color3f black = new Color3f(0.0f, 0.0f, 0.0f);
		Color3f white = new Color3f(1.0f, 1.0f, 1.0f);
		Material matgray = new Material(gray, black, gray, white, 70f);
		ap.setMaterial(matgray);
		// syntax of box(length, height, width
		Box board = new Box(5f, .1f, 1.0f, ap);
		
		boardTrans.addChild(board);
		return boardTrans;
	}

	private TransformGroup createPegs(int countTrans) {
		Transform3D trans = new Transform3D();
		trans.setTranslation(new Vector3d(0, .07f, 0));
		trans.setScale(0.2);
		if (countTrans == 0) {
			trans.setTranslation(new Vector3d(0, .07f, 0));
		} else if (countTrans == 1) {
			trans.setTranslation(new Vector3d(.6, .07f, 0));
		} else
			trans.setTranslation(new Vector3d(-.6, .07f, 0));

		TransformGroup pegs = new TransformGroup(trans);
		pegs.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		pegs.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		pegs.setCapability(TransformGroup.ENABLE_PICK_REPORTING);
		Appearance ap = new Appearance();
		Color3f gray = new Color3f(Color.gray);
		Color3f black = new Color3f(0.0f, 0.0f, 0.0f);
		Color3f white = new Color3f(1.0f, 1.0f, 1.0f);
		Material matgray = new Material(gray, black, gray, white, 70f);
		ap.setMaterial(matgray);
		Cylinder col = new Cylinder(0.1f, 2.5f, ap);
		// trans.setTranslation(new Vector3d(1,0.07f,0));
		pegs.addChild(col);

		return pegs;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}