package com.BC.entertainmentgravitation.json;

public class The_prize_list {
	private String The_prize_images;

	private String The_prize_amount;

	private String The_prize_described;

	private String Chance_of;

	private String The_prize_ID;

	public void setThe_prize_images(String The_prize_images) {
		this.The_prize_images = The_prize_images;
	}

	public String getThe_prize_images() {
		return this.The_prize_images;
	}

	public void setThe_prize_amount(String The_prize_amount) {
		this.The_prize_amount = The_prize_amount;
	}

	public String getThe_prize_amount() {
		return this.The_prize_amount;
	}

	public void setThe_prize_described(String The_prize_described) {
		this.The_prize_described = The_prize_described;
	}

	public String getThe_prize_described() {
		return this.The_prize_described;
	}

	public void setChance_of(String Chance_of) {
		this.Chance_of = Chance_of;
	}

	public String getChance_of() {
		return this.Chance_of;
	}

	public void setThe_prize_ID(String The_prize_ID) {
		this.The_prize_ID = The_prize_ID;
	}

	public String getThe_prize_ID() {
		return this.The_prize_ID;
	}

	@Override
	public String toString() {
		return "The_prize_list [The_prize_images=" + The_prize_images
				+ ", The_prize_amount=" + The_prize_amount
				+ ", The_prize_described=" + The_prize_described
				+ ", Chance_of=" + Chance_of + ", The_prize_ID=" + The_prize_ID
				+ "]";
	}
	
}
