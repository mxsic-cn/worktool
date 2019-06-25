package cn.siqishangshu.read.fit.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class TheMian {
	private int[] inMath= new int[] { 1, 3, 6, 10, 15, 21, 28, 36, 45, 55, 63,
			69, 73, 75, 75, 73, 69, 63, 55, 45, 36, 28, 21, 15, 10, 6, 3, 1 };
	private int[] before=new int[28];
	private double[] NEXT=new double[28];
	private File filebefore=null;
	private int qishu=0;
	
	
	public int getQishu() {
		return qishu;
	}

	public void setQishu(int qishu) {
		this.qishu = qishu;
	}

	public File getFilebefore() {
		return filebefore;
	}

	public void setFilebefore(File filebefore) {
		this.filebefore = filebefore;
	}

	private void putInNext(){
		for (int i = 0; i < NEXT.length; i++) {
			NEXT[i]=inMath[i]-(((double)before[i]/qishu)*1000-inMath[i]);
		}
	}
	
	
	public void readBefore(){
		try {
			FileReader fr=new FileReader(filebefore);
			BufferedReader br=new BufferedReader(fr);
			while(true){
			String s=br.readLine();
			if(s==null)break;
			int l=0;
			l=Integer.parseInt(s);
			if(l>2000000){
				System.out.println(s=br.readLine());
				l=Integer.parseInt(s);
				before[l]++;
				qishu++;
			}	
			}
			putInNext();
			show();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private void show(){
		System.out.println("the before number is:(qishu="+qishu+")");
		for (int i = 0; i < before.length; i++) {
			System.out.println(i+":"+before[i]);
		}
		System.out.println();
		for (int i = 0; i <NEXT.length; i++) {
			System.out.println(i+":"+NEXT[i]);
		}
		
	}
	

}
