package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.model.Account;

public class AccountService {

	List<Account> accounts = new ArrayList<>();

	public List<Account> getAccounts() {
		return accounts;
	}

	public Account saveAccount(Account account) {
		accounts.add(account);
		return account;
	}

	public void deleteUser(Account account) {
		accounts.remove(account);
	}

}
