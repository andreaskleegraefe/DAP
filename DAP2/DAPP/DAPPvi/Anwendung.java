import java.util.ArrayList;
import java.util.StringTokenizer;
import java.io.RandomAccessFile;

//Interval-Scheduling Folie 8 Seite 78
//Lateness-Scheduling Folie 9 Seite 64

class Anwendung{

  public static ArrayList<Interval> intervalScheduling(Interval[] array){
    //Initialisierung der Variablen zum Aufrufen des intervalSchedulings
    ArrayList<Interval> Liste = new ArrayList<Interval>();
    int[] start = new int[array.length];
    int[] ende = new int[array.length];
    for(int i = 0;i<array.length;i++){
      start[i]=array[i].getStart();	//Inv.: Vor dem i-ten Durchlauf wurde das Intervall-Array bis i-1 in start- und ende-Arrays geteilt
      ende[i]=array[i].getEnd();
    }
    //Berechnung des intervalSchedulings und umsetzund des Ergebnisses als ArrayList
    int[] Reihenfolge = intervalScheduling(start,ende);
    for(int i = 0;i<Reihenfolge.length;i++){
      Liste.add(array[Reihenfolge[i]]);		//Inv.: Vor dem i-ten Durchlauf wurden die Elemente bis i-1 in die ArrayList eingefuegt
    }
    return Liste;
  }

  public static int[] intervalScheduling(int[] s,int[] f){
    //Initialisierung der Variablen
    int n = s.length;
    int[] A = new int[s.length];
    A[0]=0;	//Waehle fruehsten Endzeitpunkt: Da sortiert nach Endzeitpunkt: Index = 0
    int alauf = 1;
    int j = 0;
    for(int i = 1;i<n;i++){ 
      if(s[i]>=f[j]){
	A[alauf]=i;	//Inv.: Vor dem i-ten Durchlauf wurden bis i-1 die kuerzesten, sich nicht Ueberschneidenden Intervall-indizes in A eingefuegt
	alauf++;
	j=i;
      }
    }
    //Da alle A[i]>=A[alauf] = 0, neues Teilarray der laenge alauf erzeugen, Ergebnis "kopieren" und zurueckgeben
    int[] B = new int[alauf];
    for(int i = 0;i<alauf;i++){
      B[i]=A[i];
    }
    return B;
  }

  public static int[] latenessScheduling(Job[] array){
    //Teilung des Jobarrays und Anwendung des latenessScheduling
    int[] dauer = new int[array.length];
    int[] deadline = new int[array.length];
    for(int i = 0;i<array.length;i++){
      dauer[i]=array[i].getDauer();	//Inv.: Vor dem i-ten Durchlauf wurde der Jobarray bis i-1 in dauer- und deadline-Arrays geteilt
      deadline[i]=array[i].getDeadline();
    }
    return latenessScheduling(dauer,deadline);
  }

  public static int[] latenessScheduling(int[] t,int[] d){
    //Initialisierung der Variablen
    int n = t.length;
    int[] A = new int[n];
    //Startzeitpunkt des ersten Jobs = 0, da vorher keine anderen Jobs ausgefÃ¼hrt
    int z = 0;
    //Berechnung der Startzeitverzoegerung des i-ten Jobs
    for(int i = 0;i<n;i++){
      A[i]=z;	//Inv.: Vor dem i-ten Durchlauf wurde die Startzeitverzoegerung bis i-1 berechnet
      z=z+t[i];
    }
    return A;
  }

