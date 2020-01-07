package com.igfgpo01.gestionpedidosmobile.singleton;

import com.igfgpo01.gestionpedidosmobile.responses.MenuResponse;

import java.util.List;

/**
 * Esta clase sirve como una especie de repositorio compartido
 * a la hora de configurar una orden a realizar. Se necesita compartir los menus
 * entre la actividad que muestra los menus disponibles y el detalle de una menú especifico
 */
public class MenusDeSucursalSingleton {

    private static MenusDeSucursalSingleton instance;

    private MenusDeSucursalSingleton() {}

    public static MenusDeSucursalSingleton getInstance(){
        if(instance == null) instance = new MenusDeSucursalSingleton();
        return instance;
    }

    private List<MenuResponse> menus; //Lista de menus a compartir entre antividades

    private double totalMenus; //Monto total de la orden configurada

    //Calcula el total a pagar de la orden configurada
    public double getTotalMenus() {
        totalMenus = 0;
        for (MenuResponse menu : menus) {
            totalMenus += menu.getSubTotalMenu();
        }

        return totalMenus;
    }

    public void setMenus(List<MenuResponse> menus) {
        this.menus = menus;
    }

    public List<MenuResponse> getMenus(){
        return this.menus;
    }

}
