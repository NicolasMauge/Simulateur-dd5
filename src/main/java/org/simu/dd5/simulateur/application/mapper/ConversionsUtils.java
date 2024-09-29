package org.simu.dd5.simulateur.application.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConversionsUtils {
    private static final Logger logger = LoggerFactory.getLogger(ConversionsUtils.class);

    public static Integer integerFromString(String s) {
        if (s == null) {
            return null;
        }

        try {
            return Integer.parseInt(s.trim());
        } catch (NumberFormatException e) {
            logger.debug("Erreur de conversion de '{}' en Integer", s);
            return null;
        }
    }
}
