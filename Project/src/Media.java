import java.io.InputStream;
import java.nio.file.Path;

public interface Media {
	
	
	public void DisplayMedia();
	
	public void printMediaInfo();
	
	public void resizeMedia(InputStream input, Path target, int width, int height);

}
