package desktopApp.utilities.textOperations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.core.utilities.result.ErrorDataResult;
import akdmEtkinlikEnvanter.core.utilities.result.SuccessDataResult;
import desktopApp.utilities.dateOperations.TextToDateConverter;

public class StringSpliter {

	public DataResult<List<String>> stringSplitForNumber0(String text) {
		if(text == null)
			return new ErrorDataResult<List<String>>(null, "Text Null Olamaz.");

		text = text.trim();
		String piece = "";//parça
		
		int character = 49;//1 ASCII
		List<String> result = new ArrayList<>();
		for (int i = 0; i < text.length() - 1; i++) {
			if(text.charAt(i) == (char) character && text.charAt(i+1) == '.') {
				
				if(text.length() < 3) {//1. dedikten sonra veri yoksa hatalıdır.
					return new ErrorDataResult<List<String>>(null, "Veri hatalı.");					
				}
				character++;
				i++;
				if(!piece.equals("")) {
					result.add(piece);
					piece = "";
				}
			}
			else if(i+2 < text.length()){
				piece += text.charAt(i);
			}
			else {//sondan bir önceki karaktere ulaşıldıysa
				piece += text.charAt(i);
				piece += text.charAt(i+1);
				result.add(piece);
				return new SuccessDataResult<List<String>>(result,"Veri başarılı şekilde parçalandı.");
			}
		}
		return new ErrorDataResult<List<String>>(null, "Text Boş Olamaz.");
	}
	
	
	public DataResult<List<String>> stringSplitForNumber(String text) {
		if(text == null)
			return new ErrorDataResult<List<String>>(null, "Text Null Olamaz.");

		text = text.trim();
		String piece = "";//parça
		
//		int character = 49;//1 ASCII
		int number = -1;
		List<String> result = new ArrayList<>();
		for (int i = 0; i < text.length(); i++) {
			int charValue = (int)text.charAt(i);
			if(charValue > 47 && charValue < 58) {
				if(number == -1)
					number = 0;
				number = number * 10 + charValue - 48;
			}
			else if(text.charAt(i) == '.' && i+1 < text.length() ) {
				number = -1;
				if(!piece.equals("")) {
					result.add(piece);
					piece = "";
				}
			}
			else if(text.charAt(i) == '\n') {
				if(number != -1) {
					piece += number;
					result.add(piece);
					piece = "";
				}
				else
					piece += " ";
			}
			else{
				if(number != -1) {
					piece += number;
					number = -1;
				}
				piece += text.charAt(i);
			}
			if(i+1 == text.length()) {
				if(number != -1)
					piece += number;
				result.add(piece);
				return new SuccessDataResult<List<String>>(result,"Veri başarılı şekilde parçalandı.");
				
			}
//			System.out.println("number:"+number+"     pice:"+piece);
		}
		return new ErrorDataResult<List<String>>(null, "Text Boş Olamaz.");
	}
	
	
	public DataResult<HashMap<String,String>> stringSplitForExample(String text){
		if(text == null)
			return new ErrorDataResult<HashMap<String,String>>(null, "Text Null Olamaz.");

		text = text.trim();
		String number = "";
		int counter = 0;
		TextToDateConverter textToDateConverter = new TextToDateConverter();
		
		int zero = 48;//0 ASCII
		HashMap<String,String> result = new HashMap<>();
		String year = "";
		String point = "";
		for (int i = 0; i < text.length(); i++) {
			
			int charValue = (int) text.charAt(i);
			if( charValue > (zero -1) && charValue < (zero + 10) || charValue == (int) '.' ) 
				number += text.charAt(i);
			else if(charValue == '\n') {
				if(!number.isEmpty()) {
					point = number;
					number = "";
				}
				if(!year.isEmpty() && !point.isEmpty()) {
					result.put(year, point);
					point = "";
					year = "" + counter++;
				}
			}
			else if(charValue == (int) '(') {
				if(!number.isEmpty()) {
					point = number;
					number = "";
					
				}
			}
			else if(charValue == (int) ')') {
				if(!number.isEmpty()) {
					year = number;
					number = "";
				}
				else
					year = "0";
				if(!point.isEmpty()) {
					result.put(year, point);
					point = "";
					year = "" + counter++;
				}
					
			}
			if((i + 1) == text.length()) {//textin sonu
				if(!number.isEmpty()) {
					point = number;
					number = "";
				}
				if(!year.isEmpty() && !point.isEmpty()) {
					result.put(year, point);
				}
			}
			
		}
		return new SuccessDataResult<HashMap<String,String>>(result, "Parçalama Başarılı.");
	}
	
	public DataResult<String[]> spliteName(String text) {
		if(text == null)
			return new ErrorDataResult<String[]>(null, "Text Null Olamaz.");
			
		String[] result = {"",""};
		String[] arr = text.split(" ");
		if(arr.length == 1) {
			result[0] = arr[0];
			return new SuccessDataResult<String[]>(result, "Metin Ayrıldı.");
		}
		for (int i = 0; i < arr.length; i++) {
			if(i+1 == arr.length)
				result[1] = arr[i];
			else
				result[0] += arr[i]+" ";
		}
		return new SuccessDataResult<String[]>(result, "Metin Ayrıldı.");
		
	}
}
