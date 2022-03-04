package desktopApp.utilities.arrayOperations;

import java.util.ArrayList;
import java.util.List;

public class ArrayCopy {
	
	public <T> List<T>  deepCopy(List<T> list){
		List<T> result = new ArrayList<>();
		for (T t : list) {
			result.add(t);
		}
		return result;
	}
}
