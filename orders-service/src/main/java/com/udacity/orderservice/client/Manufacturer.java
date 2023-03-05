package com.udacity.orderservice.client;

public class Manufacturer {

    private Integer code;
    private String name;

    public Manufacturer() { }

    public Manufacturer(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public enum Brands {
        AUDI(100),
        CHEVROLET(101),
        FORD(102),
        BMW(103),
        DODGE(104);

        public final int label;

        private Brands(int label) {
            this.label = label;
        }
    }
}
