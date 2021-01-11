package MVCModel.Controllers.ActorControllers.Customer;

import java.io.IOException;

import DataController.Order;
import MVCModel.Controllers.IViewController;

public interface IOrdersCustomerViewController extends IViewController {
	public void setPreviousView(String view);
	
	public void switchToPreviousView();
	public void switchToDefault();
	public void switchToMainMenu();
	public void switchToCart() throws IOException;
	public void switchToOrderDetails(Order order);
}
