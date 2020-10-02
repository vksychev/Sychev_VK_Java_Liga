package com.clean.code;

/**
 * Класс квадрата, имплементирует интерфейс {@link Shape2D}
 */
public class Square implements Shape2D {
    /**
     * Координата X центра квадрата
     */
    private Double centerX;
    /**
     * Координата Y центра квадрата
     */
    private Double centerY;
    /**
     * Размер стороны квадрата
     */
    private Double edgeSize;

    /**
     * Конструктор со всеми полями
     *
     * @param centerX координата X центра квадрата
     * @param centerY координата Y центра квадрата
     * @param edgeSize размер стороны квадрата
     */
    public Square(Double centerX, Double centerY, Double edgeSize) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.edgeSize = edgeSize;
    }

    /**
     * Нахождение площади квадрата
     *
     * @return Double площадь квадрата
     */
    public Double getArea() {
        return edgeSize * edgeSize;
    }

    /**
     * Возвращает координату X центра квадрата
     *
     * @return Double координата X центра квадрата
     */
    public Double getX() {
        return centerX;
    }
    /**
     * Возвращает координату Y центра квадрата
     *
     * @return Double координата Y центра квадрата
     */
    public Double getY() {
        return centerY;
    }
}
