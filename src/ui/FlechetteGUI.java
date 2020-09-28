package ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import flechette.Flechette;
import flechette.Joueur;
import flechette.PanneauGraphique;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

public class FlechetteGUI extends JPanel{
	/** a queue for read flechette
	 * 
	 */
	Queue<Flechette> flechetteQueue = new LinkedList<Flechette>();
	
	/**
	 * Animation timer
	 */
	private final Timer blinkTimer;
	private final Thread listener;//The thread of the main method
	private static final float MULT_FACTOR = 1.5f;
	/*
	largeur int�rieure des doubles et des triples : 8 mm
	diam�tre du centre (bulle) : 12,7 mm
	diam�tre du demi-centre (demi-bulle) : 31,8 mm
	rayon du cercle ext�rieur de la couronne des doubles : 170 mm
	rayon du cercle ext�rieur de la couronne des triples : 107,4 mm
	diam�tre total de la cible : 451 mm
	*/
	public static final float [] Radius= {12.7f/2*MULT_FACTOR,(31.8f/2)*MULT_FACTOR,107.4f*MULT_FACTOR,(107.4f+8)*MULT_FACTOR,(170f)*MULT_FACTOR,(170f+8)*MULT_FACTOR,451f/2*MULT_FACTOR};//The list of radiuses of the circles (6) on the cible. unit is mm
	public static final int [] Secteurs= {6,13,4,18,1,20,5,12,9,14,11,8,16,7,19,3,17,2,15,10};//Les secteur a partir de 3h, counter clock-wise
	public static final int BULL_INT_INDEX = 0;
	public static final int BULL_EXT_INDEX = 1;
	public static final int SIMPLE_INT_INDEX = 2;
	public static final int TRIPLE_INDEX = 3;
	public static final int SIMPLE_EXT_INDEX = 4;
	public static final int DOUBLE_INDEX = 5;
	public static final int EXTERNAL_INDEX = 6;
	public Rectangle2D.Float[] rectangles = new Rectangle2D.Float[7];
	private Point2D.Float center;
	private ColoredShapeEntry[] shapes = new ColoredShapeEntry[1+1+20*5];
	private Rectangle Flechette_Bounds;
	public FlechetteGUI(int width, int height, Thread listener) {
		this.listener = listener;
		Flechette_Bounds = new Rectangle(0, 0, width, height);
		Flechette_Bounds.add(0, 0);
		center = new Point2D.Float(width/2, height/2);
		for (int i=0; i<Radius.length; i++)
			rectangles[i]  = new Rectangle2D.Float(center.x-Radius[i], center.y-Radius[i], 2*Radius[i], 2*Radius[i]);
		//Bull: 2 shapes
		shapes[0] = new ColoredShapeEntry(new Arc2D.Float(rectangles[0], 0, 360, Arc2D.OPEN), Color.RED, 25,2);//Bull
		shapes[1] = new ColoredShapeEntry(createArc(rectangles[1], rectangles[0], 0, 360),Color.GREEN,25,1);//Bull Extern
		//Simple Int.
		Color c = Color.WHITE;
		Color couronne = Color.GREEN;
		for (int i=0;i<20; i++) {
			//Create a complete slice
			//Simple Int. +couronne
			shapes[i+2+20*0]=new ColoredShapeEntry(createArc(rectangles[2], rectangles[1], -9+18*i, 18),c,Secteurs[i],-1);
			shapes[i+2+20*1]=new ColoredShapeEntry(createArc(rectangles[3], rectangles[2], -9+18*i, 18),couronne,Secteurs[i],3);
			
			//Simple Ext +couronne
			shapes[i+2+20*2]=new ColoredShapeEntry(createArc(rectangles[4], rectangles[3], -9+18*i, 18),c,Secteurs[i],1);
			shapes[i+2+20*3]=new ColoredShapeEntry(createArc(rectangles[5], rectangles[4], -9+18*i, 18),couronne,Secteurs[i],2);
			shapes[i+2+20*4]=new ColoredShapeEntry(createArc(rectangles[6], rectangles[5], -9+18*i, 18),Color.BLACK, 0,0, ""+Secteurs[i]);
			
			if (c==Color.WHITE)
				c = Color.BLACK;
			else c = Color.WHITE;
			if (couronne==Color.GREEN)
				couronne = Color.RED;
			else couronne = Color.GREEN;
		}
		
		//Mouse listener
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int x=e.getX();
		        int y=e.getY();
		        for (int i=0; i<shapes.length-20; i++) {//Ommit the last 20 slices as they contain the exterior black slices (numbers) of the target
		        	ColoredShapeEntry s = shapes[i];
		        	if (s.shape.contains(x, y)) {
		        		addFlechette(new Flechette(s.secteur, s.zone));
		        		return;
		        	}
		        }
		        addFlechette(new Flechette(0, 0));// Hors cible
			}
		});
		
		this.blinkTimer = new Timer();
		
		
	}
	private ColoredShapeEntry getSector(Flechette flechette) {
		if (flechette.getSecteur()==25)//bull
			if (flechette.getZone()==1)//bull externe
				return shapes[1];
			else 
				if (flechette.getZone()==2)//bull
					return shapes[0];
				else return null;//erreur

		if (flechette.getSecteur()>20 || flechette.getSecteur()<=0)//Flechette invalide
			return null;
				
		
		int indexSecteur = -1;
		for (int i=0; i<Secteurs.length; i++) {
			if (Secteurs[i]==flechette.getSecteur()) {
				indexSecteur = i;
				break;
			}
		}
		if (indexSecteur==-1)//Erreur!!
			return null;
		switch(flechette.getZone()) {
		case -1: return shapes[indexSecteur+2];//simple interne
		case 3: return shapes[indexSecteur+2+20*1];//couronne interne
		case 1: return shapes[indexSecteur+2+20*2];//simple externe
		case 2: return shapes[indexSecteur+2+20*3];//couronne ext.
		}
		return null;
	}
	ColoredShapeEntry clickedShape;
	private Path2D.Float createArc(Rectangle2D outer_rect,Rectangle2D inner_rect, int startAngle, int arcAngle ){
		Path2D.Float path= new Path2D.Float();
		Arc2D.Float arcouterArcF = new Arc2D.Float(outer_rect, startAngle, arcAngle, Arc2D.OPEN);
		path.append(arcouterArcF.getPathIterator(null), true);
		Arc2D.Float innerArcF = new Arc2D.Float(inner_rect, startAngle+ arcAngle, -arcAngle, Arc2D.OPEN);
		path.append(innerArcF.getPathIterator(null), true);
		path.closePath();
		return path;
	}
	
	@Override
    public Dimension getPreferredSize() {
        return Flechette_Bounds.getSize();
    }
	@Override
	public void paintComponent(Graphics g) {
		BasicStroke stroke = new BasicStroke(2.0f);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.GREEN);
		g2.setStroke(stroke);
		Font font = new Font("Serif", Font.BOLD, 32);
        g2.setFont(font);
		
		for (ColoredShapeEntry e:shapes) {
			if (e!=null) {
				g2.setColor(e.color);
				g2.fill(e.shape);
				if (e.msg!=null) {
					g2.setColor(Color.WHITE);				
					 g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						        RenderingHints.VALUE_ANTIALIAS_ON);
					 Rectangle2D.Float bBox = (Rectangle2D.Float)e.shape.getBounds2D();
					 float x = (float)(bBox.getX()+bBox.getWidth()/3);
					 float y = (float)(bBox.getY()+bBox.getHeight()/2);
					 g2.drawString(e.msg,x,y); 
				}
					
			}
		}				
	}
	static class ColoredShapeEntry{
		public int secteur;
		public int zone;
		public Shape shape;
		public Color color;
		public String msg = null;
		public Color originalColor;
		public ColoredShapeEntry(Shape shape, Color color, int secteur, int zone, String msg) {
			this.color = color;
			this.originalColor = color;
			this.shape = shape;
			this.msg = msg;
			this.secteur = secteur;
			this.zone = zone;
		}
		public ColoredShapeEntry(Shape shape, Color color, int secteur, int zone) {
			this(shape,color,secteur, zone, null);
		}
	}
	
	public static FlechetteGUI createFlechetteGUI (Thread listener) {
		int size = 800;
		FlechetteGUI cible = new FlechetteGUI(size,size, listener);
		EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame();
                frame.add(cible);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
		return cible;
	}
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
            	int size = 800;
                FlechetteGUI cible = new FlechetteGUI(size,size, null);
                JFrame frame = new JFrame();
                frame.add(cible);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                //frame.setSize(size, size);
                frame.pack();
                frame.setVisible(true);
                //JOptionPane.showMessageDialog(null, cible);
                //System.exit(0);
            }
        });

	}
	private void addFlechette(Flechette fl) {
		synchronized (flechetteQueue) {
			flechetteQueue.add(fl);
			if(listener!=null)
				flechetteQueue.notifyAll();
		}
	}
	public Flechette nextFlechette() {
		synchronized (flechetteQueue) {
			while (flechetteQueue.isEmpty())
				try {
					flechetteQueue.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			return flechetteQueue.remove();
			
		}
		//return flechetteQueue.poll();
		/*if (flechetteQueue.isEmpty()) {
			try {
				listener.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return flechetteQueue.poll();*/
			
	}

	public void afficherFlechette(Flechette flechette) {
		ColoredShapeEntry shape = getSector(flechette);
		if (shape==null)
			return;
		ArrayList<ColoredShapeEntry> shapes = new ArrayList<>();
		shapes.add(shape);
		blinkSector(shapes, Color.ORANGE, 5,200);
	}
	
	public void afficherRecommandationPourJoueur(Joueur joueur, Flechette[] flechetteRecommander) {
		if (flechetteRecommander==null)
			return;
		ArrayList<ColoredShapeEntry> shapes = new ArrayList<>();
		for (Flechette f: flechetteRecommander) {
			if (f==null)
				continue;
			ColoredShapeEntry shape = getSector(f);
			if (shape!=null)
				shapes.add(shape);
		}
		if (shapes.size()==0)
			return;
		blinkSector(shapes, Color.magenta/*decode("#79FA38")*/, 11,300);
	}
	private void blinkSector(ArrayList<ColoredShapeEntry> shape, Color color, int countBlincks, long period) {
		final ArrayList<ColoredShapeEntry> clickedShape = shape;
		final Color animColor = color;
		final int times = countBlincks;
		Timer blinkTimer = new Timer();
		blinkTimer.schedule(new TimerTask() {
			int count =0;
			Color[] originalColor;
			@Override
			public void run() {
				if (count==0) {
					originalColor = new Color[clickedShape.size()];
					for (int i=0; i<clickedShape.size(); i++ )
						originalColor[i] = clickedShape.get(i).originalColor;
				}
				Color[] paintColor = new Color[originalColor.length];
				for (int i=0; i<paintColor.length; i++) {
					if (count%2==1) {
						clickedShape.get(i).color=originalColor[i];
					}
					else
						clickedShape.get(i).color = animColor;
					//FlechetteGUI.this.repaint(clickedShape.get(i).shape.getBounds2D().getBounds());
				}
				//clickedShape.color=paintColor;
				//FlechetteGUI.this.invalidate();
				
				FlechetteGUI.this.panneau.repaint();
            		//FlechetteGUI.this.repaint(/*clickedShape.shape.getBounds2D().getBounds()*/);
				if (count==times) {
					this.cancel();
				}
				count++;
				
			}
			
		}, 0, period);	
	}
	
	private PanneauGraphique panneau;
	public void setPanneauGraphique(PanneauGraphique frame) {
		panneau = frame;
		
	}

}
