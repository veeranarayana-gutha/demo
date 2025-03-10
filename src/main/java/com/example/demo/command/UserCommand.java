package com.example.demo.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;

import javax.servlet.ServletInputStream;

import javax.servlet.http.HttpServletRequest;

public class UserCommand {

	private static UserCommand userCommand = new UserCommand();

	public static UserCommand getInsatnce() {
		return userCommand;
	}

	UserService userService = new UserService();

	public List<User> execute(HttpServletRequest request) {

		String operation = request.getHeader("operation");
		List<User> users = new ArrayList<>();
		if (operation.equals("saveUser")) {

			User user = converXmlrequestToUser(request);
			users.add(userService.saveUser(user));

		} else if (operation.equals("getUsers")) {

			users = userService.getUsers();
		} else if (operation.equals("removeUser")) {
			User user = converXmlrequestToUser(request);
			userService.deleteUser(user);
			users = userService.getUsers();
		}

		return users;

	}

	private User converXmlrequestToUser(HttpServletRequest request) {
		User user = new User();
		try {
			ServletInputStream inputStream = request.getInputStream();
			JAXBContext jaxbContext = JAXBContext.newInstance(User.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			user = (User) unmarshaller.unmarshal(inputStream);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return user;
	}

}
