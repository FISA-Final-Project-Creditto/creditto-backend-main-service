package org.creditto.creditto_service.global.common;


public enum CurrencyCode {

    KRW("KRW", "원"),
    USD("USD", "미국 달러"),
    JPY("JPY", "일본 옌", 100),
    EUR("EUR", "유로"),
    CNY("CNY", "중국 위안"),
    AUD("AUD", "호주 달러"),
    BHD("BHD", "바레인 디나르"),
    HKD("HKD", "홍콩 달러"),
    SGD("SGD", "싱가포르 달러"),
    AED("AED", "아랍에미리트 디르함"),
    GBP("GBP", "영국 파운드"),
    MYR("MYR", "말레이시아 링깃"),
    IDR("IDR", "인도네시아 루피아", 100);

    private final String code;
    private final String name;
    private final int unit;

    CurrencyCode(String code, String name) {
        this(code, name, 1);
    }

    CurrencyCode(String code, String name, int unit) {
        this.code = code;
        this.name = name;
        this.unit = unit;
    }
}
