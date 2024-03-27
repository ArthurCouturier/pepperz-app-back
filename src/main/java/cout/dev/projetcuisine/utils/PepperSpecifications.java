package cout.dev.projetcuisine.utils;

public enum PepperSpecifications {
    FRUITED("FRUITED"),
    STRONG("STRONG"),
    SWEET("SWEET"),
    SPICY("SPICY"),
    MILD("MILD");

    private final String spec;

    PepperSpecifications(String spec) {
        this.spec = spec;
    }

    @Override
    public String toString() {
        return spec;
    }

    public static boolean isValid(String name) {
        for (PepperSpecifications value : values()) {
            if (value.spec.equals(name)) {
                return true;
            }
        }
        return false;
    }
}
