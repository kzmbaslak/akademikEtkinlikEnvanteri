package desktopApp.utilities.dateOperations;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TextToDateConverter {

	public LocalDate convertText2Date(String text){
		//48 ascii
		if(text == null)
			return null;
		text = text.replaceAll("\\s", "");
		
		if(text.equals("DEVAM")) {
			return null;
		}

		int result = 0;
//		DateFormat df = new SimpleDateFormat("yyyy");
		for (int i = 0; i < text.length(); i++) {
			int charValue = (int) text.charAt(i);

			if( charValue > 47 && charValue < 58 ) {
				result = result * 10 + charValue - 48;
				if(i +1 == text.length()) {
					try {
						return LocalDate.ofYearDay(result, 1);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			else if(result > 0) {
				LocalDate localDate = null;
				try {
					localDate = LocalDate.ofYearDay(result, 1);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return localDate;
			}
			else
				return null;
					
		}
		return null;
	}
}
