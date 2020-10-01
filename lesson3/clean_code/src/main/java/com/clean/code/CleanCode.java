package com.clean.code;
/**
 * Класс для запуска программы
 */
public class CleanCode {
    /**
     * Подсчет объема куба и квадрата
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        Shape3D qube = new Qube(1d, 1d, 1d, 10d);
        System.out.println("Qube volume: " + qube.getVolume());

        Shape2D square = new Square(1d, 1d, 5d);
        System.out.println("Square perimeter: " + square.getPerimeter());
    }

}
