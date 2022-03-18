package org.lsmr.software;

import java.util.HashMap;
import java.util.Map;

import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.products.BarcodedProduct;

/**
 * Contains a database of products.
 */
public class ProductDatabase {
	public static ProductDatabase Instance = new ProductDatabase();
	
	private Map<Barcode, BarcodedProduct> barcodedProducts;
	
	// Currently a singleton, so we shouldn't be able to create any instances of it
	private ProductDatabase() {
		barcodedProducts = new HashMap<>();
	}

	/**
	 * Registers the given barcoded products into the database.
	 * 
	 * @param	products	Array of BarcodedProduct's to register into the database.
	 */
	public void RegisterProducts(BarcodedProduct...products) {
		for(BarcodedProduct product : products) {
			barcodedProducts.putIfAbsent(product.getBarcode(), product);
		}
	}
	
	/**
	 * Looks up a product with the given barcode and returns it.
	 * 
	 *  @param 	barcode Barcode of the product to look up.
	 *  @return			Product that matches the specified barcode or null if the not in the database.
	 */
	public BarcodedProduct LookupItemViaBarcode(Barcode barcode) {
		// Handle case where the barcode is invalid/doesn't map to a product?
		return barcodedProducts.get(barcode);
	}
	
	/**
	 * Returns the number of products in the database.
	 * 
	 * @return	Number of products registered in the database.
	 */
	public int getNumberOfProducts() {
		return barcodedProducts.size();
	}
	
	/**
	 * Clears the database of all products
	 */
	public void clearDatabase() {
		barcodedProducts.clear();
	}
}
