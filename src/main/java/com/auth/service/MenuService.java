package com.auth.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth.entity.Menu;
import com.auth.repository.IMenuRepository;
@Service
public class MenuService implements IMenuService {
	@Autowired
	IMenuRepository menuRepo;
	
	@Override
	public List<Menu> listarMenuPorRol(int rol) {		
		return menuRepo.findMenusPorRol(rol);
	}
	@Override
	public List<Menu> listarSubMenusPorMenu(int menu_id) {
		return menuRepo.findSubMenusPorMenu(menu_id);
	}
}
