package builder;

import components.Modal;
import helpers.StageHelper;

public class ModalBuilder {
	
	public static Modal createLoginSuccessModal() {
		return new Modal(StageHelper.getInstance().getStage(), "Login Success! :D", "./resources/login-modal-success.gif");
	}
	
	public static Modal createRegisterSuccessModal() {
		return new Modal(StageHelper.getInstance().getStage(), "Register Success! :D", "./resources/register-modal-success.jpg");
	}
	
	public static Modal createLoginFailedModal() {
		return new Modal(StageHelper.getInstance().getStage(), "Login Failed! :(", "./resources/modal-failed.jpg");
	}
	
	public static Modal createRegisterFailedModal(String text) {
		return new Modal(StageHelper.getInstance().getStage(), text, "./resources/modal-failed.jpg");
	}

}
