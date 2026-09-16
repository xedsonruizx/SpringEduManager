package com.iseg.util;

public final class RutUtil {

    private RutUtil() {
    }

    public static String limpiar(String rut) {
        if (rut == null) {
            return null;
        }
        return rut.replace(".", "").replace("-", "").replace(" ", "").toUpperCase();
    }

    public static String formatear(String rut) {
        String limpio = limpiar(rut);
        if (limpio == null || limpio.length() < 2) {
            return rut;
        }
        String cuerpo = limpio.substring(0, limpio.length() - 1);
        String dv = limpio.substring(limpio.length() - 1);
        return cuerpo + "-" + dv;
    }

    public static boolean esValido(String rut) {
        String limpio = limpiar(rut);
        if (limpio == null || limpio.length() < 2) {
            return false;
        }

        String cuerpo = limpio.substring(0, limpio.length() - 1);
        String dv = limpio.substring(limpio.length() - 1);

        if (!cuerpo.matches("\\d+")) {
            return false;
        }

        int suma = 0;
        int multiplicador = 2;
        for (int i = cuerpo.length() - 1; i >= 0; i--) {
            suma += Character.getNumericValue(cuerpo.charAt(i)) * multiplicador;
            multiplicador = multiplicador == 7 ? 2 : multiplicador + 1;
        }

        int resto = 11 - (suma % 11);
        String dvEsperado = switch (resto) {
            case 11 -> "0";
            case 10 -> "K";
            default -> String.valueOf(resto);
        };

        return dvEsperado.equalsIgnoreCase(dv);
    }
}
