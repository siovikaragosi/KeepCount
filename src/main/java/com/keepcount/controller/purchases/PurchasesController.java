package com.keepcount.controller.purchases;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.keepcount.controller.business.choose.ChooseBusinessController;
import com.keepcount.model.business.create.EmailsOfABusinessLogin;
import com.keepcount.model.purchases.Purchases;
import com.keepcount.service.purchases.PurchasesService;

@Controller
public class PurchasesController {

	private int changeMade = 0;

	@Autowired
	private PurchasesService purchasesService;

	@RequestMapping(value = "/business/purchases/{businessName}/{emailOfUser}")
	public String showPurchasesPage(Model model, @PathVariable(name = "businessName") String businessName, @PathVariable(name = "emailOfUser") String emailOfUser) {
		EmailsOfABusinessLogin emailsOfABusiness = new EmailsOfABusinessLogin();
		emailOfUser = ChooseBusinessController.getEmailLoggedIn();
		businessName = ChooseBusinessController.getNameOfTheBusiness();
		model.addAttribute("emailOfUser", emailOfUser);
		model.addAttribute("businessName", businessName);
		model.addAttribute("emailsOfABusinessForm", emailsOfABusiness);
		// List<Item> list = new ArrayList<>();
		// String businessId = ChooseBusinessController.getIdOfBusiness().toString();
		// list = purchasesService.findAllItemsWithRefreshing(businessId);
		// model.addAttribute("items", list);
		setChangeMade(1);
		return "/keepCount/business/purchase/purchases";
	}

	@RequestMapping(value = "/api/business/purchases/{businessName}/{emailOfUser}", method = RequestMethod.POST)
	@ResponseBody
	public String createNewItem(HttpServletRequest request, HttpServletResponse response, @PathVariable(name = "businessName") String businessName, @PathVariable(name = "emailOfUser") String emailOfUser,
			@RequestBody Purchases purchase) {
		emailOfUser = ChooseBusinessController.getEmailLoggedIn();
		businessName = ChooseBusinessController.getNameOfTheBusiness();
		String businessId = ChooseBusinessController.getIdOfBusiness().toString();
		String subAlone = getPurchasesItemSubCategory(purchase);
		purchase.setItemSubCategory(subAlone);
		// get the item name only
		getPurchasesItemOnly(purchase);
		// get the item sub category only
		Purchases purchasesUnPunctuated = getUnPunctuatedPurchases(purchase);
		int result = purchasesService.newPurchase(businessId, purchasesUnPunctuated);
		if (result == 0) {
			setChangeMade(1);
		}
		return "/api/business/purchases/{businessName}/{emailOfUser}";
	}

	private String removeCommaThousandSeparatorFromANumber(String punctuatedNumber) {
		String unPunctuatedNo = null;
		if (punctuatedNumber != null || punctuatedNumber != "0") {
			if (punctuatedNumber.contains(",")) {
				unPunctuatedNo = punctuatedNumber.replaceAll(",", "");
			} else {
				unPunctuatedNo = punctuatedNumber;
			}
		}
		return unPunctuatedNo;
	}

	private String removeSpaceThousandSeparatorFromANumber(String punctuatedNumber) {
		String unPunctuatedNo = null;
		if (punctuatedNumber != null || punctuatedNumber != "0") {
			if (punctuatedNumber.contains(" ")) {
				unPunctuatedNo = punctuatedNumber.replaceAll(" ", "");
			} else {
				unPunctuatedNo = punctuatedNumber;
			}
		}
		return unPunctuatedNo;
	}

	private String unSpecifiedNumberDefault(String punctuatedNumber) {
		String unPunctuatedNo = null;
		if (punctuatedNumber.equals("") || punctuatedNumber.equals(null)) {
			punctuatedNumber = "0";
			unPunctuatedNo = punctuatedNumber;
		} else {
			unPunctuatedNo = punctuatedNumber;
		}
		return unPunctuatedNo;
	}

