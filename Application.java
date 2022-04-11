import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import java.util.Random;

class Application extends JFrame {
  ApplicationArea appArea;
  ApplicationUpdate appUpd;
  Field fld;
  Random rnd = new Random();
  int width, height, pixelSize;

  //Logics
  boolean spacePressed = true;
  boolean nextPressed = false;

  //Constructure
  public Application(int width, int height, int pixelSize) {
    super();

    this.pixelSize = pixelSize;
    this.width = width;
    this.height = height;

    fld = new Field(width, height, pixelSize);
    fld.Init();
    appArea = new ApplicationArea();
    appUpd = new ApplicationUpdate(appArea);

    this.addKeyListener(new ApplicationKeyListener());
    this.add(appArea);
    this.pack();
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  //Start thread
  public void begin() {
    appUpd.start();
  }

  //Area where painting
  private class ApplicationArea extends JComponent {

    public ApplicationArea() {
      super();
      setPreferredSize(new Dimension(width,height));
    }

    public void paintComponent(Graphics g) {
      super.paintComponent(g);

				g.setColor(Color.black);
      //g.setColor(new Color(rnd.nextInt(255),rnd.nextInt(255),rnd.nextInt(255)));
      for(int i = 0; i * pixelSize < width; i++) {
        for(int j = 0; j * pixelSize < height; j++) {
          if(fld.getElement(i,j)){
            //g.setColor(new Color(rnd.nextInt(255),rnd.nextInt(255),rnd.nextInt(255)));
            g.fillRect(i*pixelSize, j*pixelSize, pixelSize, pixelSize);
          }
        }
      }
    }
  }

  //Thread that update ApplicationArea
  private class ApplicationUpdate extends Thread {
    ApplicationArea appArea;

    public ApplicationUpdate(ApplicationArea appArea) {
      this.appArea = appArea;
    }

    public void run() {
      while(true) {
        try {
          Thread.sleep(25);
        } catch(Exception e) {}

        while(spacePressed) {
          try {
            if(nextPressed) {
              break;
            }
            Thread.sleep(200);
          } catch(Exception e) {}
        }


        appArea.revalidate();
        appArea.repaint();
        fld.step();
        nextPressed = false;
      }
    }
  }

  //Logics of pressed key
  class ApplicationKeyListener implements KeyListener {
    public void keyTyped(KeyEvent e) {
      //System.out.println("Typed " + (int)e.getKeyChar());
    }

    public void keyPressed(KeyEvent e) {
      char pressedKey = e.getKeyChar();
      //N - Next frame
      if(pressedKey == 'n' || pressedKey == 'N') {
        nextPressed = !nextPressed;
      }
      //Space - pause
      if(pressedKey == ' '){
        spacePressed = !spacePressed;
      }
      //ESC - exit (EXIT = ASCII.27)
      if((int)pressedKey == 27){
        System.exit(0);
      }
      //R- recreate field
      if(pressedKey == 'r' || pressedKey == 'R'){
        fld.Init();
        appArea.revalidate();
        appArea.repaint();
      }
    }

    public void keyReleased(KeyEvent e) {

    }
  }
}
