package com.github.mikephil.charting.data;

import java.math.BigDecimal;

/**
 * Created by Philipp Jahoda on 02/06/16.
 */
public abstract class BaseEntry {

    /** the y value */
    private BigDecimal y = BigDecimal.ZERO;

    /** optional spot for additional data this Entry represents */
    private Object mData = null;

    public BaseEntry() {

    }

    public BaseEntry(float y) {
        this.y = BigDecimal.valueOf(y);
    }

    public BaseEntry(float y, Object data) {
        this(y);
        this.mData = data;
    }

    public BaseEntry(BigDecimal y) {
        this.y = y;
    }

    /**
     * Returns the y value of this Entry.
     *
     * @return
     */
    public float getY() {
        return y.floatValue();
    }

    public BigDecimal getBigDecimal() {
        return y;
    }

    /**
     * Sets the y-value for the Entry.
     *
     * @param y
     */
    public void setY(float y) {
        this.y = BigDecimal.valueOf(y);
    }

    /**
     * Returns the data, additional information that this Entry represents, or
     * null, if no data has been specified.
     *
     * @return
     */
    public Object getData() {
        return mData;
    }

    /**
     * Sets additional data this Entry should represent.
     *
     * @param data
     */
    public void setData(Object data) {
        this.mData = data;
    }
}
