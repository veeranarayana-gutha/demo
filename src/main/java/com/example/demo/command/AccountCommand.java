package com.example.demo.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.example.demo.model.Account;
import com.example.demo.service.AccountService;

public class AccountCommand {

	private static AccountCommand accountCommand = new AccountCommand();

	public static AccountCommand getInsatnce() {
		return accountCommand;
	}

	AccountService accountService = new AccountService();

	public List<Account> execute(HttpServletRequest request) {

		String operation = request.getHeader("operation");
		List<Account> accounts = new ArrayList<>();
		if (operation.equals("saveAccount")) {

			Account account = converXmlrequestToAccount(request);
			accounts.add(accountService.saveAccount(account));

		} else if (operation.equals("getAccounts")) {

			accounts = accountService.getAccounts();
		} else if (operation.equals("removeAccount")) {
			Account account = converXmlrequestToAccount(request);
			accounts.add(accountService.saveAccount(account));
		}

		return accounts;

	}

	private Account converXmlrequestToAccount(HttpServletRequest request) {
		Account account = new Account();
		try {
			ServletInputStream inputStream = request.getInputStream();
			JAXBContext jaxbContext = JAXBContext.newInstance(Account.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			account = (Account) unmarshaller.unmarshal(inputStream);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return account;
	}

}
