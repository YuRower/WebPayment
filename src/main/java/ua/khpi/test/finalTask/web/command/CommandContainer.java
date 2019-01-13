package ua.khpi.test.finalTask.web.command;

import java.util.Map;
import java.util.TreeMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.khpi.test.finalTask.logic.AdminLogic;
import ua.khpi.test.finalTask.logic.CommonLogic;
import ua.khpi.test.finalTask.logic.Superuser;
import ua.khpi.test.finalTask.logic.UserLogic;
import ua.khpi.test.finalTask.web.command.admin.AccountsGreaterCommand;
import ua.khpi.test.finalTask.web.command.admin.ActionsPageCommand;
import ua.khpi.test.finalTask.web.command.admin.AdminActionCommand;
import ua.khpi.test.finalTask.web.command.admin.AllAccountsCommand;
import ua.khpi.test.finalTask.web.command.admin.GetUserListCommand;
import ua.khpi.test.finalTask.web.command.admin.ListRequestsCommand;
import ua.khpi.test.finalTask.web.command.admin.RequestResponseCommand;
import ua.khpi.test.finalTask.web.command.admin.ShowActionConfirmedCommand;
import ua.khpi.test.finalTask.web.command.commons.ChangeLanguageCommand;
import ua.khpi.test.finalTask.web.command.commons.LoginCommand;
import ua.khpi.test.finalTask.web.command.commons.LogoutCommand;
import ua.khpi.test.finalTask.web.command.commons.NoCommand;
import ua.khpi.test.finalTask.web.command.commons.RegistrationCommand;
import ua.khpi.test.finalTask.web.command.commons.VerifyEmailCommand;
import ua.khpi.test.finalTask.web.command.superuser.AddAdminCommand;
import ua.khpi.test.finalTask.web.command.superuser.ListAdminsCommand;
import ua.khpi.test.finalTask.web.command.superuser.SuperuserActionCommand;
import ua.khpi.test.finalTask.web.command.user.ChangeAccountNameCommand;
import ua.khpi.test.finalTask.web.command.user.ChangeAccountsOrder;
import ua.khpi.test.finalTask.web.command.user.ChangePaymentsOrder;
import ua.khpi.test.finalTask.web.command.user.ClearCartCommand;
import ua.khpi.test.finalTask.web.command.user.CloseAccountCommand;
import ua.khpi.test.finalTask.web.command.user.CreateNewAccountCommand;
import ua.khpi.test.finalTask.web.command.user.CreateNewCardCommand;
import ua.khpi.test.finalTask.web.command.user.ExecuteCartPaymentsCommand;
import ua.khpi.test.finalTask.web.command.user.GetAccountsByCardCommnd;
import ua.khpi.test.finalTask.web.command.user.InitializeUserSessionCommand;
import ua.khpi.test.finalTask.web.command.user.ListTransactionsCommand;
import ua.khpi.test.finalTask.web.command.user.ListUserAccountsCommand;
import ua.khpi.test.finalTask.web.command.user.ListUserCardsCommand;
import ua.khpi.test.finalTask.web.command.user.LockAccountCommand;
import ua.khpi.test.finalTask.web.command.user.NewPaymentCommand;
import ua.khpi.test.finalTask.web.command.user.RedirectNewCardCommand;
import ua.khpi.test.finalTask.web.command.user.ReplenishAccountCommand;
import ua.khpi.test.finalTask.web.command.user.SortAccountsCommand;
import ua.khpi.test.finalTask.web.command.user.SortPaymentsCommand;
import ua.khpi.test.finalTask.web.command.user.TransactionReportCommand;
import ua.khpi.test.finalTask.web.command.user.RequestUnlockAccountCommand;
import ua.khpi.test.finalTask.web.command.user.redirect.RedirectAccountSettingsCommand;
import ua.khpi.test.finalTask.web.command.user.redirect.RedirectListCartCommand;
import ua.khpi.test.finalTask.web.command.user.redirect.RedirectNewAccountCommand;
import ua.khpi.test.finalTask.web.command.user.redirect.RedirectNewPaymentCommand;
import ua.khpi.test.finalTask.web.command.user.redirect.RedirectRegistrationCommand;
import ua.khpi.test.finalTask.web.command.user.redirect.RedirectRegistrationCompletedCommand;
import ua.khpi.test.finalTask.web.command.user.redirect.RedirectReplenishAccountCommand;
import ua.khpi.test.finalTask.web.command.user.redirect.RedirectReplenishmentCompletedCommand;
import ua.khpi.test.finalTask.web.command.user.redirect.RedirectValidationCompletedCommand;

