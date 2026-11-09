package cl.technova.integracion.canonico;

public final class Rut {
    private Rut() { }

    public static String normalizar(String rut) {
        if (rut == null || rut.isBlank()) return null;
        String limpio = rut.replace(".", "").replace("-", "").replace(" ", "").toUpperCase();
        if (limpio.length() < 2) return limpio;
        return limpio.substring(0, limpio.length() - 1) + "-" + limpio.charAt(limpio.length() - 1);
    }
}
