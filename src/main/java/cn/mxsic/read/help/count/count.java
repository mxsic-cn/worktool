package cn.mxsic.read.help.count;

public class count {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//(seed * 0x5DEECE66DL + 0xBL) & ((1L << 48) - 1)
	    long aa=0x5DEECE66DL;
		long bb=0xBL;
		System.out.println("aa="+aa+"\nbb="+bb);
		int a=0,b=0,c=0;
		int total=0;
		for (int i = 0; i < 10; i++) {
			a=i;
			for (int j = 0; j < 10; j++) {
					b=j;				
				for (int j2 = 0; j2 < 10; j2++) {
					c=j2;
					if((a+b+c)==11)
						{
						System.out.println("a="+a+"b="+b+"c="+c);
						total++;
						}
				}
			}
			
		}
		System.out.println("total="+total);
		
		
		/*int n=0;
		int all=0;
		for (int i = 1; i <15; i++) {
			n=((i+2)*(i-1))/2+1;//�ι�ʽû�����ֵ���ƣ����б���С����������������ӡ���
			System.out.println(i-1+":"+n);
			all+=n;
		}
		System.out.println("all="+all*2);
*/
	}

}
