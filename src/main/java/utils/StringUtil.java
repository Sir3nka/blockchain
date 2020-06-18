package utils;

import java.nio.charset.StandardCharsets;
import com.google.common.hash.Hashing;

public class StringUtil {

    public static String applySha256(String input){
            return Hashing.sha256()
                    .hashString(input, StandardCharsets.UTF_8)
                    .toString();
        }
    }
