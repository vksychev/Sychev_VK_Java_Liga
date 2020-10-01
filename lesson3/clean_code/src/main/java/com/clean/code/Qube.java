package com.clean.code;

/**
 * Класс куба, имплементирует интерфейс {@link Shape3D}
 */
public class Qube implements Shape3D {
    /**
     * Координата X центра куба
     */
    private Double centerX;
    /**
     * Координата Y центра куба
     */
    private Double centerY;
    /**
     * Координата Z центра куба
     */
    private Double centerZ;
    /**
     * Размер грани куба
     */
    private Double edgeSize;

    /**
     * Конструктор со всеми полями
     *
     * @param centerX координата X центра куба
     * @param centerY координата Y центра куба
     * @param centerZ координата Z центра куба
     * @param edgeSize размер грани куба
     */
    public Qube(Double centerX, Double centerY, Double centerZ, Double edgeSize) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.centerZ = centerZ;
        this.edgeSize = edgeSize;
    }

    /**
     * Нахождение объема куба
     *
     * @return Double объем куба
     */
    @Override
    public Double getVolume() {
        return Math.pow(edgeSize, 3);
    }

    /**
     * Возвращает координату X центра куба
     *
     * @return Double координата X центра куба
     */
    public Double getCenterX() {
        return centerX;
    }

    /**
     * Возвращает координату Y центра куба
     *
     * @return Double координата Y центра куба
     */
    public Double getCenterY() {
        return centerY;
    }

    /**
     * Возвращает координату Z центра куба
     *
     * @return Double координата Z центра куба
     */
    public Double getCenterZ() {
        return centerZ;
    }
}
