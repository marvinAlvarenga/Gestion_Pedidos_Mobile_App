package com.igfgpo01.gestionpedidosmobile.requests;

import com.igfgpo01.gestionpedidosmobile.responses.MenuResponse;
import com.igfgpo01.gestionpedidosmobile.responses.ProductoResponse;
import com.igfgpo01.gestionpedidosmobile.singleton.MenusDeSucursalSingleton;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa el BODY de la petición de una nueva orden
 */
public class NuevaOrdenRequest {

    private int sucursal;
    private List<ProductoRequest> productos;

    private boolean nuevaOrdenLista; //Atributo local para saber si la orden está lista para ser enviada
                                    //(Tiene productos seleccionados para ordenarlos)

    public NuevaOrdenRequest(int sucursal) {
        this.sucursal = sucursal;

        recuperarProductos();
    }

    //Asignar los productos seleccionados por el usuario
    private void recuperarProductos() {
        productos = new ArrayList<>();

        List<MenuResponse> menus = MenusDeSucursalSingleton.getInstance().getMenus();
        List<ProductoResponse> productosDeMenu;

        for (MenuResponse menu : menus) {
            productosDeMenu = menu.getProductos();
            for (ProductoResponse prod : productosDeMenu) {
                int cant = prod.getCantidadSeleccionada();
                if (cant > 0) {
                    productos.add(new ProductoRequest(cant, prod.getId()));
                }
            }
        }

        nuevaOrdenLista = productos.size() > 0 ? true : false;
    }

    public boolean isNuevaOrdenLista() {
        return nuevaOrdenLista;
    }

    public int getSucursal() {
        return sucursal;
    }

    public List<ProductoRequest> getProductos() {
        return productos;
    }
}
