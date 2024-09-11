package model;

public class CustomerFinancialSummary {
    private double totalCreditAmount;
    private double totalDebitAmount;
    private double netBalance;

    public CustomerFinancialSummary(double totalCreditAmount, double totalDebitAmount, double netBalance) {
        this.totalCreditAmount = totalCreditAmount;
        this.totalDebitAmount = totalDebitAmount;
        this.netBalance = netBalance;
    }

    public double getTotalCreditAmount() {
        return totalCreditAmount;
    }

    public double getTotalDebitAmount() {
        return totalDebitAmount;
    }

    public double getNetBalance() {
        return netBalance;
    }

}
