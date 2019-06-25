package cn.siqishangshu.xml.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by siqishangshu on 17/10/10.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Computer")
@XmlType(propOrder = {
        "serialNumber",
        "brandName",
        "productDate",
        "price",
})
public class Computer implements Serializable {

    private String serialNumber;
    private String brandName;
    private Date productDate;
    private double price;

    public Computer() {
        super();
    }

    public Computer(String serialNumber, String brandName, Date productDate, double price) {
        this.serialNumber = serialNumber;
        this.brandName = brandName;
        this.productDate = productDate;
        this.price = price;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Date getProductDate() {
        return productDate;
    }

    public void setProductDate(Date productDate) {
        this.productDate = productDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Computer{" +
                "serialNumber='" + serialNumber + '\'' +
                ", brandName='" + brandName + '\'' +
                ", productDate=" + productDate +
                ", price=" + price +
                '}';
    }
}
