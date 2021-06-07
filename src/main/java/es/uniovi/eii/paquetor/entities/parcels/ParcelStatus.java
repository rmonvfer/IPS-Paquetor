package es.uniovi.eii.paquetor.entities.parcels;

/**
 * Posibles estados de un paquete en su ruta
 */
public enum ParcelStatus {
    NOT_PROCESSED,  // Paquete registrado pero no hay nada que hacer con él aún.
    PICKUP_PENDING, // Orden de recogida recibida
    IN_ORIGIN,      // En almacén de origen
    IN_DESTINY,     // En almacén de destino
    IN_DELIVERY,    // En reparto
    IN_PICKUP       // En recogida
}
