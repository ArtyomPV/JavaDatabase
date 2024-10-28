package entity.lecture1.task1202;

import jakarta.persistence.*;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Type;

@Entity
@Table(name="salaries_table", schema = "salaries")
public class Salaries {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeID;
    @Column(name = "paymentCode")
    private Boolean paymentCode;
    @Column(name = "employeeName")
    private String employeeName;
    @Column(name = "basicSalary")
    private Double basicSalary;
    @Column(name = "bonus")
    private Double bonus;
    @Column(name = "tax")
    private Double tax;
    @Formula(value = "basicSalary + bonus - tax")
    @Column(name = "total")
    private Double total;

    public Long getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Long employeeID) {
        this.employeeID = employeeID;
    }

    public Boolean getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(Boolean paymentCode) {
        this.paymentCode = paymentCode;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Double getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(Double basicSalary) {
        this.basicSalary = basicSalary;
    }

    public Double getBonus() {
        return bonus;
    }

    public void setBonus(Double bonus) {
        this.bonus = bonus;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
