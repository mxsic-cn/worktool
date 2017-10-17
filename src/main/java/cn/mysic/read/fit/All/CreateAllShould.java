package cn.mysic.read.fit.All;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class CreateAllShould {
	private int[] number=new int[28];
	private int[] ddd=new int[3];
	
	public void create3D(File file3d){
		Random random=new Random();
		int a=0;
		double f=0;
		
		try {
			if(!file3d.exists()){
				file3d.createNewFile();
			}
			FileWriter fw=new FileWriter(file3d);
		
		for (int i = 0; i <10000000; i++) {
		int all=0;
		for (int j = 0; j <3; j++) {
			f=random.nextFloat();
			a=(int) Math.floor(f*10);
			ddd[j]=a;
			all+=a;
		}
		System.out.println(ddd[0]+" "+ddd[1]+" "+ddd[2]);
		fw.append(ddd[0]+" "+ddd[1]+""+ddd[2]);
		number[all]++;
		}
		int q=0;
		for (int m : number) {
			System.out.println(q+":"+Math.floor(m/10000)+" ");
			q++;
		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
