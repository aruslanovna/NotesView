/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.text.InputFilter
 *  android.text.Spanned
 *  java.lang.CharSequence
 *  java.lang.Integer
 *  java.lang.NumberFormatException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 */
package net.lab3.myapplication;

import android.text.InputFilter;
import android.text.Spanned;

public class MinMaxFilter
implements InputFilter {
    private int mIntMax;
    private int mIntMin;

    public MinMaxFilter(int n, int n2) {
        this.mIntMin = n;
        this.mIntMax = n2;
    }

    public MinMaxFilter(String string2, String string3) {
        this.mIntMin = Integer.parseInt((String)string2);
        this.mIntMax = Integer.parseInt((String)string3);
    }

    private boolean isInRange(int n, int n2, int n3) {
        return n2 > n ? n3 >= n && n3 <= n2 : n3 >= n2 && n3 <= n;
    }

    public CharSequence filter(CharSequence charSequence, int n, int n2, Spanned spanned, int n3, int n4) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(spanned.toString());
            stringBuilder.append(charSequence.toString());
            int n5 = Integer.parseInt((String)stringBuilder.toString());
            boolean bl = this.isInRange(this.mIntMin, this.mIntMax, n5);
            if (bl) {
                return null;
            }
        }
        catch (NumberFormatException numberFormatException) {
            numberFormatException.printStackTrace();
        }
        return "";
    }
}

