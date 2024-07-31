package entity;

public class Product {

	//Productフィールド
	String id;      //商品番号(id)
	String name;    //商品名(name)
	
	 //価格(price)
	int price;       //DBに登録する用
	String priceStr; //画面表示用

	//種別
	int kind_id ;   //種別ID(kind_id)　　DBに登録する用
	String kind ;   //種別名(kind）　　　画面表示用
	


	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int getPrice() {  			
		return price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	public String getPriceStr() {
		return priceStr;
	}
	public void setPriceStr(String priceStr) {
		this.priceStr = priceStr;
	}
	
	public int getKind_id() {
		return kind_id;
	}
	public void setKind_id(int kind_id) {
		this.kind_id = kind_id;

	}
	
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}

}