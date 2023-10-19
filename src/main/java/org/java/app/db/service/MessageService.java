package org.java.app.db.service;

import org.java.app.db.pojo.Message;
import org.java.app.db.repo.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

	@Autowired
	private MessageRepo messageRepo;
	
	public void saveMessage(Message message) {
		messageRepo.save(message);
	}
}
