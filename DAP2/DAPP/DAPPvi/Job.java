class Job{

  private int Dauer;
  private int Deadline;

  public Job(int a, int b){
    Dauer=a;
    Deadline=b;
  }

  public int getDauer(){
    return Dauer;
  }

  public int getDeadline(){
    return Deadline;
  }

  public String toString(){
    return ("["+Dauer+","+Deadline+"]");
  }

}