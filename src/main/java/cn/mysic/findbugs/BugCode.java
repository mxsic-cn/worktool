package cn.mysic.findbugs;

import lombok.Data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Function: TODO: ADD FUNCTION <br>
 *
 * @author: siqishangshu <br>
 * @date: 2018-06-01 10:43:00
 */
@Data
public class BugCode extends DataUser {

    /**
     * Unread public/protected field
     * This field is never read.  The field is public or protected, so perhaps it is intended to be used with classes not seen as part of the analysis. If not, consider removing it from the class.
     *
     * @param args
     */
    private Object object = new Object();


    public static void main(String[] args) {
        System.out.println("findBug");
        BugCode bugCode = new BugCode();
        bugCode.encodingBug();
        bugCode.bitCheck();
    }

    /**
     * 1:strings using + in a loop
     * SBSC: Method concatenates strings using + in a loop (SBSC_USE_STRINGBUFFER_CONCATENATION)
     * <p>
     * The method name StringInLoop() doesn't start with a lower case letter
     */
    public void StringInLoop() {
        // This is bad
        String[] field = new String[]{"123", "123", "3212"};
        String s = "";
        for (int i = 0; i < field.length; ++i) {
            s = s + field[i];
        }
        System.out.println(s);
        // This is better
//        StringBuffer buf = new StringBuffer();
//        for (int i = 0; i < field.length; ++i) {
//            buf.append(field[i]);
//        }
//        System.out.println(buf.toString());
    }

    /**
     * 2  UrF: Unread field (URF_UNREAD_FIELD)
     * This field is never read.  Consider removing it from the class.
     * 1: @Data  覆盖问题  get  SerialNoImportFileResultDto
     * 2:无用代码
     */

    private Integer size;
    private Integer age;
    private String name;
    private List list;

    public Integer getSize() {
        return list.size();
    }

    /**
     * 3:  final  must  be  static
     * SS: Unread field: should this field be static? (SS_SHOULD_BE_STATIC)
     * This class contains an instance final field that is initialized to a compile-time static value. Consider making the field static.
     */
    private final String FINAL_STRING = "finalString";


    /**
     * 4 类型转换 与封装
     * Bx: Boxed value is unboxed and then immediately reboxed (BX_UNBOXING_IMMEDIATELY_REBOXED)
     * A boxed value is unboxed and then immediately reboxed.
     * 三元表达式  ? :
     */
    public void appenList() {
        List list = new ArrayList<>();
        DataUser dataUser = new DataUser();
        list.add(Integer.valueOf(43));
        list.add(dataUser.getAge() == null ? 0 : dataUser.getAge());
        System.out.println(list.size());
    }

    /**
     * Boxing/unboxing to parse a primitive
     * A boxed primitive is created from a String, just to extract the unboxed primitive value. It is more efficient to just call the static parseXXX method.
     */
    public void getValue() {
        String tempEnd = "4323";
        String tempStart = "423";
        System.out.println(Long.valueOf(tempEnd) - Long.valueOf(tempStart) + 1);
    }

    /**
     * SIC: Should be a static inner class (SIC_INNER_SHOULD_BE_STATIC)
     * This class is an inner class, but does not use its embedded reference to the object which created it.
     * This reference makes the instances of the class larger, and may keep the reference to the creator object alive longer than necessary.
     * If possible, the class should be made static.
     */
    public class InnerClazz {

    }

    /**
     * Dm: Reliance on default encoding (DM_DEFAULT_ENCODING)
     * Found a call to a method which will perform a byte to String (or String to byte) conversion,
     * and will assume that the default platform encoding is suitable. This will cause the application behaviour to vary between platforms.
     * Use an alternative API and specify a charset name or Charset object explicitly.
     */
    public void encodingBug() {
        byte[] bytes = "findbug".getBytes();
        String ss = new String(bytes);
        ss.toUpperCase(Locale.CHINA);
    }

    /**
     * Code contains a hard coded reference to an absolute pathname
     * This code constructs a File object using a hard coded to an absolute
     * pathname (e.g., new File("/home/dannyc/workspace/j2ee/src/share/com/sun/enterprise/deployment");
     */
    /**
     * 此方法可能无法清理（关闭，处置）流，数据库对象或其他需要显式清理操作的资源。
      通常，如果一个方法打开一个流或其他资源，该方法应该使用try / finally块来确保在方法返回之前清理流或资源。
     * @throws FileNotFoundException
     */
    public void pathName() throws FileNotFoundException {
        String filePath = "~/temp/txt.txt";
        File file = new File(filePath);

            FileReader fileReader = new FileReader(file);

    }

