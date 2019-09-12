package cn.mxsic.performance.network;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Function: TODO: ADD FUNCTION <br>
 *
 * @author: siqishangshu <br>
 * @date: 2018-04-27 16:47:00
 */
public class NetWork {


    public static void main(String args[])

    {

        String[] addrs= {"www.baidu.com"};

        if (addrs.length < 1)

        {

            System.out.println("syntax Error!");

        }

        else

        {

            for(int i=0;i<addrs.length;i++){

                String line = null;

                try

                {

                    Process pro = Runtime.getRuntime().exec("ping " + addrs+" -l 1000 -n 4");

                    BufferedReader buf = new BufferedReader(new InputStreamReader(pro.getInputStream()));

                    while((line = buf.readLine()) != null){



                        int position=0;

                        if((position=line.indexOf("Average"))>=0)

                        {  System.out.println(line);

                            String value= line.substring(position+10,line.lastIndexOf( "ms"));

                            System.out.println("your speed is:"+(1000/Integer.parseInt(value))+"KB");

                        }

                    }

                }

                catch(Exception ex)

                {

                    System.out.println(ex.getMessage());

                }

            }

        }

    }

}
