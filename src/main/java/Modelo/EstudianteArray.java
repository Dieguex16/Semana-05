/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.ArrayList;

/**
 *
 * @author dgarc
 */
public class EstudianteArray {

    private ArrayList<Estudiante> listaEstudiantes;
    private DefaultTableModel modelo;

    public EstudianteArray(DefaultTableModel modelo) {
        this.listaEstudiantes = new ArrayList<>();
        this.modelo = modelo;
    }

    public void agregarEstudiante(Estudiante estudiante) {
        // Verificar si el estudiante ya existe
        if (existeEstudiante(estudiante.getCodigo())) {
            JOptionPane.showMessageDialog(null, "Ya existe un estudiante con el código: " + estudiante.getCodigo());
            return; // No agregar si el estudiante ya existe
        }

        listaEstudiantes.add(estudiante);
        actualizarTabla();
    }

    // Método para verificar si ya existe un estudiante con el mismo código
    private boolean existeEstudiante(String codigo) {
        for (Estudiante e : listaEstudiantes) {
            if (e.getCodigo().equalsIgnoreCase(codigo)) {
                return true; // Estudiante encontrado
            }
        }
        return false; // Estudiante no encontrado
    }

    public void actualizarTabla() {
        modelo.setRowCount(0); // Limpiar la tabla
        for (Estudiante e : listaEstudiantes) {
            modelo.addRow(new Object[]{e.getCodigo(), e.getNombre(), e.getApellidom(), e.getApellidop(), e.getDireccion(), e.getEdad()});
        }
    }

    public void guardarDatosEnArchivo(String rutaArchivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
            for (Estudiante estudiante : listaEstudiantes) {
                writer.write(estudiante.getCodigo() + ","
                        + estudiante.getNombre() + ","
                        + estudiante.getApellidop() + ","
                        + estudiante.getApellidom() + ","
                        + estudiante.getDireccion() + ","
                        + estudiante.getEdad());
                writer.newLine(); // Nueva línea para cada estudiante
            }
            JOptionPane.showMessageDialog(null, "Datos guardados exitosamente en " + rutaArchivo);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar los datos: " + e.getMessage());
        }
    }

    public void cargarDatosDesdeArchivo(String rutaArchivo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            listaEstudiantes.clear();  // Limpiar la lista existente
            modelo.setRowCount(0);    // Limpiar la tabla

            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(","); // Suponiendo que los datos están separados por comas
                if (datos.length == 6) {
                    // Crear un nuevo objeto Estudiante con los datos leídos
                    Estudiante estudiante = new Estudiante();
                    estudiante.setCodigo(datos[0]);
                    estudiante.setNombre(datos[1]);
                    estudiante.setApellidop(datos[2]);
                    estudiante.setApellidom(datos[3]);
                    estudiante.setDireccion(datos[4]);
                    estudiante.setEdad(Integer.parseInt(datos[5]));

                    // Agregar el estudiante a la lista
                    listaEstudiantes.add(estudiante);

                    // Agregar el estudiante a la tabla
                    modelo.addRow(new Object[]{datos[0], datos[1], datos[2], datos[3], datos[4], datos[5]});
                }
            }

            JOptionPane.showMessageDialog(null, "Datos cargados exitosamente.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar los datos: " + e.getMessage());
        }
    }

    public ArrayList<Estudiante> getListaEstudiantes() {
        return listaEstudiantes;
    }
}
