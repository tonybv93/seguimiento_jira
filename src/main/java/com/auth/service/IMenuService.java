package com.auth.service;

import java.util.List;

import com.auth.entity.Menu;

public interface IMenuService {
	public List<Menu> listarMenuPorRol(int rol_id);
	public List<Menu> listarSubMenusPorMenu(int menu_id);
}
