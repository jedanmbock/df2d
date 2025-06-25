/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ananum.df2d.gui;

/**
 *
 * @author JD
 */
import com.ananum.df2d.functions.*;
import com.ananum.df2d.method.FiniteDifferenceSolver;
import javax.swing.*;

public class GUI {
   public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            int n = 40,m = 40; // nombre de points intérieurs
            FiniteDifferenceSolver solver = new FiniteDifferenceSolver(n, m, new F1());
            double[] uNumerical = solver.solve();
            double[] uExact = solver.computeExact();
            double[] error = new double[n * m];

            for (int i = 0; i < n * m; i++) {
                error[i] = Math.abs(uNumerical[i] - uExact[i]);
            }

            createFrame("Solution exacte", uExact, n, m);
            createFrame("Solution numérique", uNumerical, n, m);
            createFrame("Erreur absolue", error, n, m);
        });
    }

    private static void createFrame(String title, double[] data, int n, int m) {
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new HeatMapViewer(data, n, m, title));
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }
}

