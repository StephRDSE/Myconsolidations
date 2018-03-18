
public class DataBaseEntries {
	private Long customerPkId;
	private int customerCredit;
	private int cashAmount;
	private int salesId;
	private int salesQty;
	private int productPrice;
	private int productQty;
	
	public DataBaseEntries(Long customerPkId, int customerCredit, int cashAmount, int salesId ,   int salesQty , int productPrice , int productQty) {
		this.customerPkId = customerPkId;
		this.customerCredit = customerCredit;
		this.cashAmount = cashAmount;
		this.salesId = salesId;
		this.salesQty = salesQty;
		this.productPrice = productPrice;
		this.productQty = productQty;
	
	}

	public Long getCustomerPkId() {
		return customerPkId;
	}

	public void setCustomerPkId(Long customerPkId) {
		this.customerPkId = customerPkId;
	}

	public int getCustomerCredit() {
		return customerCredit;
	}

	public void setCustomerCredit(int customerCredit) {
		this.customerCredit = customerCredit;
	}

	public int getCashAmount() {
		return cashAmount;
	}

	public void setCashAmount(int cashAmount) {
		this.cashAmount = cashAmount;
	}

	public int getSalesId() {
		return salesId;
	}

	public void setSalesId(int salesId) {
		this.salesId = salesId;
	}

	public int getSalesQty() {
		return salesQty;
	}

	public void setSalesQty(int salesQty) {
		this.salesQty = salesQty;
	}

	public int getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(int productPrice) {
		this.productPrice = productPrice;
	}

	public int getProductQty() {
		return productQty;
	}

	public void setProductQty(int productQty) {
		this.productQty = productQty;
	}

	@Override
	public String toString() {
		return "DataBaseEntries [customerPkId=" + customerPkId + ", customerCredit=" + customerCredit + ", cashAmount="
				+ cashAmount + ", salesId=" + salesId + ", salesQty=" + salesQty + ", productPrice=" + productPrice
				+ ", productQty=" + productQty + "]";
	}
}