package com.company.raster;

public class Matrix3D {

    public double[][] matrix;

    public Matrix3D(){

        matrix = new double[4][4];

    }

    public static Matrix3D createIdentityMatrix() {
        Matrix3D export = new Matrix3D();

        export.matrix = new double[][] {
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        };

        return export;
    }

    public static Matrix3D createScalingMatrix(double scale) {
        Matrix3D export = new Matrix3D();

        export.matrix = new double[][] {
                {scale, 0,     0,     0},
                {0,     scale, 0,     0},
                {0,     0,     scale, 0},
                {0,     0,     0,     1}
        };

        return export;
    }

    public static Matrix3D createTranslationMatrix(double translationX, double translationY, double translationZ) {
        Matrix3D export = new Matrix3D();

        export.matrix = new double[][] {
                {1, 0, 0, translationX},
                {0, 1, 0, translationY},
                {0, 0, 1, translationZ},
                {0, 0, 0, 1}
        };

        return export;
    }

    public static Matrix3D createRotationMatrixXY(double angle) {


        angle = angle*Math.PI/180;
        Matrix3D export = new Matrix3D();



        export.matrix = new double[][] {
                {Math.cos(angle), -(Math.sin(angle)),  0, 0},
                {Math.sin(angle),   Math.cos(angle), 0, 0},
                {0,           0,           1, 0},
                {0,           0,           0, 1}
        };

        return export;
    }

    public static Matrix3D createRotationMatrixYZ(double alfa) {


        alfa = alfa*Math.PI/180;
        Matrix3D export = new Matrix3D();

         export.matrix = new double[][] {
                {1, 0,  0, 0},
                {0, Math.cos(alfa),   -(Math.sin(alfa)), 0, 0},
                {0, Math.sin(alfa),        Math.cos(alfa), 0},
                {0,           0,           0, 1}
        };
        return export;
    }

    public static Matrix3D createRotationMatrixXZ(double alfa) {

        alfa = alfa*Math.PI/180;
        Matrix3D export = new Matrix3D();

        export.matrix = new double[][] {
                {Math.cos(alfa), 0,  Math.sin(alfa), 0},
                {0,   1, 0, 0},
                {-(Math.sin(alfa)), 0,  Math.cos(alfa), 0},
                {0,           0,           0, 1}
        };
        return export;


    }

    public static Matrix3D createPerspectiveMatrix(double distance) {
        Matrix3D export = new Matrix3D();

        double matrixDistance = 1 / distance;

        export.matrix = new double[][] {
                {1, 0, 0,              0},
                {0, 1, 0,              0},
                {0, 0, 0,              0},
                {0, 0, matrixDistance, 1}
        };

        return export;
    }

    public static Matrix3D createOrthogonalMatrixXY() {
        Matrix3D export = new Matrix3D();

        export.matrix = new double[][] {
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 1}
        };

        return export;
    }

    public static Matrix3D createOrthogonalMatrixYZ() {
        Matrix3D export = new Matrix3D();

        export.matrix = new double[][] {

                {0, 0, 1, 0},
                {0, 1, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 1}
        };

        return export;
    }

    public static Matrix3D createOrthogonalMatrixXZ() {
        Matrix3D export = new Matrix3D();

        export.matrix = new double[][] {

                {1, 0, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 1}
        };

        return export;
    }

    public static Matrix3D multiplyMatrix(Matrix3D m1, Matrix3D m2) {

        Matrix3D export = new Matrix3D();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    export.matrix[i][j] += m1.matrix[i][k] * m2.matrix[k][j];
                }
            }
        }

        return export;
    }
}
