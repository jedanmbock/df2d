/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ananum.df2d.method;

/**
 *
 * @author JD
 */
import com.ananum.df2d.utils.Function;
import com.ananum.df2d.utils.Gauss;
import com.ananum.df2d.utils.GaussSeidel;
import java.util.ArrayList;

public class FiniteDifferenceSolver {
    private int n, m;
    private double h, k;
    Function funct;

    public FiniteDifferenceSolver(int n, int m, Function funct) {
        this.n = n;
        this.m = m;
        this.h = 1.0 / (n + 1);
        this.k = 1.0/ (m + 1);
        this.funct = funct;
    }

    public double[] solve() {
        int N = n * m;
        double[][] A = new double[N][N];
        double[] b = new double[N];
        double ih = 1.0/(h*h), ik = 1.0/(k*k);

        for (int j = 0; j < m; j++) {
            for (int i = 0; i < n; i++) {
                int idx = j * n + i;
                A[idx][idx] = 2.0*(ih + ik);
                if (i > 0) A[idx][idx - 1] = -ih;
                else       b[idx] += funct.u((i) * h, (j + 1) * k)*ih; // gauche
                if (i < n - 1) A[idx][idx + 1] = -ih;
                else           b[idx] += funct.u((i + 2) * h, (j + 1) * k)*ih; // droite
                if (j > 0) A[idx][idx - n] = -ik;
                else       b[idx] += funct.u((i + 1) * h, (j) * k)*ik; // bas
                if (j < m - 1) A[idx][idx + n] = -ik;
                else           b[idx] += funct.u((i + 1) * h, (j + 2) * k)*ik; // haut

                b[idx] += funct.f((i+1)*h,(j+1)*k); // f(x,y) = -4 donc RHS = -4 * h^2, 
            }
        }

        //return Gauss.gaussianElimination(A, b);
        return GaussSeidel.resolver(A, b, new double[N], 1e-4, 1000);
    }

    public double[] computeExact() {
        double[] u = new double[n * m];
        for (int j = 0; j < m; j++) {
            for (int i = 0; i < n; i++) {
                double x = (i + 1) * h;
                double y = (j + 1) * k;
                u[j * n + i] = funct.u(x, y);
            }
        }
        return u;
    }
}

