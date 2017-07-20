package com.techticz.app.domain.model.pojo;

/**
 * Created by gssirohi on 8/7/17.
 */

public class DayMeals {
    private long r1, r2, r3, r4, r5, r6, r7;

    public Long getR1() {
        return r1;
    }

    public void setR1(Long r1) {
        this.r1 = r1;
    }

    public Long getR2() {
        return r2;
    }

    public void setR2(Long r2) {
        this.r2 = r2;
    }

    public Long getR3() {
        return r3;
    }

    public void setR3(Long r3) {
        this.r3 = r3;
    }

    public Long getR4() {
        return r4;
    }

    public void setR4(Long r4) {
        this.r4 = r4;
    }

    public Long getR5() {
        return r5;
    }

    public void setR5(Long r5) {
        this.r5 = r5;
    }

    public Long getR6() {
        return r6;
    }

    public void setR6(Long r6) {
        this.r6 = r6;
    }

    public Long getR7() {
        return r7;
    }

    public void setR7(Long r7) {
        this.r7 = r7;
    }

    public void setMealOnRoutine(int routineId, Long mealId) {
        switch (routineId) {
            case 1:
                setR1(mealId);
                break;
            case 2:
                setR2(mealId);
                break;
            case 3:
                setR3(mealId);
                break;
            case 4:
                setR4(mealId);
                break;
            case 5:
                setR5(mealId);
                break;
            case 6:
                setR6(mealId);
                break;
            case 7:
                setR7(mealId);
                break;

        }
    }
}
