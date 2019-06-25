package cn.siqishangshu.file;

import java.io.File;

 

public class FileCheck { 
	
 

public static void main(String[] args) { 
	
	  
	 String path="D:/";
	 File file=new File(path);
	 FileCheck.goDeep(file);
//	  File file=new File(path);
//	  File[] tempList = file.listFiles();
	  
//	  for(int i=0;i<tempList.length;i++){
//		  System.out.println(tempList[i]);
//	  }
//	  System.out.println("��Ŀ¼�¶��������"+tempList.length);
//	  for (int i = 0; i < tempList.length; i++) {
//	   if (tempList[i].isFile()) {
//	    System.out.println("��     ����"+tempList[i]);
//	   }
//	   if (tempList[i].isDirectory()) {
//	    System.out.println("�ļ��У�"+tempList[i]);
//	   }
//	  }

}


public static void goDeep(File file){
	  File[] tempList = file.listFiles();
	  System.out.println("��Ŀ¼�¶��������"+tempList.length);
	  for (int i = 0; i < tempList.length; i++) {
	   if (tempList[i].isFile()) {
	    System.out.println("��     ����"+tempList[i]);
	   }
	   if (tempList[i].isDirectory()) {
		   goDeep(tempList[i]);
	    System.out.println("�ļ��У�"+tempList[i]);
	   }
	  }
}

} 