    /**
     * Result of integer multiplication cast to long
     * This code performs integer multiply and then converts the result to a long, as in:
     * long convertDaysToMilliseconds(int days) { return 1000*3600*24*days; }
     * If the multiplication is done using long arithmetic, you can avoid the possibility that the result will overflow.
     * For example, you could fix the above code to:
     * long convertDaysToMilliseconds(int days) { return 1000L*3600*24*days; }
     * or
     * static final long MILLISECONDS_PER_DAY = 24L*3600*1000;
     * long convertDaysToMilliseconds(int days) { return days * MILLISECONDS_PER_DAY; }
     */
    public void cast() {
        int oneHour = 60 * 60;
        Date expireTimeStamp = new Date(System.currentTimeMillis() + (oneHour * 1000));
        System.out.println(expireTimeStamp);
        Integer i = new Integer(123);
        if (i == 123) {

        } else {

        }
        /**
         * Load of known null value
         The variable referenced at this point is known to be null due to an earlier check against null.
         Although this is valid, it might be a mistake (perhaps you intended to refer to a different variable,
         or perhaps the earlier check to see if the variable is null should have been a check to see if it was non-null).
         */
        if (expireTimeStamp == null) {
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /**
         * IM: Check for oddness that won't work for negative numbers (IM_BAD_CHECK_FOR_ODD)
         The code uses x % 2 == 1 to check to see if a value is odd, but this won't work for negative numbers (e.g., (-5) % 2 == -1).
         If this code is intending to check for oddness, consider using x & 1 == 1, or x % 2 != 0.
         */
        if (i % 2 == 1) {   //奇数
        } else {//偶数
        }
    }

    /**
     * Write to static field from instance method
     * This instance method writes to a static field.
     * This is tricky to get correct if multiple instances are being manipulated, and generally bad practice.
     */
    private static String staticString = "staticString";

    public String setStaticValue(String newString) {
        staticString = newString;
        return "newString";
    }

    /**
     * Dead store to local variable
     * This instruction assigns a value to a local variable, but the value is not read or used in any subsequent instruction. Often, this indicates an error, because the value computed is never used.
     * Note that Sun's javac compiler often generates dead stores for final local variables. Because FindBugs is a bytecode-based tool, there is no easy way to eliminate these false positives.
     */
    public void deadVariable() {
        String s = new String("new");
        s = this.setStaticValue("newString");
        System.out.println(s);
    }

    /**
     * Redundant nullcheck of value known to be non-null
     * This method contains a redundant check of a known non-null value against the constant null.
     */
    public void nullCheck() {
        String s = setStaticValue("newString");
        String news = s == null ? "" : s.toUpperCase();
    }

    /**
     * Exception is caught when Exception is not thrown
     This method uses a try-catch block that catches Exception objects, but Exception is not thrown within the try block, and RuntimeException is not explicitly caught. It is a common bug pattern to say try { ... } catch (Exception e) { something } as a shorthand for catching a number of types of exception each of whose catch blocks is identical, but this construct also accidentally catches RuntimeException as well, masking potential bugs.
     A better approach is to either explicitly catch the specific exceptions that are thrown, or to explicitly catch RuntimeException exception, rethrow it, and then catch all non-Runtime Exceptions, as shown below:
     try {
     ...
     } catch (RuntimeException e) {
     throw e;
     } catch (Exception e) {
     ... deal with all non-runtime exceptions ...
     }
     */

    public void tryCatch(){
        try {
            this.pathName();
            TimeUnit.SECONDS.sleep(10);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Method with Boolean return type returns explicit null
     A method that returns either Boolean.TRUE, Boolean.FALSE or null is an accident waiting to happen. This method can be invoked as though it returned a value of type boolean, and the compiler will insert automatic unboxing of the Boolean value. If a null value is returned, this will result in a NullPointerException.
     */
    public Boolean returnNull(){
        return null;
    }

    /**
     * Field isn't final but should be
     This static field public but not final, and could be changed by malicious code or by accident from another package. The field could be made final to avoid this vulnerability.
     */
    public static List staticList;
    static {
        staticList = new ArrayList();
        staticList.add("1");
    }
    /**
     * Field is a mutable array
     A final static field references an array and can be accessed by malicious code or by accident from another package. This code can freely modify the contents of the array.
     */
    public static final char[] CHARACTER_INTEGER_ARRAY = {
            '0', '1', '2', '3', '4',
            '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E',
            'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O',
            'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y',
            'Z'
    };

    /**
     * Field is a mutable collection
     A mutable collection instance is assigned to a final static field, thus can be changed by malicious code or by accident from another package. Consider wrapping this field into Collections.unmodifiableSet/List/Map/etc. to avoid this vulnerability.
     */
    public static final Map<Character, Integer> CHARACTER_INTEGER_MAP = new HashMap() {
        {
            int i = 0;
            for (char c : CHARACTER_INTEGER_ARRAY) {
                put(c, i);
                i++;
            }
        }
    };

    /**
     * Nullcheck of value previously dereferenced
     A value is checked here to see whether it is null, but this value can't be null because it
     was previously dereferenced and if it were null a null pointer exception would have occurred at
     the earlier dereference. Essentially, this code and the previous dereference disagree as to whether this value
     is allowed to be null. Either the check is redundant or the previous dereference is erroneous.
     */

    /**
     * Possible null pointer dereference
     There is a branch of statement that, if executed, guarantees that a null value will be dereferenced,
     which would generate a NullPointerException when the code is executed. Of course,
     the problem might be that the branch or statement is infeasible and that the null pointer exception can't ever
     be executed; deciding that is beyond the ability of FindBugs.
     */
    public void checkNull(){
        DataUser dataUser = this.getDataUser();
        String s = setStaticValue(dataUser.getName());
        if ((s == null)) {
            System.out.println("s is null");

        }
    }

    private DataUser getDataUser() {
        double d = Math.random()*10;
        if(d>5){
            return null;
        }
        return new DataUser();
    }
    public void bitCheck(){
        if((23!=23)&&(100/0==0)){
            System.out.println("运算没有问题。");
        }else{
            System.out.println("没有报错");
        }


    }
}
