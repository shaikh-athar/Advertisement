package com.gov.Advertisments.ServiceImple.OtherImple;

public class IdGenerator {
        public static String getId(int n)
        {

            String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
            StringBuilder sb = new StringBuilder(n);

            for (int i = 0; i < n; i++) {
                int index
                        = (int)(AlphaNumericString.length()
                        * Math.random());
                sb.append(AlphaNumericString
                        .charAt(index));
            }

            return sb.toString();
        }
}
