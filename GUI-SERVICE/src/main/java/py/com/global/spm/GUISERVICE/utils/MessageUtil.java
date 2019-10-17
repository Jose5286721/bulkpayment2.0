package py.com.global.spm.GUISERVICE.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Created by global on 3/14/18.
 */
@Component
public class MessageUtil {

    private static final String DEFAULT_PATTERN = "=>";

    @Autowired
    private MessageSource messageSource;

    public String getMensaje(String code, List<String> replaceText) {
        Locale locale = new Locale("es","ES");
        String text;
        if (replaceText != null && !replaceText.isEmpty()) {
            text =  messageSource.getMessage(code,replaceText.toArray(),locale);
        } else {
            text = messageSource.getMessage(code,null,locale);
        }
        return text;
    }

    public Messages getMessageWithPattern(@NotNull String code, List<String> replaceText, String... pattern) {

        String text = getMensaje(code,replaceText);
        Messages messages = new Messages();

        String pattern1 = pattern.length > 0 ? pattern[0] : DEFAULT_PATTERN;
        String[] parts = text.split(Pattern.quote(pattern1));
        if (parts.length > 0) {
            messages.setMessage(parts[0]);
            if (parts.length>1) {
                messages.setDetail(parts[1]);
            }
            return messages;
        }
        return null;
    }

}