	private Purchases getUnPunctuatedPurchases(Purchases purchasesPunctuated) {
		Purchases purchases = new Purchases();
		purchases.setItem(purchasesPunctuated.getItem());
		purchases.setItemSubCategory(purchasesPunctuated.getItemSubCategory());
		purchases.setClearedBy(purchasesPunctuated.getClearedBy());
		purchases.setSupplierName(purchasesPunctuated.getSupplierName());
		purchases.setPaymentMethod(purchasesPunctuated.getPaymentMethod());

		String qtrCommaStr = removeCommaThousandSeparatorFromANumber(purchasesPunctuated.getItemQuantityStr());
		String qtrSpaceStr = removeSpaceThousandSeparatorFromANumber(qtrCommaStr);
		String qtrDefaultStr = unSpecifiedNumberDefault(qtrSpaceStr);
		purchases.setItemQuantity(new BigDecimal(qtrDefaultStr));

		String pricePerUnitCommaStr = removeCommaThousandSeparatorFromANumber(purchasesPunctuated.getItemPricePerUnitStr());
		String pricePerUnitSpaceStr = removeSpaceThousandSeparatorFromANumber(pricePerUnitCommaStr);
		purchases.setItemPricePerUnit(new BigDecimal(pricePerUnitSpaceStr));

		String totalManualCostCommaStr = removeCommaThousandSeparatorFromANumber(purchasesPunctuated.getItemTotalManualCostStr());
		String totalManualCostSpaceStr = removeSpaceThousandSeparatorFromANumber(totalManualCostCommaStr);
		purchases.setItemTotalManualCost(new BigDecimal(totalManualCostSpaceStr));

		String amountPaidCommaStr = removeCommaThousandSeparatorFromANumber(purchasesPunctuated.getAmountPaidStr());
		String amountPaidSpaceStr = removeSpaceThousandSeparatorFromANumber(amountPaidCommaStr);
		String amountPaidDefaultStr = unSpecifiedNumberDefault(amountPaidSpaceStr);
		purchases.setAmountPaid(new BigDecimal(amountPaidDefaultStr));

		String installmentPaidCommaStr = removeCommaThousandSeparatorFromANumber(purchasesPunctuated.getInstallmentPaidStr());
		String installmentPaidSpaceStr = removeSpaceThousandSeparatorFromANumber(installmentPaidCommaStr);
		String installmentPaidDefaultStr = unSpecifiedNumberDefault(installmentPaidSpaceStr);
		purchases.setInstallmentPaid(new BigDecimal(installmentPaidDefaultStr));

		String installmentBalanceCommaStr = removeCommaThousandSeparatorFromANumber(purchasesPunctuated.getInstallmentBalanceStr());
		String installmentBalanceSpaceStr = removeSpaceThousandSeparatorFromANumber(installmentBalanceCommaStr);
		String installmentBalanceDefaultStr = unSpecifiedNumberDefault(installmentBalanceSpaceStr);
		purchases.setInstallmentBalance(new BigDecimal(installmentBalanceDefaultStr));

		purchases.setItemTotalAutomaticCost(purchases.getItemPricePerUnit().multiply(purchases.getItemQuantity()));

		return purchases;
	}

	private String getPurchasesItemOnly(Purchases purchasesPunctuated) {
		String item = null;
		String itemName = purchasesPunctuated.getItem();
		if (itemName.contains(" -- (")) {
			int placeOfFirstHyphen = purchasesPunctuated.getItem().indexOf(" -- (");
			item = itemName.substring(0, placeOfFirstHyphen);
			purchasesPunctuated.setItem(item);
		}
		return purchasesPunctuated.getItem();
	}

	private static String getPurchasesItemSubCategory(Purchases purchases) {
		String itemName = purchases.getItem();
		String subCategory = null;
		if (itemName.contains(" -- (")) {
			int placeOfFirstHyphen = purchases.getItem().indexOf(" -- (");
			String subCat = itemName.substring(placeOfFirstHyphen);
			String subCat2 = subCat.replace("(", "").replace("--", "").replace(")", "");
			String space = subCat2.substring(0, 1);
			String spaceFirst = null;
			while (space.equals(" ")) {
				spaceFirst = subCat2.replace(space, "");
				space = spaceFirst.substring(0, 1);
				subCategory = spaceFirst;
				purchases.setItemSubCategory(subCategory);
			}
		}
		return purchases.getItemSubCategory();
	}

	public static void main(String[] args) {
		Purchases purchases = new Purchases();
		purchases.setItem("1 -- (3)");
		String sub = getPurchasesItemSubCategory(purchases);
		System.out.println(sub);
	}

