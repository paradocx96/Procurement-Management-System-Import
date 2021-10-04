package com.csse.pms.domain;

import java.util.List;

import org.springframework.http.ResponseEntity;

public interface ItemDataAdapter {

	ResponseEntity<?> addItem(Item item);
	List<Item> getItemBySupplierID(String id);
	ResponseEntity<?> deleteByItemID(String id);
	ResponseEntity<?> updateSingleItem(Item item);
	
}
