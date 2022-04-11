import java.util.Random;

class Field {
  private boolean[][] area;
  public int raws, cols;
  public Random rnd = new Random();

  public Field(int width, int height, int pixelSize) {
      this.cols = width / pixelSize;
      if(width % pixelSize > 0) {
        this.cols += 1;
      }
      this.raws = height / pixelSize;
      if(height % pixelSize > 0) {
        this.raws += 1;
      }

      area = new boolean[raws][cols];
  }

  public void Init() {
    for(int i = 0; i < raws; i++) {
      for(int j = 0; j < cols; j++) {
        area[i][j] = rnd.nextBoolean();
      }
    }
  }
  //REWRITE!!!
  private int neighbors(int raw, int col) {
    int count = 0;
    for(int i = -1; i < 2; i++) {
      for(int j = -1; j < 2; j++) {
        //System.out.println(String.format("[DEBUG i=%d | j=%d]", i, j));
        if( (raw + i < 0 || col + j < 0) || (i == 0 && j == 0) || (raw + i == raws || col + j == cols) ) {
          //System.out.println(String.format("[DEBUG skiped 1 raw=%d | col=%d | i=%d | j=%d]", raw, col, i, j));
          continue;
        }
        if(raw + i == -1 && col + i == -1) {
          //System.out.println(String.format("[DEBUG skiped 2 raw=%d | col=%d | i=%d | j=%d]", raw, col, i, j));
          continue;
        }

        if(area[raw + i][col + j]) {
          //System.out.println(String.format("[DEBUG counted raw=%d | col=%d | i=%d | j=%d]", raw, col, i, j));
          count++;
        }
      }
    }
    return count;
  }

  public void step() {
    boolean[][] newArea = new boolean[raws][cols];
    int n;
    //System.out.println(String.format("[DEBUG init]"));

    for(int i = 0; i < raws; i++) {
      for(int j = 0; j < cols; j++) {
        n = this.neighbors(i,j);
        //System.out.println(String.format("[DEBUG n=%d | i=%d | j=%d ]", n, i, j));

        if( n < 2 || n > 3 ) {
          //System.out.println(String.format("[DEBUG death raw=%d | col=%d | element=%b", i, j, area[i][j]));
          newArea[i][j] = false;
        }else if( n == 3 ) {
          //System.out.println(String.format("[DEBUG birth raw=%d | col=%d | element=%b", i, j, area[i][j]));
          newArea[i][j] = true;
        }else if( n == 2 || n == 3 ) {
          //System.out.println(String.format("[DEBUG copy raw=%d | col=%d | element=%b", i, j, area[i][j]));
          newArea[i][j] = area[i][j];
        }
      }
    }

    area = newArea;
  }

  public boolean getElement(int raw, int col){
    return area[raw][col];
  }

}
