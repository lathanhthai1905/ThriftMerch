package MVCModel.Controllers.ActorControllers.Shop;

import MVCModel.Controllers.IViewController;

public interface IProductEditShopViewController extends IViewController {

	public void setPreviousView(String view);

	public void switchToMainMenu();

	public void switchToOrders();

	public void switchToAddProduct();

	public void switchToPreviousView();

	public void switchToDefault();

	public String getProductID();
	public void setProductID(String id);
}
