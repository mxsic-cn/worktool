package cn.siqishangshu.read.fit.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Odd_Even {
	private int[] inMath= new int[] { 1, 3, 6, 10, 15, 21, 28, 36, 45, 55, 63,
			69, 73, 75, 75, 73, 69, 63, 55, 45, 36, 28, 21, 15, 10, 6, 3, 1 };
	private int[][] next=new int[28][28];
	private double[] NEXT=new double[28];
	private int[] count=new int[28];
	private int odd=0;
	private int even=0;
	private int all=0;
	private int pre=0;
	private File filebefore=null;
	public void setPre(int pre) {
		this.pre = pre;
	}
	public File getFilebefore() {
		return filebefore;
	}
	public void setFilebefore(File filebefore) {
		this.filebefore = filebefore;
	}
	public int getOdd() {
		return odd;
	}
	public void setOdd(int odd) {
		this.odd = odd;
	}
	public int getEven() {
		return even;
	}

	public void setEven(int even) {
		this.even = even;
	}

	
	public void whatIsTheMostInNext(){
		System.out.println("if the pre is:"+pre+"the next is:");
		for (int i = 0; i < 28; i++) {
			NEXT[i]=(double)inMath[i]-(((double)next[pre][i]/count[i])-inMath[i]);
			System.out.println(i+":"+NEXT[i]);
		}
	}
	public void readforOddAndEven(){
		try {
			FileReader fr=new FileReader(filebefore);
			BufferedReader br=new BufferedReader(fr);
			int pre=0;
			while(true){
			String s=br.readLine();
			if(s==null)break;
			int l=0;
			l=Integer.parseInt(s);
			if(l>2000000){
				
				//System.out.println(s=br.readLine());
				s=br.readLine();
				l=Integer.parseInt(s);
				count[l]++;
				all++;
				if(l%2==0){
					even++;
				}else{
					odd++;
				}
				next[pre][l]++;
				pre=l;
				
			}	
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void show(){
		System.out.println("odd is:"+odd+"\n"+"the even is:"+even);
		System.out.println("odd is:"+(double)odd/all+"\nthe even is:"+(double)even/all);
	}
	private void show1(){
		for (int i = 0; i < 28; i++) {
			System.out.println("pre is:"+i);
			for (int j = 0; j < 28; j++) {
				System.out.print( next[i][j]+" ");
			}
			System.out.println();
		}
	}
	public void giveThePre(){
		System.out.println("pre is :"+pre);
		for (int i = 0; i < 28; i++) {
			System.out.println(i+":"+next[pre][i]);
		}
	}
	public void lianShuGaiLv(int i){
		
	}
	

}
