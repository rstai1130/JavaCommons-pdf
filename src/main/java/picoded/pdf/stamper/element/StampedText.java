package picoded.pdf.stamper.element;

import java.util.Map;

import com.lowagie.text.Element;
import com.lowagie.text.Phrase;
import com.lowagie.text.FontFactory;

import com.lowagie.text.pdf.PdfContentByte;

import picoded.core.conv.GenericConvert;

import com.lowagie.text.pdf.ColumnText;

public class StampedText extends StampedElement{
	public String fontAlias = "times-roman";
	public float textSize = 10;
	public int maxChars = -1;
	public String capitalize = ""; // value can be "first", "all", ""
	private float rot = 0;
	
	public StampedText(String inKey, int inPage, float inXPos, float inYPos, float inRot, 
						float inTextSize, int inMaxChars, String inFontAlias, String inCapitalize){
		super("text", inKey, inPage, inXPos, inYPos);
		
		rot = inRot;
		textSize = inTextSize;
		maxChars = inMaxChars;
		
		if(inFontAlias != null && !inFontAlias.isEmpty()){
			fontAlias = inFontAlias;
		}
		capitalize = inCapitalize;
	}
	public void stampOnCanvas(PdfContentByte canvas, Map<String, Object> templateData){
		if(!templateData.containsKey(key())){
			return;
		}
		
		String val = GenericConvert.toString(templateData.get(key()));
		if(maxChars > -1 && val.length() > maxChars){
			val = val.substring(0, maxChars);
		}

		if (capitalize.equals("first") && val != null && !val.isEmpty()) {
			val = val.substring(0, 1).toUpperCase() + val.substring(1);
		} else if (capitalize.equals("all")) {
			val = val.toUpperCase();
		}
		
		Phrase iTextPhrase = new Phrase(val, FontFactory.getFont(fontAlias, "UTF-8", true, textSize));
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, iTextPhrase, xPos(), yPos(), rot());
	}
	
	public float rot(){ return rot; }
	public float textSize(){ return textSize; }
	public int maxChars(){ return maxChars; }
	public String capitalize(){ return capitalize; }
}