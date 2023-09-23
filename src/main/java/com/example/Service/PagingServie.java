package com.example.Service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.DAO.PagingDAOInterfaceNew;
import com.example.Entity.Order;
import com.example.Entity.Orders;


@Service
public class PagingServie {
	
	Logger log=LoggerFactory.getLogger(PagingServie.class);
	
	@Autowired
	private PagingDAOInterfaceNew pagingDAOInterface;
	
	public List<Orders> getItems() {
		log.info("in service class");
		log.info(pagingDAOInterface.toString());
		List<Orders> list=pagingDAOInterface.findAll();
		log.info("list size--"+ list.size());
		return list;
	}
	
	public Page<Orders> getItemsForPage(Pageable page) {
		return pagingDAOInterface.findAll(page);
	}
	
//	public Page<Orders> getItemsForPageAndSort(Pageable page) {
//		return pagingDAOInterface.findBy(page,Sort.by("date"));
//	}
}
