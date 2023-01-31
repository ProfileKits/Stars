package com.predictor.library.example;

/**
 * 用点调用自己
 */
public class SetSelfWithPoint {
    private boolean repeat;
    private int times;

    public static Composer with(boolean enabled) {
        return new Composer(enabled);
    }

    private SetSelfWithPoint(Composer composer) {
        repeat = composer.repeat;
        times = composer.times;
    }

    public static final class Composer {
        private boolean repeat = false;
        private int times = 0;
        private Composer(boolean enabled) {
            this.repeat = enabled;
        }

        public SetSelfWithPoint.Composer repeat(int times) {
            repeat = times != 0;
            return this;
        }

        public SetSelfWithPoint.Composer setTimes(int times) {
            times = times;
            return this;
        }
    }

}
