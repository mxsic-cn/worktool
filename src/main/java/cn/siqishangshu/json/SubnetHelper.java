package cn.siqishangshu.json;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by lxu on 1/19/15.
 */
public class SubnetHelper implements Comparable<SubnetHelper>  {
    int baseIPnumeric;
    int netmaskNumeric;
    public boolean isVoid = true;
    public String origin  = "";

    public int compareTo(SubnetHelper anotherSubnet)
    {
        if (this.getCIDR().equals(anotherSubnet.getCIDR()))
            return 0;
        else if(this.baseIPnumeric != anotherSubnet.baseIPnumeric)
            return this.baseIPnumeric - anotherSubnet.baseIPnumeric;
        else
            return this.netmaskNumeric - anotherSubnet.netmaskNumeric;
    }


    public SubnetHelper(String symbolicIP, String netmask) {
        /* IP */
        String[] st = symbolicIP.split("\\.");

        if (st.length != 4) this.isVoid = false;
//            throw new NumberFormatException("Invalid IP address: " + symbolicIP);
        int i = 24;
        baseIPnumeric = 0;

        if (this.isVoid) {
            for (int n = 0; n < st.length; n++) {
                int value = Integer.parseInt(st[n]);
                if (value != (value & 0xff)) {
                    this.isVoid = false;
                    break;
//                throw new NumberFormatException("Invalid IP address: "+ symbolicIP);
                }

                baseIPnumeric += value << i;
                i -= 8;
            }
        }

        /* Netmask */
        st = netmask.split("\\.");

        if (st.length != 4) this.isVoid = false;
//            throw new NumberFormatException("Invalid netmask address: " + netmask);

        i = 24;
        netmaskNumeric = 0;

        if (Integer.parseInt(st[0]) < 255) {
            this.isVoid = false;
//            throw new NumberFormatException("The first byte of netmask can not be less than 255");
        }
        if (this.isVoid) {
            for (int n = 0; n < st.length; n++) {

                int value = Integer.parseInt(st[n]);

                if (value != (value & 0xff)) {
                    this.isVoid = false;
                    break;
//                throw new NumberFormatException("Invalid netmask address: "  + netmask);
                }

                netmaskNumeric += value << i;
                i -= 8;

            }
        }
/*
* see if there are zeroes inside netmask, like: 1111111101111 This is
* illegal, throw exception if encountered. Netmask should always have
* only ones, then only zeroes, like: 11111111110000
*/
        boolean encounteredOne = false;
        int ourMaskBitPattern = 1;

        if (this.isVoid) {
            for (i = 0; i < 32; i++) {

                if ((netmaskNumeric & ourMaskBitPattern) != 0) {

                    encounteredOne = true; // the bit is 1
                } else { // the bit is 0
                    if (encounteredOne) {
                        this.isVoid = false;
                        break;
//                    throw new NumberFormatException("Invalid netmask: " + netmask + " (bit " + (i + 1) + ")");
                    }
                }

                ourMaskBitPattern = ourMaskBitPattern << 1;
            }
        }
    }

    /**
     * Specify IP in CIDR format like: new IPv4("10.1.0.25/16");
     *
     * @param IPinCIDRFormat
     */
    public SubnetHelper(String IPinCIDRFormat) throws NumberFormatException {
        this.origin = IPinCIDRFormat;
        String[] st = IPinCIDRFormat.split("\\/");
        if (st.length != 2) this.isVoid = false;
//            throw new NumberFormatException("Invalid CIDR format '"
//                    + IPinCIDRFormat + "', should be: xx.xx.xx.xx/xx");
        if (this.isVoid) {
            String symbolicIP = st[0];
            String symbolicCIDR = st[1];
            Integer numericCIDR = 0;
            try {
                numericCIDR = new Integer(symbolicCIDR);
            } catch (Exception e) {
                this.isVoid = false;
            }
            if (numericCIDR > 32) this.isVoid = false;
//            throw new NumberFormatException("CIDR can not be greater than 32");

        /* IP */
            st = symbolicIP.split("\\.");

            if (st.length != 4) this.isVoid = false;
//            throw new NumberFormatException("Invalid IP address: " + symbolicIP);

            int i = 24;
            baseIPnumeric = 0;

            for (int n = 0; n < st.length; n++) {
                int value = Integer.parseInt(st[n]);

                if (value != (value & 0xff)) {
                    this.isVoid = false;
                    break;
//                throw new NumberFormatException("Invalid IP address: " + symbolicIP);
                }
                baseIPnumeric += value << i;
                i -= 8;
            }
        /* netmask from CIDR */
            if (numericCIDR < 8) this.isVoid = false;
//            throw new NumberFormatException("Netmask CIDR can not be less than java8");
            netmaskNumeric = 0xffffffff;
            netmaskNumeric = netmaskNumeric << (32 - numericCIDR);
        }
    }

