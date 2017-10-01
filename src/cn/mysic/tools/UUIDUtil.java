package com;

import java.util.UUID;

public class UUIDUtil {
    public static String getUUID() {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replace("-", "");
        return uuid;
    }
    public static void main(String[] args){
		//   Auto-generated method stub
    	int i=0;
    	do{
    		System.out.println(UUIDUtil.getUUID());
    		i++;
    	}while(i<1000);
	}
}