  public static void main(String[] args){
    try{
      //Nur ausfuehren, wenn 2 Parameter Uebergeben, sonst Fehlermeldung
      if(args.length==2){
	RandomAccessFile file = new RandomAccessFile(args[1],"r");
	System.out.println("Bearbeite Datei: "+args[1]+"\n");
	int n = 0;
	int i = 0;
	while(file.readLine()!=null){
	  n++;
	}
	if(args[0].equals("Interval")){
	  Interval[] Intervalle = new Interval[n];
	  file = new RandomAccessFile(args[1],"r");
	  String zeile;
	  while((zeile = file.readLine())!=null){
	    StringTokenizer st = new StringTokenizer(zeile,",");
	    int start = Integer.parseInt(st.nextToken());
	    int end = Integer.parseInt(st.nextToken());
	    Intervalle[i]= new Interval(start,end);
	    i++;
	  }
	  System.out.println("Es wurden "+n+" Zeilen mit folgendem Inhalt gelesen:");
	  String Ausgabe = "[";
	  for(int s = 0;s<n-1;s++){
	    Ausgabe = Ausgabe + Intervalle[s].toString() +", ";
	  }
	  Ausgabe = Ausgabe + Intervalle[n-1].toString()+"]";
	  System.out.println(Ausgabe+"\n");
	
	  //Sortieren + Ausgabe
	  mergeSort(Intervalle);
	  System.out.println("Sortiert:");
	  Ausgabe = "[";
	  for(int s = 0;s<n-1;s++){
	    Ausgabe = Ausgabe + Intervalle[s].toString() +", ";
	  }
	  Ausgabe = Ausgabe + Intervalle[n-1].toString()+"]";
	  System.out.println(Ausgabe+"\n");

	  //IntervalScheduling + Ausgabe
	  ArrayList<Interval> Ergebnis = intervalScheduling(Intervalle);
	  if(Ergebnis.isEmpty()){
	    System.out.println("Kein Ergebnis!");
	    System.exit(0);
	  }
	  Ausgabe = "[";
	  for(int s = 0;s<Ergebnis.size();s++){
	    if(s==(Ergebnis.size()-1)){
	      Ausgabe=Ausgabe+Ergebnis.get(s).toString()+"]";
	    }
	    else{
	      Ausgabe=Ausgabe+Ergebnis.get(s).toString()+", ";
	    }
	  }
	  System.out.println("Berechnetes Intervallscheduling:\n"+Ausgabe);
	}
	else{
	  if(args[0].equals("Lateness")){
	    Job[] Jobs = new Job[n];
	    file = new RandomAccessFile(args[1],"r");
	    String zeile;
	    while((zeile = file.readLine())!=null){
	      StringTokenizer st = new StringTokenizer(zeile,",");
	      int dauer = Integer.parseInt(st.nextToken());
	      int deadline = Integer.parseInt(st.nextToken());
	      Jobs[i]= new Job(dauer,deadline);
	      i++;
	    }
	    System.out.println("Es wurden "+n+" Zeilen mit folgendem Inhalt gelesen:");
	    String Ausgabe = "[";
	    for(int s = 0;s<n-1;s++){
	      Ausgabe = Ausgabe + Jobs[s].toString() +", ";
	    }
	    Ausgabe = Ausgabe + Jobs[n-1].toString()+"]";
	    System.out.println(Ausgabe+"\n");

	    //Sortieren + Ausgabe
	    mergeJSort(Jobs);
	    System.out.println("Sortiert:");
	    Ausgabe = "[";
	    for(int s = 0;s<n-1;s++){
	      Ausgabe = Ausgabe + Jobs[s].toString() +", ";
	    }
	    Ausgabe = Ausgabe + Jobs[n-1].toString()+"]";
	    System.out.println(Ausgabe+"\n");

	    //LatenessScheduling + Ausgabe
	    int[] Starts = latenessScheduling(Jobs);
	    System.out.println("Berechnetes Latenessscheduling:");
	    Ausgabe = "[";
	    for(int s = 0;s<n-1;s++){
	      Ausgabe = Ausgabe + Starts[s] +", ";
	    }
	    Ausgabe = Ausgabe + Starts[n-1]+"]";
	    System.out.println(Ausgabe+"\n");
	    
	    //Maximale Verspaetung ausrechnen und ausgeben:
	    int max = 0;
	    for(int s = 0;s<n;s++){	//Inv.: Vor dem i-ten Durchlauf wurde die groesste verspaetung > 0 bis zum i-1-ten Job bestimmt.
	      if((Starts[s] - Jobs[s].getDeadline() + Jobs[s].getDauer() ) > max){
		max = Starts[s]-Jobs[s].getDeadline()+Jobs[s].getDauer();
	      }
	    }
	    System.out.println("Berechnete maximale VerspÃ¤tung: "+max);
	  }
	  //Fehler: zu viele/wenige Parameter!
	  else{
	    System.out.println("Falsche Parameter! \"Anwendung Interval|Lateness TESTDATEI\"");
	  }
	}
      }
      //Fehler: 1. Parameter weder Interval noch Lateness
      else{
	System.out.println("Falsche Parameterwahl! Bitte geben sie Interval oder Lateness und den Pfad zu einer Testdatei an!");
      }
    //Fehlerbehandlung:
    }catch(Exception E){
      System.out.println("Falsche Parameter! \"Anwendung Interval|Lateness TESTDATEI\"");
    }
  }

  //mergeSort fuer Intervalle:
  public static void mergeSort(Interval[] array){
    Interval[] B = new Interval[array.length];    
    mergeSort(array,0,array.length-1,B);
  }
  
