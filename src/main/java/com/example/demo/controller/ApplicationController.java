package com.example.demo.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.springframework.util.StringUtils;

import com.example.demo.command.AccountCommand;
import com.example.demo.command.UserCommand;
import com.example.demo.model.AccountList;
import com.example.demo.model.UserList;

@WebServlet(urlPatterns = "/app")
public class ApplicationController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		String serviceName = req.getHeader("serviceName");

		if (serviceName.equals("User")) {
			UserCommand.getInsatnce().execute(req);
		}

		resp.getWriter().write("Hello from MyCustomServlet");

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		try {
			
			String responseString = "";
			String serviceName = req.getHeader("serviceName");

			if (serviceName.equals("User")) {
				UserList users = new UserList();
				users.setUsers( UserCommand.getInsatnce().execute(req));
				responseString = convertUserListToXml(users);
			}else if(serviceName.equals("Account")) {
				AccountList accounts = new AccountList();
				accounts.setAccounts(AccountCommand.getInsatnce().execute(req));
				responseString = convertAccountListToXml(accounts);
			}

			

			resp.setContentType("application/xml");
			PrintWriter out = resp.getWriter();
			out.print(responseString);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error serializing object to XML");
		}

	}

	private static String convertAccountListToXml(AccountList accounts) throws Exception {
		JAXBContext jaxbContext = JAXBContext.newInstance(AccountList.class);
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); // Optional: for pretty formatting

		StringWriter writer = new StringWriter();
		marshaller.marshal(accounts, writer);
		return writer.toString();
	}
	
	private String convertUserListToXml(UserList users) throws Exception {
		JAXBContext jaxbContext = JAXBContext.newInstance(UserList.class);
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); // Optional: for pretty formatting

		StringWriter writer = new StringWriter();
		marshaller.marshal(users, writer);
		return writer.toString();
	}

}
