package cn.mysic.read.fit.test;


import cn.mysic.read.fit.main.Odd_Even;

import java.io.File;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
// todo auto-generated method stub
		/*ReadAndWrite rw=new ReadAndWrite();
		File filein=new File("d:/aa.txt");
		File fileout=new File("d:/bb.txt");
		File fileinone=new File("d:/all.txt");
		rw.setFileInOne(fileinone);
		rw.setFilein(filein);
		rw.setFileout(fileout);
		rw.readAndWrite();*/
		/*File fileinone=new File("d:/all.txt");
		AllForRead al=new AllForRead();
		al.setTotal(3179);
		al.allADD(fileinone);
		
		CreateAllShould cas=new CreateAllShould();
		File file3d=new File("D:/3DfromRandom.txt");
		cas.create3D(file3d);*/
		File filebefore=new File("D:/all.txt");
	/*	TheMian tm=new TheMian();
		tm.setFilebefore(filebefore);
		tm.readBefore();*/
		Odd_Even oe=new Odd_Even();
		oe.setFilebefore(filebefore);
		oe.setPre(10);
		oe.readforOddAndEven();
		oe.whatIsTheMostInNext();
	}

}
