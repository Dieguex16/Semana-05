/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Estudiante;
import Modelo.EstudianteArray;
import Vista.IRegistro;

import javax.swing.*;
import java.util.Date;

/**
 *
 * @author dgarc
 */
public class Controlador {

    private IRegistro vista;
    private EstudianteArray gestor;

    public Controlador(IRegistro vista, EstudianteArray gestor) {
        this.vista = vista;
        this.gestor = gestor;

        // Inicializar eventos
        this.vista.getBtnGuardar().addActionListener(e -> guardarEstudiante());
        this.vista.getBtnCargar().addActionListener(e -> cargarEstudiantes());
    }

    public void guardarEstudiante() {
        // Validar campos
        if (camposVacios()) {
            JOptionPane.showMessageDialog(vista, "Por favor, complete todos los campos.");
            return;
        }

        // Crear un nuevo estudiante
        Estudiante estudiante = new Estudiante(
                vista.getTxtCodigo().getText(),
                vista.getTxtNombre().getText(),
                vista.getTxtApPaterno().getText(),
                vista.getTxtApMaterno().getText(),
                vista.getTxtDireccion().getText(),
                calcularEdad(vista.getDateChooserEdad().getDate())
        );

        // Agregar el estudiante a la lista y actualizar la tabla
        gestor.agregarEstudiante(estudiante);
        gestor.guardarDatosEnArchivo("E:\\123.txt"); // Guardar en archivo
    }

    private boolean camposVacios() {
        return vista.getTxtCodigo().getText().isEmpty()
                || vista.getTxtNombre().getText().isEmpty()
                || vista.getTxtApPaterno().getText().isEmpty()
                || vista.getTxtApMaterno().getText().isEmpty()
                || vista.getTxtDireccion().getText().isEmpty()
                || vista.getDateChooserEdad().getDate() == null;
    }

    private int calcularEdad(Date fechaNacimiento) {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(fechaNacimiento);
        int añoNacimiento = cal.get(java.util.Calendar.YEAR);
        int añoActual = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        return añoActual - añoNacimiento;
    }

    public void cargarEstudiantes() {
        gestor.cargarDatosDesdeArchivo("E:\\RegistrosdeEstudiante.txt"); // Cargar datos desde archivo
    }
}
