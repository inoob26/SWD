package swd;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Check {
    //Проверка формы добавления фирмы
    public boolean check_TextfieldFirmTitle(String string){
        Pattern p = Pattern.compile("^\\w+(\\s|\\'|\\\")?(\\w+)?(\\1)?$");
        Matcher m = p.matcher(string);
        return m.matches();
    }
    
    public boolean check_TextfieldPhone(String string){
        Pattern p = Pattern.compile("^\\d+$");
        Matcher m = p.matcher(string);
        return m.matches();
    }
    
    public boolean check_TextfieldAddress(String string){
        Pattern p = Pattern.compile("^(\\w+||[\\\"\\'-]|\\s)+$");
        Matcher m = p.matcher(string);
        return m.matches();
    }
}
