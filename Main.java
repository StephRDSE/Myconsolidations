import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;
import java.util.TreeSet;

public class Main {

	private static String urlPath = "jdbc:postgresql://baasu.db.elephantsql.com:5432/ymtswzki";
	private static String userName = "ymtswzki";
	private static String password = "MpsP0oxQLbnOUWdUIfb19zIkPEltC5PU";

	public static void main(String[] args) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(urlPath, userName, password);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		Long pkId = (long) 1;
		listDatabaseInfos(conn, pkId);

		Random rng = new Random();
		while (true) {
			credit(conn, pkId, rng.nextInt(100) + 1);
			addToInventory(conn, pkId, rng.nextInt(10) + 1);
			sell(conn, pkId, pkId, rng.nextInt(10) + 1);
			listDatabaseInfos(conn, pkId);
		}

	}

	// function credit increase credit customer Table AND amount cash Table with
	// random number between 0 to 100

	public static void credit(Connection connect, Long pkidToCredit, int credit) {
		System.out.println("Montant crédité " + credit);

		int valueToCredit = credit;

		try {

			PreparedStatement insertCustomerCredit = connect.prepareStatement(
					"UPDATE customer SET credit = credit + " + valueToCredit + " WHERE pk_id = " + pkidToCredit + ";");
			insertCustomerCredit.execute();
			PreparedStatement insertCashCredit = connect.prepareStatement(
					"UPDATE cash SET amount = amount + " + valueToCredit + " WHERE pk_id = " + pkidToCredit + ";");
			insertCashCredit.execute();

		} catch (SQLException e1) {
			e1.printStackTrace();
		}

	}

	// function addToInventory increase qty product with random number between 0 to
	// 10

	public static void addToInventory(Connection connect, Long pkidToInvent, int inventory) {
		System.out.println("ajout à l'inventaire : " + inventory);
		int oldProdQty;

		int ProdQtyToUpdate = inventory;

		try {
			PreparedStatement retrieveProdQty = connect
					.prepareStatement("SELECT qty FROM product WHERE pk_id =" + pkidToInvent + ";");
			ResultSet resultProdQtytRetrieved = retrieveProdQty.executeQuery();

			while (resultProdQtytRetrieved.next()) {
				oldProdQty = resultProdQtytRetrieved.getInt("qty");
				int newProdQty = oldProdQty + ProdQtyToUpdate;
				PreparedStatement insertProdQty = connect.prepareStatement(
						"UPDATE product SET qty = " + newProdQty + " WHERE pk_id = " + pkidToInvent + ";");
				insertProdQty.execute();
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}

	}

	// function sell decrease credit of customer Table value price of product
	// multiply product qty random number between 0 to 10 pieces.
	// increase amount of cash Table value price of product multiply product qty
	// same random number.
	// decrease qty of product Table value same random number.
	// increase qty of sales_log Table value same random number.

	public static void sell(Connection connect, Long pkidCustomer, Long pkidLogued, int sellValue) {

		System.out.println("nb d'articles vendus : " + sellValue);

		int prodValue = 11;
		int nbSelledValue = sellValue;

		try {

			PreparedStatement insertCustomerCredit = connect.prepareStatement("UPDATE customer SET credit = credit - "
					+ (nbSelledValue * prodValue) + " WHERE pk_id = " + pkidCustomer + ";");
			PreparedStatement insertCashAmount = connect.prepareStatement("UPDATE cash SET amount = amount + "
					+ (nbSelledValue * prodValue) + " WHERE pk_id = " + pkidCustomer + ";");
			PreparedStatement insertProdQty = connect.prepareStatement(
					"UPDATE product SET qty = qty - " + nbSelledValue + " WHERE pk_id = " + pkidCustomer + ";");
			PreparedStatement insertSalesQty = connect.prepareStatement(
					"UPDATE sales_log SET qty = qty + " + nbSelledValue + " WHERE pk_id = " + pkidCustomer + ";");

			insertCustomerCredit.execute();
			insertCashAmount.execute();
			insertProdQty.execute();
			insertSalesQty.execute();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// Function listDatabaseInfos evaluate values of each Table and insert into an
	// Object DatabaseEntries

	public static void listDatabaseInfos(Connection connect, Long pkidToSearch) {
		try {
			PreparedStatement infosCustList = connect
					.prepareStatement("SELECT pk_id , credit FROM customer WHERE pk_id =" + pkidToSearch + ";");
			PreparedStatement infosCashList = connect
					.prepareStatement("SELECT amount FROM cash WHERE pk_id =" + pkidToSearch + ";");
			PreparedStatement infosSalesList = connect
					.prepareStatement("SELECT fk_product_id , qty FROM sales_log WHERE pk_id =" + pkidToSearch + ";");
			PreparedStatement infosProductList = connect
					.prepareStatement("SELECT price , qty FROM product WHERE pk_id =" + pkidToSearch + ";");

			ResultSet resultCust = infosCustList.executeQuery();
			ResultSet resultCash = infosCashList.executeQuery();
			ResultSet resultSales = infosSalesList.executeQuery();
			ResultSet resultProduct = infosProductList.executeQuery();

			ArrayList<DataBaseEntries> dataBaseList = new ArrayList<DataBaseEntries>();
			while (resultCust.next() && resultCash.next() && resultSales.next() && resultProduct.next()) {

				DataBaseEntries searchedInfos = new DataBaseEntries(resultCust.getLong("pk_id"),
						resultCust.getInt("credit"), resultCash.getInt("amount"), resultSales.getInt("fk_product_id"),
						resultSales.getInt("qty"), resultProduct.getInt("price"), resultProduct.getInt("qty"));

				dataBaseList.add(searchedInfos);

			}
			System.out.println("Voici les informations complètes des champs des tables de la base de données");
			System.out.println(dataBaseList);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
