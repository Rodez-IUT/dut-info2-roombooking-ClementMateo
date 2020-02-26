package xmlws.roombooking.xmltools;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import xmlws.roombooking.xmltools.samples.RoomBookingBasicSaxParser;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;

public class RoomBookingSaxParser implements RoomBookingParser {

    /**
     * Parse an xml file provided as an input stream
     * @param inputStream the input stream corresponding to the xml file
     * @return
     */
    public RoomBooking parse(InputStream inputStream) {

        RoomBooking roomBooking = new RoomBooking();

        try {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            spf.setNamespaceAware(true);
            SAXParser saxParser = spf.newSAXParser();
            saxParser.parse(inputStream, new RoomBookingHandler());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return roomBooking;
    }

    private class RoomBookingHandler extends DefaultHandler {

        public void startElement(String namespaceURI,
                                 String localName,
                                 String qName,
                                 Attributes atts,
                                 RoomBooking roomBooking)
                throws SAXException {
            if(localName.equals("label")){
                roomBooking.setRoomLabel(qName);
            }
            if(localName.equals("username")){
                roomBooking.setUsername(qName);
            }
            if(localName.equals("username")){
                roomBooking.setStartDate(qName);
                roomBooking.setEndDate(qName);
            }
        }

        public void characters(char[] ch, int start, int length)
                throws SAXException {
            System.out.println(new String(ch, start, length));
        }
    }
}