  private static void mergeSort(Interval[] array,int p,int r,Interval[] B){
    if(p<r){
      int q=(p+r)/2;
      mergeSort(array,p,q,B);	//Inv.: Vor dem i-ten Durchlauf ist der zu sortierende Bereich r/(2^i).
      mergeSort(array,q+1,r,B);
      Merge(array,p,q,r,B);
    }
  }

  private static void Merge(Interval[] array,int p,int q,int r,Interval[] B){
    //Initialisierung der Indexvariablen und des Hilfsarrays
    int i = p;
    int j = q+1;
    int s = p;
    //AuswÃ¤hlen und AnfÃ¼gen des kleineren Elementes in den Hilfsarray
    while((i<=q)&&(j<=r)){
            if(array[i].getEnd()<array[j].getEnd()){
	      B[s++]=array[i++];	//Inv.: Vor dem i+j-ten Durchlauf sind alle Elemente von p bis i-1 und q+1 bis j-1 im Hilfsarray von klein nach gross einsortiert.
	    }
            else{
	      B[s++]=array[j++];
	    }
    }
    //Falls der zweite Teil abgearbeitet ist, wird der erste angefuegt
    while(i<=q){    
      assert (j>r) : (j+" ist nicht groesser als "+r);
      B[s++]=array[i++];	//Inv.: Vor dem i-ten Durchlauf sind die Elemente von q+1 bis r und von p bis i-1 sortiert in Array B eingefuegt worden.
    }
    //Falls der erste Teil abgearbeitet ist, wird der zweite angefuegt    
    while(j<=r){
      assert (i>q) : (i+" ist nicht groesser als "+q);
      B[s++]=array[j++];	//Inv.: Vor dem j-ten Durchlauf sind die Elemente von p bis j-1 sortiert in Array B eingefuegt worden.
    }
    //ZurÃ¼ckkopieren der sortierten Elemente aus dem Hilfsarray
    for(int k=p;k<s;k++){
      array[k]=B[k];	//Inv.: Vor dem k-ten Durchlauf sind die sortierten Elemente von p bis k-1 von B nach array kopiert.
    }
  }

  //mergeSort fuer Jobs
  public static void mergeJSort(Job[] array){
    Job[] B = new Job[array.length];    
    mergeJSort(array,0,array.length-1,B);
  }
  
  private static void mergeJSort(Job[] array,int p,int r,Job[] B){
    if(p<r){
      int q=(p+r)/2;
      mergeJSort(array,p,q,B);	//Inv.: Vor dem i-ten Durchlauf ist der zu sortierende Bereich r/(2^i).
      mergeJSort(array,q+1,r,B);
      MergeJ(array,p,q,r,B);
    }
  }

  private static void MergeJ(Job[] array,int p,int q,int r,Job[] B){
    //Initialisierung der Indexvariablen und des Hilfsarrays
    int i = p;
    int j = q+1;
    int s = p;
    //Auswaehlen und Anfuegen des kleineren Elementes in den Hilfsarray
    while((i<=q)&&(j<=r)){
            if(array[i].getDeadline()<array[j].getDeadline()){
	      B[s++]=array[i++];	//Inv.: Vor dem i+j-ten Durchlauf sind alle Elemente von p bis i-1 und q+1 bis j-1 im Hilfsarray von klein nach gross einsortiert.
	    }
            else{
	      B[s++]=array[j++];
	    }
    }
    //Falls der zweite Teil abgearbeitet ist, wird der erste angefÃ¼gt
    while(i<=q){    
      assert (j>r) : (j+" ist nicht groesser als "+r);
      B[s++]=array[i++];	//Inv.: Vor dem i-ten Durchlauf sind die Elemente von q+1 bis r und von p bis i-1 sortiert in Array B eingefuegt worden.
    }
    //Falls der erste Teil abgearbeitet ist, wird der zweite angefuegt    
    while(j<=r){
      assert (i>q) : (i+" ist nicht groesser als "+q);
      B[s++]=array[j++];	//Inv.: Vor dem j-ten Durchlauf sind die Elemente von p bis j-1 sortiert in Array B eingefuegt worden.
    }
    //Zurueckkopieren der sortierten Elemente aus dem Hilfsarray
    for(int k=p;k<s;k++){
      array[k]=B[k];	//Inv.: Vor dem k-ten Durchlauf sind die sortierten Elemente von p bis k-1 von B nach array kopiert.
    }
  }
}