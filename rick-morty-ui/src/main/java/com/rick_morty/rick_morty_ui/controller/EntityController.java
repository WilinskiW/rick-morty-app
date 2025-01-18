package com.rick_morty.rick_morty_ui.controller;

import org.springframework.ui.Model;


public interface EntityController<TDto> {
    String showSinglePage(int id, Model model);

    String showAll(Model model);

    String showEditPage(int id, Model model);

    String showAddPage();

    String delete(int id);

    String update(int id, TDto dto);

    String create();
}
