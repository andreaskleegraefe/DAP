class Interval{

  private int Start;
  private int End;

  public Interval(int a, int b){
    Start=a;
    End=b;
  }

  public int getStart(){
    return Start;
  }

  public int getEnd(){
    return End;
  }

  public String toString(){
    return ("["+Start+","+End+"]");
  }

}