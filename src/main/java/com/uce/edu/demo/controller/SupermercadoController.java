package com.uce.edu.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uce.edu.demo.repository.modelo.Producto;
import com.uce.edu.demo.repository.modelo.Venta;
import com.uce.edu.demo.service.IGestorMegaSantaMaria;
import com.uce.edu.demo.service.IProductoService;
import com.uce.edu.demo.service.IVentaService;

@Controller
@RequestMapping("/supermercado")
public class SupermercadoController {

	@Autowired
	private IProductoService iProductoService;

	@Autowired
	private IVentaService iVentaService;
	
	@Autowired
	private IGestorMegaSantaMaria iGestorMegaSantaMaria;
	
	@GetMapping("/reporteVentas")
	public String buscarVentas(Model modelo) {
		List<Venta> lista = this.iVentaService.buscarTodos();
		modelo.addAttribute("ventas", lista);
		return "VistaReporteVentas";
	}
	
	@GetMapping("/buscarProductos")
	public String buscarProductos(Model modelo) {
		List<Producto> lista = this.iProductoService.buscarTodos();
		modelo.addAttribute("productos", lista);
		return "VistaProductos";
	}
	
	@GetMapping("/buscarUno/{idProducto}")
	public String buscarProducto(@PathVariable("idProducto") String codigoBarras, Model modelo) {
		System.out.println("El Codigo es: " + codigoBarras);
		Producto p = this.iProductoService.buscar(codigoBarras);
		modelo.addAttribute("producto", p);
		return "VistaProducto";
	}
	
	@GetMapping("/buscarUno/{idProducto}")
	public String consultarStock(@PathVariable("idProducto") String codigoBarras, Model modelo) {
		System.out.println("El Codigo es: " + codigoBarras);
		List<Producto> lp=this.iGestorMegaSantaMaria.consultarStock(codigoBarras);
		modelo.addAllAttributes(lp);
		return "VistaProducto";
	}

	@PutMapping("/actualizar/{idProducto}")
	public String actualizarPersona(@PathVariable("idProducto") Integer id, Producto producto) {
		producto.setId(id);
		this.iProductoService.actualizar(producto);
		return "redirect:/supermercado/buscarProductos";
	}

	@PostMapping("/ingresarProducto")
	public String ingresarProducto(Producto producto) {
		this.iProductoService.insertar(producto);
		return "redirect:/supermercado/buscarProductos";
	}
	
	@PostMapping("/ingresarConStock")
	public String ingresarProductoConStock(Producto producto) {
		Integer s=producto.getStock();
		this.iGestorMegaSantaMaria.ingresarProducto(producto, s);
		return "redirect:/supermercado/buscarProductos";
	}
	
	@GetMapping("/nuevoProducto")
	public String paginaNuevoProducto(Producto producto) {
		return "VistaNuevoProducto";
	}
}
