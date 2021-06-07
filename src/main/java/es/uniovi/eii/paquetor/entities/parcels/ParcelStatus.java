package es.uniovi.eii.paquetor.entities.parcels;

/**
 * Posibles estados de un paquete en su ruta
 */
public enum ParcelStatus {
    NOT_REGISTERED, // Paquete no registrado en el sistema
    PICKUP_PENDING, // Orden de recogida recibida
    IN_ORIGIN,      // En almacén de origen
    IN_DESTINY,     // En almacén de destino
    IN_DELIVERY,    // En reparto
    IN_PICKUP       // En recogida
}
