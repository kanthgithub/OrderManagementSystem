package com.yieldBroker.common;

/**
 * Enum to maintain valid list of OrderSide
 */
public enum Side {

    BUY("Buy"),SELL("Sell");

    private String code;

    /**
     *
     * @param code
     */
    Side(String code) {
        this.code = code;
    }

    /**
     *
     * @return Enum Code (Buy/Sell)
     */
    public String getCode() {
        return code;
    }

    /**
     *
     * @param code
     * @return Side Enum
     */
    public Side fromCode(String code){

        for(Side sideArg : Side.values()){

            if(sideArg.code.equalsIgnoreCase(code)){
                return sideArg;
            }
        }

        return null;
    }

    /**
     * check if the String represents a valid Side Enum
     * @param code
     * @return
     */
    public Boolean isValid(String code){
        return fromCode(code)!=null;
    }

}


