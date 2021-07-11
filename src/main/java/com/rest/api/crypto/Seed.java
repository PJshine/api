package com.rest.api.crypto;

import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
public class Seed {
    private static final Charset UTF_8 = StandardCharsets.UTF_8;
    private static final byte[] pbszUserKey = "testCrypt2020!@#".getBytes();
    private static final byte[] pbszIV = "1234567890123456".getBytes();
    public static String encrypt(String rawMessage) {
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] message = rawMessage.getBytes(UTF_8);
        byte[] encryptedMessage = KISA_SEED_CBC.SEED_CBC_Encrypt(pbszUserKey, pbszIV, message, 0, message.length);
        return new String(encoder.encode(encryptedMessage), UTF_8);
    }
    public static String decrypt(String encryptedMessage) {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] message = decoder.decode(encryptedMessage);
        byte[] decryptedMessage = KISA_SEED_CBC.SEED_CBC_Decrypt(pbszUserKey, pbszIV, message, 0, message.length);
        return new String(decryptedMessage, UTF_8);
    }
}