package com.ssafy.togeball.domain.common.utils;

import java.util.Random;

public class PasswordUtil {

    /*
     * 소셜 사용자를 위한 랜덤 비밀번호 생성기
     */

    public static String generateRandomPassword() {
        int index;
        char[] charSet = new char[] {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
                'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
        };

        StringBuilder password = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 8 ; i++) {
            double rd = random.nextDouble();
            index = (int) (charSet.length * rd);

            password.append(charSet[index]);
        }

        return password.toString();
    }
}