package cl.technova.integracion.comun;

import java.util.concurrent.CountDownLatch;

public final class Consola {
    private Consola() { }

    public static void esperarHastaCtrlC(AutoCloseable recurso) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try { recurso.close(); } catch (Exception ignored) { }
            latch.countDown();
        }));
        latch.await();
    }
}
