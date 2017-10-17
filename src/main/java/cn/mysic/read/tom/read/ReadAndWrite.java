package cn.mysic.read.tom.read;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class ReadAndWrite {
public File filein;
public File fileout;
public File fileInOne;

	
public File getFileInOne() {
	return fileInOne;
}


public void setFileInOne(File fileInOne) {
	this.fileInOne = fileInOne;
}


public File getFilein() {
	return filein;
}


public void setFilein(File filein) {
	this.filein = filein;
}


public File getFileout() {
	return fileout;
}


public void setFileout(File fileout) {
	this.fileout = fileout;
}


public void readAndWrite(){
		
		int zuliu=0;
		int zusan=0;
		int baozi=0;
		try{
			int all=0;
			String c=null;
			int mark=0;
			int allinone=0;
		FileReader fr=new FileReader(filein);
		FileWriter fw=new FileWriter(fileout);
		FileWriter fwall=new FileWriter(fileInOne);
		BufferedReader br=new BufferedReader(fr);
		
	
			if(!fileout.exists()){
				fileout.createNewFile();
			}
			if(!fileInOne.exists()){
				fileInOne.createNewFile();
			}
			while(true){
				c=br.readLine();
				if(c==null)break;
				String[] t=c.split(" ");
				for (String string : t) {
					//System.out.println(string+"||");
					if(mark>0){
						//System.out.println(string);
						fw.write(" "+string);
						allinone=allinone+Integer.parseInt(string);
						mark--;
					}else{
						if((string.equals("����")||string.equals("����")||string.equals("����"))){
							if(string.equals("����"))zusan++;
							if(string.equals("����"))zuliu++;
							if(string.equals("����"))baozi++;
					     }else{
					    	 long a=Long.parseLong(string);
								if(a>2004000){
									fwall.write(""+allinone);
									fwall.write("\r\n"+String.valueOf(a));
									fwall.write("\r\n");
									fw.write("\r\n"+String.valueOf(a)+"\r\n");
									fw.write("\r\n");
									System.out.println(allinone);
									System.out.println(a);
									
								mark=3;
								allinone=0;
								all++;
								}	
							}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	
	
		System.out.println("����"+zusan);
		System.out.println("����"+zuliu);
		System.out.println("����"+baozi);
	}

}