    /**
     * Get the IP in symbolic form, i.e. xxx.xxx.xxx.xxx
     *
     * @return
     */
    public String getIP() {
        return convertNumericIpToSymbolic(baseIPnumeric);

    }

    private String convertNumericIpToSymbolic(Integer ip) {
        StringBuffer sb = new StringBuffer(15);

        for (int shift = 24; shift > 0; shift -= 8) {

            // process 3 bytes, from high order byte down.
            sb.append(Integer.toString((ip >>> shift) & 0xff));
            sb.append('.');
        }
        sb.append(Integer.toString(ip & 0xff));
        return sb.toString();
    }

    /**
     * Get the net mask in symbolic form, i.e. xxx.xxx.xxx.xxx
     *
     * @return
     */

    public String getNetmask() {
        StringBuffer sb = new StringBuffer(15);
        for (int shift = 24; shift > 0; shift -= 8) {
            // process 3 bytes, from high order byte down.
            sb.append(Integer.toString((netmaskNumeric >>> shift) & 0xff));
            sb.append('.');
        }
        sb.append(Integer.toString(netmaskNumeric & 0xff));

        return sb.toString();
    }

    /**
     * Get the IP and netmask in CIDR form, i.e. xxx.xxx.xxx.xxx/xx
     *
     * @return
     */

    public String getCIDR() {
        int i;
        for (i = 0; i < 32; i++) {
            if ((netmaskNumeric << i) == 0)
                break;
        }
        return convertNumericIpToSymbolic(baseIPnumeric & netmaskNumeric) + "/" + i;
    }

    /**
     * Get an arry of all the IP addresses available for the IP and netmask/CIDR
     * given at initialization
     *
     * @return
     */
    public List<String> getAvailableIPs(Integer numberofIPs) {

        List<String> result = new ArrayList<String>();
        int numberOfBits;

        for (numberOfBits = 0; numberOfBits < 32; numberOfBits++) {

            if ((netmaskNumeric << numberOfBits) == 0)
                break;

        }
        Integer numberOfIPs = 0;
        for (int n = 0; n < (32 - numberOfBits); n++) {

            numberOfIPs = numberOfIPs << 1;
            numberOfIPs = numberOfIPs | 0x01;

        }

        Integer baseIP = baseIPnumeric & netmaskNumeric;

        for (int i = 1; i < (numberOfIPs) && i < numberofIPs; i++) {

            Integer ourIP = baseIP + i;

            String ip = convertNumericIpToSymbolic(ourIP);

            result.add(ip);
        }
        return result;
    }

    /**
     * Range of hosts
     *
     * @return
     */
    public String getHostAddressRange() {

        int numberOfBits;
        for (numberOfBits = 0; numberOfBits < 32; numberOfBits++) {

            if ((netmaskNumeric << numberOfBits) == 0)
                break;
        }
        Integer numberOfIPs = 0;
        for (int n = 0; n < (32 - numberOfBits); n++) {

            numberOfIPs = numberOfIPs << 1;
            numberOfIPs = numberOfIPs | 0x01;

        }

        Integer baseIP = baseIPnumeric & netmaskNumeric;
        String firstIP = convertNumericIpToSymbolic(baseIP + 1);
        String lastIP = convertNumericIpToSymbolic(baseIP + numberOfIPs - 1);
        return firstIP + " - " + lastIP;
    }

    public Map<String, String> getAllAddresses() {

        int numberOfBits;
        for (numberOfBits = 0; numberOfBits < 32; numberOfBits++) {

            if ((netmaskNumeric << numberOfBits) == 0)
                break;
        }
        Integer numberOfIPs = 0;
        for (int n = 0; n < (32 - numberOfBits); n++) {

            numberOfIPs = numberOfIPs << 1;
            numberOfIPs = numberOfIPs | 0x01;

        }

        Integer baseIP = baseIPnumeric & netmaskNumeric;
        return IntStream.range(baseIP, baseIP + numberOfIPs)
                .mapToObj(this::convertNumericIpToSymbolic)
                .collect(Collectors.toMap(e -> e, e -> origin));

    }


    List<Integer> getHostAddressRangeInt() {
        List<Integer> result = new ArrayList<>();
        int numberOfBits;
        for (numberOfBits = 0; numberOfBits < 32; numberOfBits++) {

            if ((netmaskNumeric << numberOfBits) == 0)
                break;
        }
        Integer numberOfIPs = 0;
        for (int n = 0; n < (32 - numberOfBits); n++) {

            numberOfIPs = numberOfIPs << 1;
            numberOfIPs = numberOfIPs | 0x01;

        }

        Integer baseIP = baseIPnumeric & netmaskNumeric;
        result.add(baseIP + 1);
        result.add(baseIP + numberOfIPs - 1);
        return result;
    }