	@RequestMapping(value = "/api/business/purchases/items/{businessName}/{emailOfUser}/allItems", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String findAllItems(Model model, @PathVariable(name = "businessName") String businessName, @PathVariable(name = "emailOfUser") String emailOfUser) {

		EmailsOfABusinessLogin emailsOfABusiness = new EmailsOfABusinessLogin();
		emailOfUser = ChooseBusinessController.getEmailLoggedIn();
		businessName = ChooseBusinessController.getNameOfTheBusiness();
		// String emailOfTheBusiness =
		// ChooseBusinessController.getEmailOfThisBusiness();
		model.addAttribute("emailsOfABusinessForm", emailsOfABusiness);
		model.addAttribute("emailOfUser", emailOfUser);
		model.addAttribute("businessName", businessName);
		String businessId = ChooseBusinessController.getIdOfBusiness().toString();

		String allItems = null;
		if (getChangeMade() == 0) {
			System.out.println("not refreshed items");
			allItems = new Gson().toJson(purchasesService.findAllItemsWithoutRefreshing());
			return allItems;
		} else {
			System.out.println("refreshed items");
			allItems = new Gson().toJson(purchasesService.findAllItemsWithRefreshing(businessId));
			return allItems;
		}
	}

	@RequestMapping(value = "/api/business/purchases/suppliers/{businessName}/{emailOfUser}/allSuppliers", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String findAllSuppliers(Model model, @PathVariable(name = "businessName") String businessName, @PathVariable(name = "emailOfUser") String emailOfUser) {

		EmailsOfABusinessLogin emailsOfABusiness = new EmailsOfABusinessLogin();
		emailOfUser = ChooseBusinessController.getEmailLoggedIn();
		businessName = ChooseBusinessController.getNameOfTheBusiness();
		// String emailOfTheBusiness =
		// ChooseBusinessController.getEmailOfThisBusiness();
		model.addAttribute("emailsOfABusinessForm", emailsOfABusiness);
		model.addAttribute("emailOfUser", emailOfUser);
		model.addAttribute("businessName", businessName);
		String businessId = ChooseBusinessController.getIdOfBusiness().toString();

		if (getChangeMade() != 1) {
			return new Gson().toJson(purchasesService.findAllSuppliersNotDirectly(businessId));
		} else {
			return new Gson().toJson(purchasesService.findAllSuppliersDirect(businessId));
		}
	}

	@RequestMapping(value = "/api/business/purchases/{businessName}/{emailOfUser}/allPurchases", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String findAllPurchases(Model model, @PathVariable(name = "businessName") String businessName, @PathVariable(name = "emailOfUser") String emailOfUser,
			@RequestParam("numberFormat") String numberFormat) {

		EmailsOfABusinessLogin emailsOfABusiness = new EmailsOfABusinessLogin();
		emailOfUser = ChooseBusinessController.getEmailLoggedIn();
		businessName = ChooseBusinessController.getNameOfTheBusiness();
		String businessId = ChooseBusinessController.getIdOfBusiness().toString();

		model.addAttribute("emailsOfABusinessForm", emailsOfABusiness);
		model.addAttribute("emailOfUser", emailOfUser);
		model.addAttribute("businessName", businessName);

		if (getChangeMade() != 1) {
			return new Gson().toJson(purchasesService.findAllPurchasesNotDirect(businessId, numberFormat));

		} else {
			return new Gson().toJson(purchasesService.findAllPurchasesDirect(businessId, numberFormat));

		}

	}

	@RequestMapping(value = "/api/business/purchases/{businessName}/{emailOfUser}/{id}/delete", method = RequestMethod.DELETE)
	@ResponseBody
	public String deleteFromPurchases(HttpServletRequest request, HttpServletResponse response, @PathVariable(name = "businessName") String businessName,
			@PathVariable(name = "emailOfUser") String emailOfUser, @PathVariable(name = "id") BigDecimal id) {
		new ChooseBusinessController();
		emailOfUser = ChooseBusinessController.getEmailLoggedIn();
		new ChooseBusinessController();
		businessName = ChooseBusinessController.getNameOfTheBusiness();
		new ChooseBusinessController();
		String businessId = ChooseBusinessController.getIdOfBusiness().toString();

		System.out.println("id: " + id);

		int result = purchasesService.deleteFromPurchases(businessId, id);

		if (result >= 1) {
			System.out.println("deleted successfully");
			setChangeMade(1);
		} else {
			System.out.println("something went wrong");
		}
		return "/api/business/purchases/{businessName}/{emailOfUser}/{id}/delete";
	}

	@RequestMapping(value = "/api/business/purchases/{businessName}/{emailOfUser}/{id}/update", method = RequestMethod.PUT)
	@ResponseBody
	public String updatePurchase(HttpServletRequest request, HttpServletResponse response, @PathVariable(name = "businessName") String businessName, @PathVariable(name = "emailOfUser") String emailOfUser,
			@PathVariable(name = "id") BigDecimal id, @RequestBody Purchases purchases) {
		emailOfUser = ChooseBusinessController.getEmailLoggedIn();
		businessName = ChooseBusinessController.getNameOfTheBusiness();
		String businessId = ChooseBusinessController.getIdOfBusiness().toString();

		String subAlone = getPurchasesItemSubCategory(purchases);
		purchases.setItemSubCategory(subAlone);
		// get the item name only
		getPurchasesItemOnly(purchases);
		// get the item sub category only
		Purchases purchasesUnPunctuated = getUnPunctuatedPurchases(purchases);
		int result = purchasesService.updatePurchase(businessId, purchasesUnPunctuated, id);
		if (result == 0) {
			setChangeMade(1);
		}
		setChangeMade(1);
		return "/api/business/purchases/{businessName}/{emailOfUser}/{id}";
	}

	@RequestMapping(value = "/api/business/purchases/numberFormats", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String findNumberFormats() {
		return new Gson().toJson(purchasesService.getPurchasesNumberFormat());
	}

	public int getChangeMade() {
		return changeMade;
	}

	public void setChangeMade(int changeMade) {
		this.changeMade = changeMade;
	}
}
