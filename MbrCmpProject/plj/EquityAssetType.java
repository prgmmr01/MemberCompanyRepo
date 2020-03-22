package com.MbrCmpProject.plj;

public class EquityAssetType {

	private int Year;
	private float Amount;
	private String Close;
	private int Slot;

	public int getSlot() {
		return Slot;
	}

	public void setSlot(int slot) {
		Slot = slot;
	}

	// methods...
	public int getYear() {
		return this.Year;
	}

	public void setYear(int year) {
		this.Year = year;
	}

	public float getAmount() {
		return this.Amount;
	}

	public void setAmount(float amount) {
		this.Amount = amount;
	}

	public String getClose() {
		return this.Close;
	}

	public void setClose(String close) {
		if (close.isEmpty()) {
			this.Close = "N";
		} else {
			this.Close = close;

		}
	}

}