    public boolean checkOverlap(SubnetHelper that) {
        if (this.isVoid && that.isVoid) {
            List<Integer> thatResult = that.getHostAddressRangeInt();
            List<Integer> thisResult = this.getHostAddressRangeInt();
            if (thisResult.get(0) >= thatResult.get(0) && thisResult.get(1) <= thatResult.get(1)) return true;
            if (thisResult.get(0) <= thatResult.get(0) && thisResult.get(1) >= thatResult.get(1)) return true;
            if (thisResult.get(0) >= thatResult.get(0) && thisResult.get(0) <= thatResult.get(1)) return true;
            if (thatResult.get(0) >= thisResult.get(0) && thatResult.get(0) <= thisResult.get(1)) return true;
            return false;
        } else {
            return true;
        }
    }

    /**
     * Returns number of hosts available in given range
     *
     * @return number of hosts
     */
    public Long getNumberOfHosts() {
        int numberOfBits;

        for (numberOfBits = 0; numberOfBits < 32; numberOfBits++) {

            if ((netmaskNumeric << numberOfBits) == 0)
                break;

        }

        Double x = Math.pow(2, (32 - numberOfBits));

        if (x == -1)
            x = 1D;

        return x.longValue();
    }

    /**
     * The XOR of the netmask
     *
     * @return wildcard mask in text form, i.e. 0.0.15.255
     */

    public String getWildcardMask() {
        Integer wildcardMask = netmaskNumeric ^ 0xffffffff;

        StringBuffer sb = new StringBuffer(15);
        for (int shift = 24; shift > 0; shift -= 8) {

            // process 3 bytes, from high order byte down.
            sb.append(Integer.toString((wildcardMask >>> shift) & 0xff));

            sb.append('.');
        }
        sb.append(Integer.toString(wildcardMask & 0xff));

        return sb.toString();

    }

    public String getBroadcastAddress() {

        if (netmaskNumeric == 0xffffffff)
            return "0.0.0.0";

        int numberOfBits;
        for (numberOfBits = 0; numberOfBits < 32; numberOfBits++) {

            if ((netmaskNumeric << numberOfBits) == 0)
                break;

        }
        Integer numberOfIPs = 0;
        for (int n = 0; n < (32 - numberOfBits); n++) {

            numberOfIPs = numberOfIPs << 1;
            numberOfIPs = numberOfIPs | 0x01;
        }

        Integer baseIP = baseIPnumeric & netmaskNumeric;
        Integer ourIP = baseIP + numberOfIPs;

        return convertNumericIpToSymbolic(ourIP);
    }

    private String getBinary(Integer number) {
        String result = "";

        Integer ourMaskBitPattern = 1;
        for (int i = 1; i <= 32; i++) {

            if ((number & ourMaskBitPattern) != 0) {

                result = "1" + result; // the bit is 1
            } else { // the bit is 0

                result = "0" + result;
            }
            if ((i % 8) == 0 && i != 0 && i != 32)

                result = "." + result;
            ourMaskBitPattern = ourMaskBitPattern << 1;

        }
        return result;
    }

    public String getNetmaskInBinary() {

        return getBinary(netmaskNumeric);
    }

    /**
     * Checks if the given IP address contains in subnet
     *
     * @param IPaddress
     * @return
     */
    public boolean contains(String IPaddress) {

        Integer checkingIP = 0;
        String[] st = IPaddress.split("\\.");

        if (st.length != 4) {
            this.isVoid = false;
            return false;
        }
//            throw new NumberFormatException("Invalid IP address: " + IPaddress);

        int i = 24;
        for (int n = 0; n < st.length; n++) {

            int value = Integer.parseInt(st[n]);

            if (value != (value & 0xff)) {
                this.isVoid = false;
                return false;
//                throw new NumberFormatException("Invalid IP address: " + IPaddress);
            }

            checkingIP += value << i;
            i -= 8;
        }

        if ((baseIPnumeric & netmaskNumeric) == (checkingIP & netmaskNumeric))

            return true;
        else
            return false;
    }

    public boolean contains(SubnetHelper child) {

        Integer subnetID = child.baseIPnumeric;

        Integer subnetMask = child.netmaskNumeric;

        if ((subnetID & this.netmaskNumeric) == (this.baseIPnumeric & this.netmaskNumeric)) {

            if ((this.netmaskNumeric < subnetMask)
                    && this.baseIPnumeric <= subnetID) {

                return true;
            }

        }
        return false;

    }
}
