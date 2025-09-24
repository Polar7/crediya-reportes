package co.com.pragma.reports.dynamodb;

import lombok.Getter;

@Getter
public enum MetricsEnum {
    CREDITS_APPROVED("SOLICITUDES_APROBADAS"),
    TOTAL_AMOUNT_CREDITS_APPROVED("MONTO_TOTAL_PRESTAMOS_APROBADOS");

    private final String value;

    MetricsEnum(String value) {
        this.value = value;
    }

}
