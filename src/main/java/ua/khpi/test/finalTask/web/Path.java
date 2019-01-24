package ua.khpi.test.finalTask.web;


public final class Path {
	
	// pages
	public static final String PAGE_LOGIN = "login.jsp";
	public static final String PAGE_ERROR_PAGE = "/WEB-INF/jsp/error_page.jsp";
	public static final String PAGE_REGISTRATION_COMPLETED = "/WEB-INF/jsp/registration_completed.jsp";
	public static final String PAGE_VALIDATION_COMPLETED = "/WEB-INF/jsp/validation_completed.jsp";
	public static final String PAGE_LIST_ACCOUNTS = "/WEB-INF/jsp/user/accounts.jsp";
	public static final String PAGE_LIST_REQUESTS = "/WEB-INF/jsp/admin/list_requests.jsp";
	public static final String PAGE_REGISTRATION = "/WEB-INF/jsp/registration.jsp";
	public static final String PAGE_PAYMENT_CONFIRMED = "/WEB-INF/jsp/user/payment_confirmed.jsp";
	public static final String PAGE_REPLENISHMENT = "/WEB-INF/jsp/user/replenish_account.jsp";
	public static final String PAGE_ACCOUNT_SETTINGS = "/WEB-INF/jsp/user/account_settings.jsp";
	public static final String PAGE_NEW_PAYMENT = "/WEB-INF/jsp/user/new_payment.jsp";
	public static final String PAGE_CART = "/WEB-INF/jsp/user/cart.jsp";
	public static final String PAGE_TRANSACTIONS = "/WEB-INF/jsp/user/transactions.jsp";
	public static final String PAGE_NEW_ACCOUNT = "/WEB-INF/jsp/user/new_account.jsp";
	public static final String PAGE_NEW_CARD = "/WEB-INF/jsp/user/new_card.jsp";

	public static final String PAGE_LIST_ADMINS = "/WEB-INF/jsp/superuser/superuser_page.jsp";
	public static final String PAGE_ADMINS_ACTIONS = "/WEB-INF/jsp/admin/show_actions.jsp";
	public static final String PAGE_ADMINS_ACTION_CONFIRMED = "/WEB-INF/jsp/admin/action_confirmed.jsp";
	public static final String PAGE_EPAM_TASK = "/WEB-INF/jsp/admin/list_task.jsp";
	public static final String PAGE_USER_LIST = "/WEB-INF/jsp/admin/client_list.jsp";
	public static final String PAGE_ACCOUNT_LIST = "/WEB-INF/jsp/admin/account_list.jsp";
	public static final String PAGE_CART_REMOVAL_CONFIRMED = "/WEB-INF/jsp/user/card_deleted.jsp";
	

	// commands
	public static final String COMMAND_REDIRECT_CART_REMOVAL_COMPLETED = "controller?command=redirectRemovalCart";

	public static final String COMMAND_LIST_CARDS = "controller?command=listCards";
	public static final String COMMAND_LIST_ACCOUNTS = "controller?command=listAccounts";
	public static final String COMMAND_LIST_TRANSACTIONS= "controller?command=listTransactions";
	public static final String COMMAND_LIST_REQUESTS = "controller?command=listRequests";
	public static final String COMMAND_LIST_ADMINS= "controller?command=listAdmins";
	public static final String COMMAND_REDIRECT_REGISTRATION_COMPLETED= "controller?command=redirectRegistrationCompleted";
	public static final String COMMAND_REDIRECT_VALIDATION_COMPLETED = "controller?command=redirectValidationCompleted";
	public static final String COMMAND_REDIRECT_TRANSACTION_COMPLETED = "controller?command=redirectReplenishmentCompleted";
	public static final String COMMAND_INITIALIZE_USER_SESSION= "controller?command=initializeUserSession";
	public static final String COMMAND_SORT_ACCOUNTS= "controller?command=sortAccounts";
	public static final String COMMAND_SORT_TRANSACTIONS= "controller?command=sortTransactions";
	public static final String COMMAND_ADMIN_ACTION_CONFIRMED= "controller?command=showActionConfirmed";

}