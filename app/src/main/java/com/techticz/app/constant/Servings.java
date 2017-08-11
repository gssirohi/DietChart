package com.techticz.app.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gssirohi on 10/9/16.
 */
public enum Servings {

    STANDARD_SERVING(1, "Standard Serving"),
    PIECE(2, "Piece"),
    CUP(3, "Cup"),
    GLASS(4, "Glass"),
    CANE(5, "Cane"),
    BOWL(6, "Bowl"),
    TBL_SPN(7, "Tbl-Spn"),
    SLICE(8, "Slice"),
    BOTTLE(9, "Bottle");

    public final int code;
    public final String lable;

    Servings(int i, String lable) {
        this.code = i;
        this.lable = lable;
    }


    public static Integer getIdByName(String s) {
        if (s.equalsIgnoreCase(STANDARD_SERVING.lable)) return STANDARD_SERVING.code;
        if (s.equalsIgnoreCase(PIECE.lable)) return PIECE.code;
        if (s.equalsIgnoreCase(CUP.lable)) return CUP.code;
        if (s.equalsIgnoreCase(GLASS.lable)) return GLASS.code;
        if (s.equalsIgnoreCase(CANE.lable)) return CANE.code;
        if (s.equalsIgnoreCase(BOWL.lable)) return BOWL.code;
        if (s.equalsIgnoreCase(TBL_SPN.lable)) return TBL_SPN.code;
        if (s.equalsIgnoreCase(SLICE.lable)) return SLICE.code;
        if (s.equalsIgnoreCase(BOTTLE.lable)) return BOTTLE.code;
        return 0;
    }

    public static List<Servings> getAll() {
        List<Servings> all = new ArrayList<>();
        all.add(STANDARD_SERVING);
        all.add(PIECE);
        all.add(CUP);
        all.add(GLASS);
        all.add(CANE);
        all.add(BOWL);
        all.add(TBL_SPN);
        all.add(SLICE);
        all.add(BOTTLE);
        return all;
    }

    public static List<String> getAllNames() {
        List<Servings> list = getAll();
        List<String> names = new ArrayList<>();
        for (Servings type : list) {
            names.add(type.lable);
        }
        return names;
    }

    public static Servings getById(int type) {
        if (type == STANDARD_SERVING.code) return STANDARD_SERVING;
        if (type == PIECE.code) return PIECE;
        if (type == CUP.code) return CUP;
        if (type == GLASS.code) return GLASS;
        if (type == CANE.code) return CANE;
        if (type == BOWL.code) return BOWL;
        if (type == TBL_SPN.code) return TBL_SPN;
        if (type == SLICE.code) return SLICE;
        if (type == BOTTLE.code) return BOTTLE;

        return STANDARD_SERVING;
    }
}