public class CommandContainer {

	private static final Logger LOG = LogManager.getLogger(CommandContainer.class);

	private static Map<String, Command> commands = new TreeMap<String, Command>();

	static {
		// common commands
		commands.put("login", new LoginCommand(new CommonLogic()));
		commands.put("logout", new LogoutCommand());
		commands.put("commandNotFound", new NoCommand());
		commands.put("registration", new RegistrationCommand(new CommonLogic()));
		commands.put("verify", new VerifyEmailCommand(new CommonLogic()));
		commands.put("redirectRegistration", new RedirectRegistrationCommand());
		commands.put("redirectRegistrationCompleted", new RedirectRegistrationCompletedCommand());
		commands.put("redirectValidationCompleted", new RedirectValidationCompletedCommand());
		commands.put("language", new ChangeLanguageCommand());

		// user commands
		commands.put("initializeUserSession", new InitializeUserSessionCommand());
		commands.put("listCards", new ListUserCardsCommand(new UserLogic()));
		commands.put("numCard", new GetAccountsByCardCommnd(new UserLogic()));
		commands.put("lockAccount", new LockAccountCommand(new UserLogic()));
		commands.put("sortAccounts", new SortAccountsCommand());
		commands.put("sortTransactions", new SortPaymentsCommand());
		commands.put("changeAccountsOrder", new ChangeAccountsOrder());
		commands.put("changePaymentsOrder", new ChangePaymentsOrder());
		commands.put("replanishAccount", new ReplenishAccountCommand(new UserLogic()));
		commands.put("listAccounts", new ListUserAccountsCommand(new UserLogic()));
		commands.put("listCart", new RedirectListCartCommand());
		commands.put("redirectReplenishAccount", new RedirectReplenishAccountCommand());
		commands.put("redirectReplenishmentCompleted", new RedirectReplenishmentCompletedCommand());
		commands.put("redirectAccountSettings", new RedirectAccountSettingsCommand());
		commands.put("redirectNewPayment", new RedirectNewPaymentCommand());
		commands.put("newPayment", new NewPaymentCommand(new UserLogic()));
		commands.put("clearCart", new ClearCartCommand());
		commands.put("executeCartPayments", new ExecuteCartPaymentsCommand(new UserLogic()));
		commands.put("listTransactions", new ListTransactionsCommand(new UserLogic()));
		commands.put("createNewAccount", new CreateNewAccountCommand(new UserLogic()));
		commands.put("createNewCard", new CreateNewCardCommand(new UserLogic()));

		commands.put("redirectNewAccount", new RedirectNewAccountCommand());
		commands.put("redirectNewCard", new RedirectNewCardCommand());

		commands.put("closeAccount", new CloseAccountCommand(new UserLogic()));
		commands.put("unlockAccount", new RequestUnlockAccountCommand(new UserLogic()));
		commands.put("changeAccountName", new ChangeAccountNameCommand(new UserLogic()));
		commands.put("getTransactionReport", new TransactionReportCommand());

		// admin commands
		commands.put("listAllAccounts", new AllAccountsCommand(new AdminLogic()));
		commands.put("listRequests", new ListRequestsCommand(new AdminLogic()));
		commands.put("actionsPage", new ActionsPageCommand());
		commands.put("adminAction", new AdminActionCommand(new AdminLogic()));
		commands.put("showActionConfirmed", new ShowActionConfirmedCommand());
		commands.put("requestResponse", new RequestResponseCommand(new AdminLogic()));
		commands.put("getAccountsGreater", new AccountsGreaterCommand(new AdminLogic()));
		commands.put("userList", new GetUserListCommand(new AdminLogic()));

		// superuser commands
		commands.put("listAdmins", new ListAdminsCommand(new Superuser()));
		commands.put("superuserAction", new SuperuserActionCommand(new Superuser()));
		commands.put("addAdmin", new AddAdminCommand(new Superuser()));

		LOG.debug("Command container was successfully initialized");
		LOG.trace("Number of commands --> " + commands.size());
	}

	public static Command get(String commandName) {
		if (commandName == null || !commands.containsKey(commandName)) {
			LOG.trace("Command not found, name --> " + commandName);
			return commands.get("commandNotFound");
		}

		return commands.get(commandName);
	}

}
