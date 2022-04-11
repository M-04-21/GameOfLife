class Main {
  public static void main(String[] args) {
    Application app = new Application(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
    app.setVisible(true);
    app.begin();
  }
}
