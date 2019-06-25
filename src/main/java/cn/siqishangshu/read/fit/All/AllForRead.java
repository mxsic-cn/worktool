package cn.siqishangshu.read.fit.All;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class AllForRead {
	private int[] allInOne=new int[28];
	private long total=0;
	
	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public void allADD(File fileinall){
		try {
			FileReader fr=new FileReader(fileinall);
			BufferedReader br=new BufferedReader(fr);
			String c=null;
			while(true){
				c=br.readLine();
				if(c==null)break;
				int a=0;
				a=Integer.parseInt(c);
				if(a<30){
					allInOne[a]++;
					if(a==0)System.out.println(br.readLine()+"��ǰһ��");
				}
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int I=0;
		for (int i : allInOne) {
			if(I%10==0)System.out.println();
			System.out.print(I+":"+i+"  ");
			I++;
		}
		
	}
	

}
